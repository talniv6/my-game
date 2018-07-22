package com.talniv.game.utils;

import com.badlogic.gdx.Gdx;
import com.talniv.game.Game;


public class InputTransformer {

    public static float getX(float cursorX)
    {
        return ((cursorX) * Game.V_WIDTH) / (Gdx.graphics.getWidth());
    }

    public static float getY(float cursorY)
    {
        return ((Gdx.graphics.getHeight() - cursorY)) * Game.V_HEIGHT / (Gdx.graphics.getHeight()) ;
    }

}
