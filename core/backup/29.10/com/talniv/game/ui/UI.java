package com.talniv.game.ui;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.talniv.game.*;

public class UI implements Disposable{
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private TextRenderer textRenderer;

    public UI(){
        batch = new SpriteBatch();

        camera = new OrthographicCamera(Game.V_WIDTH, Game.V_HEIGHT);
        camera.position.x = Game.V_WIDTH/2;
        camera.position.y = Game.V_HEIGHT/2;

        textRenderer = new TextRenderer();
    }

    public void tick(float dt){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        textRenderer.tick(dt);
    }

    public void render(){
        batch.begin();
        textRenderer.render(batch);
        batch.end();
    }

    public void showText(Text text){
        textRenderer.start(text);
    }

    public boolean isActive(){
        return textRenderer.isActive();
    }

    @Override
    public void dispose() {
        batch.dispose();
        textRenderer.dispose();
    }
}
