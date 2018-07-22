package com.talniv.game.GameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.talniv.game.Game;
import com.talniv.game.map.Map;


public class Person extends Character {
    // private ShowText text = null;
    private Animation<TextureRegion> walkDown, walkUp, walkRight, walkLeft;
    private TextureRegion standDown, standUp, standRight, standLeft;
    private boolean showText = false, textShown = false, moved;
    private float timer, dx, dy;

    public Person(Map map, float x, float y, int width, int height, Animation<TextureRegion> walkDown, Animation<TextureRegion> walkUp, Animation<TextureRegion> walkRight, Animation<TextureRegion> walkLeft,
                  TextureRegion standDown, TextureRegion standUp, TextureRegion standRight, TextureRegion standLeft) {
        super(map, x, y, width, height);
        this.walkDown = walkDown;
        this.walkUp = walkUp;
        this.walkRight = walkRight;
        this.walkLeft = walkLeft;
        this.standDown = standDown;
        this.standUp = standUp;
        this.standRight = standRight;
        this.standLeft = standLeft;
    }

    private void chooseDirection(){
        dx = 0;
        dy = 0;
        // choose dx dy auto if provided
    }

    public void tick(float dt) {
        chooseDirection();
        moved = move(dx, dy);
        timer += dt;

        if (Game.inputManager.ENTER){
            if (getEventsBox().intersect(map.getPlayer().getEventsBox()))
            {
                // System.out.println("ddddddd");
                if (isPlayerFacing()){
                    // System.out.println(orientation(map.getPlayer()));
                    curDirection = orientation(map.getPlayer());
                }
            }
        }

    }


    public void render(Batch batch) {
        batch.draw(getCurrentFrame(), x, y, getWidth(), getHeight());
    }


    private TextureRegion getCurrentFrame() {
        if (!moved){
            timer = 0;
            switch (curDirection){
                case LEFT:
                    return standLeft;
                case RIGHT:
                    return standRight;
                case UP:
                    return standUp;
                default:
                    return standDown;
            }
        }
        switch (curDirection){
            case LEFT:
                return walkLeft.getKeyFrame(timer, true);
            case RIGHT:
                return walkRight.getKeyFrame(timer, true);
            case UP:
                return walkUp.getKeyFrame(timer, true);
            default:
                return walkDown.getKeyFrame(timer, true);
        }
    }

//    public void addText(ArrayList<String> text){
//        this.text = new ShowText(map, text);
//    }
}

