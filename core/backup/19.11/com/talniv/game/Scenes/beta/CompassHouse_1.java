package com.talniv.game.Scenes.beta;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;


public class CompassHouse_1 extends Scene {

    private Box exit;
    boolean isSecretPassageVisible = false;
    private Box secretPassage;
    Compass compass;
    private Box compassEvent;

    public CompassHouse_1(String pathToMap) {
        super(pathToMap);
        buildScene();
    }

    public void buildScene() {
        exit = map.getEventByName("exit");
        secretPassage = map.getEventByName("secretPassage");

        compass = new Compass(map, 130, 74);
        compass.setIndex(0);
        map.addObject(compass);
        compassEvent = map.getEventByName("compass");
    }

    @Override
    public void tick(float dt) {
        if (Game.ui.isActive())
            return;

        map.tick(dt);
        if (map.getPlayer().getCollisionBox().intersect(exit)  && map.getPlayer().getCurDirection() == MapObject.DOWN){
            changeScene(ScenesCollection.BETA_MAIN, 134, 934, MapObject.UP);
        }
        if (isSecretPassageVisible && map.getPlayer().getCollisionBox().intersect(secretPassage)){
            changeScene(ScenesCollection.BETA_UNDER_GROUND, 167, 17, MapObject.UP);
        }
        if (map.getPlayer().getCollisionBox().intersect(compassEvent) && Game.inputManager.ENTER){
            compass.progressIndex();
        }
        if (compass.getIndex() == 3 && ScenesCollection.COMPASS_HOUSE_2.compass.getIndex() == 1){
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
        if (!isSecretPassageVisible) {
            isSecretPassageVisible = true;
            map.getMap().getLayers().get("secretPassage1").setVisible(true);
            map.getMap().getLayers().get("secretPassage2").setVisible(true);

            if (!ScenesCollection.COMPASS_HOUSE_2.isSecretPassageVisible)
                ScenesCollection.COMPASS_HOUSE_2.discoverSecretPassage();
        }
    }

    void hideSecretPassage(){
        if (isSecretPassageVisible) {
            isSecretPassageVisible = false;
            map.getMap().getLayers().get("secretPassage1").setVisible(false);
            map.getMap().getLayers().get("secretPassage2").setVisible(false);

            if (ScenesCollection.COMPASS_HOUSE_2.isSecretPassageVisible)
                ScenesCollection.COMPASS_HOUSE_2.hideSecretPassage();
        }
    }
}
