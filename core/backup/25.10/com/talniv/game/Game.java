package com.talniv.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.beta.BetaMain;

public class Game implements ApplicationListener{
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 360;
	private Batch batch;
	public static final InputManager inputManager = new InputManager();
	private Viewport gamePort;

	private BetaMain beta_main;


	@Override
	public void create () {
		batch = new SpriteBatch();
        Gdx.input.setInputProcessor(new GestureDetector(inputManager));

		gamePort = new FitViewport(16, 9);

		beta_main = new BetaMain("beta/beta_main.tmx");

		Gdx.gl.glClearColor(0, 0, 0, 0);

	}

	@Override
	// game loop
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();

		// tick
		inputManager.tick();
		beta_main.tick(dt);

		// render
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		beta_main.render(batch);

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void resize(int width, int height){
		gamePort.update(width,height);
	}
}
