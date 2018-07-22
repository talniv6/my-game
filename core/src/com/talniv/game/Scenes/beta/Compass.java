package com.talniv.game.Scenes.beta;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.talniv.game.Assets;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.UnsupportedShapeTypeException;
import com.talniv.game.map.Box;
import com.talniv.game.map.Map;

public class Compass extends MapObject {

    private TextureRegion[] frames;
    private int index;

    public Compass(Map map, float x, float y) {
        super(map, x, y, 32, 32);
        TextureRegion[][] sheet = Assets.compass.split(32, 32);

        frames = new TextureRegion[4];
        frames[0] = sheet[0][0];
        frames[1] = sheet[0][1];
        frames[2] = sheet[0][2];
        frames[3] = sheet[0][3];
        index = 0;

        try {
            setCollisionBox(new Box(new Rectangle(0,0,32,32)), 0,0);
            setEventsBox(new Box(new Rectangle(0,0,32,32)), 0,0);
        } catch (UnsupportedShapeTypeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick(float dt) {

    }

    @Override
    public void render(Batch batch) {
        batch.draw(frames[index], x, y, getWidth(), getHeight());
    }

    public void progressIndex(){
        index++;
        if (index == 4){
            index = 0;
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index){
        if (index > 3 || index < 0){
            System.out.println("index invalid");
            System.exit(1);
        }
        this.index = index;
    }
}
