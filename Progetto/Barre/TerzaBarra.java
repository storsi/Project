package Progetto.Barre;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Progetto.Main.Global;
import Progetto.Main.Strumenti.BarraDiSeparazione;
import Progetto.Main.Strumenti.ToggleButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TerzaBarra extends Barra{

    private ParteAlta pa;
    private BarraDiSeparazione ba;
    private ParteBassa pb;
    private SecondaBarra sb;
    private String nome;
    
    public TerzaBarra() {
        super(new Dimension((int)(Global.FRAME_WIDTH * 0.8), Global.FRAME_HEIGHT), Global.COLORE_TERZA_BARRA,Global.FL_C_0_0);
    }

    @Override
    protected void setUp() {
        this.pa = new ParteAlta(this);
        this.ba = new BarraDiSeparazione((int)(getPreferredSize().getWidth() * 0.7), pa.getLblForeground());
        this.pb = new ParteBassa(this);
        
        add(pa);
        add(ba);
        add(pb);
    }

    @Override
    public void btnIconClicked(BtnIcon btn) {

        switch (btn.getTipologia()) {
            case BtnIcon.ELIMINA:

                String messaggio = "Stai eliminando il database \"" + nome + "\" sei sicuro di continuare? Ogni informazione sarà perduta";
                getParent().add(new PopUp(Global.AVVISO_WIDTH, Global.AVVISO_HEIGHT, PopUp.ELIMINA_DATABASE, messaggio, this), JLayeredPane.PALETTE_LAYER);

            break;
        
            default:
                break;
        }
    }

    public void mostraDatabase(String nome) {

        this.nome = nome;

        pa.setVisible(true);
        pa.setLblNome(nome.toUpperCase());

        pb.popola(nome);
        pb.setVisible(true);

        ba.setVisible(true);
    }

    public void setSB(SecondaBarra sb) {
        this.sb = sb;
    }

    public void eliminaDatabase() {
        sb.eliminaDatabase(nome);
        nascondiDatabase();
    }

    private void nascondiDatabase() {
        pa.setVisible(false);
        pb.setVisible(false);
        ba.setVisible(false);
    }

    public void modificaDisposizioneTabelle() {
        pb.modificaDisposizioneTabelle();
    }
}

class ParteAlta extends JPanel {

    private TerzaBarra tb;
    private JLabel lbl_nomeDatabase;
    private JPanel pnl_pulsanti, pnl_nome, pnl_vuoto;

    public ParteAlta(TerzaBarra tb) {

        this.tb = tb;

        setLayout(Global.BL_0_0);
        setPreferredSize(new Dimension((int)tb.getPreferredSize().getWidth(), (int)(tb.getPreferredSize().getHeight() * 0.15)));
        setOpaque(false);
        setVisible(false);

        setUp();
    }

    private void setUp() {
        pnl_pulsanti = new JPanel(Global.FL_R_20_30);
        pnl_pulsanti.setOpaque(false);

        pnl_pulsanti.add(new BtnIcon(BtnIcon.AGGIUNGI_TABELLA, tb));
        pnl_pulsanti.add(new BtnIcon(BtnIcon.MOSTRA_SCHEMA, tb));
        pnl_pulsanti.add(new BtnIcon(BtnIcon.ELIMINA, tb));

        lbl_nomeDatabase = new JLabel();
        lbl_nomeDatabase.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_nomeDatabase.setForeground(Color.WHITE);
        lbl_nomeDatabase.setFont(Global.FONT_MOLTO_GRANDE);

        pnl_nome = new JPanel(Global.FL_C_0_0);


        pnl_nome.add(lbl_nomeDatabase);
        pnl_nome.setOpaque(false);

        pnl_vuoto = new JPanel();
        pnl_vuoto.setPreferredSize(pnl_pulsanti.getPreferredSize());
        pnl_vuoto.setOpaque(false);

        add(pnl_vuoto, BorderLayout.WEST);
        add(pnl_nome, BorderLayout.CENTER);
        add(pnl_pulsanti, BorderLayout.EAST);
    }

    public void setLblNome(String text) {
        lbl_nomeDatabase.setPreferredSize(new Dimension(text.length() * lbl_nomeDatabase.getFont().getSize(), (int)getPreferredSize().getHeight()));
        lbl_nomeDatabase.setText(text);
    }

    public Color getLblForeground() {
        return lbl_nomeDatabase.getForeground();
    }

    @Override
    protected void paintComponent(Graphics g) {
        //QUESTO PERMETTE LA SFUMATURA DALL'ALTO AL BASSO
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        GradientPaint gpaint = new GradientPaint(0, 0, Global.COLORE_TERZA_BARRA.darker(), 0, getHeight() - 10, new Color(0,0,0,0));

        g2d.setPaint(gpaint);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}

class ParteBassa extends JPanel {

    private JPanel pnl_switch, pnl_tabelle;
    private TerzaBarra tb;
    private boolean tabelleLunghe;
    private String nomeDatabase;

    public ParteBassa(TerzaBarra tb) {

        this.tb = tb;
        this.tabelleLunghe = false;
        
        setPreferredSize(new Dimension((int)(Global.FRAME_WIDTH * 0.8), (int)(Global.FRAME_HEIGHT * 0.9) - Global.BARRA_DI_SEPARAZIONE_HEIGHT - 40));
        setBackground(Global.COLORE_TERZA_BARRA);

        pnl_switch = new JPanel(Global.FL_R_10_10);
        pnl_switch.setPreferredSize(new Dimension((int)(getPreferredSize().getWidth() * 0.94), (int)(getPreferredSize().getHeight() * 0.1)));
        pnl_switch.setBackground(getBackground());

        pnl_switch.add(new ToggleButton(tb));
        
        pnl_tabelle = new JPanel(Global.FL_L_30_30);
        pnl_tabelle.setPreferredSize(new Dimension((int)(getPreferredSize().getWidth() * 0.94), (int)(getPreferredSize().getHeight() * 0.75)));
        pnl_tabelle.setBackground(getBackground());

        add(pnl_switch);
        add(pnl_tabelle);

        setVisible(false);
    }

    public void modificaDisposizioneTabelle() {
        tabelleLunghe = !tabelleLunghe;

        if(tabelleLunghe) pnl_tabelle.setLayout(Global.FL_C_10_10);
        else pnl_tabelle.setLayout(Global.FL_L_30_30);

        popola(nomeDatabase);
    }

    public void popola(String nomeDatabase) {
        pnl_tabelle.removeAll();
        

        this.nomeDatabase = nomeDatabase;
        String[] tabelle = Global.c.getTables(nomeDatabase);

        for(String nome : tabelle) {
            pnl_tabelle.add(new PanelTabella(nome, tabelleLunghe, this));
        }

        pnl_tabelle.revalidate();
        pnl_tabelle.repaint();
    }
}

