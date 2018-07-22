package com.talniv.game.ui;

import com.badlogic.gdx.utils.ArrayMap;


public class Text {
    private ArrayMap<String, SelectionBox> text;
    private int index;
    private boolean finished;

    public Text(ArrayMap<String, SelectionBox> text){
        this.text = text;
        index = 0;
        finished = false;
    }

    public void next(){
        index ++;
        if(index == text.size){
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public String currentText(){
        return text.getKeyAt(index);
    }

    public SelectionBox getSelectionBox(int i){
        int counter = 0;
        for (int j = 0; j < text.size; j++) {
            if (text.getValueAt(j) != null){
                if (counter == i) {
                    return text.getValueAt(j);
                }
                else {
                    counter ++;
                }
            }
        }
        return null;
    }

    public SelectionBox getSelectionBox(){
        return text.getValueAt(index);
    }

    public boolean isLastLine(){
        return index == text.size-1;
    }

    public void reset(){
        finished = false;
        index = 0;
        for (int i = 0; i < text.size; i++) {
            if (text.getValueAt(i) != null){
                text.getValueAt(i).selectLast();
            }
        }
    }

    public boolean isQuestion(){
        if (isFinished()){
            return false;
        }
        return text.getValueAt(index) != null;
    }

}
