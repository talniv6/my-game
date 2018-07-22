package com.talniv.game.ui;


import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.talniv.game.Game;

public class TextRenderer implements Disposable{
    private Text text;

    private boolean active;
    private TextureRegion textBackground;
    private BitmapFont font;
    private boolean justStarted;
    private Animation<TextureRegion> arrow;
    private float arrowTimer;

    public TextRenderer(){
        active = false;

        Pixmap background = new Pixmap(1,1, Pixmap.Format.Alpha);
        background.setColor(0, 0, 0, 0.5f);
        background.fill();
        textBackground = new TextureRegion(new Texture(background));

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
            arrowTimer = 0;
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
        batch.draw(textBackground, 0, 0, Game.V_WIDTH, Game.V_HEIGHT/4);
        font.draw(batch, text.currentText(), 50, 50);

        if (!text.isLastLine()){
            batch.draw(arrow.getKeyFrame(arrowTimer, true), Game.V_WIDTH-50, 10);
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
