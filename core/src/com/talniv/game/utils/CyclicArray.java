package com.talniv.game.utils;

import com.badlogic.gdx.utils.Array;

public class CyclicArray<T> {
    private Array<T> elements;
    private int current;

    public CyclicArray() {
        this.elements = new Array<T>();
        current = 0;
    }

    public CyclicArray(Array<T> elements) {
        this.elements = elements;
        current = 0;
    }

    public void add(T element){
        elements.add(element);
    }

    public void remove(int index){
        elements.removeIndex(index);
    }

    public void prev(){
        current--;
        if (current < 0){
            current = elements.size - 1;
        }
    }

    public void next(){
        current++;
        if (current == elements.size){
            current = 0;
        }
    }

    public int getCurrentIndex(){
        return current;
    }

    public T getCurrent(){
        return elements.get(current);
    }

    public int size(){
        return elements.size;
    }

    public void selectLast(){
        current = elements.size-1;
    }

    public void selectFirst(){
        current = 0;
    }

    public T get(int i){
        if (i > elements.size || i < 0 || elements.size == 0)
            return null;
        return elements.get(i);
    }

    public void select(int i){
        if (i > elements.size - 1 || i < 0){
            System.out.println("select invalid");
            System.exit(1);
        }
        current = i;
    }

    public void clear(){
        elements.clear();
    }

    public Array<T> getElementsAsArray(){
        return elements;
    }
}


