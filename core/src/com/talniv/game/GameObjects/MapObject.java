package com.talniv.game.GameObjects;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.talniv.game.UnsupportedShapeTypeException;
import com.talniv.game.map.Box;
import com.talniv.game.map.Map;

public abstract class MapObject {

    public static final int DOWN = 0, LEFT = 1, RIGHT = 2, UP = 3;

    public float x, y;
    protected Map map;
    private float width, height;
    private Box collisionBox, eventsBox;
    private Vector2 collisionBoxOffset, eventBoxOffset;


    private Vector2 center;
    private boolean solid;

    public MapObject(Map map, float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.map = map;
        this.width = width;
        this.height = height;
        solid = true;

        try {
            collisionBox = new Box(new Rectangle(0, 0, width, height));
            eventsBox = new Box(new Rectangle(0, 0, width, height));
        } catch (UnsupportedShapeTypeException e){
            e.printStackTrace();
            System.exit(1);
        }

        center = new Vector2(width/2, height/2);
        collisionBoxOffset = new Vector2(0,0);
        eventBoxOffset = new Vector2(0,0);
    }

    public abstract void tick(float dt);

    public abstract void render(Batch batch);



    // BOUNDS MANAGEMENT:

    public void setCollisionBox(Box collisionBox, float dx, float dy){
        this.collisionBox = collisionBox;
        collisionBoxOffset.x = dx;
        collisionBoxOffset.y = dy;
    }

    public void setEventsBox(Box eventsBox, float dx, float dy){
        this.eventsBox = eventsBox;
        eventBoxOffset.x = dx;
        eventBoxOffset.y = dy;
    }

    public Box getCollisionBox(float xOffset, float yOffset){
        collisionBox.translate(x + collisionBoxOffset.x + xOffset, y + collisionBoxOffset.y + yOffset);
        return collisionBox;
    }

    public Box getCollisionBox(){
        collisionBox.translate(x + collisionBoxOffset.x, y + collisionBoxOffset.y);
        return collisionBox;
    }

    public Box getEventsBox(){
        eventsBox.translate(x + eventBoxOffset.x, y + eventBoxOffset.y);
        return eventsBox;
    }




    // ORIENTATION:

    public boolean isPlayerFacing(){
        Player p = map.getPlayer();
        switch (orientation(p)){
            case UP:
                return p.getCurDirection() == DOWN;
            case DOWN:
                return p.getCurDirection() == UP;
            case LEFT:
                return p.getCurDirection() == RIGHT;
            case RIGHT:
                return p.getCurDirection() == LEFT;
        }
        return false;
    }

    public int orientation(MapObject o){
        float myCenterX = getCenter().x , myCenterY = getCenter().y;
        float objectCenterX = o.getCenter().x, objectCenterY = o.getCenter().y;

        //System.out.println("player x: " + objectCenterX + " player y: " + objectCenterY);
        //System.out.println("cabin x: " + myCenterX + " cabin y: " + myCenterY);

        if (objectCenterX >= myCenterX ){
            if(objectCenterY < myCenterY - (objectCenterX-myCenterX)){
                return DOWN;
            }
            else if (objectCenterY > myCenterY + (objectCenterX-myCenterX)){
                return UP;
            }
            else {
                return RIGHT;
            }
        }

        else{
            if(objectCenterY < myCenterY - (myCenterX-objectCenterX)){
                return DOWN;
            }
            else if (objectCenterY > myCenterY + (myCenterX-objectCenterX)){
                return UP;
            }
            else {
                return LEFT;
            }
        }

    }



    // GETTERS AND SETTERS:

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Vector2 getCenter() {
        return new Vector2(x + center.x, y + center.y);
    }

    public void setCenter(float dx, float dy) {
        center.x = dx;
        center.y = dy;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

}
