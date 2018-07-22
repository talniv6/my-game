package com.talniv.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    public static TextureRegion blackBackground, whiteBackground, beta_graph, ghost, compass, soundPic, soundAnim;

    public static Music ghostCreation, laugh;

    public static void init() {
        Pixmap background = new Pixmap(1, 1, Pixmap.Format.Alpha);
        background.setColor(0, 0, 0, 0.5f);
        background.fill();
        blackBackground = new TextureRegion(new Texture(background));

        Pixmap background2 = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        background2.setColor(Color.WHITE);
        background2.fill();
        whiteBackground = new TextureRegion(new Texture(background2));

        beta_graph = new TextureRegion(new Texture("beta/graph.png"));
        ghost = new TextureRegion(new Texture("beta/ghost.png"));
        compass = new TextureRegion(new Texture("beta/compass.png"));
        soundPic = new TextureRegion(new Texture("general/sound.png"));
        soundAnim = new TextureRegion(new Texture("general/sound-animation.png"));

        ghostCreation = Gdx.audio.newMusic(Gdx.files.internal("beta/ghost.mp3"));
        laugh = Gdx.audio.newMusic(Gdx.files.internal("beta/laugh.mp3"));
    }


}
