package com.talniv.game.Scenes.beta;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.GameObjects.Person;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;
import com.talniv.game.ui.TextEvent;
import com.talniv.game.utils.Event;


public class CompassHouse_2 extends Scene {

    private Box exit;
    boolean isSecretPassageVisible = false;
    private Box secretPassage;
    Compass compass;
    private Person woman;

    public CompassHouse_2(String path) {
        super(path);
        buildScene();
    }

    public void buildScene() {
        exit = map.getEventByName("exit");
        secretPassage = map.getEventByName("secretPassage");
        woman = map.addPerson("general/people/adult-woman.txt", map.getObjectByName("woman"));
        woman.setDirection(MapObject.RIGHT);

        compass = new Compass(map, map.getObjectByName("compass").getX(), map.getObjectByName("compass").getY());
        compass.setIndex(2);
        map.addObject(compass);

        eventsManager.add(talkToWoman);
    }

    @Override
    public void tick(float dt) {
        if (Game.ui.isActive())
            return;

        map.tick(dt);
        if (map.getPlayer().getCollisionBox().intersect(exit)  && map.getPlayer().getCurDirection() == MapObject.UP){
            changeScene(ScenesCollection.BETA_MAIN, ScenesCollection.BETA_MAIN.map.getObjectByName("exit_compass_2"), MapObject.UP);
        }
        if (isSecretPassageVisible && map.getPlayer().getCollisionBox().intersect(secretPassage)){
            changeScene(ScenesCollection.BETA_UNDER_GROUND, ScenesCollection.BETA_UNDER_GROUND.getMap().getObjectByName("spawn2"), MapObject.UP);
        }
        if (map.getPlayer().getEventsBox().intersect(compass.getEventsBox()) && Game.inputManager.ENTER){
            compass.progressIndex();
        }
        if (compass.getIndex() == 1 && ScenesCollection.COMPASS_HOUSE_1.compass.getIndex() == 3){
            discoverSecretPassage();
        } else {
            hideSecretPassage();
        }

        eventsManager.tick(dt);

    }

    @Override
    public void render(Batch batch) {
        map.render();

        batch.begin();
        eventsManager.render(batch);
        batch.end();
    }

    void discoverSecretPassage(){
        isSecretPassageVisible = true;
        map.getMap().getLayers().get("hidden passage").setVisible(true);

        if (!ScenesCollection.COMPASS_HOUSE_1.isSecretPassageVisible)
            ScenesCollection.COMPASS_HOUSE_1.discoverSecretPassage();
    }

    void hideSecretPassage(){
        if (isSecretPassageVisible) {
            isSecretPassageVisible = false;
            map.getMap().getLayers().get("hidden passage").setVisible(false);

            if (ScenesCollection.COMPASS_HOUSE_1.isSecretPassageVisible)
                ScenesCollection.COMPASS_HOUSE_1.hideSecretPassage();
        }
    }


    // ----------------------------- EVENTS ------------------------------------

    private TextEvent talkToWoman = new TextEvent("beta/no-one-came-back.txt") {
        @Override
        public boolean conditionToStart() {
            return woman.isPlayerTurn();
        }

        @Override
        public void begin() {
            woman.setDirection(woman.orientation(map.getPlayer()));
        }

        @Override
        public void end() {

        }

        @Override
        public boolean oneTimeEvent() {
            return false;
        }
    };

}


