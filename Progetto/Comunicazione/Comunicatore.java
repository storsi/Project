package Progetto.Comunicazione;

import Progetto.Main.Global;
import Utilities.SqLite;

public class Comunicatore {

    private SqLite sqlite;
    private String database;

    public Comunicatore() {
        sqlite = new SqLite("./SqLite/Databases/");
    }
    
    public String[] getTables(String nomeDB) {
        if(database == null || !database.equals(nomeDB)) database = nomeDB;

        changePath();

        return sqlite.getTables();
    }

    private void changePath() {
        sqlite.changePath(Global.pathToDB + database + ".db");
    }
}