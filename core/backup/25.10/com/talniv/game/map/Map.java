package com.talniv.game.map;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.ObjectManager;
import com.talniv.game.Player;
import com.talniv.game.UnsupportedShapeTypeException;

import java.util.ArrayList;

public class Map {

    private OrthographicCamera camera;

    private ObjectManager objectManager;
    private ArrayList<Box> collisionObjects;
    private ArrayList<Box> events;

    private MapRenderer mapRenderer;
    private TiledMap map;

    private int mapTileWidth, mapTileHeight, tileWidth, tileHeight, mapPixelWidth, mapPixelHeight;


    public Map(String path){
        camera = new OrthographicCamera(Game.V_WIDTH, Game.V_HEIGHT);
        objectManager = new ObjectManager(new Player(this));

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load(path);
        mapRenderer = new MapRenderer(map, objectManager);

        MapProperties mapProperties = map.getProperties();
        mapTileWidth = mapProperties.get("width", Integer.class);
        mapTileHeight = mapProperties.get("height", Integer.class);
        tileWidth = mapProperties.get("tilewidth", Integer.class);
        tileHeight = mapProperties.get("tileheight", Integer.class);

        mapPixelWidth = mapTileWidth * tileWidth;
        mapPixelHeight = mapTileHeight * tileHeight;

        collisionObjects = new ArrayList<Box>();
        events = new ArrayList<Box>();
        createBoxesFromObjectLayer(map.getLayers().get("collisions"), collisionObjects);
        createBoxesFromObjectLayer(map.getLayers().get("events"), events);

    }

    private void createBoxesFromObjectLayer(MapLayer layer, ArrayList<Box> container){
        MapObjects collisionObjects = layer.getObjects();
        for (MapObject object : collisionObjects){
            try{
                if (object instanceof RectangleMapObject){
                    container.add(new Box(((RectangleMapObject)object).getRectangle()));
                }
                else if (object instanceof PolygonMapObject){
                    container.add(new Box(((PolygonMapObject)object).getPolygon()));
                }
                else if (object instanceof EllipseMapObject){
                    container.add(new Box(((EllipseMapObject)object).getEllipse()));
                }
            } catch (UnsupportedShapeTypeException e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void tick(float dt){
        objectManager.tick(dt);

        camera.position.set(objectManager.getPlayer().x, objectManager.getPlayer().y, 0);
        camera.update();
        mapRenderer.setView(camera);
    }

    public void render(){
        mapRenderer.render();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public boolean checkCollision(com.talniv.game.GameObjects.MapObject object, float dx, float dy){
        for (Box b : collisionObjects){
            if(b.intersect(object.getCollisionBox(dx,dy))){
                return true;
            }
        }
        for (com.talniv.game.GameObjects.MapObject o : objectManager.getObjects()){
            if (o.equals(object))
                continue;
            if (o.getCollisionBox().intersect(object.getCollisionBox(dx,dy))){
                return true;
            }
        }

        //        MapLayers layers = map.getLayers();
//        for (MapLayer layer : layers){
//            if (layer.isVisible()) {
//                if (layer instanceof TiledMapTileLayer) {
//                    TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
//                    TiledMapTileLayer.Cell cell = tileLayer.getCell((int)(sprite.getX() / tileLayer.getTileWidth()),
//                            (int)(sprite.getY() / tileLayer.getTileHeight()));
//                    if (cell != null && cell.getTile().getProperties().containsKey("blocked")) {
//                        // System.out.println("yaaaaaaaaaaa");
//                    }
//                }
//            }
//        }
        return false;
    }

    public void addObject(com.talniv.game.GameObjects.MapObject object){
        objectManager.addObject(object);
    }





    // GETTERS:

    public Player getPlayer(){
        return objectManager.getPlayer();
    }

    public int getMapTileWidth() {
        return mapTileWidth;
    }

    public int getMapTileHeight() {
        return mapTileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getMapPixelWidth() {
        return mapPixelWidth;
    }

    public int getMapPixelHeight() {
        return mapPixelHeight;
    }

    public ArrayList<com.talniv.game.GameObjects.MapObject> getObjects() {
        return objectManager.getObjects();
    }

    public ArrayList<Box> getCollisionObjects() {
        return collisionObjects;
    }

    public ArrayList<Box> getEvents() {
        return events;
    }

    public Box getEventByName(String name){
        MapObject object = map.getLayers().get("events").getObjects().get(name);
        try{
            if (object instanceof RectangleMapObject){
                return (new Box(((RectangleMapObject)object).getRectangle()));
            }
            else if (object instanceof PolygonMapObject){
                return (new Box(((PolygonMapObject)object).getPolygon()));
            }
            else if (object instanceof EllipseMapObject){
                return (new Box(((EllipseMapObject)object).getEllipse()));
            }
        } catch (UnsupportedShapeTypeException e){
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
