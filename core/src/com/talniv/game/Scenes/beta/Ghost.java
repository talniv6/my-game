package com.talniv.game.Scenes.beta;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.Assets;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.Character;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.GameObjects.Player;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;
import com.talniv.game.map.Map;
import com.talniv.game.ui.TextEvent;
import com.talniv.game.ui.TextGraph;
import com.talniv.game.utils.Event;

public class Ghost extends Character{

    private Animation<TextureRegion> showAnim, standAnim, disAnim;
    private float timer=0;
    private Box position, initialization;
    private boolean stand, show, disappear;

    public Ghost(Map map, float x, float y) {
        super(map, x, y, 18, 18);
        setSolid(false);
        show = false;
        disappear = false;
        stand = false;

        position = map.getObjectByName("ghost");
        initialization = map.getEventByName("laugh");

        TextureRegion[][] spriteSheet = Assets.ghost.split(18, 18);

        Array<TextureRegion> show = new Array<TextureRegion>(spriteSheet[0]);
        show.add(spriteSheet[1][0]);
        showAnim = new Animation<TextureRegion>(0.2f, show);

        Array<TextureRegion> disappear = new Array<TextureRegion>();
        disappear.add(spriteSheet[1][0]);
        disappear.add(spriteSheet[0][3]);
        disappear.add(spriteSheet[0][2]);
        disappear.add(spriteSheet[0][1]);
        disappear.add(spriteSheet[0][0]);
        disAnim = new Animation<TextureRegion>(0.2f, disappear);

        Array<TextureRegion> standing = new Array<TextureRegion>();
        standing.add(spriteSheet[1][0]);
        standing.add(spriteSheet[1][1]);
        standAnim = new Animation<TextureRegion>(0.4f, standing);
    }

    @Override
    public void tick(float dt) {
        if (show || disappear || stand){
            timer += dt;
        }
    }

    @Override
    public void render(Batch batch) {
        if (batch == null)
            return;
        if (show){
            batch.draw(showAnim.getKeyFrame(timer, false), position.getX(), position.getY());
        } else if (stand){
            batch.draw(standAnim.getKeyFrame(timer, true), position.getX(), position.getY());
        } else if (disappear){
            batch.draw(disAnim.getKeyFrame(timer, false), position.getX(), position.getY());
        }
    }

    public void show(){
        show = true;
        stand = false;
        disappear = false;
        timer = 0;
    }

    public void stand(){
        stand = true;
        show = false;
        disappear = false;
        timer = 0;
    }

    public void disappear(){
        disappear = true;
        show = false;
        stand = false;
        timer = 0;
    }

    // ---------------------------------------- EVENTS ----------------------------------

    public Event showGhost = new Event() {
        @Override
        public boolean conditionToStart() {
            return map.getPlayer().getCollisionBox().intersect(initialization) && Game.inputManager.ENTER && !stand && !disappear;
        }

        @Override
        public void atStart() {
            map.getPlayer().setFreeze(true);
            map.getPlayer().setDirection(MapObject.LEFT);
            Assets.ghostCreation.setVolume(0.5f);
            Game.Dj.play(Assets.ghostCreation);
            show();
        }

        @Override
        public void tick(float dt) {

        }

        @Override
        public void render(Batch batch) {

        }

        @Override
        public boolean conditionToEnd() {
            return showAnim.isAnimationFinished(timer);
        }

        @Override
        public void atEnd() {
            stand();
        }

        @Override
        public boolean oneTimeEvent() {
            return false;
        }
    };

    public TextEvent ghostTalking = new TextEvent("beta/ghost-def-text.txt") {

        TextGraph secondText = new TextGraph("beta/ghost-second-text.txt");
        TextGraph thirdText = new TextGraph("beta/ghost-third-text.txt");

        private boolean hasBeer=false;
        private int beerIndex = -1;

        @Override
        public boolean conditionToStart() {
            return stand;
        }

        public void atStart() {
            Game.getCurScene().map.getPlayer().setFreeze(true);
            Game.inputManager.setProtectKeys(true);
            timer = 0;
            if (hasBeer){
                this.setTextGraph(thirdText);
            }
            else {
                beerIndex = map.getPlayer().hasItem("beer");
                if (beerIndex != -1) {
                    this.setTextGraph(secondText);
                    hasBeer = true;
                }
            }
            getTextRenderer().start(getTextGraph());
        }

        @Override
        public void begin() {

        }

        @Override
        public void end() {
            disappear();
            if (hasBeer && getTextGraph().equals(secondText)) {
                int[] expectedPath = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

                if (getTextGraph().arePathsEqual(expectedPath)){
                    Player.items.removeIndex(beerIndex);
                    ScenesCollection.BETA_MAIN.runPlayerGhostEvent();
                }
                else {
                    hasBeer = false;
                }
            }
            else if (hasBeer){
                if (getTextGraph().getPath().get(1) == 1){
                    ScenesCollection.BETA_MAIN.runPlayerGhostEvent();
                }
            }
        }

        @Override
        public boolean oneTimeEvent() {
            return false;
        }
    };

    public Event ghostDisappear = new Event() {
        @Override
        public boolean conditionToStart() {
            return disappear;
        }

        @Override
        public void atStart() {
            Assets.ghostCreation.setVolume(0.5f);
            Game.Dj.play(Assets.ghostCreation);
            timer = 0;
        }

        @Override
        public void tick(float dt) {

        }

        @Override
        public void render(Batch batch) {

        }

        @Override
        public boolean conditionToEnd() {
            return disAnim.isAnimationFinished(timer);
        }

        @Override
        public void atEnd() {
            disappear = false;
        }

        @Override
        public boolean oneTimeEvent() {
            return false;
        }
    };
}


