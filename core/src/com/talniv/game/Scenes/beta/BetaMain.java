package com.talniv.game.Scenes.beta;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.talniv.game.Assets;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.GameObjects.Person;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;
import com.talniv.game.ui.TextEvent;
import com.talniv.game.utils.ShortEvent;


public class BetaMain extends Scene {

    private Person oldman, blondman, girlWithRope;
    private Ghost ghost;
    private Box entrance_compass_1, entrance_compass_2, entrance_keyHouse, monument;
    private boolean isPlayerGhost=false, isBeerHouseOpen=false;

    public BetaMain(String path){
        super(path);
        create();
    }

    private void create(){
        oldman = map.addPerson("general/people/old-man.txt", map.getObjectByName("oldman"));
        oldman.setDirection(MapObject.RIGHT);

        blondman = map.addPerson("general/people/adult-pink-hair.txt", map.getObjectByName("blondman"));
        blondman.setDirection(MapObject.LEFT);

        girlWithRope = map.addPerson("general/people/girl-with-rope.txt", map.getObjectByName("girl"));
        girlWithRope.setDirection(MapObject.DOWN);
        girlWithRope.getTexture().setFrameDuration(girlWithRope.getTexture().standDown, 0.08f);

        ghost = new Ghost(map, map.getObjectByName("ghost").getX(), map.getObjectByName("ghost").getY());
        map.addObject(ghost);

        entrance_compass_1 = map.getEventByName("compass_1");
        entrance_compass_2 = map.getEventByName("compass_2");
        entrance_keyHouse = map.getEventByName("entrance_keyHouse");
        monument = map.getEventByName("monument");

        eventsManager.add(talkToBlondMan);
        eventsManager.add(talkToOldMan);
        eventsManager.add(talkToGirl);
        eventsManager.add(monumentEvent);

        eventsManager.add(ghost.ghostDisappear);
        eventsManager.add(ghost.ghostTalking);
        eventsManager.add(ghost.showGhost);

        eventsManager.add(laugh);
        eventsManager.add(playerGhost);

    }


    private boolean checkEnterCompasses(){
        if (map.getPlayer().getCollisionBox().intersect(entrance_compass_2) && map.getPlayer().getCurDirection() == MapObject.DOWN) {
            changeScene(ScenesCollection.COMPASS_HOUSE_2, ScenesCollection.COMPASS_HOUSE_2.map.getObjectByName("spawn"), MapObject.DOWN);
            return true;
        }
        if (map.getPlayer().getCollisionBox().intersect(entrance_compass_1) && map.getPlayer().getCurDirection() == MapObject.DOWN) {
            changeScene(ScenesCollection.COMPASS_HOUSE_1, ScenesCollection.COMPASS_HOUSE_1.map.getObjectByName("spawn"), MapObject.UP);
            return true;
        }
        return false;
    }


    public void tick(float dt){
        if(Game.ui.isActive())
            return;

        map.tick(dt);

        checkEnterCompasses();

        if(map.getPlayer().getEventsBox().intersect(entrance_keyHouse)
                && isBeerHouseOpen && map.getPlayer().getCurDirection() == MapObject.DOWN){
            changeScene(ScenesCollection.BETA_KEYHOUSE, ScenesCollection.BETA_KEYHOUSE.map.getObjectByName("door"), MapObject.DOWN);
        }

        eventsManager.tick(dt);
    }

    public void runPlayerGhostEvent(){
        isPlayerGhost = true;
    }

    public void openBeerHouse(){
        isBeerHouseOpen = true;
    }

    public void render(Batch batch) {
        map.render();

        batch.begin();
        eventsManager.render(batch);
        batch.end();
    }


    // -------------- EVENTS -----------------------------------------

    private TextEvent talkToOldMan = new TextEvent("beta/oldmantext.txt") {
        @Override
        public boolean conditionToStart() {
            return oldman.isPlayerTurn();
        }

        @Override
        public void begin() {
            oldman.setDirection(oldman.orientation(map.getPlayer()));
        }

        @Override
        public void end() {
            oldman.setDirection(MapObject.RIGHT);
        }

        @Override
        public boolean oneTimeEvent() {
            return false;
        }
    };


    private TextEvent talkToBlondMan = new TextEvent("beta/blondmantext.txt") {
        @Override
        public boolean conditionToStart() {
            return blondman.isPlayerTurn();
        }

        @Override
        public void begin() {
            blondman.setDirection(blondman.orientation(map.getPlayer()));
        }

        @Override
        public void end() {
            blondman.setDirection(MapObject.LEFT);
        }

        @Override
        public boolean oneTimeEvent() {
            return false;
        }
    };


    private TextEvent talkToGirl = new TextEvent("beta/girl-rope-text.txt") {
        @Override
        public boolean conditionToStart() {
            return girlWithRope.isPlayerTurn();
        }

        @Override
        public void begin() {

        }

        @Override
        public void end() {

        }

        @Override
        public boolean oneTimeEvent() {
            return false;
        }
    };


    private ShortEvent laugh = new ShortEvent() {
        private Box laugh = map.getEventByName("laugh");

        @Override
        public boolean conditionToStart() {
            return map.getPlayer().getCollisionBox().intersect(laugh);
        }

        @Override
        public void atStart() {
            Assets.laugh.setVolume(0.5f);
            Game.Dj.play(Assets.laugh, true);
        }

        @Override
        public boolean conditionToEnd() {
            return !map.getPlayer().getCollisionBox().intersect(laugh);
        }

        @Override
        public void atEnd() {

        }

        @Override
        public boolean oneTimeEvent() {
            return false;
        }
    };


    private ShortEvent playerGhost = new ShortEvent() {
        private Box forestRightLimit = map.getObjectByName("forestRightLimit");
        private Box forestLeftLimit = map.getObjectByName("forestLeftLimit");

        @Override
        public boolean conditionToStart() {
            return isPlayerGhost;
        }

        @Override
        public void atStart() {
            map.getPlayer().resetPosition(map.getObjectByName("playerGhost").getX(), map.getPlayer().y);
            map.getPlayer().setDirection(MapObject.LEFT);
            map.getPlayer().changeTexture(map.getPlayer().ghostTexture);
        }

        @Override
        public boolean conditionToEnd() {
            return (map.getPlayer().getEventsBox().intersect(forestRightLimit) && map.getPlayer().getCurDirection() == MapObject.RIGHT) ||
                    (map.getPlayer().getEventsBox().intersect(forestLeftLimit) && map.getPlayer().getCurDirection() == MapObject.LEFT);
        }

        @Override
        public void atEnd() {
            isPlayerGhost = false;
            Game.inputManager.wait(400);
            if (map.getPlayer().x > map.getObjectByName("ghost").getX())
                map.getPlayer().move(45, 0);
            else
                map.getPlayer().move(-45, 0);
            map.getPlayer().changeTexture(map.getPlayer().defTexture);
        }

        @Override
        public boolean oneTimeEvent() {
            return false;
        }
    };


    private TextEvent monumentEvent = new TextEvent("beta/monument.txt") {
        @Override
        public boolean conditionToStart() {
            return map.getPlayer().getEventsBox().intersect(monument) && Game.inputManager.ENTER &&
                    map.getPlayer().getCurDirection() == MapObject.UP;
        }

        @Override
        public void begin() {

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
