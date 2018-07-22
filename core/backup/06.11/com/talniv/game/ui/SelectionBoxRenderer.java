package com.talniv.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.Assets;
import com.talniv.game.Game;

public class SelectionBoxRenderer {

    public SelectionBox selectionBox;
    private Rectangle position;
    private float entityHeight, numOfEntities;
    private boolean choiceMade=false;
    private BitmapFont font = new BitmapFont();

    /**
     * numOfEntities not provided, drawing all the entities evenly on "position"
     */
    public SelectionBoxRenderer(SelectionBox selectionBox, Rectangle position) {
        this.selectionBox = selectionBox;
        this.position = position;
        this.numOfEntities = selectionBox.size();
        entityHeight = position.height/selectionBox.getOptions().size;
        choiceMade=false;
    }

    /**
     * numOfEntities provided. if cyclicArray.size > numOfEntities drawing only numOfEntities.
     * otherwise draw them evenly like numOfEntities didn't provided
     */
    public SelectionBoxRenderer(Array<Option> selectionBox, Rectangle position, float numOfEntities) {
        this.selectionBox = new SelectionBox(selectionBox);
        this.position = position;
        if (selectionBox.size > numOfEntities) {
            entityHeight = position.height / numOfEntities;
            this.numOfEntities = numOfEntities;
        }
        else {
            entityHeight = position.height / selectionBox.size;
            this.numOfEntities = selectionBox.size;
        }
        choiceMade=false;
    }

    public void tick(){
        Game.inputManager.setProtectKeys(true);
        if (Game.inputManager.UP){
            selectionBox.selectNext();
        }
        if (Game.inputManager.DOWN){
            selectionBox.selectPrev();
        }
        if (Game.inputManager.ENTER) {
            choiceMade = true;
            float pointerX = InputTransform.getCursorToModelX(Gdx.graphics.getWidth(), Gdx.input.getX());
            float pointerY = InputTransform.getCursorToModelY(Gdx.graphics.getHeight(), Gdx.input.getY());

            if (position.contains(pointerX,pointerY)){
                // calculate selected option
            }
        }
    }

    public void render(Batch batch){
        batch.draw(Assets.blackBackground, position.x, position.y, position.width, position.height);
        for (int i = 0; i < numOfEntities; i++) {
            Option entity = selectionBox.getOptions().get(i);
            if (selectionBox.getSelected() == i){
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
