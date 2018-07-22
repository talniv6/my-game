package com.talniv.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.Assets;
import com.talniv.game.Game;

public class DJ {
    private final int NOT_MUTED = 0, MUTE = 1;
    private Rectangle position;

    private boolean mute, importantSound;
    private Array<TextureRegion> soundConsPictures;
    private Animation<TextureRegion> soundWaves;
    private float timer;
    private Music curMusic;

    public DJ(){
        mute = false;
        importantSound = false;

        TextureRegion[][] sheet = Assets.soundPic.split(24,24);
        soundConsPictures = new Array<TextureRegion>();
        soundConsPictures.add(sheet[0][0]);
        soundConsPictures.add(sheet[0][1]);

        TextureRegion[][] soundAnim = Assets.soundAnim.split(32,32);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(soundAnim[0][0]);
        frames.add(soundAnim[0][1]);
        frames.add(soundAnim[0][2]);
        soundWaves = new Animation<TextureRegion>(0.1f, frames);

        position = new Rectangle(20, Game.V_HEIGHT - 50, 32 ,32);
    }

    public void play(Music music){
        if (mute)
            music.setVolume(0);
        music.play();
        curMusic = music;
        importantSound = false;
    }

    public void play(Music music, boolean importantSound){
        play(music);
        this.importantSound = importantSound;
    }

    public void tick(float dt){
        if (Game.inputManager.ENTER) {
            float pointerX = InputTransformer.getX(Gdx.input.getX());
            float pointerY = InputTransformer.getY(Gdx.input.getY());
            if (position.contains(pointerX, pointerY)) {
                setMute(!mute);
            }
        }
        if (curMusic != null && curMusic.isPlaying() && importantSound){
            timer += dt;
        }
        else {
            timer = 0;
        }

    }

    public void render(Batch batch){
        batch.draw(getCurFrame(), position.x, position.y);
    }

    public void setMute(boolean mute) {
        this.mute = mute;
        if (mute && curMusic != null && curMusic.isPlaying()){
            curMusic.stop();
        }
    }

    private TextureRegion getCurFrame(){
        if (curMusic != null && curMusic.isPlaying() && importantSound){
            return soundWaves.getKeyFrame(timer, true);
        }
        else if (mute){
            return soundConsPictures.get(MUTE);
        }
        else {
            return soundConsPictures.get(NOT_MUTED);
        }
    }
}
