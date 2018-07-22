package com.talniv.game.map;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.ObjectManager;
import com.talniv.game.GameObjects.Person;
import com.talniv.game.GameObjects.Player;
import com.talniv.game.UnsupportedShapeTypeException;
import com.talniv.game.utils.PersonLoader;

import java.util.ArrayList;

public class Map implements Disposable{

    private OrthographicCamera camera;

    private ObjectManager objectManager;
    private ArrayList<Box> collisionObjects;
    private ArrayList<Box> events;

    private MapRenderer mapRenderer;
    private TiledMap map;

    private float mapTileWidth, mapTileHeight, tileWidth, tileHeight, mapPixelWidth, mapPixelHeight;

    private DebugMapRenderer debugMapRenderer;


    public Map(String path){
        camera = new OrthographicCamera(Game.V_WIDTH, Game.V_HEIGHT);
        objectManager = new ObjectManager(new Player(this));

//        TmxMapLoader mapLoader = new TmxMapLoader();
//        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
//        params.generateMipMaps = true;
//        params.textureMinFilter = Texture.TextureFilter.MipMapNearestNearest;
//        params.textureMagFilter = Texture.TextureFilter.MipMapNearestNearest;
//        map = mapLoader.load(path, params);

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

        debugMapRenderer = new DebugMapRenderer(this);

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

    private void updateCamera(){
        camera.position.set(objectManager.getPlayer().x, objectManager.getPlayer().y, 0);
        if (camera.position.x < Game.V_WIDTH/2)
            camera.position.x = Game.V_WIDTH/2;
        if (camera.position.x > mapPixelWidth - Game.V_WIDTH/2)
            camera.position.x =  mapPixelWidth - Game.V_WIDTH/2;
        if (camera.position.y < Game.V_HEIGHT/2)
            camera.position.y = Game.V_HEIGHT/2;
        if (camera.position.y > mapPixelHeight - Game.V_HEIGHT/2)
            camera.position.y =  mapPixelHeight - Game.V_HEIGHT/2;

        if (mapPixelWidth < Game.V_WIDTH){
            camera.position.x = mapPixelWidth/2 + 0.005f;
        }
        if (mapPixelHeight < Game.V_HEIGHT){
            camera.position.y = mapPixelHeight/2 + 0.005f;
        }

//        camera.position.x = camera.position.x + 0.05f;
//        camera.position.y = camera.position.y + 0.05f;
        camera.update();
    }

    public void tick(float dt){
        objectManager.tick(dt);
        updateCamera();
        mapRenderer.setView(camera);
    }

    public void render(){
        mapRenderer.render();

        if (Game.inputManager.DEBUG)
            debugMapRenderer.render();
    }

    public boolean checkCollision(com.talniv.game.GameObjects.MapObject object, float dx, float dy){
        if (!object.isSolid()){
            return false;
        }
        for (Box b : collisionObjects){
            if(b.intersect(object.getCollisionBox(dx,dy))){
                return true;
            }
        }
        for (com.talniv.game.GameObjects.MapObject o : objectManager.getObjects()){
            if (o.equals(object))
                continue;
            if (o.isSolid() && o.getCollisionBox().intersect(object.getCollisionBox(dx,dy))){
                return true;
            }
        }
        return false;
    }

    public void addObject(com.talniv.game.GameObjects.MapObject object){
        objectManager.addObject(object);
    }

    public Person addPerson(String path, float spawnX, float spawnY){
        PersonLoader personLoader = new PersonLoader();
        Person person = personLoader.loadPerson(this, path, spawnX, spawnY);
        addObject(person);
        return person;
    }

    public Person addPerson(String path, Box spawn){
        return addPerson(path, spawn.getX(),spawn.getY());
    }

    @Override
    public void dispose() {
        map.dispose();
        debugMapRenderer.dispose();
    }




    // GETTERS:


    public TiledMap getMap() {
        return map;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Player getPlayer(){
        return objectManager.getPlayer();
    }

    public float getMapTileWidth() {
        return mapTileWidth;
    }

    public float getMapTileHeight() {
        return mapTileHeight;
    }

    public float getTileWidth() {
        return tileWidth;
    }

    public float getTileHeight() {
        return tileHeight;
    }

    public float getMapPixelWidth() {
        return mapPixelWidth;
    }

    public float getMapPixelHeight() {
        return mapPixelHeight;
    }

    public Array<com.talniv.game.GameObjects.MapObject> getObjects() {
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

    public Box getObjectByName(String name){
        MapLayers layers = map.getLayers();
        for (MapLayer layer : layers){
            MapObject object = layer.getObjects().get(name);
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
        }
        return null;
    }

    public Array<Box> getAllEventsByName(String name){
        Array<Box> events = new Array<Box>();
        MapObjects objects = map.getLayers().get("events").getObjects();
        for (MapObject object : objects){
            if (object.getName().equals(name)) {
                try {
                    if (object instanceof RectangleMapObject) {
                        events.add (new Box(((RectangleMapObject) object).getRectangle()));
                    } else if (object instanceof PolygonMapObject) {
                        events.add (new Box(((PolygonMapObject) object).getPolygon()));
                    } else if (object instanceof EllipseMapObject) {
                        events.add (new Box(((EllipseMapObject) object).getEllipse()));
                    }
                } catch (UnsupportedShapeTypeException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
        return events;
    }

}
