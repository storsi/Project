package Progetto.Barre;

import java.awt.Dimension;

import javax.swing.JPanel;

import Progetto.Main.Global;

public class PrimaBarra extends JPanel{

    private SecondaBarra sb;
    
    public PrimaBarra(SecondaBarra sb) {
        
        setPreferredSize(new Dimension((int)(Global.FRAME_WIDTH * 0.05), Global.FRAME_HEIGHT));
        setBackground(Global.COLORE_PRIMA_BARRA);
        setLayout(Global.FL_C_10_10);

        this.sb = sb;

        setBtns();
    }

    private void setBtns() {
        
        for(int i = 0; i < BtnIcon.NOMI_PRIMA_BARRA.length; i++) {
            add(new BtnIcon(i, this));
        }
    }

    public void btnIconCliccato(int tipologia) {
        
        if(tipologia == 0) sb.mostraElementi(BtnIcon.NOMI_PRIMA_BARRA[tipologia]);
        else {
            //Ottenere le informazioni dal db
        }
    }
}
