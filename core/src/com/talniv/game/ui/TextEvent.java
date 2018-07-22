package com.talniv.game.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import com.talniv.game.Game;
import com.talniv.game.utils.Event;


public abstract class TextEvent implements Event, Disposable{
    private TextGraph textGraph;
    private TextRenderer textRenderer;

    public TextEvent(String path) {
        textGraph = new TextGraph(path);
        textRenderer = new TextRenderer();
    }

    public abstract boolean conditionToStart();

    @Override
    public void atStart() {
        Game.getCurScene().map.getPlayer().setFreeze(true);
        Game.inputManager.setProtectKeys(true);
        textRenderer.start(textGraph);
        begin();
    }

    @Override
    public void tick(float dt) {
        textRenderer.tick(dt);
    }

    @Override
    public void render(Batch batch) {
        textRenderer.render(batch);
    }

    @Override
    public void atEnd() {
        Game.getCurScene().map.getPlayer().setFreeze(false);
        Game.inputManager.setProtectKeys(false);
        end();
    }

    @Override
    public boolean conditionToEnd() {
        return !textRenderer.isActive();
    }

    @Override
    public void dispose() {
        textRenderer.dispose();
    }

    public abstract void begin();

    public abstract void end();

    public TextGraph getTextGraph() {
        return textGraph;
    }

    public void setTextGraph(TextGraph textGraph) {
        this.textGraph = textGraph;
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }
}
