package com.talniv.game.ui;

import java.util.ArrayList;


public class Text {
    private ArrayList<String> text;
    private int index;
    private boolean finished;

    public Text(ArrayList<String> text){
        this.text = text;
        index = 0;
        finished = false;
    }

    public void next(){
        index ++;
        if(index == text.size()){
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public String currentText(){
        return text.get(index);
    }

    public boolean isLastLine(){
        return index == text.size()-1;
    }

    public void reset(){
        finished = false;
        index = 0;
    }

}
