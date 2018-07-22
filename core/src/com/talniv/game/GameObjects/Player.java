package com.talniv.game.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.Game;
import com.talniv.game.UnsupportedShapeTypeException;
import com.talniv.game.map.Box;
import com.talniv.game.map.Map;


public class Player extends Character{

    public static Array<Item> items = new Array<Item>();
    public PersonTexture defTexture, ghostTexture;

    private float timer;
    private PersonTexture curTexture;
    private float dx, dy;
    private boolean moved;

    public Player(Map map) {
        super(map, 700, 700, 32, 32);
        this.map = map;
        defTexture = new PersonTexture("general/people/player-def-texture.txt");
        ghostTexture = new PersonTexture("general/people/player-ghost-texture.txt");

        curTexture = defTexture;
        try {
            setCollisionBox(new Box(new Rectangle(0,0,13,14)), 6, 0);
            setEventsBox(new Box(new Rectangle(0,0,24,28)), 1, -4);
        } catch (UnsupportedShapeTypeException e) {
            e.printStackTrace();
        }

        curDirection = DOWN;
    }

    public void tick(float dt){
        timer += dt;
        getInput();
        moved = move(dx*dt, dy*dt); // dx*dt, dy*dt and increase speed to 180
    }

    public void render(Batch batch){
        batch.draw(getCurrentAnimationFrame(), x, y);
    }


    private void getInput(){
        dx = 0;
        dy = 0;

        if (Game.inputManager.UP) {
            dy = ySpeed;
        }
        if (Game.inputManager.DOWN) {
            dy = -ySpeed;
        }
        if (Game.inputManager.RIGHT) {
            dx = xSpeed;
        }
        if (Game.inputManager.LEFT) {
            dx = -xSpeed;
        }
    }


    private TextureRegion getCurrentAnimationFrame(){
        if (!moved){
            timer = 0;
            switch (curDirection){
                case LEFT:
                    return curTexture.standLeft.getKeyFrame(timer);
                case RIGHT:
                    return curTexture.standRight.getKeyFrame(timer);
                case UP:
                    return curTexture.standUp.getKeyFrame(timer);
                default:
                    return curTexture.standDown.getKeyFrame(timer);
            }
        }
        switch (curDirection){
            case LEFT:
                return curTexture.animationLeft.getKeyFrame(timer, true);
            case RIGHT:
                return curTexture.animationRight.getKeyFrame(timer, true);
            case UP:
                return curTexture.animationUp.getKeyFrame(timer, true);
            default:
                return curTexture.animationDown.getKeyFrame(timer, true);
        }
    }

    public void setDirection(int direction){
        switch (direction){
            case UP:
                curDirection = UP;
                break;
            case DOWN:
                curDirection = DOWN;
                break;
            case RIGHT:
                curDirection = RIGHT;
                break;
            case LEFT:
                curDirection = LEFT;
                break;
        }
    }

    public void resetPosition(float x, float y){
        dx = 0;
        dy = 0;
        this.x = x;
        this.y = y;
    }

    public void changeTexture(PersonTexture personTexture){
        curTexture = personTexture;
    }

    public int hasItem(String itemName){
        for (int i=0; i <items.size; i++){
            if (itemName.equals(items.get(i).getName())){
                return i;
            }
        }
        return -1;
    }
}
