package Progetto.Barre;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Progetto.Comunicazione.Comunicatore;
import Progetto.Main.Global;
import Progetto.Main.Interface.Clickable;
import Progetto.Main.Strumenti.BarraDiSeparazione;
import Progetto.Main.Strumenti.Label;

public class PanelTabella extends JPanel implements Clickable{

    private JLabel lbl_nome;
    private int width, height;
    private boolean lungo;
    private ParteBassa pb;
    private BarraDiSeparazione bdsAlta, bdsBassa;
    private Dimension dim;
    private JPanel pnl_parteAlta, pnl_parteBassa, pnl_generale, pnl_info, pnl_foreignKey, pnl_triggers;
    private String nomeTabella;

    public PanelTabella(String nomeTabella, boolean lungo, ParteBassa pb) {
        super();

        this.lungo = lungo;
        this.pb = pb;
        this.nomeTabella = nomeTabella;
        this.dim = new Dimension();
        
        setPreferredSize(dim);
        createElements();
        setDimension(lungo);
        setOpaque(false);
        setUp();

        repaint();
    }

    public void setDimension(boolean lungo) {

        if(lungo) {
            width = (int)(pb.getPreferredSize().getWidth() * 0.9);
            height = (int)(pb.getPreferredSize().getHeight() * 0.05);

            lbl_nome.setPreferredSize(new Dimension((int)(width * 0.4), height / 2));
            pnl_parteAlta.setPreferredSize(lbl_nome.getPreferredSize());
            bdsAlta.setVisible(false);

            
        } else {
            width = (int)(pb.getPreferredSize().getWidth() * 0.3);
            height = (int)(pb.getPreferredSize().getHeight() * 0.5);

            lbl_nome.setPreferredSize(new Dimension(width, (int)(height * 0.18)));
            pnl_parteAlta.setPreferredSize(new Dimension(width, (int)(height * 0.25)));
            bdsAlta.setWidth((int)(width * 0.8));
            bdsAlta.setVisible(true);
        }

        dim.setSize(width, height);
        bdsBassa.setWidth(bdsAlta.getWidth());
        bdsBassa.setVisible(bdsAlta.isVisible());

        pnl_generale.setVisible(!lungo);
        pnl_parteBassa.setVisible(!lungo);
    }

    private void createElements() {
        pnl_parteAlta = new JPanel(Global.FL_C_0_0);
        pnl_parteBassa = new JPanel(Global.FL_C_40_10);
        pnl_generale = new JPanel(Global.FL_L_30_10);
        lbl_nome = new JLabel(nomeTabella, SwingConstants.CENTER);
        bdsAlta = new BarraDiSeparazione(0);
        bdsBassa = new BarraDiSeparazione(0);

        pnl_info = new JPanel(Global.FL_L_30_10);
    }

    private void setUp() {
        pnl_parteAlta.setOpaque(false);

        pnl_generale.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)(getPreferredSize().getHeight() * 0.45)));
        pnl_generale.setOpaque(false);

        pnl_parteBassa.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)(getPreferredSize().getHeight() * 0.3)));
        pnl_parteBassa.setOpaque(false);

        lbl_nome.setFont(Global.FONT_MEDIO);

        pnl_info.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)pnl_generale.getPreferredSize().getHeight()));
        pnl_info.setOpaque(false);
        pnl_info.setVisible(false);

        pnl_info.add(new Label("n° Righe: " + Global.c.getRowNumber(nomeTabella), true, lbl_nome.getFont()));
        pnl_info.add(new Label("n° Colonne: " + Global.c.getColumnNumber(nomeTabella), true, lbl_nome.getFont()));

        pnl_parteAlta.add(lbl_nome);
        pnl_parteAlta.add(bdsAlta);
        pnl_generale.add(new BtnText("Mostra Tabella", this, BtnText.MOSTRA_TABELLA));
        pnl_generale.add(new BtnText("Informazioni", this, BtnText.MOSTRA_INFO_TABELLA));
        pnl_generale.add(new BtnText("Foreign Keys", this, BtnText.MOSTRA_FOREIGN_KEYS));
        pnl_generale.add(new BtnText("Triggers", this, BtnText.MOSTRA_TRIGGERS));
        pnl_parteBassa.add(bdsBassa);
        pnl_parteBassa.add(new BtnIcon(BtnIcon.ELIMINA_NERO, this));

        add(pnl_parteAlta);
        add(pnl_generale);
        add(pnl_info);
        add(pnl_parteBassa);
    }

    public void elimina() {
        
    }

    public void mostraTabella() {
    }

    public void mostraInfo() {
        lbl_nome.setText("Info (" + nomeTabella + ")");
        pnl_generale.setVisible(false);
        pnl_info.setVisible(true);
    }

    public void mostraFK() {
    }

    public void mostraTriggers() {
    }

    @Override
    public void afterClick() {
        mostraTabella();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int raggioCurvatura = 40;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, width, height, raggioCurvatura, raggioCurvatura);
    }
}
