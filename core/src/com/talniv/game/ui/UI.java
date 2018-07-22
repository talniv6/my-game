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
    private MenuRenderer menuRenderer;
    private ItemsRenderer itemsRenderer;

    public UI(){
        batch = new SpriteBatch();

        camera = new OrthographicCamera(Game.V_WIDTH, Game.V_HEIGHT);
        camera.position.x = Game.V_WIDTH/2;
        camera.position.y = Game.V_HEIGHT/2;

        menuRenderer = new MenuRenderer();
        itemsRenderer = new ItemsRenderer();
    }

    public void tick(float dt){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        if (!itemsRenderer.isActive()) {
            menuRenderer.tick(dt);
        }
        itemsRenderer.tick(dt);
        Game.Dj.tick(dt);
    }

    public void render(){
        batch.begin();
        if (!itemsRenderer.isActive()) {
            menuRenderer.render(batch);
        }
        itemsRenderer.render(batch);
        Game.Dj.render(batch);
        batch.end();
    }

    public void showItems(){
        itemsRenderer.start();
    }

    public boolean isActive(){
        return menuRenderer.isActive() || itemsRenderer.isActive();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

