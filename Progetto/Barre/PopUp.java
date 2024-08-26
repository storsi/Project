package Progetto.Barre;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import Progetto.Main.Global;
import Progetto.Main.Interface.Hover;
import Progetto.Main.Strumenti.BarraDiSeparazione;
import Progetto.Main.Strumenti.Overlay;
import Progetto.Main.Strumenti.ScrollPane;
import Progetto.Main.Strumenti.TextArea;

public class PopUp extends Overlay implements ActionListener{

    //< 100 conferma, > 100 < 200 prosegui, > 200 custom
    public static final int ELIMINA_DATABASE = 0, AGGIUNGI_TABELLA = 1;

    private int tipologia;
    private JLabel lbl_avviso, lbl_descrizione;
    private BtnIcon btn_accetta, btn_annulla, btn_prosegui;
    private JPanel pnl_scelte;
    private TerzaBarra tb;
    private Dimension dim_avviso;

    //Componenti per l'aggiunta della tabella
    private JPanel pnl_colonne, pnl_creaColonne;
    private JLabel lbl_colonne, lbl_creaColonne, lbl_nomeColonne, lbl_tipologiaColonna, lbl_caratteristicaColonna;
    private TextArea ta_nomeTabella;
    private ButtonGroup bg_tipologia;
    private ScrollPane sp_creaColonna;
    private Dimension dim_creaColonna;
    private RadioButton[] radioButtons;
    private BtnIcon btn_creaColonna, btn_resetColonna;

    public PopUp() {

        setUp();
        setVisible(false);
        
    }

    public void setTerzaBarra(TerzaBarra tb) {
        this.tb = tb;
    }

    public void setTipologia(int tipologia) {
        this.tipologia = tipologia;

        checkTipologia();
    }

    public void setMessaggio(String messaggio) {

        lbl_descrizione.setText(setUpText(messaggio));
    }

    private void checkTipologia() {
        switch (tipologia) {
            case ELIMINA_DATABASE: setConferma();
            break;
            case AGGIUNGI_TABELLA: setAzione();
            break;
            default:
            break;
        }

        setVisible(true);
    }

    private void setAzione() {
        changeSize((int)(Global.FRAME_WIDTH * 0.7), (int)(Global.FRAME_HEIGHT * 0.8));
        setLayout(Global.FL_L_15_10);

        //MODIFICHE A LBL_AVVISO:
        lbl_avviso.setText("AGGIUNGI TABELLA");
        dim_avviso.setSize(getWidth(), (int)(Global.AVVISO_WIDTH * 0.1));
        lbl_avviso.setHorizontalAlignment(SwingConstants.LEFT);

        pnl_colonne.setVisible(true);
        pnl_creaColonne.setVisible(true);

        pnl_scelte.setVisible(false);
        lbl_descrizione.setVisible(false);

    }

    private void setConferma() {
        changeSize(Global.AVVISO_WIDTH, Global.AVVISO_HEIGHT);
        setLayout(Global.FL_C_10_10);

        //MODIFICHE A LBL_AVVISO
        lbl_avviso.setText("ATTENZIONE!");
        dim_avviso.setSize(getWidth(), (int)(Global.AVVISO_WIDTH * 0.1));
        lbl_avviso.setHorizontalAlignment(SwingConstants.CENTER);

        //TODO quando creo il pulsante btn_prosegui, impostare la sua visibilità su false

        btn_accetta.setVisible(true);
        btn_annulla.setVisible(true);

        pnl_scelte.setVisible(true);
        lbl_descrizione.setVisible(true);
        pnl_colonne.setVisible(false);
        pnl_creaColonne.setVisible(false);
    }

    private void setProsegui() {
        changeSize(Global.AVVISO_WIDTH, Global.AVVISO_HEIGHT);
        setLayout(Global.FL_C_10_10);

        //MODIFICHE A LBL_AVVISO
        lbl_avviso.setText("INFORMAZIONE:");
        dim_avviso.setSize(getWidth(), (int)(Global.AVVISO_WIDTH * 0.1));
        lbl_avviso.setHorizontalAlignment(SwingConstants.CENTER);

        //TODO quando creo il pulsante btn_prosegui, impostare la sua visibilità su true

        btn_accetta.setVisible(false);
        btn_annulla.setVisible(false);

        pnl_scelte.setVisible(true);
        lbl_descrizione.setVisible(true);
        pnl_colonne.setVisible(false);
        pnl_creaColonne.setVisible(false);
    }

