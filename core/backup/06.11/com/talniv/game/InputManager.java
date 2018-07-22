package com.talniv.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.input.GestureDetector;



public class InputManager extends GestureDetector.GestureAdapter{

    private final int up=1, down=2, right=3, left=4;

    public boolean UP, DOWN, RIGHT, LEFT, ENTER, DEBUG;
    private boolean  wait, panUp=false, panRight=false, panDown=false, panLeft=false, tap=false, protectKeys=false;
    private long timeStart, timeToWait;
    private boolean lockUp=false,lockDown=false,lockRight=false,lockLeft=false;
    private int prevInput;

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

        if (protectKeys && lockUp) {
            UP = false;
            lockUp = Gdx.input.isKeyPressed(Input.Keys.UP) || panUp;
        }
        else {
            UP = Gdx.input.isKeyPressed(Input.Keys.UP) || panUp;
            lockUp = UP;
        }

        if (protectKeys && lockDown) {
            DOWN = false;
            lockDown = Gdx.input.isKeyPressed(Input.Keys.DOWN) || panDown;
        }
        else {
            DOWN = Gdx.input.isKeyPressed(Input.Keys.DOWN) || panDown;
            lockDown = DOWN;
        }

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
        if (Math.abs(deltaX) <= 3 && Math.abs(deltaY) <= 3){
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

    public void setProtectKeys(boolean protectKeys) {
        this.protectKeys = protectKeys;
    }


    private boolean isUP() {
        if (protectKeys && prevInput==up)
            return false;
        return  Gdx.input.isKeyPressed(Input.Keys.UP) || panUp;
    }

    private boolean isDOWN() {
        if (protectKeys && DOWN)
            return false;
        return Gdx.input.isKeyPressed(Input.Keys.DOWN) || panDown;
    }

    private boolean isRIGHT() {
        if (protectKeys && RIGHT)
            return false;
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT) || panRight;
    }

    private boolean isLEFT() {
        if (protectKeys && LEFT)
            return false;
        return Gdx.input.isKeyPressed(Input.Keys.LEFT) || panLeft;
    }
}
