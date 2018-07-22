package com.talniv.game.Scenes.beta;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.Item;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.GameObjects.Person;
import com.talniv.game.GameObjects.Player;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;
import com.talniv.game.ui.TextEvent;
import com.talniv.game.utils.Event;
import com.talniv.game.utils.ShortEvent;


public class KeyHouse extends Scene{
    private Person father;
    private Person daughter;
    private Box exit, door, exit2;
    private boolean moveDaughter = false, daughterMoved = false, showGraph = false, doorOpen = false;
    private Graph graph;

    public KeyHouse(String path) {
        super(path);
        father = map.addPerson("general/people/adult-with-hat.txt", map.getObjectByName("father"));
        daughter = map.addPerson("general/people/blue-hair-girl.txt", map.getObjectByName("girl"));

        exit = map.getEventByName("exit");
        exit2 = map.getEventByName("exit2");
        door = map.getObjectByName("door");

        eventsManager.add(talkToDaughter);
        eventsManager.add(talkToFather);
        eventsManager.add(moveDaughterEvent);
        eventsManager.add(gotBeer);
        eventsManager.add(daughterSecond);
        eventsManager.add(graphEvent);
        eventsManager.add(openDoorEvent);

        graph = new Graph();
    }


    @Override
    public void tick(float dt) {
        if (Game.ui.isActive())
            return;

        if (!showGraph)
            map.tick(dt);

        eventsManager.tick(dt);

        if (map.getPlayer().getCollisionBox().intersect(exit) && map.getPlayer().getCurDirection() == MapObject.DOWN){
            changeScene(ScenesCollection.BETA_UNDER_GROUND, ScenesCollection.BETA_UNDER_GROUND.map.getObjectByName("back_from_keyHouse"), MapObject.DOWN);
        }

        if (map.getPlayer().getEventsBox().intersect(exit2) && map.getPlayer().getCurDirection() == MapObject.UP && doorOpen){
            changeScene(ScenesCollection.BETA_MAIN, ScenesCollection.BETA_MAIN.map.getObjectByName("exit-beer-house"), MapObject.UP);
        }

    }

    @Override
    public void render(Batch batch) {
        if (!showGraph)
            map.render();

        batch.begin();
        eventsManager.render(batch);
        batch.end();
    }

    private boolean checkIfMoveGirl(){
        int[] expectedPath = {0,1,3,5,6,18,19};
        Array<Integer> path = talkToDaughter.getTextGraph().getPath();
        if (path.size != expectedPath.length){
            return false;
        }
        for (int i = 0; i < expectedPath.length; i++) {
            if (expectedPath[i] != path.get(i)){
                return false;
            }
        }
        return true;
    }



    // ------------------------- EVENTS ---------------------------

    private Event talkToFather = new TextEvent("beta/father.txt") {
        @Override
        public void begin() {
            father.setDirection(father.orientation(map.getPlayer()));
        }

        @Override
        public void end() {

        }

        @Override
        public boolean conditionToStart() {
            return father.isPlayerTurn();
        }

        @Override
        public boolean oneTimeEvent() {
            return false;
        }
    };


    private Event moveDaughterEvent = new Event() {
                float moved=0;

                @Override
                public boolean conditionToStart() {
                    return moveDaughter;
                }

                @Override
                public void atStart() {
                    Game.inputManager.setHold(true);
                }

                @Override
                public void tick(float dt) {
                    daughter.move(0, daughter.getySpeed());
                    moved += daughter.getySpeed();
                }

                @Override
                public void render(Batch batch) {

                }

                @Override
                public boolean conditionToEnd() {
                    return moved > 100;
                }

                @Override
                public void atEnd() {
                    Game.inputManager.setHold(false);
                    daughter.setDirection(MapObject.DOWN);
                    daughterMoved = true;
                }

                @Override
                public boolean oneTimeEvent() {
                    return true;
                }
            };


    private TextEvent gotBeer = new TextEvent("beta/got-beer.txt") {
        private Box beerLeft = map.getEventByName("getBeerLeft"), beerDown = map.getEventByName("getBeerDown");

        @Override
        public boolean conditionToStart() {
            if(!map.getMap().getLayers().get("beer").isVisible())
                return false;
            if(map.getPlayer().getEventsBox().intersect(beerLeft) && map.getPlayer().getCurDirection() == MapObject.RIGHT &&
                    Game.inputManager.ENTER){
                return true;
            }
            if(map.getPlayer().getEventsBox().intersect(beerDown) && map.getPlayer().getCurDirection() == MapObject.UP &&
                    Game.inputManager.ENTER){
                return true;
            }
            return false;
        }

        @Override
        public void begin() {

        }

        @Override
        public void end() {
            map.getMap().getLayers().get("beer").setVisible(false);
            Player.items.add(new Item("beer"));
        }



        @Override
        public boolean oneTimeEvent() {
            return true;
        }
     };


    private Event daughterSecond = new TextEvent("beta/daughter-second.txt") {
                @Override
                public void begin() {
                    daughter.setDirection(daughter.orientation(map.getPlayer()));
                }

                @Override
                public void end() {

                }

                @Override
                public boolean conditionToStart() {
                    return daughter.isPlayerTurn() && daughterMoved;
                }

                @Override
                public boolean oneTimeEvent() {
                    return false;
                }
            };


    private TextEvent talkToDaughter = new TextEvent("beta/daughter.txt") {
        private boolean oneTimeEvent = false;
        @Override
        public void begin() {

        }

        @Override
        public void end() {
            moveDaughter = checkIfMoveGirl();
            if(moveDaughter){
                oneTimeEvent = true;
            }
        }

        @Override
        public boolean conditionToStart() {
            return daughter.isPlayerTurn();
        }

        @Override
        public boolean oneTimeEvent() {
            return oneTimeEvent;
        }
    };


    private Event graphEvent = new Event() {
        @Override
        public boolean conditionToStart() {
            return  map.getPlayer().getCollisionBox().intersect(map.getEventByName("graph")) &&
                    Game.inputManager.ENTER && map.getPlayer().getCurDirection() == MapObject.UP;
        }

        @Override
        public void atStart() {
            showGraph = true;
        }

        @Override
        public void tick(float dt) {
            graph.tick(dt);
        }

        @Override
        public void render(Batch batch) {
            graph.render(batch);
        }

        @Override
        public boolean conditionToEnd() {
            return Game.inputManager.ENTER;
        }

        @Override
        public void atEnd() {
            showGraph = false;
        }

        @Override
        public boolean oneTimeEvent() {
            return false;
        }
    };


    private ShortEvent openDoorEvent = new ShortEvent() {
        @Override
        public boolean conditionToStart() {
            return map.getPlayer().getEventsBox().intersect(door) && map.getPlayer().getCurDirection() == MapObject.UP
                    && Game.inputManager.ENTER;
        }

        @Override
        public void atStart() {
            map.getMap().getLayers().get("door").setVisible(false);
        }

        @Override
        public boolean conditionToEnd() {
            return Game.inputManager.UP || !map.getPlayer().getEventsBox().intersect(door);
        }

        @Override
        public void atEnd() {
            doorOpen = true;
            ScenesCollection.BETA_MAIN.openBeerHouse();
        }

        @Override
        public boolean oneTimeEvent() {
            return true;
        }
    };
}
