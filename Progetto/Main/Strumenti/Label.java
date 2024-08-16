package Progetto.Main.Strumenti;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class Label extends JLabel{
    
    public Label(String text, boolean adaptSizeToText, Font font) {
        setText(text);
        setFont(font);

        if(adaptSizeToText) setPreferredSize(new Dimension(text.length() * font.getSize(), font.getSize()));
    }
}
