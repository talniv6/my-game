package com.talniv.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.talniv.game.Assets;
import com.talniv.game.Game;
import com.talniv.game.utils.*;

public class SelectionBoxRenderer {

    private CyclicArray<Option> options=null;
    private Rectangle position;
    private float entityHeight, numOfEntities;
    private boolean choiceMade=false;
    private BitmapFont font = new BitmapFont();


    public SelectionBoxRenderer(Rectangle position) {
        this.position = position;
        choiceMade=false;
    }
    
    public void setOptions(CyclicArray<Option> options){
        this.options = options;
    }

    public void tick(){
        if (options == null){
            System.out.println("Options should be set before using the renderer");
            System.exit(1);
        }
        Game.inputManager.setProtectKeys(true);
        if (Game.inputManager.UP){
            options.prev();
        }
        if (Game.inputManager.DOWN){
            options.next();
        }
        if (Game.inputManager.ENTER) {
            choiceMade = true;
            float pointerX = InputTransformer.getX(Gdx.input.getX());
            float pointerY = InputTransformer.getY(Gdx.input.getY());

            if (position.contains(pointerX,pointerY)){
                // calculate selected option
            }
        }
        numOfEntities = options.size();
        entityHeight = position.height/numOfEntities;
        if (entityHeight > 40){
            entityHeight = 40;
        }
    }

    public void render(Batch batch){
        batch.draw(Assets.blackBackground, position.x, position.y, position.width, position.height);
        for (int i = 0; i < numOfEntities; i++) {
            String curText = options.get((int)numOfEntities-1-i).name;
            if (options.getCurrentIndex() == (int)numOfEntities-1-i){
                batch.draw(Assets.whiteBackground, position.x, position.y+i*entityHeight, position.width, entityHeight);
                font.setColor(0,0,0,1);
                font.draw(batch, curText, position.x+5, position.y+(i+1)*entityHeight-entityHeight/4);
                font.setColor(1,1,1,1);
            }
            else {
                font.draw(batch, curText, position.x+5, position.y+(i+1)*entityHeight-entityHeight/4);
            }
        }
    }

    public boolean isChoiceMade() {
        return choiceMade;
    }

    public void finish(){
        choiceMade = false;
        Game.inputManager.setProtectKeys(false);
    }

}
