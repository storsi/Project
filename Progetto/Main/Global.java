package Progetto.Main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.Font;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.BorderLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.util.regex.Pattern;

import Progetto.Comunicazione.Comunicatore;

public class Global {
    
    //Informazioni Panel principale
    public static final int FRAME_WIDTH = 1400;
    public static final int FRAME_HEIGHT = 800;
    public static final int BARRA_DI_SEPARAZIONE_HEIGHT = 3;
    
    public static final int AVVISO_WIDTH = (int)(FRAME_WIDTH * 0.4);
    public static final int AVVISO_HEIGHT = 300;

    public static final int ICON_SIZE = (int)(FRAME_WIDTH * 0.03);

    public static String pathToDB = getDataBasePath();

    //Colori
    public static final Color COLORE_PRIMA_BARRA = new Color(46,41,58);
    public static final Color COLORE_SECONDA_BARRA = new Color(61,53,75);
    public static final Color COLORE_TERZA_BARRA = new Color(91,73,101);
    public static final Color COLORE_AVVISO = new Color(162,131,178);

    //Layout
    public static final FlowLayout FL_C_0_0 = new FlowLayout(FlowLayout.CENTER, 0, 0);
    public static final FlowLayout FL_L_15_10 = new FlowLayout(FlowLayout.LEFT, 15, 10);
    public static final FlowLayout FL_L_30_10 = new FlowLayout(FlowLayout.LEFT, 30, 10);
    public static final FlowLayout FL_L_0_0 = new FlowLayout(FlowLayout.LEFT, 0, 0);
    public static final FlowLayout FL_C_10_10 = new FlowLayout(FlowLayout.CENTER, 10, 10);
    public static final FlowLayout FL_C_10_40 = new FlowLayout(FlowLayout.CENTER, 10, 40);
    public static final FlowLayout FL_C_40_10 = new FlowLayout(FlowLayout.CENTER, 40, 10);
    public static final FlowLayout FL_L_30_30 = new FlowLayout(FlowLayout.LEFT, 30, 30);
    public static final FlowLayout FL_R_20_30 = new FlowLayout(FlowLayout.RIGHT, 20, 30);
    public static final FlowLayout FL_R_10_10 = new FlowLayout(FlowLayout.RIGHT, 10, 10);
    public static final BorderLayout BL_0_0 = new BorderLayout(0, 0);

    //Font
    public static final Font FONT_PICCOLO = new Font("Courier", Font.PLAIN, 13);
    public static final Font FONT_MEDIO = new Font("Courier", Font.BOLD, 18);
    public static final Font FONT_GRANDE = new Font("Courier", Font.BOLD, 25);
    public static final Font FONT_MOLTO_GRANDE = new Font("Courier", Font.BOLD, 45);

    public static final Comunicatore c = new Comunicatore();


    /************************************ FUNZIONI **************************************************/

    public static String getDataBasePath() {
        File f;
        String path = "";
        String[] percorsi;
        int counter = 0;

        try {
            do{
                if(counter == 2) {
                    path += "/";
                    counter = 0;
                }

                path += ".";
                f = new File(path);
                percorsi = f.getCanonicalPath().split(Pattern.quote(File.separator)); 
                counter++;
            
            }while(percorsi.length != 3);

        } catch (Exception e) {
            e.printStackTrace();
        }

        path += "/OneDrive - Politecnico di Milano/Programmazione/Sqlite/Databases/";

        return path;
    }

    public static Icon getIcon(String path, int w) {
        Image im = null;


        

        try {
            im = ImageIO.read(new File("Progetto/img/" + path));

        } catch(Exception e){
            System.out.println("Errore nell'apertura del file");
        }

        return new ImageIcon(im.getScaledInstance(w, w, Image.SCALE_SMOOTH));
    }

    public static int getTextWidth(String text, Font font) {
        
        AffineTransform afr = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(afr, true, true);        

        return (int)font.getStringBounds(text, frc).getWidth();
    } 
}
