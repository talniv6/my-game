package com.talniv.game.ui;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class UiElement {
    boolean active=false;

    public abstract void tick(float dt);

    public abstract void render(Batch batch);

    public boolean isActive() {
        return active;
    }
}
