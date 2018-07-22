package com.talniv.game.GameObjects;

import com.talniv.game.map.Map;

public abstract class Character extends MapObject {

    public static final float DEFAULT_SPEED = 5.0f;

    protected float xSpeed, ySpeed;
    private boolean freeze;
    // protected boolean moved = false;
    // protected AutomaticMovement automaticMovement = null;
    protected int curDirection; // direction chosen


    public Character(Map map, float x, float y, int width, int height) {
        super(map, x, y, width, height);
        xSpeed = DEFAULT_SPEED;
        ySpeed = DEFAULT_SPEED;
        freeze = false;
        curDirection = DOWN;
    }

    /**
     * return true if it was attempt to move. trying to move into collision object count as attempt to move.
     * if dx=dy=0 or character freeze it's count as there was no attempt to move.
     */
    public boolean move(float dx, float dy){
        // moved = false;
        if (freeze || (dx==0 && dy==0)){
            return false;
        }
        if (!checkBounds(dx, dy) && !map.checkCollision(this, dx, dy)) {
            x += dx;
            y += dy;
        }

        if (dx < 0)
            curDirection = LEFT;
        else if (dx > 0)
            curDirection = RIGHT;
        else if (dy > 0)
            curDirection = UP;
        else if (dy < 0)
            curDirection = DOWN;

        return true;
    }

    private boolean checkBounds(float dx, float dy){
        if (dx > 0){
            if (getCollisionBox(dx, 0).getBounds().x + getCollisionBox().getBounds().width >= map.getMapPixelWidth()){
                return true;
            }
        }
        if (dx < 0){
            if (getCollisionBox(dx, 0).getBounds().x <= 0){
                return true;
            }
        }
        if (dy > 0){
            if (getCollisionBox(0, dy).getBounds().y + getCollisionBox(0, dy).getBounds().height >= map.getMapPixelHeight()){
                return true;
            }
        }
        if (dy < 0){
            if (getCollisionBox(0, dy).getBounds().y <= 0){
                return true;
            }
        }
        return false;
    }


    // GETTERS AND SETTERS:

    public float getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

//    public void addAutomaticMovement(int stepsEachMove, int timePerMove){
//        automaticMovement = new AutomaticMovement(this, stepsEachMove, timePerMove, xSpeed, ySpeed);
//    }

    public int getCurDirection() {
        return curDirection;
    }

    public void setCurDirection(int curDirection) {
        this.curDirection = curDirection;
    }
}
