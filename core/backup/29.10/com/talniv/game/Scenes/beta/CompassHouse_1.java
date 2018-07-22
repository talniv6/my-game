package com.talniv.game.Scenes.beta;


import com.talniv.game.Scenes.Scene;

import java.awt.*;

public class CompassHouse_1 extends Scene {

    private Shape exit;
    boolean isSecretPassageVisible = false;
    private Shape secretPassage;
    Compass compass;
    private Shape compassEvent;

    public CompassHouse_1(String pathToMap) {
        super(pathToMap);
        buildScene();
    }

    public void buildScene() {
        map.camera.setxOffset(-(Game.FRAME_WIDTH-map.getMapWidth())/2);
        map.camera.setyOffset(-(Game.FRAME_WIDTH-map.getMapWidth())/2);

        exit = map.getEventBox("exit");
        secretPassage = map.getEventBox("secretPassage");

        compass = new Compass(map, 130, 130);
        compass.setIndex(0);
        map.addObject(compass);
        compassEvent = map.getEventBox("compass");
    }

    @Override
    public void tick() {
        map.tick();
        if (map.getPlayer().collisionShapeIntersects(exit)  && map.getPlayer().getCurDirection() == MapObject.DOWN){
            changeScene(ScenesCollection.BETA_MAIN, 140, 630, MapObject.UP, null);
        }
        if (isSecretPassageVisible && map.getPlayer().collisionShapeIntersects(secretPassage)){
            changeScene(ScenesCollection.BETA_UNDER_GROUND, 165, 490, MapObject.UP, null);
        }
        if (map.getPlayer().eventShapeIntersects(compassEvent) && getKeyManager().enter){
            compass.progressIndex();
        }
        if (compass.getIndex() == 3 && ScenesCollection.COMPASS_HOUSE_2.compass.getIndex() == 1){
            discoverSecretPassage();
        } else {
            hideSecretPassage();
        }
    }

    @Override
    public void render(GameGraphics g) {
        map.render(g);
    }

    void discoverSecretPassage(){
        if (!isSecretPassageVisible) {
            isSecretPassageVisible = true;
            map.getLayerByName("secretPassage1").setVisible(true);
            map.getLayerByName("secretPassage2").setVisible(true);

            if (!ScenesCollection.COMPASS_HOUSE_2.isSecretPassageVisible)
                ScenesCollection.COMPASS_HOUSE_2.discoverSecretPassage();
        }
    }

    void hideSecretPassage(){
        if (isSecretPassageVisible) {
            isSecretPassageVisible = false;
            map.getLayerByName("secretPassage1").setVisible(false);
            map.getLayerByName("secretPassage2").setVisible(false);

            if (ScenesCollection.COMPASS_HOUSE_2.isSecretPassageVisible)
                ScenesCollection.COMPASS_HOUSE_2.hideSecretPassage();
        }
    }
}
