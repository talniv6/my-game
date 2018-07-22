package com.talniv.game.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.GameObjects.ObjectManager;
import com.talniv.game.Player;

import java.util.ArrayList;


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
