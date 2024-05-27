package it.ji.game.utils;

import it.ji.game.utils.logging.LoggerG;
import it.ji.game.utils.settings.Settings;

public class Main {

    public static void main(String[] args) {
        LoggerG.setMessage("[CLIENT] Loading settings...").system();
        Settings.getInstance();
        LoggerG.setMessage("[CLIENT] Settings loaded").system();
    }
}
