package Progetto.Barre;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;

import Progetto.DBM.CreaTabella;
import Progetto.Main.Global;
import Progetto.Main.Panel;
import Progetto.Main.Interface.Clickable;
import Progetto.Main.Interface.Hover;
import Progetto.Main.Strumenti.Informazione;

public class BtnIcon extends JLabel implements Clickable, Hover{

    public static final int AGGIUNGI_TABELLA = 0, MOSTRA_SCHEMA = 1, ELIMINA = 2, AGGIUNGI = 3, CONFERMA = 4;
    public static final int ANNULLA = 5, PROSEGUI = 6, RIDUCI_BARRA = 7, ALLARGA_BARRA = 8, ELIMINA_NERO = 9;
    public static final int CREA_COLONNA = 10, RESET_COLONNA = 11;

    public static final String[] NOMI_PRIMA_BARRA = {"Database"};
    public static final String[] NOMI_TERZA_BARRA = {
        "Aggiungi Tabella", "Mostra Schema", "Elimina", "Aggiungi", "Conferma", "Annulla", "Prosegui", 
        "Riduci", "Allarga", "Elimina Tabella", "Crea Colonna", "Reset Informazioni"
        };

    private String messaggio, categoria;
    private int tipologia, iconSize, profonditaMax;
    private Icon icon;
    private SecondaBarra sb;
    private PanelTabella pt;
    private PopUp avviso;
    private Barra barra;
    private boolean infoActive, hoverAttivo, hoverable;
    private Component parent;
    private Panel panel;
    private Informazione info;
    private CreaTabella ct;

    
    public BtnIcon(int tipologia, Object obj) {

        if(obj instanceof Barra) barra = (Barra)obj;
        else if(obj instanceof PanelTabella) pt = (PanelTabella)obj;
        else if(obj instanceof PopUp) avviso = (PopUp)obj;
        else if(obj instanceof CreaTabella) ct = (CreaTabella)obj;

        iconSize = Global.ICON_SIZE;
        String[] nomi = (barra instanceof PrimaBarra) ? NOMI_PRIMA_BARRA : NOMI_TERZA_BARRA;
        setUpIcon(tipologia, nomi);
    }

    public BtnIcon(int tipologia, Object obj, int iconSize) {

        if(obj instanceof Barra) barra = (Barra)obj;
        else if(obj instanceof PanelTabella) pt = (PanelTabella)obj;
        else if(obj instanceof PopUp) avviso = (PopUp)obj;
        else if(obj instanceof CreaTabella) ct = (CreaTabella)obj;

        this.iconSize = iconSize;
        String[] nomi = (barra instanceof PrimaBarra) ? NOMI_PRIMA_BARRA : NOMI_TERZA_BARRA;
        setUpIcon(tipologia, nomi);
    }

    public BtnIcon(int tipologia, SecondaBarra sb, String categoria) {

        this.sb = sb;
        this.categoria = categoria;
        
        if(tipologia == AGGIUNGI) {
            iconSize = getFont().getSize();
            setUpIcon(tipologia, NOMI_TERZA_BARRA);
        } else {
            iconSize = 20;
            setUpIcon(tipologia, NOMI_TERZA_BARRA);
        }
    }

    private void setUpIcon(int tipologia, String[] arrayIcone) {
        
        this.tipologia = tipologia;
        this.messaggio = arrayIcone[tipologia];
        this.icon = Global.getIcon(arrayIcone[tipologia].replace(' ', '_') + ".png", iconSize);
        this.info = new Informazione();

        this.infoActive = false;
        this.parent = this;
        this.hoverAttivo = false;

        setHoverThread();
        setForeground(Color.WHITE);
        addMouseListener(this);
        setPreferredSize(new Dimension(iconSize, iconSize));
        setIcon(icon);
    }

    public int getTipologia() {
        return tipologia;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void changeTipologia(int nuovaTipologia) {
        setUpIcon(nuovaTipologia, NOMI_TERZA_BARRA);
    }

    @Override
    public void afterClick() {
        outHover();
        hoverAttivo = false;

        switch (tipologia) {
            case 0:

                if(barra != null) barra.btnIconClicked(this);
            break;

            case 1:
            break;

            case 2: 
            case 9:
                    if(barra != null) barra.btnIconClicked(this);
                    else if(pt != null) pt.elimina();
            break;

            case 3:
            case 4: 
            case 5: 
                if(sb != null) sb.btnIconClicked(this);
                else if(avviso != null) avviso.scelta(ANNULLA - tipologia);
            break;

            case 7: 
            case 8:
                barra.afterClick();
            break;
        
            default: sb.btnIconClicked(this);
            break;
        }
    }

    @Override
    public void hover() {

        info.mostra();

        hoverAttivo = false;
    }

    @Override
    public boolean hoverActive() {
        return hoverAttivo;
    }

    @Override
    public int getMilliseconds() {
        return 800;
    }

    @Override
    public void inHover() {
        hoverAttivo = true;
        info.setTesto(messaggio);
        info.getPanel(this);
        
        attivaHover();
    }

    @Override
    public void outHover() {
        hoverAttivo = false;
        
        info.nascondi();
    }
}
