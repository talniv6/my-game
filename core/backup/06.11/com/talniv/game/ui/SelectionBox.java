package com.talniv.game.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class SelectionBox {
    private Array<Option> options;
    private int selected;

    public SelectionBox(Array<Option> options) {
        this.options = options;
        selectLast();
    }

    public SelectionBox(Array<Option> options, boolean last) {
        this.options = options;
        if (last){
            selectLast();
        }
        else {
            selectFirst();
        }
    }

    public void selectPrev(){
        selected --;
        if (selected < 0){
            selected = options.size - 1;
        }
    }

    public void selectNext(){
        selected ++;
        if (selected == options.size){
            selected = 0;
        }
    }

    public int getSelected(){
        return selected;
    }

    public int size(){
        return options.size;
    }

    public Array<Option> getOptions() {
        return options;
    }

    public void selectLast(){
        selected = options.size-1;
    }

    public void selectFirst(){
        selected = 0;
    }

    public void select(int i){
        if (i > options.size - 1 || i < 0){
            System.out.println("select invalid");
            System.exit(1);
        }
        selected = i;
    }
}


