package it.ji.game.server;

import it.ji.game.server.manager.ServerGameManager;
import it.ji.game.utils.logging.LoggerG;

public class Main {

    public static void main(String[] args) {
        LoggerG.setMessage("[JI Game Server] Starting...").system();
        ServerGameManager.getInstance().startServer();
        LoggerG.setMessage("\r[JI Game Server] Started ... OK").system();
    }
}
