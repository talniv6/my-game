package com.talniv.game.Scenes.beta;


import com.talniv.game.Scenes.Scene;


public class UnderGround extends Scene {

    private EventsManager eventsManager;
    private ArrayList<Shape> back;

    public UnderGround(String pathToMap) {
        super(pathToMap);
        map.camera.centerOnObject(map.getPlayer());
        eventsManager = new EventsManager();
        buildScene();
    }

    private void buildScene(){
        Shape exit_to_compass_2 = map.getEventBox("exit_to_compass_2");
        if (exit_to_compass_2 == null){
            System.out.println("at UnderGround- exit_to_compass_2 is null");
            System.exit(1);
        }
        Event exitToCompassHouse_2 = new Event(() -> {
            if (map.getPlayer().collisionShapeIntersects(exit_to_compass_2) && map.getPlayer().getCurDirection() == MapObject.DOWN) {
                changeScene(ScenesCollection.COMPASS_HOUSE_2, 65, 25, MapObject.RIGHT, null);
            }
        }, null, true);
        eventsManager.addEvent(exitToCompassHouse_2);

        Shape exit_to_compass_1 = map.getEventBox("exit_to_compass_1");
        if (exit_to_compass_1 == null){
            System.out.println("at UnderGround- exit_to_compass_1 is null");
            System.exit(1);
        }
        Event exitToCompassHouse_1 = new Event(() -> {
            if (map.getPlayer().collisionShapeIntersects(exit_to_compass_1) && map.getPlayer().getCurDirection() == MapObject.DOWN) {
                changeScene(ScenesCollection.COMPASS_HOUSE_1, 305, 315, MapObject.LEFT, null);
            }
        }, null, true);
        eventsManager.addEvent(exitToCompassHouse_1);

        back = map.getEventsByName("back");
    }

    @Override
    public void tick() {
        eventsManager.tick();
        map.tick();

        if (checkIfBack()){
            changeScene(ScenesCollection.BETA_MAIN, 250, 250, MapObject.DOWN, null);
        }
    }

    @Override
    public void render(GameGraphics g) {
        map.render(g);
        eventsManager.render(g);
    }

    private boolean checkIfBack(){
        for (Shape shape : back){
            if (map.getPlayer().collisionShapeIntersects(shape)){
                return true;
            }
        }
        return false;
    }

}
