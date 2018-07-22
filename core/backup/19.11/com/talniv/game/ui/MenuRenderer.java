package com.talniv.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.Assets;
import com.talniv.game.Game;
import com.talniv.game.utils.*;

public class MenuRenderer extends UiElement{

    private Rectangle closedMenuPosition = new Rectangle(Game.V_WIDTH-80, Game.V_HEIGHT-30, 50, 30);
    private Rectangle openedMenuPosition = new Rectangle(Game.V_WIDTH-100, Game.V_HEIGHT-150, 100, 150);
    private SelectionBoxRenderer openedMenuRenderer;

    private BitmapFont font;
    private boolean open;
    private SelectionBox menuOptions;


    public MenuRenderer(){
        font = new BitmapFont();
        open = false;

        Array<Option> options = new Array<Option>();
        options.add(new Option("exit"));
        options.add(new Option("save"));
        options.add(new Option("settings"));
        options.add(new Option("items"));
        options.add(new Option("close"));
        menuOptions = new SelectionBox(options);
        menuOptions.selectPrev();

        openedMenuRenderer = new SelectionBoxRenderer();
        openedMenuRenderer.setSelectionBox(menuOptions, openedMenuPosition);
    }

    public void tick(float dt){
        if (openedMenuRenderer.isChoiceMade()){
            switch (menuOptions.getSelectedIndex()){
                case 0:
                    // exit
                    break;
                case 1:
                    // save
                    break;
                case 2:
                    // settings
                    break;
                case 3:
                    // items
                    Game.ui.showItems();
                    break;
            }
            closeMenu();
            return;
        }
        if (open){
            openedMenuRenderer.tick();
        }
        else {
            if (Game.inputManager.ENTER) {
                float pointerX = InputTransformer.getX(Gdx.input.getX());
                float pointerY = InputTransformer.getY(Gdx.input.getY());
                if (closedMenuPosition.contains(pointerX, pointerY)) {
                    openMenu();
                }
            }
        }
    }

    private void closeMenu(){
        open = false;
        active = false;
        openedMenuRenderer.finish();
    }

    private void openMenu(){
        open = true;
        active = true;
        menuOptions.selectLast();
    }

    public void render(Batch batch){
        if (!open) {
            batch.draw(Assets.blackBackground, closedMenuPosition.x, closedMenuPosition.y, closedMenuPosition.width, closedMenuPosition.height);
            font.draw(batch, "Menu", closedMenuPosition.x + 5, closedMenuPosition.y + 20);
        }
        else {
            openedMenuRenderer.render(batch);
        }

    }

}





