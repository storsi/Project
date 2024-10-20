package Progetto.Barre;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Box;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import Progetto.DBM.CreaTabella;
import Progetto.Main.Global;
import Progetto.Main.Interface.Animated;
import Progetto.Main.Interface.Clickable;
import Progetto.Main.Interface.Hover;
import Progetto.Main.Strumenti.BarraDiSeparazione;

public class BtnText extends JPanel implements Hover, Animated, Clickable{

    public enum Tipologia {
        
        SCELTA_FILE,
        MOSTRA_INFO_TABELLA,
        MOSTRA_FOREIGN_KEYS,
        MOSTRA_TRIGGERS,
        MOSTRA_TABELLA,
        CREA_TABELLA,
        CREA_COLONNA,
        RESET_COLONNA
    }

    private boolean hoverActive, animationActive, pulsanteConSfondo, animazioneInPausa;
    private SecondaBarra sb;
    private String text, categoria;
    private JLabel lbl_nome;
    private BarraDiSeparazione bds;
    private int lunghezzaTesto;
    private Tipologia tipologia;
    private double wBds;
    private PanelTabella pt;

    private CreaTabella creaTabella;

    /**
     * Rispettivamente:
     * - Colore iniziale del background del pulsante utile per la creazione della tabella (prima dell'animazione)
     * - Colore del testo contenente nel pulsante utile per la creazione della tabella
     * - Colore finale del background del pulsante utile per la creazione della tabella (dopo l'animazione)
     */
    private Color coloreFinale = new Color(128,112,137);
    private Color coloreIniziale = Color.WHITE;
    private Color coloreForeground = Color.BLACK;
    private Color coloreDisabilitato = Color.GRAY;

    /**
     * Variabili per la gestione dell'animazione pr i BtnText con la variabile pulsanteConSfondo = true
     */
    private float red, blue, green;

    /**
     * Variabili usata per modificare il colore durante l'animazione dei BtnText con la variabile
     * pulsanteConSfondo = true
     */
    private final double RATIO_CAMBIO_COLORE = 0.000005;

    public BtnText(String text, SecondaBarra sb, String categoria) {
        
        this.tipologia = Tipologia.SCELTA_FILE;
        this.sb = sb;
        this.text = text;
        this.categoria = categoria;
        this.hoverActive = false;
        this.animationActive = false;
        this.wBds = 0;
        this.pulsanteConSfondo = this.animazioneInPausa = false;

        lbl_nome = new JLabel("\u2022 " + text.toUpperCase());
        lbl_nome.setBackground(sb.getBackground());

        setOpaque(false);
        
        setPreferredSize(new Dimension((int)sb.getPreferredSize().getWidth() - 5, 25));
        setUp(Color.WHITE);
    }

    public BtnText(String text, PanelTabella pt, Tipologia tipologia) {

        this.tipologia = tipologia;
        this.pt = pt;
        this.text = text;
        this.hoverActive = false;
        this.animationActive = false;
        this.wBds = 0;
        this.pulsanteConSfondo = this.animazioneInPausa = false;

        lbl_nome = new JLabel("\u2022 " + text.toUpperCase());
        lbl_nome.setBackground(pt.getBackground());

        setOpaque(false);

        setPreferredSize(new Dimension((int)pt.getPreferredSize().getWidth() - 5, 25));
        setUp(Color.BLACK);
    }

    public BtnText(Tipologia tipologia, Object parent, String text) {
        
        this.tipologia = tipologia;
        this.text = text;
        
        this.pulsanteConSfondo = true;
        this.animazioneInPausa = false;
        
        this.red = coloreIniziale.getRed();
        this.green = coloreIniziale.getGreen();
        this.blue = coloreIniziale.getBlue();
        
        lbl_nome = new JLabel(text);

        if(parent instanceof CreaTabella) creaTabella = (CreaTabella)parent;
        
        
        addMouseListener(this);
        setOpaque(false);
        setBackground(coloreIniziale);

        setUp(coloreForeground);
    }

