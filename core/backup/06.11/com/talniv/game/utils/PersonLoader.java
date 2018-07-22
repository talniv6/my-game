package com.talniv.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.GameObjects.Person;
import com.talniv.game.UnsupportedShapeTypeException;
import com.talniv.game.map.Box;
import com.talniv.game.map.Map;

public class PersonLoader {

    private String[] fileString;
    private int line=-1;

    private String nextLine(){
        line++;
        return fileString[line];
    }

    private Box createBox(String type){
        Box collisionBox = null;
        try {
                if (type.equals("rectangle")) {
                    String[] recDimensions = nextLine().split(",");
                    collisionBox = new Box(new Rectangle(Integer.parseInt(recDimensions[0]), Integer.parseInt(recDimensions[1]),
                            Integer.parseInt(recDimensions[2]), Integer.parseInt(recDimensions[3])));
                }
                else if (type.equals("ellipse")) {
                    String[] ellDimensions = nextLine().split(",");
                    collisionBox = new Box(new Ellipse(Integer.parseInt(ellDimensions[0]), Integer.parseInt(ellDimensions[1]),
                            Integer.parseInt(ellDimensions[2]), Integer.parseInt(ellDimensions[3])));
                }
                else if (type.equals("polygon")) {
                    String[] points_str = nextLine().split(",");
                    float[] points = new float[points_str.length];
                    for (int i = 0; i < points_str.length; i++) {
                        points[i] = Float.parseFloat(points_str[i]);
                    }
                    collisionBox = new Box(new Polygon(points));
                }
        } catch (UnsupportedShapeTypeException e){
            System.out.println("problem reading shape");
            System.exit(1);
        }
        return collisionBox;
    }

    public Person loadPerson(Map map, String path, float spawnX, float spawnY){
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
        Animation<TextureRegion> walkingLeft = new Animation<TextureRegion>(0.2f, leftFrames);

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
        Animation<TextureRegion> walkingRight = new Animation<TextureRegion>(0.2f, rightFrames);

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
        Animation<TextureRegion> walkingDown = new Animation<TextureRegion>(0.2f, downFrames);

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
        Animation<TextureRegion> walkingUp = new Animation<TextureRegion>(0.2f, upFrames);

        // standing left
        String standLeftPoint[] = nextLine().replace("standing left: ", "").split(",");
        TextureRegion standingLeft = frames[Integer.parseInt(standLeftPoint[0])][Integer.parseInt(standLeftPoint[1])];

        // standing right
        String standRightPoint[] = nextLine().replace("standing right: ", "").split(",");
        TextureRegion standingRight = frames[Integer.parseInt(standRightPoint[0])][Integer.parseInt(standRightPoint[1])];

        // standing down
        String standDownPoint[] = nextLine().replace("standing down: ", "").split(",");
        TextureRegion standingDown = frames[Integer.parseInt(standDownPoint[0])][Integer.parseInt(standDownPoint[1])];

        // standing up
        String standUpPoint[] = nextLine().replace("standing up: ", "").split(",");
        TextureRegion standingUp = frames[Integer.parseInt(standUpPoint[0])][Integer.parseInt(standUpPoint[1])];

        // width & height
        int width = Integer.parseInt(nextLine().replace("width: ", ""));
        int height = Integer.parseInt(nextLine().replace("height: ", ""));

        Person person = new Person(map, spawnX, spawnY, width, height, walkingDown, walkingUp,
                walkingRight, walkingLeft, standingDown, standingUp, standingRight, standingLeft);

        if (line == fileString.length-1){
            return person;
        }

        // collision shape
        String shapeType = nextLine().replace("collision shape: ", "");
        Box box = createBox(shapeType);
        if (box != null) {
            String[] dxdy = nextLine().replace("dxdy: ", "").split(",");
            person.setCollisionBox(box, Float.parseFloat(dxdy[0]), Float.parseFloat(dxdy[1]));
        }
        else {
            System.out.println("invalid shape");
            System.exit(1);
        }

        if (line == fileString.length-1){
            return person;
        }

        // event shape
        shapeType = nextLine().replace("event shape: ", "");
        box = createBox(shapeType);
        if (box != null) {
            String[] dxdy = nextLine().replace("dxdy: ", "").split(",");
            person.setCollisionBox(box, Float.parseFloat(dxdy[0]), Float.parseFloat(dxdy[1]));
        }
        else {
            System.out.println("invalid shape");
            System.exit(1);
        }

        if (line == fileString.length-1){
            return person;
        }

        //speed
        float speed = Float.parseFloat(nextLine().replace("speed: ", ""));
        person.setxSpeed(speed);
        person.setySpeed(speed);

        if (line == fileString.length-1){
            return person;
        }

        return person;
    }
}
