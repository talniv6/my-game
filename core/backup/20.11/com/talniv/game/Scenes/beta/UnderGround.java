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
    private Box exit_to_compass_1,  exit_to_compass_2;

    public UnderGround(String pathToMap) {
        super(pathToMap);
        buildScene();
    }

    private void buildScene(){
        exit_to_compass_1 = map.getEventByName("exit_to_compass_1");
        if (exit_to_compass_1 == null){
            System.out.println("at UnderGround- exit_to_compass_1 is null");
            System.exit(1);
        }

        exit_to_compass_2 = map.getEventByName("exit_to_compass_2");
        if (exit_to_compass_2 == null){
            System.out.println("at UnderGround- exit_to_compass_2 is null");
            System.exit(1);
        }

        back = map.getAllEventsByName("back");
    }

    @Override
    public void tick(float dt) {
        if (Game.ui.isActive())
            return;

        map.tick(dt);

        if (map.getPlayer().getCollisionBox().intersect(exit_to_compass_1) && map.getPlayer().getCurDirection() == MapObject.DOWN) {
            changeScene(ScenesCollection.COMPASS_HOUSE_1, 305, 27, MapObject.LEFT);
        }

        if (map.getPlayer().getCollisionBox().intersect(exit_to_compass_2) && map.getPlayer().getCurDirection() == MapObject.DOWN) {
            changeScene(ScenesCollection.COMPASS_HOUSE_2, 65, 290, MapObject.RIGHT);
        }

        if (checkIfBack()){
            changeScene(ScenesCollection.BETA_MAIN, 440, 1250, MapObject.DOWN);
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
