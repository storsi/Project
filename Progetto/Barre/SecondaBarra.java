package Progetto.Barre;

import java.awt.Dimension;
import java.io.File;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import Progetto.Main.Global;
import Progetto.Main.Strumenti.BarraDiSeparazione;
import Progetto.Main.Strumenti.TextArea;

public class SecondaBarra extends Barra{

    private JLabel lbl_titolo;
    private JPanel pnl_elementi, pnl_resize;
    private BarraDiSeparazione bds;
    private TerzaBarra tb;
    private TextArea ta_aggiungi;
    private BtnIcon btn_aggiungi, btn_conferma, btn_annulla, btn_resize;
    private String categoria;
    private boolean open;
    private Dimension dim_barra, dim_pnlResize;
    
    public SecondaBarra(TerzaBarra tb) {
        super(Global.COLORE_SECONDA_BARRA, Global.FL_C_10_10, true);

        dim_barra = new Dimension((int)(Global.FRAME_WIDTH * 0.15), Global.FRAME_HEIGHT);

        setPreferredSize(dim_barra);

        dim_pnlResize = new Dimension((int)getPreferredSize().getWidth(), (int)(getPreferredSize().getHeight() * 0.05));


        this.tb = tb;
        tb.setSB(this);
        open = true;

        setUp();
    }

