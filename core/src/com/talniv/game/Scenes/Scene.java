package com.talniv.game.Scenes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.talniv.game.Game;
import com.talniv.game.map.Box;
import com.talniv.game.map.Map;
import com.talniv.game.utils.EventsManager;


public abstract class Scene implements Disposable{

    public Map map;
    protected EventsManager eventsManager;

    public Scene(String path){
        map = new Map(path);
        eventsManager = new EventsManager();
    }


    // TICK AND RENDER:

    public abstract void tick(float dt);

    public abstract void render(Batch batch);



    // SCENE MANAGEMENT:

    public void changeScene(Scene scene, float spawnX, float spawnY, int direction){
        Game.changeScene(scene, spawnX, spawnY, direction);
    }

    public void changeScene(Scene scene, Box position, int direction){
        Game.changeScene(scene, position, direction);
    }


    public Map getMap() {
        return map;
    }

    public void dispose(){
        // map.dispose();
    }
}
