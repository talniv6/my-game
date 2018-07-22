package com.talniv.game.Scenes;

import com.talniv.game.Scenes.beta.BetaMain;
import com.talniv.game.Scenes.beta.CompassHouse_1;
import com.talniv.game.Scenes.beta.CompassHouse_2;
import com.talniv.game.Scenes.beta.KeyHouse;
import com.talniv.game.Scenes.beta.UnderGround;
import com.talniv.game.Scenes.hilbert.HilbertMain;

public class ScenesCollection {

    public static BetaMain BETA_MAIN;
    public static CompassHouse_2 COMPASS_HOUSE_2;
    public static CompassHouse_1 COMPASS_HOUSE_1;
    public static UnderGround BETA_UNDER_GROUND;
    public static KeyHouse BETA_KEYHOUSE;

    public static HilbertMain HILBERT_MAIN;

    public static void build(){
        BETA_MAIN = new BetaMain("beta/beta_main2.tmx");
        COMPASS_HOUSE_2 = new CompassHouse_2("beta/compass2.tmx");
        COMPASS_HOUSE_1 = new CompassHouse_1("beta/compass1.tmx");
        BETA_UNDER_GROUND = new UnderGround("beta/under-ground.tmx");
        BETA_KEYHOUSE = new KeyHouse("beta/beer-house.tmx");

        HILBERT_MAIN = new HilbertMain("hilbert/hilbert_main.tmx");
    }

    public static void dispose(){
        BETA_MAIN.dispose();
        COMPASS_HOUSE_1.dispose();
        COMPASS_HOUSE_2.dispose();
        BETA_MAIN.dispose();
        BETA_KEYHOUSE.dispose();
    }

}
