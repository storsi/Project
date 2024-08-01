package Progetto.Barre;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Progetto.Main.Global;
import Progetto.Main.Strumenti.BarraDiSeparazione;
import Progetto.Main.Strumenti.PanelPerBtn;

public class PanelTabella extends PanelPerBtn{

    private JLabel lbl_nome;
    private int width, height;
    private boolean lungo;
    private ParteBassa pb;
    private BarraDiSeparazione bdsAlta, bdsBassa;
    private JPanel pnl_parteAlta, pnl_parteBassa, pnl_generale, pnl_info, pnl_foreignKey, pnl_triggers;

    public PanelTabella(String nome, boolean lungo, ParteBassa pb) {
        super();

        this.lungo = lungo;
        this.pb = pb;
        
        createElements(nome);
        setDimension(lungo);
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
            pnl_parteAlta.setPreferredSize(new Dimension(width, (int)(height * 0.3)));
            bdsAlta.setWidth((int)(width * 0.8));
            bdsAlta.setVisible(true);
        }

        super.changeDimension(width, height);
        bdsBassa.setWidth(bdsAlta.getWidth());
        bdsBassa.setVisible(bdsAlta.isVisible());

        pnl_generale.setVisible(!lungo);
        pnl_parteBassa.setVisible(!lungo);
    }

    private void createElements(String nome) {
        pnl_parteAlta = new JPanel(Global.FL_C_0_0);
        pnl_parteBassa = new JPanel(Global.FL_C_0_0);
        pnl_generale = new JPanel(Global.FL_L_30_10);
        lbl_nome = new JLabel(nome, SwingConstants.CENTER);
        bdsAlta = new BarraDiSeparazione(0);
        bdsBassa = new BarraDiSeparazione(0);
    }

    private void setUp() {
        pnl_parteAlta.setOpaque(false);

        pnl_generale.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)(getPreferredSize().getHeight() * 0.4)));
        pnl_generale.setOpaque(false);

        pnl_parteBassa.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)(getPreferredSize().getHeight() * 0.3)));
        pnl_parteBassa.setOpaque(false);

        lbl_nome.setFont(Global.FONT_MEDIO);

        pnl_parteAlta.add(lbl_nome);
        pnl_parteAlta.add(bdsAlta);
        pnl_generale.add(new BtnIcon("Informazioni", this, BtnIcon.MOSTRA_INFO_TABELLA));
        pnl_generale.add(new BtnIcon("Foreign Keys", this, BtnIcon.MOSTRA_FOREIGN_KEYS));
        pnl_generale.add(new BtnIcon("Triggers", this, BtnIcon.MOSTRA_TRIGGERS));
        pnl_parteBassa.add(bdsBassa);

        add(pnl_parteAlta);
        add(pnl_generale);
        add(pnl_parteBassa);
    }

    public void mostraTabella() {
    }

    public void mostraInfo() {
    }

    public void mostraFK() {
    }

    public void mostraTriggers() {
    }

    @Override
    public void onClick() {
        mostraTabella();
    }

    @Override
    public void inHover() {
        
    }

    @Override
    public void outHover() {

    }

    
    
}
