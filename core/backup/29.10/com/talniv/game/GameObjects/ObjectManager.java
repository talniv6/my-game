package com.talniv.game.GameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import com.talniv.game.Player;

import java.util.ArrayList;
import java.util.Comparator;


public class ObjectManager {
    private Array<MapObject> objects;
    private Sort sorter;

    public Player player;

    public ObjectManager(Player player) {
        objects = new Array<MapObject>();
        objects.add(player);
        this.player = player;
        sorter = Sort.instance();
    }

    private Comparator<MapObject> sort = new Comparator<MapObject>() {
        @Override
        public int compare(MapObject object1, MapObject object2) {
            if (object1.y > object2.y){
                return -1;
            }
            return 1;
        }
    };

    public Player getPlayer() {
        return player;
    }

    public void tick(float dt){
        for (int i = 0; i < objects.size; i++) {
            objects.get(i).tick(dt);
        }
        sorter.sort(objects, sort);
    }

    public void render(Batch batch){
        for (int i = 0; i < objects.size; i++) {
            objects.get(i).render(batch);
        }
    }

    public Array<MapObject> getObjects() {
        return objects;
    }

    public void addObject(MapObject object){
        objects.add(object);
    }
}
