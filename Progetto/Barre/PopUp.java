package Progetto.Barre;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Progetto.Main.Global;
import Progetto.Main.Strumenti.BarraDiSeparazione;
import Progetto.Main.Strumenti.Overlay;
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
    private JLabel lbl_colonne, lbl_creaColonne, lbl_nomeColonne, lbl_tipologiaColonna;
    private TextArea ta_nomeTabella;
    private JRadioButton rbtn_int, rbtn_real, rbtn_text, rbtn_blob;
    private ButtonGroup bg_tipologia;

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
        add(pnl_creaColonne);
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
        pnl_colonne.setPreferredSize(new Dimension((int)(Global.FRAME_WIDTH * 0.33), (int)(Global.FRAME_HEIGHT * 0.70)));
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
        pnl_creaColonne = new JPanel(Global.FL_C_10_10);
        pnl_creaColonne.setPreferredSize(pnl_colonne.getPreferredSize());
        pnl_creaColonne.setOpaque(false);
        pnl_creaColonne.setVisible(false);

        lbl_creaColonne = new JLabel("CREA COLONNA:", SwingConstants.CENTER);
        lbl_creaColonne.setFont(Global.FONT_GRANDE);
        lbl_creaColonne.setPreferredSize(lbl_colonne.getPreferredSize());
        lbl_creaColonne.setForeground(Color.WHITE);

        lbl_nomeColonne = new JLabel("NOME COLONNA:", SwingConstants.CENTER);
        lbl_nomeColonne.setFont(Global.FONT_MEDIO);
        lbl_nomeColonne.setPreferredSize(lbl_colonne.getPreferredSize());
        lbl_nomeColonne.setForeground(Color.WHITE);

        ta_nomeTabella = new TextArea(Global.FONT_MEDIO, new Dimension((int)(pnl_creaColonne.getPreferredSize().getWidth() * 0.6), 30));
        ta_nomeTabella.setCharacterLimit(24);

        lbl_tipologiaColonna = new JLabel("TIPOLOGIA:", SwingConstants.CENTER);
        lbl_tipologiaColonna.setFont(Global.FONT_MEDIO);
        lbl_tipologiaColonna.setPreferredSize(lbl_colonne.getPreferredSize());
        lbl_tipologiaColonna.setForeground(Color.WHITE);

        bg_tipologia = new ButtonGroup();

        rbtn_int = setUpRadioButton("INTEGER", bg_tipologia);
        rbtn_int.setSelected(true);
        rbtn_real = setUpRadioButton("REAL", bg_tipologia);
        rbtn_text = setUpRadioButton("TEXT", bg_tipologia);
        rbtn_blob = setUpRadioButton("BLOB", bg_tipologia);

        bg_tipologia.add(rbtn_int);
        
        pnl_creaColonne.add(lbl_creaColonne);
        pnl_creaColonne.add(new BarraDiSeparazione((int)(pnl_creaColonne.getPreferredSize().getWidth() * 0.6), Color.WHITE, true));
        pnl_creaColonne.add(Box.createVerticalStrut(20));
        pnl_creaColonne.add(lbl_nomeColonne);
        pnl_creaColonne.add(ta_nomeTabella);
        pnl_creaColonne.add(lbl_tipologiaColonna);
        pnl_creaColonne.add(rbtn_int);
        pnl_creaColonne.add(rbtn_real);
        pnl_creaColonne.add(rbtn_text);
        pnl_creaColonne.add(rbtn_blob);
    }

    private JRadioButton setUpRadioButton(String text, ButtonGroup bg) {
        
        JRadioButton rbtn = new JRadioButton(text);
        rbtn.setFont(Global.FONT_MEDIO);
        rbtn.setOpaque(false);
        rbtn.setForeground(Color.WHITE);
        rbtn.setFocusPainted(false);
        rbtn.setIcon(Global.getIcon("RadioButtonNotSelected.png", 10));
        rbtn.addActionListener(this);

        bg.add(rbtn);

        return rbtn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {       

        Enumeration<AbstractButton> btns = bg_tipologia.getElements();
        while(btns.hasMoreElements()) {
            AbstractButton button = btns.nextElement();

            if(button.isSelected()) button.setIcon(Global.getIcon("RadioButtonSelected.png", 10));
            else button.setIcon(Global.getIcon("RadioButtonNotSelected.png", 10));
        }
    }
}
