package com.talniv.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.input.GestureDetector;



public class InputManager extends GestureDetector.GestureAdapter{

    public boolean UP, DOWN, RIGHT, LEFT, ENTER, DEBUG;
    private boolean  wait, panUp=false, panRight=false, panDown=false, panLeft=false, tap=false;
    private long timeStart, timeToWait;

    public InputManager(){
        wait = false;
    }

    public void tick(){
        DEBUG = Gdx.input.isKeyPressed(Input.Keys.D);

        if (wait) {
            if (System.currentTimeMillis() - timeStart >= timeToWait){
                wait = false;
            }
            return;
        }

        UP = Gdx.input.isKeyPressed(Input.Keys.UP) || panUp;
        DOWN = Gdx.input.isKeyPressed(Input.Keys.DOWN) || panDown;
        RIGHT = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || panRight;
        LEFT = Gdx.input.isKeyPressed(Input.Keys.LEFT) || panLeft;
        ENTER = Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || tap;

        tap = false;
    }

    public void reset(){
        UP = false;
        DOWN = false;
        RIGHT = false;
        LEFT = false;
    }

    public void wait(int amt){
        reset();

        wait = true;
        timeToWait = amt;
        timeStart = System.currentTimeMillis();
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        tap = true;
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (Math.abs(deltaX) <= 5 && Math.abs(deltaY) <= 5){
            return false;
        }
        if (Math.abs(deltaX) > Math.abs(deltaY)){ // right or left
            panUp = false;
            panDown = false;
            if (deltaX < 0){ // left
                panLeft = true;
                panRight = false;
            }
            else {
                panRight = true;
                panLeft = false;
            }
        }
        else { // up or down
            panRight = false;
            panLeft= false;
            if (deltaY < 0) { // up
                panDown = false;
                panUp = true;
            } else { // down
                panUp = false;
                panDown = true;
            }
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        panLeft = false;
        panDown = false;
        panRight = false;
        panUp = false;
        return false;
    }

}
