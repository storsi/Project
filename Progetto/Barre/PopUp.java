package Progetto.Barre;

import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Progetto.Main.Global;
import Progetto.Main.Strumenti.Overlay;

public class PopUp extends Overlay{

    //< 100 conferma, > 100 < 200 prosegui, > 200 custom
    public static final int ELIMINA_DATABASE = 0;

    private int tipologia;
    private JLabel lbl_avviso, lbl_descrizione;
    private String messaggio;
    private BtnIcon btn_accetta, btn_annulla, btn_prosegui;
    private JPanel pnl_scelte;
    private TerzaBarra tb;
    
    public PopUp(int width, int height, int tipologia, String messaggio, TerzaBarra tb) {
        super(width, height);

        this.tipologia = tipologia;
        this.messaggio = messaggio;
        this.tb = tb;

        setUpPopUp(tipologia);
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

    private void setUpPopUp(int tipologia) {

        if(tipologia < 100) setUpConferma();
    }

    private void setUpConferma() {

        lbl_avviso = new JLabel("AVVISO", SwingConstants.CENTER);
        lbl_avviso.setPreferredSize(new Dimension(getWidth(), (int)(getHeight() * 0.1)));
        lbl_avviso.setFont(Global.FONT_GRANDE);
        lbl_avviso.setForeground(Color.WHITE);

        lbl_descrizione = new JLabel(setUpText(messaggio), SwingConstants.CENTER);
        lbl_descrizione.setPreferredSize(new Dimension(getWidth(), (int)(getHeight() * 0.6)));
        lbl_descrizione.setFont(Global.FONT_MEDIO);
        lbl_descrizione.setForeground(Color.WHITE);

        pnl_scelte = new JPanel(Global.FL_C_40_10);
        pnl_scelte.setPreferredSize(new Dimension(getWidth(), (int)(getHeight() * 0.3)));
        pnl_scelte.setBackground(getBackground());
        pnl_scelte.setOpaque(false);

        btn_accetta = new BtnIcon(BtnIcon.CONFERMA, this);
        btn_annulla = new BtnIcon(BtnIcon.ANNULLA, this);

        pnl_scelte.add(btn_accetta);
        pnl_scelte.add(btn_annulla);

        add(lbl_avviso);
        add(lbl_descrizione);
        add(pnl_scelte);
    }

    public void scelta(int scelta) {
        //scelta > 0 confermato, scelta < 0 annullato
        switch (tipologia) {
            case ELIMINA_DATABASE: 
            
                if(scelta > 0) tb.eliminaDatabase();
                tb.getParent().remove(this);

            break;
            default:
            break;
        }
    }
}
