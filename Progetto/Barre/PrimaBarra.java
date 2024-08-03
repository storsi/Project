package Progetto.Barre;

import java.awt.Dimension;

import Progetto.Main.Global;

public class PrimaBarra extends Barra{

    private SecondaBarra sb;
    
    public PrimaBarra(SecondaBarra sb) {
        super(Global.COLORE_PRIMA_BARRA, Global.FL_C_10_10, false);
        setPreferredSize(new Dimension((int)(Global.FRAME_WIDTH * 0.05), Global.FRAME_HEIGHT));

        this.sb = sb;

        setUp();
    }

    @Override
    protected void setUp() {
        
        for(int i = 0; i < BtnIcon.NOMI_PRIMA_BARRA.length; i++) {
            add(new BtnIcon(i, this));
        }
    }

    @Override
    public void btnIconClicked(BtnIcon btn) {

        if(btn.getTipologia() == 0) sb.mostraElementi(BtnIcon.NOMI_PRIMA_BARRA[btn.getTipologia()]);
        else;
    }

    @Override
    protected void animaBarra() {
        
    }
}
