package com.talniv.game.Scenes.beta;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.Item;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.GameObjects.Person;
import com.talniv.game.GameObjects.Player;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;
import com.talniv.game.utils.PersonLoader;


public class BetaMain extends Scene {

    private Person oldman, blondman;
    private Box entrance_compass_1, entrance_compass_2, entrance_keyHouse;

    public BetaMain(String path){
        super(path);
        create();
    }

    private void create(){
        PersonLoader personLoader = new PersonLoader();
        oldman = personLoader.loadPerson(map, "general/oldman.txt", 77, 1010);
        oldman.setDirection(MapObject.RIGHT);
        oldman.addText(TextLoader.loadText("beta/oldmantext.txt"));
        map.addObject(oldman);

        blondman = personLoader.loadPerson(map, "general/blondman.txt", 435, 530);
        blondman.setDirection(MapObject.RIGHT);
        blondman.addText(TextLoader.loadText("beta/blondmantext.txt"));
        map.addObject(blondman);

        entrance_compass_1 = map.getEventByName("entrance_compass_1");
        entrance_compass_2 = map.getEventByName("entrance_compass_2");
        entrance_keyHouse = map.getEventByName("entrance_keyHouse");

        Player.items.add(new Item("item1"));
        Player.items.add(new Item("item2"));
        Player.items.add(new Item("item3"));
        Player.items.add(new Item("item4"));

    }

    private boolean checkTurningToPersons(){
        if (oldman.playerTurn()){
            oldman.setDirection(oldman.orientation(map.getPlayer()));
            Game.ui.showText(oldman.getText(0));
            return true;
        }
        else {
            oldman.setDirection(MapObject.RIGHT);
        }

        if (blondman.playerTurn()){
            blondman.setDirection(blondman.orientation(map.getPlayer()));
            Game.ui.showText(blondman.getText(0));
            return true;
        }
        else {
            blondman.setDirection(MapObject.RIGHT);
        }
        return false;
    }

    private boolean checkEnterCompasses(){
        if (map.getPlayer().getCollisionBox().intersect(entrance_compass_2) && map.getPlayer().getCurDirection() == MapObject.DOWN) {
            changeScene(ScenesCollection.COMPASS_HOUSE_2, 168, 300, MapObject.DOWN);
            return true;
        }
        else if (map.getPlayer().getCollisionBox().intersect(entrance_compass_1) && map.getPlayer().getCurDirection() == MapObject.DOWN) {
            changeScene(ScenesCollection.COMPASS_HOUSE_1, 200, 10, MapObject.UP);
            return true;
        }
        return false;
    }


    public void tick(float dt){
        if(Game.ui.isActive())
            return;

        map.tick(dt);

        checkEnterCompasses();
        checkTurningToPersons();
        if(map.getPlayer().getCollisionBox().intersect(entrance_keyHouse)){
            changeScene(ScenesCollection.BETA_KEYHOUSE, 305, 32, MapObject.UP);
        }
    }


    public void render(Batch batch) {
        map.render();
    }
}
