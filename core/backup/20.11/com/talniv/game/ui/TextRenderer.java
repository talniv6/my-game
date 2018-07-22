package com.talniv.game.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.talniv.game.Assets;
import com.talniv.game.Game;
import com.talniv.game.utils.InputTransformer;

public class TextRenderer extends UiElement implements Disposable{
    private final Vector2 BACKGROUND_DIMENSIONS = new Vector2(Game.V_WIDTH, Game.V_HEIGHT/4);
    private final Vector2 TEXT_POSITION = new Vector2(50, BACKGROUND_DIMENSIONS.y/2);
    private final Rectangle QuestionBoxPosition = new Rectangle(Game.V_WIDTH-100, BACKGROUND_DIMENSIONS.y, 100, 100);
    private final Vector2 ARROW_POSITION = new Vector2(BACKGROUND_DIMENSIONS.x-50, 10);

    private TextGraph textGraph;

    private boolean active;
    private BitmapFont font;
    private boolean justStarted;
    private Animation<TextureRegion> arrow;
    private float arrowTimer;
    private QuestionRenderer questionRenderer;

    public TextRenderer(){
        active = false;

        font = new BitmapFont();

        TextureRegion[][] arrow = new TextureRegion(new Texture("general/textarrow.png")).split(15,15);
        this.arrow = new Animation<TextureRegion>(0.2f, arrow[0]);
    }

    public void tick(float dt){
        if (!active){
            return;
        }
        if (justStarted){
            return;
        }
        if (Game.inputManager.ENTER){
            textGraph.next();
            while (!textGraph.isFinished() && textGraph.getCurNode().text.equals("")){
                textGraph.next();
            }
            Game.inputManager.setProtectKeys(false);
            arrowTimer = 0;
        }
        if (textGraph.isQuestion()){
            questionRenderer.tick();
        }
        if (textGraph.isFinished()){
            active = false;
            arrowTimer = 0;
        }
        arrowTimer += dt;
    }

    public void render(Batch batch){
        if (!active){
            return;
        }
        if (justStarted){
            justStarted = false;
            return;
        }
        batch.draw(Assets.blackBackground, 0, 0, BACKGROUND_DIMENSIONS.x, BACKGROUND_DIMENSIONS.y);
        font.draw(batch, textGraph.getCurNode().text, TEXT_POSITION.x, TEXT_POSITION.y);

        if (!textGraph.last() && !textGraph.isQuestion()){
            batch.draw(arrow.getKeyFrame(arrowTimer, true), ARROW_POSITION.x, ARROW_POSITION.y);
        }

        if (textGraph.isQuestion()){
            questionRenderer.render(batch);
        }
    }

    public void start(TextGraph textGraph){
        if (active){
            return;
        }
        active = true;
        this.textGraph = textGraph;
        this.textGraph.reset();
        justStarted = true;
        questionRenderer = new QuestionRenderer(this.textGraph, QuestionBoxPosition);
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void dispose() {
        font.dispose();
    }

}

class QuestionRenderer {

    private TextGraph textGraph;
    private Rectangle position;
    private float entityHeight, numOfEntities;
    private BitmapFont font = new BitmapFont();


    public QuestionRenderer(TextGraph textGraph, Rectangle position) {
        this.textGraph = textGraph;
        this.position = position;
    }


    public void tick(){
        Game.inputManager.setProtectKeys(true);
        if (Game.inputManager.UP){
            textGraph.getCurNode().outgoings.prev();
        }
        if (Game.inputManager.DOWN){
            textGraph.getCurNode().outgoings.next();
        }
        if (Game.inputManager.ENTER) {
            float pointerX = InputTransformer.getX(Gdx.input.getX());
            float pointerY = InputTransformer.getY(Gdx.input.getY());

            if (position.contains(pointerX,pointerY)){
                // calculate selected option
            }
        }
        numOfEntities = textGraph.getCurNode().outgoings.size();
        entityHeight = position.height/numOfEntities;
    }

    public void render(Batch batch){
        batch.draw(Assets.blackBackground, position.x, position.y, position.width, position.height);
        for (int i = 0; i < numOfEntities; i++) {
            TextGraph.Edge edge = textGraph.getCurNode().outgoings.get((int)numOfEntities-1-i);
            if (textGraph.getCurNode().outgoings.getCurrentIndex() == (int)numOfEntities-1-i){
                batch.draw(Assets.whiteBackground, position.x, position.y+i*entityHeight, position.width, entityHeight);
                font.setColor(0,0,0,1);
                font.draw(batch, edge.text, position.x+5, position.y+(i+1)*entityHeight-entityHeight/4);
                font.setColor(1,1,1,1);
            }
            else {
                font.draw(batch, edge.text, position.x+5, position.y+(i+1)*entityHeight-entityHeight/4);
            }
        }
    }

}
