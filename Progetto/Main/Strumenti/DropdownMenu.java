package Progetto.Main.Strumenti;

import javax.swing.JPanel;

import Progetto.Main.Global;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Color;


public class DropdownMenu extends JPanel{

    private String[] choices;
    private int indexOfChoice;

    private Label lbl_choice;
    private JPanel pnl_choices;

    private ScrollPane sp;
    private Dimension dim;
    
    public DropdownMenu(String[] choices, Dimension dim) {

        this.choices = choices;
        this.dim = dim;

        setupDdM();

    }

    private void setupDdM() {
        indexOfChoice = -1;

        lbl_choice = new Label("", Global.FONT_MEDIO, dim, Color.BLACK);
        lbl_choice.setIcon(Global.getIcon("vDdM.png", 15));
        lbl_choice.setHorizontalTextPosition(SwingConstants.LEFT);
    }
}

class Choice extends JButton{

}
