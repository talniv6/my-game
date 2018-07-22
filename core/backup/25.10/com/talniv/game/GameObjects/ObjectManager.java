package com.talniv.game.GameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.talniv.game.Player;

import java.util.ArrayList;


public class ObjectManager {
    private ArrayList<MapObject> objects;

    public Player player;

    public ObjectManager(Player player) {
        objects = new ArrayList<MapObject>();
        objects.add(player);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void tick(float dt){
        for (MapObject object : objects){
            object.tick(dt);
        }
    }

    public void render(Batch batch){
        for (MapObject object : objects){
            object.render(batch);
        }
    }

    public ArrayList<MapObject> getObjects() {
        return objects;
    }

    public void addObject(MapObject object){
        objects.add(object);
    }
}
