package com.talniv.game.Scenes.beta;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.UnsupportedShapeTypeException;
import com.talniv.game.map.Box;
import com.talniv.game.map.Map;

public class Compass extends MapObject {

    private Texture[] frames;
    private int index;

    public Compass(Map map, float x, float y) {
        super(map, x, y, 145, 145);
        frames = new Texture[4];
        frames[0] = new Texture("beta/compass_up.png");
        frames[1] = new Texture("beta/compass_right.png");
        frames[2] = new Texture("beta/compass_down.png");
        frames[3] = new Texture("beta/compass_left.png");
        index = 0;

        try {
            setCollisionBox(new Box(new Rectangle(0,0,2,2)), 100,100);
            setEventsBox(new Box(new Rectangle(0,0,4,4)), 100,100);
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
