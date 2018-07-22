package com.talniv.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    public static TextureRegion blackBackground, whiteBackground;

    public static void init() {
        Pixmap background = new Pixmap(1, 1, Pixmap.Format.Alpha);
        background.setColor(0, 0, 0, 0.5f);
        background.fill();
        blackBackground = new TextureRegion(new Texture(background));

        Pixmap background2 = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        background2.setColor(Color.WHITE);
        background2.fill();
        whiteBackground = new TextureRegion(new Texture(background2));
    }


}