    private String setUpText(String text) {
        boolean aCapo = false;
        String risposta = "<html><p>";

        for(int i = 0; i < text.length(); i++) {
            risposta += text.charAt(i);
            if(i != 0 && i % 30 == 0) aCapo = true;

            if(aCapo && text.charAt(i) == ' ') {
                risposta += "<br>";
                aCapo = false;
            }
        }

        risposta += "</p></html>";

        return risposta;
    }

    public void scelta(int scelta) {
        //scelta > 0 confermato, scelta < 0 annullato
        switch (tipologia) {
            case ELIMINA_DATABASE: 
            
                if(scelta > 0) tb.eliminaDatabase();
                setVisible(false);

            break;
            default:
            break;
        }
    }


    //SetUp del popUp

    private void setUp() {
        lbl_avviso = new JLabel();
        lbl_avviso.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_avviso.setPreferredSize(dim_avviso = new Dimension());
        lbl_avviso.setFont(Global.FONT_MOLTO_GRANDE);
        lbl_avviso.setForeground(Color.WHITE);

        lbl_descrizione = new JLabel();
        lbl_descrizione.setPreferredSize(new Dimension((int)(Global.AVVISO_WIDTH * 0.8), (int)(Global.AVVISO_HEIGHT * 0.5)));
        lbl_descrizione.setFont(Global.FONT_MEDIO);
        lbl_descrizione.setForeground(Color.WHITE);

        pnl_scelte = new JPanel(Global.FL_C_40_10);
        pnl_scelte.setPreferredSize(new Dimension(Global.AVVISO_WIDTH, (int)(Global.AVVISO_HEIGHT * 0.2)));
        pnl_scelte.setBackground(Global.COLORE_AVVISO);
        pnl_scelte.setOpaque(false);

        setUpConferma();
        setUpProsegui();

        add(lbl_avviso);
        add(lbl_descrizione);
        add(pnl_scelte);

        setUpAzione();

        add(pnl_colonne);
        add(sp_creaColonna);
    }

    private void setUpConferma() {
        btn_accetta = new BtnIcon(BtnIcon.CONFERMA, this);
        btn_annulla = new BtnIcon(BtnIcon.ANNULLA, this);

        pnl_scelte.add(btn_accetta);
        pnl_scelte.add(btn_annulla);
    }

    private void setUpProsegui() {
        //TODO inserire il pulsante prosegui
    }

