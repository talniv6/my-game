package com.talniv.game.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

public class TextRenderer implements Disposable{
    private final Vector2 BACKGROUND_DIMENSIONS = new Vector2(Game.V_WIDTH, Game.V_HEIGHT/4);
    private final Vector2 TEXT_POSITION = new Vector2(50, BACKGROUND_DIMENSIONS.y/2);
    private final Rectangle QuestionBoxPosition = new Rectangle(Game.V_WIDTH-100, BACKGROUND_DIMENSIONS.y, 100, 100);
    private final Vector2 ARROW_POSITION = new Vector2(BACKGROUND_DIMENSIONS.x-50, 10);

    private TextGraph textGraph;

    private boolean active;
    private Animation<TextureRegion> arrow;
    private float arrowTimer;
    private QuestionRenderer questionRenderer;
    private OrthographicCamera camera;
    private TextPrinter textPrinter;

    public TextRenderer(){
        active = false;

        camera = new OrthographicCamera(Game.V_WIDTH, Game.V_HEIGHT);
        camera.position.x = Game.V_WIDTH/2;
        camera.position.y = Game.V_HEIGHT/2;

        TextureRegion[][] arrow = new TextureRegion(new Texture("general/textarrow.png")).split(15,15);
        this.arrow = new Animation<TextureRegion>(0.2f, arrow[0]);

        textPrinter = new TextPrinter();
    }

    public void tick(float dt){
        if (!active){
            return;
        }
        if (Game.inputManager.ENTER && !textPrinter.isPrinting()){
            textGraph.next();
            while (!textGraph.isFinished() && textGraph.getCurNode().text.equals("")){
                textGraph.next();
            }
            textPrinter.setText(textGraph.getCurNode().text);
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
        camera.update();
        textPrinter.tick();
    }

    public void render(Batch batch){
        if (!active){
            return;
        }
        batch.setProjectionMatrix(camera.combined);
        batch.draw(Assets.blackBackground, 0, 0, BACKGROUND_DIMENSIONS.x, BACKGROUND_DIMENSIONS.y);
        textPrinter.render(batch, TEXT_POSITION.x, TEXT_POSITION.y);

        if (!textGraph.last() && !textGraph.isQuestion()){
            batch.draw(arrow.getKeyFrame(arrowTimer, true), ARROW_POSITION.x, ARROW_POSITION.y);
        }

        if (textGraph.isQuestion()){
            if (!textPrinter.isPrinting()) {
                questionRenderer.render(batch);
            }
        }
    }

    public void start(TextGraph textGraph){
        if (active){
            return;
        }
        active = true;
        this.textGraph = textGraph;
        this.textGraph.reset();
        questionRenderer = new QuestionRenderer(this.textGraph, QuestionBoxPosition);
        textPrinter.setText(textGraph.getCurNode().text);
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void dispose() {

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

class TextPrinter {

    private String text, textToDraw;
    private int index;
    private BitmapFont font;
    private boolean printing;

    TextPrinter() {
        font = new BitmapFont();
        font.getData().scale(0.05f);
        printing = false;
    }

    public void tick(){
        index += 2;
        if(index >= text.length()) {
            index = text.length();
            textToDraw = text;
            printing = false;
        }
        else {
            textToDraw = text.substring(0,index);
            printing = true;
        }
    }

    public void render(Batch batch, float x, float y){
        font.draw(batch, textToDraw, x, y);
    }

    public void setText(String text){
        this.text = text;
        textToDraw = "" + text.charAt(0);
        index = 0;
    }

    public boolean isPrinting() {
        return printing;
    }
}
