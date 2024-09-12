package Progetto.Barre;

import java.awt.Dimension;
import java.awt.Color;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Progetto.DBM.CreaTabella;
import Progetto.Main.Global;
import Progetto.Main.Strumenti.Overlay;

public class PopUp extends Overlay{

    //< 100 conferma, > 100 < 200 prosegui, > 200 custom
    public static final int ELIMINA_DATABASE = 0, AGGIUNGI_TABELLA = 1;

    private int tipologia;
    private JLabel lbl_avviso, lbl_descrizione;
    private BtnIcon btn_accetta, btn_annulla, btn_prosegui;
    private JPanel pnl_scelte;
    private TerzaBarra tb;
    private Dimension dim_avviso;

    //Componenti per l'aggiunta della tabella

    private CreaTabella creaTabella;

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

        creaTabella.setVisible(true);

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
        creaTabella = new CreaTabella();

        add(creaTabella);
    }
}