    private void setUpAzione() {
        //Set up panels per la creazione della tabella

        //Panel delle colonne già create
        pnl_colonne = new JPanel(Global.FL_C_10_10);
        pnl_colonne.setPreferredSize(new Dimension((int)(Global.FRAME_WIDTH * 0.33), (int)(Global.FRAME_HEIGHT * 0.60)));
        pnl_colonne.setOpaque(false);
        pnl_colonne.setVisible(false);

        lbl_colonne = new JLabel("COLONNE CREATE:", SwingConstants.CENTER);
        lbl_colonne.setFont(Global.FONT_GRANDE);
        lbl_colonne.setPreferredSize(new Dimension((int)pnl_colonne.getPreferredSize().getWidth(), 30));
        lbl_colonne.setForeground(Color.WHITE);
        
        pnl_colonne.add(lbl_colonne);
        pnl_colonne.add(new BarraDiSeparazione((int)(pnl_colonne.getPreferredSize().getWidth() * 0.6), Color.WHITE, true));
        pnl_colonne.add(Box.createVerticalStrut(20));


        //Panel delle colonne da creare
        sp_creaColonna = new ScrollPane(pnl_colonne.getPreferredSize(), Color.WHITE);

        dim_creaColonna = new Dimension((int)pnl_colonne.getPreferredSize().getWidth() - 20, 1000);

        pnl_creaColonne = new JPanel(Global.FL_C_10_10);
        pnl_creaColonne.setPreferredSize(dim_creaColonna);
        pnl_creaColonne.setBackground(Global.COLORE_AVVISO);
        pnl_creaColonne.setVisible(false);

        sp_creaColonna.setViewportView(pnl_creaColonne);

        lbl_creaColonne = new JLabel("CREA COLONNA:", SwingConstants.CENTER);
        lbl_creaColonne.setFont(Global.FONT_GRANDE);
        lbl_creaColonne.setPreferredSize(lbl_colonne.getPreferredSize());
        lbl_creaColonne.setForeground(Color.WHITE);

        lbl_nomeColonne = new JLabel("<html><b>NOME COLONNA:</b></html>", SwingConstants.CENTER);
        lbl_nomeColonne.setFont(Global.FONT_MEDIO);
        lbl_nomeColonne.setPreferredSize(lbl_colonne.getPreferredSize());
        lbl_nomeColonne.setForeground(lbl_creaColonne.getForeground());

        ta_nomeTabella = new TextArea(Global.FONT_MEDIO, new Dimension((int)(pnl_creaColonne.getPreferredSize().getWidth() * 0.6), 30));
        ta_nomeTabella.setCharacterLimit(24);

        lbl_tipologiaColonna = new JLabel("<html><b>TIPOLOGIA:</b></html>", SwingConstants.CENTER);
        lbl_tipologiaColonna.setFont(Global.FONT_MEDIO);
        lbl_tipologiaColonna.setPreferredSize(lbl_colonne.getPreferredSize());
        lbl_tipologiaColonna.setForeground(lbl_creaColonne.getForeground());

        lbl_caratteristicaColonna = new JLabel("<html><b>CARATTERISTICA:</b></html>", SwingConstants.CENTER);
        lbl_caratteristicaColonna.setFont(Global.FONT_MEDIO);
        lbl_caratteristicaColonna.setPreferredSize(lbl_colonne.getPreferredSize());
        lbl_caratteristicaColonna.setForeground(lbl_creaColonne.getForeground());

        bg_tipologia = new ButtonGroup();

        pnl_creaColonne.add(lbl_creaColonne);
        pnl_creaColonne.add(new BarraDiSeparazione((int)(pnl_creaColonne.getPreferredSize().getWidth() * 0.6), lbl_creaColonne.getForeground(), true));
        pnl_creaColonne.add(Box.createVerticalStrut(20));
        pnl_creaColonne.add(lbl_nomeColonne);
        pnl_creaColonne.add(ta_nomeTabella);
        pnl_creaColonne.add(Box.createVerticalStrut(40));
        pnl_creaColonne.add(lbl_tipologiaColonna);
        pnl_creaColonne.add(Box.createVerticalStrut(40));
    
        RadioButton rbtn;
        boolean bg, needWord;
        radioButtons = new RadioButton[RadioButton.getNumRadioButtons()];

        for(int i = 0; i < RadioButton.getNumRadioButtons(); i++) {
            bg = needWord = false;

            if(i < 4) bg = true;
            else if(i == 8 || i == 9 || i == 10) needWord = true;

            if(bg) rbtn = new RadioButton(i, needWord, bg_tipologia, this);
            else rbtn = new RadioButton(i, needWord, this);

            if(i == 4) {
                pnl_creaColonne.add(Box.createVerticalStrut(40));
                pnl_creaColonne.add(lbl_caratteristicaColonna);
                pnl_creaColonne.add(Box.createVerticalStrut(40));
            }
            pnl_creaColonne.add(rbtn);
            radioButtons[i] = rbtn;
        }

        checkRadioButtonsTipologia();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        checkRadioButtonCaratteristica();
    }

    private void checkRadioButtonsTipologia() {
        if(radioButtons[0].isSelected()) {
            
            radioButtons[8].abilita();
            radioButtons[7].abilita();
            
            radioButtons[10].disabilita();

        } else if(radioButtons[1].isSelected()) {

            radioButtons[8].abilita();
            
            radioButtons[7].disabilita();
            radioButtons[10].disabilita();

        } else if(radioButtons[2].isSelected()) {
            
            radioButtons[8].abilita();
            radioButtons[10].abilita();

            
            radioButtons[7].disabilita();
        } else {
            
            radioButtons[7].disabilita();
            radioButtons[8].disabilita();
            radioButtons[10].disabilita();
        }

        aggiornaRadioButtons();
    }

