package com.talniv.game.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

public class EventsManager {
    private Array<Event> waiting, running, almostRunning;

    public EventsManager() {
        waiting = new Array<Event>();
        running = new Array<Event>();
        almostRunning = new Array<Event>();
    }

    public void tick(float dt){
        for (int i = 0; i < almostRunning.size; i++) {
            running.add(almostRunning.get(i));
            almostRunning.removeIndex(i);
        }
        for (int i = 0; i < waiting.size; i++) {
            if (waiting.get(i).conditionToStart()) {
                almostRunning.add(waiting.get(i));
                waiting.get(i).atStart();
                waiting.removeIndex(i);
            }
        }
        for (int i = 0; i < running.size; i++) {
            if (!running.get(i).conditionToEnd()){
                running.get(i).tick(dt);
            }
            else {
                running.get(i).atEnd();

                if (!running.get(i).oneTimeEvent()){
                    waiting.add(running.get(i));
                }
                running.removeIndex(i);
            }
        }
    }

    public void render(Batch batch){
        for (int i = 0; i < running.size; i++) {
            running.get(i).render(batch);
        }
    }

    public void add(Event event){
        waiting.add(event);
    }
}
