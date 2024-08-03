package Progetto.Barre;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import Progetto.Main.Strumenti.LabelPerBtn;
import Progetto.Main.Global;
import Progetto.Main.Panel;

public class BtnIcon extends LabelPerBtn{

    public static final int AGGIUNGI_TABELLA = 0, MOSTRA_SCHEMA = 1, ELIMINA = 2, AGGIUNGI = 3, CONFERMA = 4;
    public static final int ANNULLA = 5, PROSEGUI = 6, RIDUCI_BARRA = 7, ALLARGA_BARRA = 8;
    public static final int MOSTRA_INFO_TABELLA = 9, MOSTRA_FOREIGN_KEYS = 10, MOSTRA_TRIGGERS = 11;

    public static final String[] NOMI_PRIMA_BARRA = {"Database"};
    public static final String[] NOMI_TERZA_BARRA = {"Aggiungi Tabella", "Mostra Schema", "Elimina", "Aggiungi",
                                                    "Conferma", "Annulla", "Prosegui", "Riduci", "Allarga"};

    private String messaggio, categoria;
    private int tipologia, iconSize, profonditaMax;
    private Icon icon;
    private Informazione info;
    private SecondaBarra sb;
    private PanelTabella pt;
    private PopUp avviso;
    private Barra barra;
    private boolean infoActive;
    private Component parent;
    private Panel panel;

    public BtnIcon(int tipologia, Barra barra) {

        this.barra = barra;
        iconSize = Global.ICON_SIZE;
        String[] nomi = (barra instanceof PrimaBarra) ? NOMI_PRIMA_BARRA : NOMI_TERZA_BARRA;
        setUpIcon(tipologia, nomi);
    }

    public BtnIcon(int tipologia, PanelTabella pt) {

        this.pt = pt;
        iconSize = Global.ICON_SIZE;
        String[] nomi = (barra instanceof PrimaBarra) ? NOMI_PRIMA_BARRA : NOMI_TERZA_BARRA;
        setUpIcon(tipologia, nomi);
    }

    public BtnIcon(int tipologia, Barra barra, int iconSize) {

        this.barra = barra;
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

    public BtnIcon(int tipologia, PopUp avviso) {

        this.avviso = avviso;
        iconSize = Global.ICON_SIZE;
        setUpIcon(tipologia, NOMI_TERZA_BARRA);
    }

    public BtnIcon(String text, SecondaBarra sb, String categoria) {
        
        this.tipologia = -1;
        this.sb = sb;
        this.messaggio = text;
        this.categoria = categoria;
        
        super.setTwoSecondsHover(false);

        setFont(Global.FONT_MEDIO);
        setText("\u2022 " + text.toUpperCase());
        setPreferredSize(new Dimension((int)sb.getPreferredSize().getWidth() - 5, 20));
    }

    public BtnIcon(String message, PanelTabella pt, int tipologia) {

        this.tipologia = tipologia;
        this.messaggio = message;
        this.pt = pt;

        setFont(Global.FONT_MEDIO);
        setText("\u2022 " + message.toUpperCase());
        setForeground(Color.BLACK);      
        setOpaque(false);
        setBackground(Color.RED);
        setPreferredSize(new Dimension((int)pt.getDimension().getWidth() - 5, 20));
    }

    private void setUpIcon(int tipologia, String[] arrayIcone) {
        this.tipologia = tipologia;
        this.messaggio = arrayIcone[tipologia];

        if(tipologia == 2 && pt != null) this.icon = Global.getIcon(arrayIcone[tipologia] + "Nero".replace(' ', '_') + ".png", iconSize);
        else this.icon = Global.getIcon(arrayIcone[tipologia].replace(' ', '_') + ".png", iconSize);

        this.info = new Informazione(messaggio);
        this.infoActive = false;
        this.parent = this;
        super.setTwoSecondsHover(true);

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
    public void onClick() {
        exitedHover();
        super.setIsActive(false);

        switch (tipologia) {
            case 0:

                if(barra != null) barra.btnIconClicked(this);
            break;

            case 1:
            break;

            case 2: if(barra != null) barra.btnIconClicked(this);
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
                barra.startAnimation();
            break;

            case 9: pt.mostraInfo();
            break;
            case 10: pt.mostraFK();
            break;
            case 11: pt.mostraTriggers();
            break;
        
            default: sb.btnIconClicked(this);
            break;
        }
    }

    private void setInfo() {
        int profondita;
        Component element = this;
        profonditaMax = 0;

        do{
            parent = parent.getParent();
        }while(!(parent.getClass().getSimpleName().equals("Panel")));

        panel = (Panel)parent;

        do{
            profondita = ((JLayeredPane)panel).getLayer(element);
            profonditaMax = (profondita > profonditaMax) ? profondita : profonditaMax;

            element = element.getParent();
        }while(profondita >= 0);
    }

    @Override
    public void hover2sec() {
        int x;

        if(panel == null) setInfo();

        Point screenPoint = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(screenPoint, panel);

        int w = messaggio.length() * 9, h = (int)(Global.ICON_SIZE * 0.5);

        if(screenPoint.x + w > Global.FRAME_WIDTH) x = screenPoint.x - (screenPoint.x + w - Global.FRAME_WIDTH + 10);
        else x = screenPoint.x;

        info.setBounds(x, screenPoint.y - h, w, h);

        panel.add(info, Integer.valueOf(profonditaMax + 100));
    }

    @Override
    public void exitedHover() {

        if(info != null && panel != null) panel.eliminaElemtno(info.getClass().getSimpleName());

        if(tipologia == 2 && pt != null) setIcon(Global.getIcon("EliminaNero.png", Global.ICON_SIZE));
        else if(tipologia == 2) setIcon(Global.getIcon("Elimina.png", Global.ICON_SIZE));
    }

    @Override
    public void hover() {
        if(tipologia == 2) setIcon(Global.getIcon("EliminaRosso.png", Global.ICON_SIZE));
    }
}

class Informazione extends JLabel{

    public Informazione(String messaggio) {
        
        setText(messaggio.toUpperCase());
        setFont(Global.FONT_PICCOLO);
        setHorizontalAlignment(SwingUtilities.CENTER);
        setForeground(Color.BLACK);
        setOpaque(false);
        setBackground(new Color(245, 245, 220));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int raggioCurvatura = 20;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), raggioCurvatura, raggioCurvatura);

        super.paintComponent(g);
    }
}