    private void checkRadioButtonCaratteristica() {
        
        if(radioButtons[7].isSelected()) {

            radioButtons[4].setSelected(true);

        } 
        
        if(radioButtons[4].isSelected()) {

            radioButtons[5].setSelected(true);
            radioButtons[6].setSelected(true);

        }
        checkRadioButtonsTipologia();
    }

    private void aggiornaRadioButtons() {
        
        for(int i = 0; i < radioButtons.length; i++) {

            if(radioButtons[i].isSelected()) radioButtons[i].setIcon(Global.getIcon("RadioButtonSelected.png", 10));
            else radioButtons[i].setIcon(Global.getIcon("RadioButtonNotSelected.png", 10));

            if(!radioButtons[i].isEnabled()) radioButtons[i].setIcon(Global.getIcon("Annulla.png", 10));
        }
    }
}


class RadioButton extends JRadioButton implements Hover{

    private int indice;
    private String text;
    private boolean needWord;
    private ButtonGroup bg;
    private static String[] radioButtonNames = {"INTEGER", "REAL", "TEXT", "BLOB", "PRIMARY KEY", "UNIQUE", "NOT NULL",
                                    "AUTO INCREMENT", "DEFAULT", "CHECK", "COLLATE"};
    //0 INTEGER, 1 REAL, 2 TEXT, 3 BLOB, 4 PRIMARY KEY, 5 UNIQUE, 6 NOT NULL, 7 AUTO INCREMENT, 8 DEFAULT, 9 CHECK, 10 COLLATE

    private static String[] descrizioneRadioButtons = {"Permette l'inserimento dei soli valori numerici interi"};

    public RadioButton(int indice, boolean needWord, ActionListener al) {
        this.indice = indice;
        this.text = radioButtonNames[indice];
        this.needWord = needWord;
        this.bg = null;

        creaRadioButton(al);

    }
    public RadioButton(int indice, boolean needWord, ButtonGroup bg, ActionListener al) {
        this.indice = indice;
        this.text = radioButtonNames[indice];
        this.needWord = needWord;
        this.bg = bg;

        creaRadioButton(al);

        bg.add(this);
    }

    private void creaRadioButton(ActionListener al) {
        
        setText(text);
        setFont(Global.FONT_PICCOLO);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setHoverThread();
        addMouseListener(this);
        
        if(indice == 0) {
            setSelected(true);
            setIcon(Global.getIcon("RadioButtonSelected.png", 10));
        }
        else setIcon(Global.getIcon("RadioButtonNotSelected.png", 10));
        
        addActionListener(al);
        
    }

    public int getIndice() {
        return indice;
    }

    public String getText() {
        return text;
    }

    public boolean needWord() {
        return needWord;
    }

    public static int getNumRadioButtons() {
        return radioButtonNames.length;
    }

    public void abilita() {
        setEnabled(true);
        setIcon(Global.getIcon("RadioButtonNotSelected.png", 10));
    }

    public void disabilita() {
        setSelected(false);
        setEnabled(false);
    }
    @Override
    public boolean hoverActive() {
        return false;
    }
    @Override
    public int getMilliseconds() {
        return 800;
    }
    @Override
    public void hover() {
    }
    @Override
    public void inHover() {
    }
    @Override
    public void outHover() {
    }
}

class Informazione extends JLabel {


    public Informazione() {

        setBackground(new Color(245, 245, 220));
        setOpaque(false);
        setFont(Global.FONT_PICCOLO);
        setForeground(Color.BLACK);

    }

    public void setText(String text) {
        setText(manageText(text));
    }

    private String manageText(String text) {
        String nuovotesto = "<html><p>";

        boolean aCapo = false;

        for(int i = 0; i < text.length(); i++) {
            nuovotesto += text.charAt(i);
            if(i != 0 && i % 30 == 0) aCapo = true;

            if(aCapo && text.charAt(i) == ' ') {
                nuovotesto += "<br>";
                aCapo = false;
            }
        }

        nuovotesto += "</p></html>";

        return nuovotesto;
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