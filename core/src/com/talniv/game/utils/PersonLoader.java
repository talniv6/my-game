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
import com.talniv.game.GameObjects.PersonTexture;
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

        fileString = info.readString().split("\\r\\n");

        PersonTexture texture = new PersonTexture(nextLine().replace("texture: ", ""));

        // width & height
        int width = Integer.parseInt(nextLine().replace("width: ", ""));
        int height = Integer.parseInt(nextLine().replace("height: ", ""));

        Person person = new Person(map, spawnX, spawnY, width, height, texture);

        if (line == fileString.length-1){
            return person;
        }

        // collision shape
        String shapeType = nextLine().replace("collision shape: ", "");
        Box collision = createBox(shapeType);
        if (collision != null) {
            String[] dxdy = nextLine().replace("dxdy: ", "").split(",");
            person.setCollisionBox(collision, Float.parseFloat(dxdy[0]), Float.parseFloat(dxdy[1]));
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
        Box eventBox = createBox(shapeType);
        if (eventBox != null) {
            String[] dxdy = nextLine().replace("dxdy: ", "").split(",");
            person.setEventsBox(eventBox, Float.parseFloat(dxdy[0]), Float.parseFloat(dxdy[1]));
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
