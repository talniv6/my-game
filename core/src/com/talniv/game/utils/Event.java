package com.talniv.game.utils;


import com.badlogic.gdx.graphics.g2d.Batch;

public interface Event {
    boolean conditionToStart();
    void atStart();
    void tick(float dt);
    void render(Batch batch);
    boolean conditionToEnd();
    void atEnd();

    boolean oneTimeEvent();
}
