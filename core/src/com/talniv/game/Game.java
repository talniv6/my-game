package com.talniv.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.talniv.game.GameObjects.MapObject;
import com.talniv.game.Scenes.Scene;
import com.talniv.game.Scenes.ScenesCollection;
import com.talniv.game.map.Box;
import com.talniv.game.ui.UI;
import com.talniv.game.utils.DJ;

public class Game implements ApplicationListener{
	public static final float V_WIDTH = 720; // 752
	public static final float V_HEIGHT = 405; //432

	public static InputManager inputManager;
	public static UI ui;
	public static DJ Dj;

	private Batch batch;
	private Viewport gamePort;

	private int fps=0;
	private long time;

	private static Scene curScene;


	@Override
	public void create () {
		batch = new SpriteBatch();

		gamePort = new FitViewport(16, 9);

		Assets.init();

		inputManager = new InputManager();
		Gdx.input.setInputProcessor(new GestureDetector(inputManager));
		ui = new UI();
		Dj = new DJ();

		ScenesCollection.build();

		Gdx.gl.glClearColor(0, 0, 0, 0);
		time = System.currentTimeMillis();

		changeScene(ScenesCollection.HILBERT_MAIN, ScenesCollection.HILBERT_MAIN.map.getObjectByName("spawn"), MapObject.DOWN);
	}

	@Override
	// game loop
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();

		 // tick
		inputManager.tick();
		curScene.tick(dt);
		ui.tick(dt);

		// render
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		curScene.render(batch);
		ui.render();


		fps++;

		if (System.currentTimeMillis() - time >= 1000){
			/// System.out.println(fps);
			fps = 0;
			time = System.currentTimeMillis();
		}

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
		ScenesCollection.dispose();
		ui.dispose();
	}

	public void resize(int width, int height){
		gamePort.update(width,height);
	}

	public static void changeScene(Scene scene, float spawnX, float spawnY, int direction){
		if (spawnX > scene.map.getMapPixelWidth() || spawnX < 0){
			System.out.println("Invalid player X position");
			System.exit(1);
		}
		if (spawnY > scene.map.getMapPixelHeight() || spawnY < 0){
			System.out.println("Invalid player Y position");
			System.exit(1);
		}
		scene.map.getPlayer().resetPosition(spawnX, spawnY);

		if (direction != MapObject.DOWN && direction != MapObject.UP && direction != MapObject.LEFT && direction != MapObject.RIGHT){
			System.out.println("Invalid direction");
			direction = MapObject.DOWN;
		}
		scene.map.getPlayer().setDirection(direction);

//		if (actions != null)
//			actions.run();

		curScene = scene;
		inputManager.wait(500);
		curScene.tick(Gdx.graphics.getDeltaTime());
	}

	public static void changeScene(Scene scene, Box position , int direction){
		changeScene(scene, position.getX(), position.getY(), direction);
	}

	public static Scene getCurScene() {
		return curScene;
	}
}
