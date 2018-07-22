package com.talniv.game.GameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.talniv.game.Game;
import com.talniv.game.map.Map;
import com.talniv.game.ui.TextEvent;


public class Person extends Character {
    private PersonTexture texture;
    private boolean moved;
    private float standingTimer, walkingTimer, dx, dy;

    public Person(Map map, float x, float y, int width, int height, PersonTexture texture) {
        super(map, x, y, width, height);
        this.texture = texture;
    }

    private void chooseDirection(){
        dx = 0;
        dy = 0;
        // choose dx dy auto if provided
    }

    public void tick(float dt) {
        chooseDirection();
        moved = move(dx, dy);
        standingTimer += dt;
        walkingTimer += dt;
    }


    public void render(Batch batch) {
        batch.draw(getCurrentFrame(), x, y, getWidth(), getHeight());
    }


    private TextureRegion getCurrentFrame() {
        if (!moved){
            walkingTimer = 0;
            switch (curDirection){
                case LEFT:
                    return texture.standLeft.getKeyFrame(standingTimer, true);
                case RIGHT:
                    return texture.standRight.getKeyFrame(standingTimer, true);
                case UP:
                    return texture.standUp.getKeyFrame(standingTimer, true);
                default:
                    return texture.standDown.getKeyFrame(standingTimer, true);
            }
        }
        else {
            standingTimer = 0;
        }
        switch (curDirection){
            case LEFT:
                return texture.animationLeft.getKeyFrame(walkingTimer, true);
            case RIGHT:
                return texture.animationRight.getKeyFrame(walkingTimer, true);
            case UP:
                return texture.animationUp.getKeyFrame(walkingTimer, true);
            default:
                return texture.animationDown.getKeyFrame(walkingTimer, true);
        }
    }


    public boolean isPlayerTurn(){
        return Game.inputManager.ENTER && getEventsBox().intersect(map.getPlayer().getEventsBox()) && isPlayerFacing();
    }

    public PersonTexture getTexture() {
        return texture;
    }
}

