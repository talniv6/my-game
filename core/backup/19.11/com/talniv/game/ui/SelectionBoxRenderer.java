package com.talniv.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.talniv.game.Assets;
import com.talniv.game.Game;
import com.talniv.game.utils.InputTransformer;

public class SelectionBoxRenderer {

    public SelectionBox selectionBox;
    private Rectangle position;
    private float entityHeight, numOfEntities;
    private boolean choiceMade=false;
    private BitmapFont font = new BitmapFont();


    public SelectionBoxRenderer() {
        choiceMade=false;
    }

    /**
     * numOfEntities not provided, drawing all the entities evenly on "position"
     */
    public void setSelectionBox(SelectionBox selectionBox, Rectangle position){
        if(selectionBox.equals(this.selectionBox) && position.equals(this.position)){
            return;
        }
        this.selectionBox = selectionBox;
        this.position = position;
        this.numOfEntities = selectionBox.size();
        entityHeight = position.height/selectionBox.getOptions().size;
    }

    /**
     * numOfEntities provided. if cyclicArray.size > numOfEntities drawing only numOfEntities.
     * otherwise draw them evenly like numOfEntities didn't provided
     */
    public void setSelectionBox(SelectionBox selectionBox, Rectangle position, float numOfEntities){
        if(selectionBox.equals(this.selectionBox) && position.equals(this.position)){
            return;
        }
        this.selectionBox = selectionBox;
        this.position = position;
        if (selectionBox.getOptions().size > numOfEntities) {
            entityHeight = position.height / numOfEntities;
            this.numOfEntities = numOfEntities;
        }
        else {
            entityHeight = position.height / selectionBox.getOptions().size;
            this.numOfEntities = selectionBox.getOptions().size;
        }
    }

    public void tick(){
        Game.inputManager.setProtectKeys(true);
        if (Game.inputManager.UP){
            selectionBox.selectPrev();
        }
        if (Game.inputManager.DOWN){
            selectionBox.selectNext();
        }
        if (Game.inputManager.ENTER) {
            choiceMade = true;
            float pointerX = InputTransformer.getX(Gdx.input.getX());
            float pointerY = InputTransformer.getY(Gdx.input.getY());

            if (position.contains(pointerX,pointerY)){
                // calculate selected option
            }
        }
    }

    public void render(Batch batch){
        batch.draw(Assets.blackBackground, position.x, position.y, position.width, position.height);
        for (int i = 0; i < numOfEntities; i++) {
            Option entity = selectionBox.getOptions().get((int)numOfEntities-1-i);
            if (selectionBox.getSelectedIndex() == (int)numOfEntities-1-i){
                batch.draw(Assets.whiteBackground, position.x, position.y+i*entityHeight, position.width, entityHeight);
                font.setColor(0,0,0,1);
                font.draw(batch, entity.name, position.x+5, position.y+(i+1)*entityHeight-entityHeight/4);
                font.setColor(1,1,1,1);
            }
            else {
                font.draw(batch, entity.name, position.x+5, position.y+(i+1)*entityHeight-entityHeight/4);
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

    public SelectionBox getSelectionBox() {
        return selectionBox;
    }
}
