package com.talniv.game.Scenes.beta;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.DebugMapRenderer;
import com.talniv.game.GameObjects.Person;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.UnsupportedShapeTypeException;
import com.talniv.game.map.Box;

import java.util.ArrayList;


public class BetaMain extends Scene {

    public BetaMain(String path){
        super(path);
        create();
    }

    private void create(){
        // create old man
        TextureRegion[][] spriteSheet = new TextureRegion(new Texture("general/oldMan.png")).split(24,24);
        Animation<TextureRegion> animationDown, animationUp, animationRight, animationLeft;
        Array<TextureRegion> down = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            down.add(spriteSheet[0][i]);
        }
        animationDown = new Animation<TextureRegion>(0.2f, down);

        Array<TextureRegion> up = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            up.add(spriteSheet[3][i]);
        }
        animationUp = new Animation<TextureRegion>(0.2f, up);

        Array<TextureRegion> right = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            right.add(spriteSheet[2][i]);
        }
        animationRight = new Animation<TextureRegion>(0.2f, right);

        Array<TextureRegion> left = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            left.add(spriteSheet[3][i]);
        }
        animationLeft = new Animation<TextureRegion>(0.2f, left);

        TextureRegion standUp = spriteSheet[3][1];
        TextureRegion standDown = spriteSheet[0][1];
        TextureRegion standRight = spriteSheet[2][1];
        TextureRegion standLeft = spriteSheet[1][1];

        Person oldman = new Person(map, 800, 700, 24, 24, animationDown, animationUp, animationRight, animationLeft, standDown,
                standUp, standRight, standLeft);
        try {
            oldman.setCollisionBox(new Box(new Rectangle(0, 0, 22, 22)), 2, -2);
            oldman.setEventsBox(new Box(new Rectangle(0, 0, 28, 28)), 0, -4);
        } catch (UnsupportedShapeTypeException e) {
            e.printStackTrace();
        }
        map.addObject(oldman);

    }


    public void tick(float dt){
        if(!Game.ui.isActive())
            map.tick(dt);

    }


    public void render(Batch batch) {
        map.render();
    }
}
