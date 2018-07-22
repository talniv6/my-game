package com.talniv.game.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

public class Ticker {
    private Array<TemporaryAction> actions;

    public Ticker() {
        actions = new Array<TemporaryAction>();
    }

    public void tick(float dt){
        for (int i = 0; i < actions.size; i++) {
            if (actions.get(i).condition())
                actions.get(i).tick(dt);
            else {
                actions.get(i).end();
                actions.removeIndex(i);
            }
        }
    }

    public void render(Batch batch){
        for (int i = 0; i < actions.size; i++) {
            if (actions.get(i).condition())
                actions.get(i).render(batch);
        }
    }

    public void add(TemporaryAction action){
        actions.add(action);
        action.start();
    }
}
