package com.talniv.game.Scenes.beta;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;


public class UnderGround extends Scene {

    private Array<Box> back;
    private Box exit_to_compass_1,  exit_to_compass_2, exit_keyHouse;

    public UnderGround(String pathToMap) {
        super(pathToMap);
        buildScene();
    }

    private void buildScene(){
        exit_to_compass_1 = map.getEventByName("exit_to_compass1");
        if (exit_to_compass_1 == null){
            System.out.println("at UnderGround- exit_to_compass1 is null");
            System.exit(1);
        }

        exit_to_compass_2 = map.getEventByName("exit_to_compass2");
        if (exit_to_compass_2 == null){
            System.out.println("at UnderGround- exit_to_compass2 is null");
            System.exit(1);
        }

        exit_keyHouse = map.getEventByName("exit_keyHouse");

        back = map.getAllEventsByName("back");
    }

    @Override
    public void tick(float dt) {
        if (Game.ui.isActive())
            return;

        map.tick(dt);

        if (map.getPlayer().getCollisionBox().intersect(exit_to_compass_1) && map.getPlayer().getCurDirection() == MapObject.DOWN) {
            changeScene(ScenesCollection.COMPASS_HOUSE_1, ScenesCollection.COMPASS_HOUSE_1.map.getObjectByName("back_from_underground"), MapObject.RIGHT);
        }

        if (map.getPlayer().getCollisionBox().intersect(exit_to_compass_2) && map.getPlayer().getCurDirection() == MapObject.DOWN) {
            changeScene(ScenesCollection.COMPASS_HOUSE_2, ScenesCollection.COMPASS_HOUSE_2.map.getObjectByName("back_from_underground"), MapObject.RIGHT);
        }

        if (map.getPlayer().getCollisionBox().intersect(exit_keyHouse) && map.getPlayer().getCurDirection() == MapObject.UP) {
            changeScene(ScenesCollection.BETA_KEYHOUSE, ScenesCollection.BETA_KEYHOUSE.map.getObjectByName("spawn"), MapObject.UP);
        }

        if (checkIfBack()){
            changeScene(ScenesCollection.BETA_MAIN, ScenesCollection.BETA_MAIN.map.getObjectByName("spawn"), MapObject.DOWN);
        }
    }

    @Override
    public void render(Batch batch) {
        map.render();
    }

    private boolean checkIfBack(){
        for (Box box : back){
            if (map.getPlayer().getCollisionBox().intersect(box)){
                return true;
            }
        }
        return false;
    }

}
