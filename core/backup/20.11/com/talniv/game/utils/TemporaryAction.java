package com.talniv.game.utils;


import com.badlogic.gdx.graphics.g2d.Batch;

public interface TemporaryAction {
    void tick(float dt);
    void start();
    void render(Batch batch);
    void end();
    boolean condition();
}
