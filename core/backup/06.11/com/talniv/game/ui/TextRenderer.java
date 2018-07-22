package com.talniv.game.ui;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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

public class TextRenderer extends UiElement implements Disposable{
    private final Vector2 BACKGROUND_DIMENSIONS = new Vector2(Game.V_WIDTH, Game.V_HEIGHT/4);
    private final Vector2 TEXT_POSITION = new Vector2(50, BACKGROUND_DIMENSIONS.y/2);
    private final float SELECTION_BOX_WIDTH = BACKGROUND_DIMENSIONS.x/5;
    private final Rectangle selectionBox = new Rectangle(Game.V_WIDTH-100, BACKGROUND_DIMENSIONS.y, 100, 100);
    private final Vector2 ARROW_POSITION = new Vector2(BACKGROUND_DIMENSIONS.x-50, 10);

    private Text text;

    private boolean active;
    private BitmapFont font;
    private boolean justStarted, selectionBoxCreated=false;
    private Animation<TextureRegion> arrow;
    private float arrowTimer;
    private SelectionBoxRenderer selectionBoxRenderer;

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
            justStarted = false;
            return;
        }
        if (Game.inputManager.ENTER){
            text.next();
            Game.inputManager.setProtectKeys(false);
            arrowTimer = 0;
        }
        if (text.isQuestion()){
            if (!selectionBoxCreated) {
                selectionBoxRenderer = new SelectionBoxRenderer(text.getSelectionBox(), selectionBox);
                selectionBoxCreated = true;
            }
            selectionBoxRenderer.tick();
        }
        else {
            selectionBoxCreated = false;
        }
        if (text.isFinished()){
            active = false;
            arrowTimer = 0;
        }
        arrowTimer += dt;
    }

    public void render(Batch batch){
        if (!active){
            return;
        }
        batch.draw(Assets.blackBackground, 0, 0, BACKGROUND_DIMENSIONS.x, BACKGROUND_DIMENSIONS.y);
        font.draw(batch, text.currentText(), TEXT_POSITION.x, TEXT_POSITION.y);

        if (!text.isLastLine() && !text.isQuestion()){
            batch.draw(arrow.getKeyFrame(arrowTimer, true), ARROW_POSITION.x, ARROW_POSITION.y);
        }

        if (text.isQuestion()){
            selectionBoxRenderer.render(batch);
        }
    }

    public void start(Text text){
        if (active){
            return;
        }
        active = true;
        this.text = text;
        this.text.reset();
        justStarted = true;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void dispose() {
        font.dispose();
    }


}
