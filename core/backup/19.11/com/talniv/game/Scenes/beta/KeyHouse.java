package com.talniv.game.Scenes.beta;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.GameObjects.Person;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;
import com.talniv.game.utils.PersonLoader;


public class KeyHouse extends Scene{
    private Person father;
    private Person daughter;
    private Box exit;
    private boolean text=false;

    public KeyHouse(String path) {
        super(path);
        PersonLoader personLoader = new PersonLoader();
        father = personLoader.loadPerson(map, "general/oldman.txt", 455, 185);
        father.addText(TextLoader.loadText("beta/father.txt"));

        daughter = personLoader.loadPerson(map, "general/blondgirl.txt", 100, 345);
        for (int i = 0; i < 19; i++) {
            daughter.addText(TextLoader.loadText("beta/daughterText/"+i+".txt"));
        }

        map.addObject(father);
        map.addObject(daughter);

        exit = map.getEventByName("exit");
    }

    @Override
    public void tick(float dt) {

        if(!Game.ui.isActive())
            map.tick(dt);

        if (father.playerTurn()){
            father.setDirection(father.orientation(map.getPlayer()));
            Game.ui.showText(father.getText(0));
        }

        if (daughter.playerTurn()){
            if (!Game.ui.isActive()) {
                Game.ui.showText(daughter.getText(0));
                text = true;
            }
        }
        if (text && !Game.ui.isActive()){
            Game.ui.showText(daughter.getText(1));
            text = false;
        }


        if (map.getPlayer().getCollisionBox().intersect(exit)){
            changeScene(ScenesCollection.BETA_MAIN, 132, 1495, MapObject.UP);
        }
    }

    @Override
    public void render(Batch batch) {
        map.render();
    }
}
