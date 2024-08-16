package Progetto.Comunicazione;

import java.io.File;

import Progetto.Main.Global;
import Utilities.SqLite;

public class Comunicatore {

    private SqLite sqlite;
    private String database;

    public Comunicatore() {
        sqlite = new SqLite(Global.pathToDB);
        
    }
    
    public String[] getTables(String nomeDB) {
        if(database == null || !database.equals(nomeDB)) database = nomeDB;

        changePath();

        return sqlite.getTables();
    }

    private void changePath() {
        sqlite.changePath(Global.pathToDB + database + ".db");
    }

    public int getColumnNumber(String nomeTabella) {
        return sqlite.getColumns(nomeTabella).length;
    }

    public int getRowNumber(String nomeTabella) {
        return sqlite.select("SELECT * FROM " + nomeTabella, nomeTabella).length;
    }
}