package Progetto.Barre;

import javax.swing.JLabel;
import javax.swing.Box;

import java.awt.Color;
import java.awt.Dimension;

import Progetto.Main.Global;
import Progetto.Main.Interface.Animated;
import Progetto.Main.Interface.Clickable;
import Progetto.Main.Interface.Hover;
import Progetto.Main.Strumenti.BarraDiSeparazione;

public class BtnText extends JLabel implements Hover, Animated, Clickable{

    public static final int MOSTRA_INFO_TABELLA = 0, MOSTRA_FOREIGN_KEYS = 1, MOSTRA_TRIGGERS = 2, MOSTRA_TABELLA = 3;

    private boolean hoverActive, animationActive;
    private SecondaBarra sb;
    private String text, categoria;
    private JLabel lbl_nome;
    private BarraDiSeparazione bds;
    private int lunghezzaTesto, tipologia;
    private double wBds;
    private PanelTabella pt;

    public BtnText(String text, SecondaBarra sb, String categoria) {
        
        this.tipologia = -1;
        this.sb = sb;
        this.text = text;
        this.categoria = categoria;
        this.hoverActive = false;
        this.animationActive = false;
        this.wBds = 0;

        lbl_nome = new JLabel("\u2022 " + text.toUpperCase());
        lbl_nome.setBackground(sb.getBackground());
        
        setPreferredSize(new Dimension((int)sb.getPreferredSize().getWidth() - 5, 25));
        setUp(Color.WHITE);
    }

    public BtnText(String text, PanelTabella pt, int tipologia) {

        this.tipologia = tipologia;
        this.pt = pt;
        this.text = text;
        this.hoverActive = false;
        this.animationActive = false;
        this.wBds = 0;

        lbl_nome = new JLabel("\u2022 " + text.toUpperCase());
        lbl_nome.setBackground(pt.getBackground());

        setPreferredSize(new Dimension((int)pt.getPreferredSize().getWidth() - 5, 25));
        setUp(Color.BLACK);
    }

    private void setUp(Color lblForeground) {
        this.lunghezzaTesto = text.length() * 11;

        setLayout(Global.FL_L_0_0);
        setAnimationThread();

        
        lbl_nome.setForeground(lblForeground);
        lbl_nome.setFont(Global.FONT_MEDIO);
        lbl_nome.setPreferredSize(new Dimension(lunghezzaTesto + 20, 20));
        lbl_nome.addMouseListener(this);
        lbl_nome.setOpaque(true);
        

        bds = new BarraDiSeparazione((int)wBds, lbl_nome.getForeground());
        bds.setVisible(true);

        add(lbl_nome);
        add(Box.createHorizontalStrut((int)getPreferredSize().getWidth() - (lunghezzaTesto + 20)));
        add(Box.createHorizontalStrut(18));
        add(bds);
    }

    public String getCategoria() {
        return categoria;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean hoverActive() {
        return hoverActive;
    }

    @Override
    public int getMilliseconds() {
        return 0;
    }

    @Override
    public void hover() {
        
    }

    @Override
    public void inHover() {
        animationActive = true;
        hoverActive = true;
        attivaThread();
    }

    @Override
    public void outHover() {
        hoverActive = false;
    }

    @Override
    public boolean animationActive() {
        return animationActive;
    }

    @Override
    public void anima() {

        if(hoverActive) {

            if(wBds < lunghezzaTesto) {
                wBds += 0.0001;
                bds.setWidth((int)wBds);
            }
        } else {
            wBds -= 0.0001;
            bds.setWidth((int)wBds);

            if(wBds <= 0) animationActive = false;
        }
    }

    @Override
    public void afterClick() {
        
        switch (tipologia) {
            case -1: sb.aggiornaTerzaBarra(this);
            break;
            case 0: pt.mostraInfo();
            break;
            case 1: pt.mostraFK();
            break;
            case 2: pt.mostraTriggers();
            break;
            case 3: pt.mostraTabella();
            break;
            default:
            break;
        }
        
    }

}
