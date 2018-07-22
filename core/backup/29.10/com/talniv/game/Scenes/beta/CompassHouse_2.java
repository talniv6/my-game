package com.talniv.game.Scenes.beta;

import com.talniv.game.Scenes.Scene;



public class CompassHouse_2 extends Scene {

    private Shape exit;
    boolean isSecretPassageVisible = false;
    private Shape secretPassage;
    Compass compass;
    private Shape compassEvent;

    public CompassHouse_2(String path) {
        super(path);
        buildScene();
    }

    public void buildScene() {
        map.camera.setxOffset(-(Game.FRAME_WIDTH-map.getMapWidth())/2);
        map.camera.setyOffset(-(Game.FRAME_WIDTH-map.getMapWidth())/2);

        exit = map.getEventBox("exit");
        secretPassage = map.getEventBox("secretPassage");

        compass = new Compass(map, 110, 120);
        compass.setIndex(2);
        map.addObject(compass);
        compassEvent = map.getEventBox("compass");
    }

    @Override
    public void tick() {
        map.tick();
        if (map.getPlayer().collisionShapeIntersects(exit)  && map.getPlayer().getCurDirection() == MapObject.UP){
            changeScene(ScenesCollection.BETA_MAIN, 745, 640, MapObject.UP, null);
        }
        if (isSecretPassageVisible && map.getPlayer().collisionShapeIntersects(secretPassage)){
            changeScene(ScenesCollection.BETA_UNDER_GROUND, 775, 490, MapObject.UP, null);
        }
        if (map.getPlayer().eventShapeIntersects(compassEvent) && getKeyManager().enter){
            compass.progressIndex();
        }
        if (compass.getIndex() == 1 && ScenesCollection.COMPASS_HOUSE_1.compass.getIndex() == 3){
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
        isSecretPassageVisible = true;
        map.getLayerByName("secretPassage1").setVisible(true);
        map.getLayerByName("secretPassage2").setVisible(true);

        if (!ScenesCollection.COMPASS_HOUSE_1.isSecretPassageVisible)
            ScenesCollection.COMPASS_HOUSE_1.discoverSecretPassage();
    }

    void hideSecretPassage(){
        if (isSecretPassageVisible) {
            isSecretPassageVisible = false;
            map.getLayerByName("secretPassage1").setVisible(false);
            map.getLayerByName("secretPassage2").setVisible(false);

            if (ScenesCollection.COMPASS_HOUSE_1.isSecretPassageVisible)
                ScenesCollection.COMPASS_HOUSE_1.hideSecretPassage();
        }
    }
}
