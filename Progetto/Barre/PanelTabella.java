package Progetto.Barre;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Progetto.Main.Global;
import Progetto.Main.Strumenti.PanelPerBtn;

public class PanelTabella extends PanelPerBtn{

    private JLabel lbl_nome;
    private int width, height;
    private boolean lungo;
    private ParteBassa pb;

    public PanelTabella(String nome, boolean lungo, ParteBassa pb) {
        super();

        this.lungo = lungo;
        this.pb = pb;
        
        setUp(nome);
        setDimension();


        add(lbl_nome);
    }

    private void setDimension() {

        if(lungo) {
            width = (int)(pb.getPreferredSize().getWidth() * 0.9);
            height = (int)(pb.getPreferredSize().getHeight() * 0.05);

            pb.setLayout(Global.FL_C_10_10);

            lbl_nome.setPreferredSize(new Dimension((int)(width * 0.4), height / 2));
        } else {
            width = (int)(pb.getPreferredSize().getWidth() * 0.3);
            height = (int)(pb.getPreferredSize().getHeight() * 0.5);

            pb.setLayout(Global.FL_L_30_30);

            lbl_nome.setPreferredSize(new Dimension(width, (int)(height * 0.1)));
            
        }

        super.changeDimension(width, height);
    }

    private void setUp(String nome) {
        lbl_nome = new JLabel(nome, SwingConstants.CENTER);
        lbl_nome.setFont(Global.FONT_MEDIO);
    }

    @Override
    public void onClick() {
        System.out.println("click");
    }

    @Override
    public void inHover() {
        System.out.println("In hover");
    }

    @Override
    public void outHover() {
        System.out.println("Out hover");
    }
    
}
