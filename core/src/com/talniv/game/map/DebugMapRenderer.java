package com.talniv.game.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.map.Box;
import com.talniv.game.map.Map;


public class DebugMapRenderer implements Disposable{
    private Map map;
    private ShapeRenderer shapeRenderer;
    private long timer;

    public DebugMapRenderer(Map map) {
        this.map = map;
        shapeRenderer = new ShapeRenderer();
        timer = System.currentTimeMillis();
    }

    private void renderBox(Box box){
        if (box.getType() == Box.ShapeType.RECTANGLE){
            Rectangle shape = (Rectangle)box.getShape();
            shapeRenderer.rect(shape.x, shape.y, shape.width, shape.height);
        }
        else if (box.getType() == Box.ShapeType.POLYGON){
            Polygon shape = (Polygon)box.getShape();
            shapeRenderer.polygon(shape.getTransformedVertices());
        }
        else if (box.getType() == Box.ShapeType.ELLIPSE){
            Ellipse shape = (Ellipse)box.getShape();
            shapeRenderer.ellipse(shape.x, shape.y, shape.width, shape.height);
        }
    }

    public void render(){
        shapeRenderer.setProjectionMatrix(map.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        for (MapObject object : map.getObjects()){
            renderBox(object.getEventsBox());
        }
        for (Box box : map.getEvents()){
            renderBox(box);
        }
        shapeRenderer.setColor(Color.RED);
        for (MapObject object : map.getObjects()){
            renderBox(object.getCollisionBox());
        }
        for (Box box : map.getCollisionObjects()){
            renderBox(box);
        }
        shapeRenderer.end();

        if (System.currentTimeMillis() - timer >= 2000){
            System.out.println(map.getPlayer().x + "," + map.getPlayer().y);
            timer = System.currentTimeMillis();
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