    private void setUp(Color lblForeground) {

        setLayout(Global.FL_L_0_0);
        setAnimationThread();
        
        lbl_nome.setForeground(lblForeground);
        lbl_nome.setOpaque(false);
        lbl_nome.setFont(Global.FONT_MEDIO);

        if(!pulsanteConSfondo) {
            lbl_nome.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), 20));
            lbl_nome.addMouseListener(this);

            this.lunghezzaTesto = text.length() * getFont().getSize();

            bds = new BarraDiSeparazione((int)wBds, lbl_nome.getForeground());
            bds.setVisible(true);

            add(lbl_nome);
            add(Box.createHorizontalStrut(14));
            add(bds);
        } else {
            
            lbl_nome.setPreferredSize(new Dimension(200, 37));
            lbl_nome.setHorizontalAlignment(SwingConstants.CENTER);

            add(lbl_nome);
        }
    }

    /**
     * Disabilita il pulsante, con eventuali opzioni aggiuntive (come cambio di colore o richiamo 
     * di ulteriori funzioni)
     */
    public void disableButton() {
        
        setEnabled(false);
        setBackground(coloreDisabilitato);

    }

    /**
     * Abilita il pulsante e ripristina le sue caratteristiche (come il colore)
     */
    public void ableButton() {

        setEnabled(true);
        setBackground(coloreIniziale);

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
        animationActive = true;
        hoverActive = false;
        attivaThread();
    }

    @Override
    public boolean animationActive() {
        return animationActive;
    }

    @Override
    public void anima() {

        if(isEnabled())
            if(hoverActive) {

                if(pulsanteConSfondo) {
                    
                    red += (coloreFinale.getRed() - coloreIniziale.getRed()) * RATIO_CAMBIO_COLORE;
                    green += (coloreFinale.getGreen() - coloreIniziale.getGreen()) * RATIO_CAMBIO_COLORE;
                    blue += (coloreFinale.getBlue() - coloreIniziale.getBlue()) * RATIO_CAMBIO_COLORE;
                    

                    setBackground(new Color((int)Math.round(red), (int)Math.round(green), (int)Math.round(blue)));                  
                    revalidate();
                    repaint();

                    if((int)Math.round(red) == coloreFinale.getRed()) animationActive = false;

                } else {
                    
                    wBds += 0.0001;
                    bds.setWidth((int)wBds);

                    if(wBds >= lunghezzaTesto) animationActive = false;
                }
                
            } else {

                if(pulsanteConSfondo) {

                    if(red != coloreFinale.getRed()) {
                        
                        red -= (coloreFinale.getRed() - coloreIniziale.getRed()) * RATIO_CAMBIO_COLORE;
                        green -= (coloreFinale.getGreen() - coloreIniziale.getGreen()) * RATIO_CAMBIO_COLORE;
                        blue -= (coloreFinale.getBlue() - coloreIniziale.getBlue()) * RATIO_CAMBIO_COLORE;

                        setBackground(new Color((int)Math.round(red), (int)Math.round(green), (int)Math.round(blue)));
                        revalidate();
                        repaint();
                    }

                    if((int)Math.round(red) == coloreIniziale.getRed()) animationActive = false;

                } else {
                    
                    wBds -= 0.0001;
                    bds.setWidth((int)wBds);
                    
                    if(wBds <= 0) animationActive = false;
                }

            }
    }

    @Override
    public void afterClick() {
        
        switch (tipologia) {
            case SCELTA_FILE: sb.aggiornaTerzaBarra(this);
            break;
            case MOSTRA_INFO_TABELLA: pt.mostraInfo();
            break;
            case MOSTRA_FOREIGN_KEYS: pt.mostraFK();
            break;
            case MOSTRA_TRIGGERS: pt.mostraTriggers();
            break;
            case MOSTRA_TABELLA: pt.mostraTabella();
            break;
            case CREA_TABELLA: creaTabella.creaTabella();
            break;
            case RESET_COLONNA: 

                creaTabella.reset();
                disableButton();

            break;
            case CREA_COLONNA: creaTabella.prosegui();
            break;
        }
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(pulsanteConSfondo) {
            
            Graphics2D g2d = (Graphics2D) g;
            int raggioCurvatura = 40;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, (int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight(), raggioCurvatura, raggioCurvatura);
        
        }

        
    }

}