    @Override
    protected void setUp() {

        int iconSize = 20;
        pnl_resize = new JPanel(Global.FL_R_10_10);
        pnl_resize.setPreferredSize(dim_pnlResize);
        pnl_resize.setBackground(getBackground());
        btn_resize = new BtnIcon(BtnIcon.RIDUCI_BARRA, this, iconSize);
        
        lbl_titolo = new JLabel();
        lbl_titolo.setHorizontalAlignment(SwingUtilities.CENTER);
        lbl_titolo.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)(getPreferredSize().getHeight() * 0.05)));
        lbl_titolo.setForeground(Color.WHITE);
        lbl_titolo.setFont(Global.FONT_GRANDE);
        lbl_titolo.setVisible(false);

        bds = new BarraDiSeparazione((int)(getPreferredSize().getWidth() * 0.7), lbl_titolo.getForeground());

        pnl_elementi = new JPanel(Global.FL_L_15_10);

        int w = (int)getPreferredSize().getWidth();
        int h = (int)(getPreferredSize().getHeight() - lbl_titolo.getPreferredSize().getHeight() - Global.BARRA_DI_SEPARAZIONE_HEIGHT);

        pnl_elementi.setPreferredSize(new Dimension(w, h));
        pnl_elementi.setBackground(Global.COLORE_SECONDA_BARRA);
        pnl_elementi.setVisible(false);

        ta_aggiungi = new TextArea(Global.FONT_MEDIO, new Dimension(180, 28));
        ta_aggiungi.changeColor(Global.COLORE_SECONDA_BARRA, Color.WHITE);
        ta_aggiungi.createBorder(Color.WHITE, 2);
        ta_aggiungi.setCharacterLimit(15);
        ta_aggiungi.setVisible(false);


        pnl_resize.add(btn_resize);
        add(pnl_resize);
        add(lbl_titolo);
        add(bds);
        add(Box.createVerticalStrut(50));
        add(pnl_elementi);
    }

    @Override
    public void btnIconClicked(BtnIcon btn) {
        switch (btn.getTipologia()) {
            case -1:

                if(btn.getCategoria().equals("Database")) {
                    tb.mostraDatabase(btn.getMessaggio());
                    resetAddElement();
                }

            break;
            case BtnIcon.AGGIUNGI:

                    /*
                    * In questo caso è stato cliccato il pulsante aggiungi. Attiva la possibilità di 
                    * inserire un nuovo database
                    */
                    ta_aggiungi.setVisible(true);
                    btn_conferma.setVisible(true);
                    btn_annulla.setVisible(true);
                    btn_aggiungi.setVisible(false);

            break;

            case BtnIcon.CONFERMA:

                /**
                 * In questo caso è stato cliccato il pulsante per la conferma e quindi l'aggiunta
                 * del nuovo database
                 */

                if(ta_aggiungi.getText().length() == 0) {
                    resetAddElement();
                    return;
                }

                File f = new File(Global.pathToDB, ta_aggiungi.getText() + ".db");

                try {
                    if(!f.exists()) f.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tb.mostraDatabase(ta_aggiungi.getText());
                aggiornaPnlElementi();
        
            default: 
            
                /**
                 * Il default è adibito al pulsante per annullare l'aggiunta di un database e quindi 
                 * la conseguente disabilitazione della funzione (viene richiamato anche dopo la funzione
                 * per il pulsante aggiunta, infatti quello non possiede il "break")
                 */
                resetAddElement();
            
            break;
        }
    }

    /**
     * Funzione richiamata solo quando si cambia categoria su cui si vuole lavorare. Setuppa la barra
     * @param categoria Indica la categoria a cui il bottone appartiene (Database, ...)
     */
    public void mostraElementi(String categoria) {
        this.categoria = categoria;

        if(open) {

            bds.setVisible(true);
            lbl_titolo.setVisible(true);
            pnl_elementi.setVisible(true);
            lbl_titolo.setText(categoria.toUpperCase());

            aggiornaPnlElementi();
        }
    }

    /**
     * Funzione richiamata quando viene effettuato un aggiornamento in questa barra (ad esempio l'aggiunta di un database)
     */
    private void aggiornaPnlElementi() {
        pnl_elementi.removeAll();
        pnl_elementi.revalidate();

        String[] elementi = null;

        if(categoria.equals("Database")) {
            File folder = new File(Global.pathToDB);

            if(folder.exists() && folder.isDirectory()) {
                File[] dbs = folder.listFiles();
                elementi = new String[dbs.length];

                for(int i = 0; i < dbs.length; i++) {
                    String nome = dbs[i].getName();
                    elementi[i] = nome.substring(0, nome.length() - 3);
                }
            }
        }
        
        for(String nome: elementi) {
            pnl_elementi.add(new BtnIcon(nome, this, categoria));
        }

        btn_aggiungi = new BtnIcon(BtnIcon.AGGIUNGI, this, categoria);
        btn_conferma = new BtnIcon(BtnIcon.CONFERMA, this, categoria);
        btn_conferma.setVisible(false);
        btn_annulla = new BtnIcon(BtnIcon.ANNULLA, this, categoria);
        btn_annulla.setVisible(false);

        pnl_elementi.add(btn_aggiungi);
        pnl_elementi.add(ta_aggiungi);
        pnl_elementi.add(btn_conferma);
        pnl_elementi.add(btn_annulla);

        resetAddElement();
    }

    /**
     * Funzione richiamata per disabilitare la funzione per l'aggiunta di un nuovo database
     */
    private void resetAddElement() {
        ta_aggiungi.setText("");
        ta_aggiungi.setVisible(false);
        btn_conferma.setVisible(false);
        btn_annulla.setVisible(false);
        btn_aggiungi.setVisible(true);

        repaint();
    } 

    /**
     * Funzione richiamata per eliminare un database e quindi aggiornare anche la barra
     * @param nome Indica il nome del database che si vuole eliminare
     */
    public void eliminaDatabase(String nome) {
        File f = new File(Global.pathToDB + nome + ".db");
        f.delete();

        aggiornaPnlElementi();
    }

    @Override
    protected void animaBarra() {
        btn_resize.exitedHover();

        int modifica = (open) ? -10 : 10, w = (int)dim_barra.getWidth() + modifica;



        if(open) {
            btn_resize.changeTipologia(BtnIcon.ALLARGA_BARRA);

            pnl_elementi.setVisible(false);
            bds.setVisible(false);
            lbl_titolo.setVisible(false);

            if(w <= 40) {
                modifica += 40 - w;
                w = 40;
                stopAnimation();
                open = false;
            }
        } else {
            btn_resize.changeTipologia(BtnIcon.RIDUCI_BARRA);

            if(w > (int)(Global.FRAME_WIDTH * 0.15)) {
                modifica -= w - (int)(Global.FRAME_WIDTH * 0.15);
                w = (int)(Global.FRAME_WIDTH * 0.15);
                stopAnimation();            
                open = true;

                if(categoria != null) mostraElementi(categoria);
            }
        }

        tb.modificaDimensione(-modifica);

        dim_pnlResize.setSize(w, (int)dim_pnlResize.getHeight());
        dim_barra.setSize(w, (int)dim_barra.getHeight());
        revalidate();
        repaint();
    }
}