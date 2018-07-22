package com.talniv.game.Scenes.beta;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.GameObjects.Person;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;
import com.talniv.game.ui.TextEvent;


public class CompassHouse_1 extends Scene {

    private Box exit;
    boolean isSecretPassageVisible = false;
    private Box secretPassage;
    private Person man;
    Compass compass;

    public CompassHouse_1(String pathToMap) {
        super(pathToMap);
        buildScene();
    }

    public void buildScene() {
        exit = map.getEventByName("exit");
        secretPassage = map.getEventByName("secretPassage");

        man = map.addPerson("general/people/bold-man.txt", map.getObjectByName("person"));

        compass = new Compass(map, map.getObjectByName("compass").getX(), map.getObjectByName("compass").getY());
        compass.setIndex(0);
        map.addObject(compass);

        eventsManager.add(talkToMan);
    }

    @Override
    public void tick(float dt) {
        if (Game.ui.isActive())
            return;

        map.tick(dt);
        if (map.getPlayer().getCollisionBox().intersect(exit)  && map.getPlayer().getCurDirection() == MapObject.DOWN){
            changeScene(ScenesCollection.BETA_MAIN, ScenesCollection.BETA_MAIN.map.getObjectByName("exit_compass_1"), MapObject.UP);
        }
        if (isSecretPassageVisible && map.getPlayer().getCollisionBox().intersect(secretPassage)){
            changeScene(ScenesCollection.BETA_UNDER_GROUND, ScenesCollection.BETA_UNDER_GROUND.getMap().getObjectByName("spawn1"), MapObject.UP);
        }
        if (map.getPlayer().getEventsBox().intersect(compass.getEventsBox()) && Game.inputManager.ENTER){
            compass.progressIndex();
        }
        if (compass.getIndex() == 3 && ScenesCollection.COMPASS_HOUSE_2.compass.getIndex() == 1){
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
        if (!isSecretPassageVisible) {
            isSecretPassageVisible = true;
            map.getMap().getLayers().get("hidden passage").setVisible(true);

            if (!ScenesCollection.COMPASS_HOUSE_2.isSecretPassageVisible)
                ScenesCollection.COMPASS_HOUSE_2.discoverSecretPassage();
        }
    }

    void hideSecretPassage(){
        if (isSecretPassageVisible) {
            isSecretPassageVisible = false;
            map.getMap().getLayers().get("hidden passage").setVisible(false);

            if (ScenesCollection.COMPASS_HOUSE_2.isSecretPassageVisible)
                ScenesCollection.COMPASS_HOUSE_2.hideSecretPassage();
        }
    }


    private TextEvent talkToMan = new TextEvent("beta/i-knew-emmil.txt") {
        @Override
        public boolean conditionToStart() {
            return man.isPlayerTurn();
        }

        @Override
        public void begin() {
            man.setDirection(man.orientation(map.getPlayer()));
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

