package com.talniv.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.GameObjects.Character;
import com.talniv.game.map.Box;
import com.talniv.game.map.Map;


public class Player extends Character{

    private TextureRegion[][] spriteSheet;
    private Map map;
    private float timer;
    private Animation<TextureRegion> animationDown, animationUp, animationLeft, animationRight;
    private float dx, dy;
    private boolean moved;

    public Player(Map map) {
        super(map, 700, 700, 16, 32);
        this.map = map;
        spriteSheet = new TextureRegion(new Texture("general/player.png")).split(16,32);

        try {
            setCollisionBox(new Box(new Rectangle(0,0,12,16)), 3, 3);
            setEventsBox(new Box(new Rectangle(0,0,22,24)), -2, 2);
        } catch (UnsupportedShapeTypeException e) {
            e.printStackTrace();
        }

        Array<TextureRegion> down = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            down.add(spriteSheet[0][i]);
        }
        animationDown = new Animation<TextureRegion>(0.2f, down);

        Array<TextureRegion> up = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            up.add(spriteSheet[2][i]);
        }
        animationUp = new Animation<TextureRegion>(0.2f, up);

        Array<TextureRegion> right = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            right.add(spriteSheet[1][i]);
        }
        animationRight = new Animation<TextureRegion>(0.2f, right);

        Array<TextureRegion> left = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            left.add(spriteSheet[3][i]);
        }
        animationLeft = new Animation<TextureRegion>(0.2f, left);

        curDirection = DOWN;
    }

    public void tick(float dt){
        timer += dt;
        getInput();
        moved = move(dx, dy); // dx*dt, dy*dt and increase speed to 180
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
                    return spriteSheet[3][0];
                case RIGHT:
                    return spriteSheet[1][0];
                case UP:
                    return spriteSheet[2][0];
                default:
                    return spriteSheet[0][0];
            }
        }
        switch (curDirection){
            case LEFT:
                return animationLeft.getKeyFrame(timer, true);
            case RIGHT:
                return animationRight.getKeyFrame(timer, true);
            case UP:
                return animationUp.getKeyFrame(timer, true);
            default:
                return animationDown.getKeyFrame(timer, true);
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

    public void resetPosition(int x, int y){
        dx = 0;
        dy = 0;
        this.x = x;
        this.y = y;
    }

}
