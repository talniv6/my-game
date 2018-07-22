package com.talniv.game.map;

import com.badlogic.gdx.maps.MapLayer;;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.talniv.game.GameObjects.ObjectManager;


public class MapRenderer extends OrthogonalTiledMapRenderer{

    private ObjectManager objectManager;

    public MapRenderer(TiledMap map, ObjectManager objectManager) {
        super(map);
        this.objectManager = objectManager;
    }

    @Override
    public void renderObjects(MapLayer layer) {
        if (layer.getName().equals("characters")) {
            // render all the characters
            objectManager.render(getBatch());
        }
    }
}
