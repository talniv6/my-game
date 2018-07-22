package com.talniv.game.Scenes.beta;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.GameObjects.Person;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;
import com.talniv.game.ui.TextGraph;
import com.talniv.game.utils.PersonLoader;
import com.talniv.game.utils.TemporaryAction;
import com.talniv.game.utils.Ticker;


public class KeyHouse extends Scene{
    private Person father;
    private Person daughter;
    private Box exit;
    private boolean daughterSpoke=false;
    private Ticker ticker;
    private TextGraph daughterSecond;

    public KeyHouse(String path) {
        super(path);
        ticker = new Ticker();
        father = map.addPerson("general/oldman.txt", 455, 185);
        father.setText(new TextGraph("beta/father.txt"));

        daughterSecond = new TextGraph("beta/daughterSecond.txt");
        daughter = map.addPerson("general/blondgirl.txt", 100, 345);
        daughter.setText(new TextGraph("beta/daughter.txt"));

        map.addObject(father);
        map.addObject(daughter);

        exit = map.getEventByName("exit");
    }

    @Override
    public void tick(float dt) {
        if (Game.ui.isActive())
            return;

        map.tick(dt);

        if (father.playerTurn()){
            father.setDirection(father.orientation(map.getPlayer()));
            Game.ui.showText(father.getText());
        }

        if (daughterSpoke){
            checkIfMoveGirl();
            daughterSpoke = false;
        }
        if (daughter.playerTurn()){
            daughter.setDirection(daughter.orientation(map.getPlayer()));
            Game.ui.showText(daughter.getText());
            daughterSpoke = true;
        }

        if (map.getPlayer().getCollisionBox().intersect(exit)){
            changeScene(ScenesCollection.BETA_MAIN, 132, 1495, MapObject.UP);
        }

        ticker.tick(dt);
    }

    @Override
    public void render(Batch batch) {
        map.render();
        ticker.render(batch);
    }

    private void checkIfMoveGirl(){
        int[] expectedPath = {0,1,3,5,6,18,19};
        Array<Integer> path = daughter.getText().getPath();
        if (path.size != expectedPath.length){
            return;
        }
        for (int i = 0; i < expectedPath.length; i++) {
            if (expectedPath[i] != path.get(i)){
                return;
            }
        }
        ticker.add(moveGirl);
    }

    private TemporaryAction moveGirl = new TemporaryAction() {
        float moved=0;
        @Override
        public void tick(float dt) {
            daughter.move(0, daughter.getySpeed());
            moved += daughter.getySpeed();
        }

        @Override
        public void start() {
            Game.inputManager.setHold(true);
        }

        @Override
        public void render(Batch batch) {

        }

        @Override
        public void end() {
            Game.inputManager.setHold(false);
            daughter.setDirection(MapObject.DOWN);
            daughter.setText(daughterSecond);
        }

        @Override
        public boolean condition() {
            return moved<=100;
        }
    };
}
