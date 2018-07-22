package com.talniv.game.Scenes.beta;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;


public class CompassHouse_2 extends Scene {

    private Box exit;
    boolean isSecretPassageVisible = false;
    private Box secretPassage;
    Compass compass;
    private Box compassEvent;

    public CompassHouse_2(String path) {
        super(path);
        buildScene();
    }

    public void buildScene() {
        exit = map.getEventByName("exit");
        secretPassage = map.getEventByName("secretPassage");

        compassEvent = map.getEventByName("compass");
        compass = new Compass(map, 110, 84);
        compass.setIndex(2);
        map.addObject(compass);
    }

    @Override
    public void tick(float dt) {
        if (Game.ui.isActive())
            return;

        map.tick(dt);
        if (map.getPlayer().getCollisionBox().intersect(exit)  && map.getPlayer().getCurDirection() == MapObject.UP){
            changeScene(ScenesCollection.BETA_MAIN, 742, 932, MapObject.UP);
        }
        if (isSecretPassageVisible && map.getPlayer().getCollisionBox().intersect(secretPassage)){
            changeScene(ScenesCollection.BETA_UNDER_GROUND, 777, 15, MapObject.UP);
        }
        if (map.getPlayer().getEventsBox().intersect(compassEvent) && Game.inputManager.ENTER){
            compass.progressIndex();
        }
        if (compass.getIndex() == 1 && ScenesCollection.COMPASS_HOUSE_1.compass.getIndex() == 3){
            discoverSecretPassage();
        } else {
            hideSecretPassage();
        }
    }

    @Override
    public void render(Batch batch) {
        map.render();
    }

    void discoverSecretPassage(){
        isSecretPassageVisible = true;
        map.getMap().getLayers().get("secretPassage1").setVisible(true);
        map.getMap().getLayers().get("secretPassage2").setVisible(true);

        if (!ScenesCollection.COMPASS_HOUSE_1.isSecretPassageVisible)
            ScenesCollection.COMPASS_HOUSE_1.discoverSecretPassage();
    }

    void hideSecretPassage(){
        if (isSecretPassageVisible) {
            isSecretPassageVisible = false;
            map.getMap().getLayers().get("secretPassage1").setVisible(false);
            map.getMap().getLayers().get("secretPassage2").setVisible(false);

            if (ScenesCollection.COMPASS_HOUSE_1.isSecretPassageVisible)
                ScenesCollection.COMPASS_HOUSE_1.hideSecretPassage();
        }
    }
}
