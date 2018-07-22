package com.talniv.game.Scenes.beta;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.talniv.game.Assets;
import com.talniv.game.Game;

public class Graph {
    private OrthographicCamera camera;

    public Graph() {
        camera = new OrthographicCamera(Game.V_WIDTH, Game.V_HEIGHT);
        camera.position.x = Game.V_WIDTH/2;
        camera.position.y = Game.V_HEIGHT/2;
        camera.update();
    }

    public void tick(float dt){

    }

    public void render(Batch batch){
        batch.setProjectionMatrix(camera.combined);
        batch.draw(Assets.beta_graph, 0, 0, Game.V_WIDTH, Game.V_HEIGHT);
    }

}
