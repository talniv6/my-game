package com.talniv.game.GameObjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public class PersonTexture {

    private String[] fileString;
    private int line=-1;

    public Animation<TextureRegion> animationDown, animationUp, animationLeft, animationRight,
            standDown, standUp, standRight, standLeft;

    public PersonTexture(String path) {
        load(path);
    }

    private String nextLine(){
        line++;
        return fileString[line];
    }

    private void load(String path){
        line = -1;
        FileHandle info = Gdx.files.internal(path);
        TextureRegion[][] frames;

        fileString = info.readString().split("\\r\\n");

        String source = nextLine().replace("source: ", "");

        int tilewidth = Integer.parseInt(nextLine().replace("tilewidth: ", ""));
        int tileheight = Integer.parseInt(nextLine().replace("tileheight: ", ""));

        frames = (new TextureRegion(new Texture(source))).split(tilewidth, tileheight);

        // animation left
        String[] leftTiles = nextLine().replace("walking left: ", "").split(",");
        if (leftTiles.length % 2 != 0){
            System.out.println("Animation left: invalid points- odd number of coordinates");
            System.exit(1);
        }
        Array<TextureRegion> leftFrames = new Array<TextureRegion>();
        for (int i = 0; i < leftTiles.length; i += 2) {
            leftFrames.add(frames[Integer.parseInt(leftTiles[i])][Integer.parseInt(leftTiles[i+1])]);
        }
        animationLeft = new Animation<TextureRegion>(0.2f, leftFrames);

        // animation right
        String[] rightTiles = nextLine().replace("walking right: ", "").split(",");
        if (rightTiles.length % 2 != 0){
            System.out.println("Animation right: invalid points- odd number of coordinates");
            System.exit(1);
        }
        Array<TextureRegion> rightFrames = new Array<TextureRegion>();
        for (int i = 0; i < rightTiles.length; i += 2) {
            rightFrames.add(frames[Integer.parseInt(rightTiles[i])][Integer.parseInt(rightTiles[i+1])]);
        }
        animationRight = new Animation<TextureRegion>(0.2f, rightFrames);

        // animation down
        String[] downTiles = nextLine().replace("walking down: ", "").split(",");
        if (downTiles.length % 2 != 0){
            System.out.println("Animation down: invalid points- odd number of coordinates");
            System.exit(1);
        }
        Array<TextureRegion> downFrames = new Array<TextureRegion>();
        for (int i = 0; i < downTiles.length; i += 2) {
            downFrames.add(frames[Integer.parseInt(downTiles[i])][Integer.parseInt(downTiles[i+1])]);
        }
        animationDown = new Animation<TextureRegion>(0.2f, downFrames);

        // animation up
        String[] upTiles = nextLine().replace("walking up: ", "").split(",");
        if (upTiles.length % 2 != 0){
            System.out.println("Animation up: invalid points- odd number of coordinates");
            System.exit(1);
        }
        Array<TextureRegion> upFrames = new Array<TextureRegion>();
        for (int i = 0; i < upTiles.length; i += 2) {
            upFrames.add(frames[Integer.parseInt(upTiles[i])][Integer.parseInt(upTiles[i+1])]);
        }
        animationUp = new Animation<TextureRegion>(0.2f, upFrames);

        // standing left
        String[] standLeftTiles = nextLine().replace("standing left: ", "").split(",");
        if (standLeftTiles.length % 2 != 0){
            System.out.println("Standing left: invalid points- odd number of coordinates");
            System.exit(1);
        }
        Array<TextureRegion> standLeftFrames = new Array<TextureRegion>();
        for (int i = 0; i < standLeftTiles.length; i += 2) {
            standLeftFrames.add(frames[Integer.parseInt(standLeftTiles[i])][Integer.parseInt(standLeftTiles[i+1])]);
        }
        standLeft = new Animation<TextureRegion>(0.2f, standLeftFrames);

        // standing right
        String[] standRightTiles = nextLine().replace("standing right: ", "").split(",");
        if (standRightTiles.length % 2 != 0){
            System.out.println("Standing left: invalid points- odd number of coordinates");
            System.exit(1);
        }
        Array<TextureRegion> standRightFrames = new Array<TextureRegion>();
        for (int i = 0; i < standRightTiles.length; i += 2) {
            standRightFrames.add(frames[Integer.parseInt(standRightTiles[i])][Integer.parseInt(standRightTiles[i+1])]);
        }
        standRight = new Animation<TextureRegion>(0.2f, standRightFrames);

        // standing down
        String[] standDownTiles = nextLine().replace("standing down: ", "").split(",");
        if (standDownTiles.length % 2 != 0){
            System.out.println("Standing left: invalid points- odd number of coordinates");
            System.exit(1);
        }
        Array<TextureRegion> standDownFrames = new Array<TextureRegion>();
        for (int i = 0; i < standDownTiles.length; i += 2) {
            standDownFrames.add(frames[Integer.parseInt(standDownTiles[i])][Integer.parseInt(standDownTiles[i+1])]);
        }
        standDown = new Animation<TextureRegion>(0.2f, standDownFrames);

        // standing up
        String[] standUpTiles = nextLine().replace("standing up: ", "").split(",");
        if (standUpTiles.length % 2 != 0){
            System.out.println("Standing left: invalid points- odd number of coordinates");
            System.exit(1);
        }
        Array<TextureRegion> standUpFrames = new Array<TextureRegion>();
        for (int i = 0; i < standUpTiles.length; i += 2) {
            standUpFrames.add(frames[Integer.parseInt(standUpTiles[i])][Integer.parseInt(standUpTiles[i+1])]);
        }
        standUp = new Animation<TextureRegion>(0.2f, standUpFrames);
    }

    public void setFrameDuration(Animation<TextureRegion> animation, float duration){
        animation.setFrameDuration(duration);
    }

}
