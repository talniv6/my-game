package com.talniv.game.Scenes.beta;

import mapObjects.MapObject;
import graphics.Assets;
import graphics.GameGraphics;
import graphics.map.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Compass extends MapObject {

    private BufferedImage[] frames;
    private int index;

    public Compass(Map map, float x, float y) {
        super(map, x, y, 145, 145);
        frames = Assets.compass;
        index = 0;
        setCollisionShape(new Rectangle(22,22,100,100));
        setEventsShape(new Rectangle(10,10,0,0));
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(GameGraphics g) {
        g.drawImage(map.camera, frames[index], (int)x, (int)y, getWidth(), getHeight(), null);
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
