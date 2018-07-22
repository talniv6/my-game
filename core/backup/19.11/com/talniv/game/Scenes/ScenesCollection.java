package com.talniv.game.Scenes;

import com.talniv.game.Scenes.beta.BetaMain;
import com.talniv.game.Scenes.beta.CompassHouse_1;
import com.talniv.game.Scenes.beta.CompassHouse_2;
import com.talniv.game.Scenes.beta.KeyHouse;
import com.talniv.game.Scenes.beta.UnderGround;

public class ScenesCollection {

    public static BetaMain BETA_MAIN;
    public static CompassHouse_2 COMPASS_HOUSE_2;
    public static CompassHouse_1 COMPASS_HOUSE_1;
    public static UnderGround BETA_UNDER_GROUND;
    public static KeyHouse BETA_KEYHOUSE;

    public static void build(){
        BETA_MAIN = new BetaMain("beta/beta_main.tmx");
        COMPASS_HOUSE_2 = new CompassHouse_2("beta/beta_compassHouse2.tmx");
        COMPASS_HOUSE_1 = new CompassHouse_1("beta/beta_compassHouse1.tmx");
        BETA_UNDER_GROUND = new UnderGround("beta/beta_underGround.tmx");
        BETA_KEYHOUSE = new KeyHouse("beta/beta_keyHouse.tmx");
    }

    public static void dispose(){
        BETA_MAIN.dispose();
        COMPASS_HOUSE_1.dispose();
        COMPASS_HOUSE_2.dispose();
        BETA_MAIN.dispose();
        BETA_KEYHOUSE.dispose();
    }

}
