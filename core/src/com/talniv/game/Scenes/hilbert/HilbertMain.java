package com.talniv.game.Scenes.hilbert;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.talniv.game.Scenes.Scene;


public class HilbertMain extends Scene{
    public HilbertMain(String path) {
        super(path);
    }

    @Override
    public void tick(float dt) {
        map.tick(dt);
    }

    @Override
    public void render(Batch batch) {
        map.render();
    }
}
