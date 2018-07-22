package com.talniv.game.Scenes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.talniv.game.map.Map;


public abstract class Scene implements Disposable{

    public Map map;

    public Scene(String path){
        map = new Map(path);
    }


    // TICK AND RENDER:

    public abstract void tick(float dt);

    public abstract void render(Batch batch);



    // SCENE MANAGEMENT:

//    public void changeScene(Scene scene, int spawnX, int spawnY, int direction, Actions actions){
//        Luncher.game.changeScene(scene, spawnX, spawnY, direction, actions);
//    }


    public Map getMap() {
        return map;
    }

    public void dispose(){
        map.dispose();
    }
}
