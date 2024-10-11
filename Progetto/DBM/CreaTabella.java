package Progetto.DBM;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.ButtonGroup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Progetto.Barre.BtnIcon;
import Progetto.Barre.BtnText;
import Progetto.Barre.BtnText.Tipologia;
import Progetto.Main.Global;
import Progetto.Main.Strumenti.BarraDiSeparazione;
import Progetto.Main.Strumenti.Label;
import Progetto.Main.Strumenti.Panel;
import Progetto.Main.Strumenti.RadioButton;
import Progetto.Main.Strumenti.ScrollPane;
import Progetto.Main.Strumenti.TextArea;

public class CreaTabella extends JPanel implements ActionListener{

    //VARIABILI GENERALI
    /**
     * Inizializzazione dei valori che indicheranno la dimensione del panel generale (la classe). GENERAL_HEIGHT è leggermente più 
     * piccolo perchè è presente il Label che descrive la scena che è alto esattamente (int)(Global.AVVISO_HEIGHT * 0.1)
     */
    private final int GENERAL_WIDTH = (int)(Global.FRAME_WIDTH * 0.7);
    private final int GENERAL_HEIGHT = (int)(Global.FRAME_HEIGHT * 0.72);

    /**
     * Inizializzazione dei valori che indicheranno la dimensione dei sottopanels che conterranno
     * le colonne già create e lo spazio per crearle
     */
    private final int PANEL_WIDTH = (int)(GENERAL_WIDTH * 0.46);
    private final int PANEL_HEIGHT = (int)(GENERAL_HEIGHT * 0.9);

    /**
     * Inizializzazione dei valori che indicheranno la dimensione dei Labels all'interno dei Panels.
     * LABEL_HEIGHT verrà poi ridimensionato a seconda delle necessità
     */
    private final int LABEL_WIDTH = PANEL_WIDTH;
    private final int LABEL_HEIGHT = 30;

    /**
     * Rispettivamente:
     * - Il colore destinato a tutti i background dei Panel
     * - Il colore destinato alle scritte di tutte le Label
     */
    private final Color BACKGROUND_COLOR = Global.COLORE_AVVISO;
    private final Color FOREGROUND_COLOR = Color.WHITE;

    /**
     * Indica il massimo di caratteri che si possono inserire nel TextArea designato a contenere il nome
     * della colonna 
     */
    private final int MAX_CHAR_NOME_COLONNA = 27;

    /**
     * Spessore delle istanze della classe BarraDiSeparazione (Progetto.Main.Strumenti.BarraDiSeparazione)
     */
    private final int BDS_WIDTH = (int)(LABEL_WIDTH * 0.1);

    /**
     * Rispettivamente:
     * - Diensione del panel generale (la classe) che è composto dai valori di GENERAL_WIDTH e GENERAL_HEIGHT
     * - Dimensione dei sottopanels che è composto dai valori di PANEL_WIDTH e PANEL_HEIGHT
     * - Dimesnione dei sottopanels che conterranno gli elementi per il raggiungimento del loro scopo
     * - Dimensione dei Label che indicheranno la sezione
     * - Dimensione del TextArea utile per l'inserimento del nome della colonna
     * - Dimesnione della BarraDiSeparazione che separa il titolo con il sottopanel
     * - Dimensione del Panel che conterrà il BtnText che creerà la tabella
     * - Dimensione dei Panels che conterranno le informazioni per la creazione della colonnas
     * - Dimensione del Panel dedicato all'aggiunta di ulteriori informazioni quando si scelgono determinate caratteristiche
    */
    private Dimension 
    dim_generale, dim_pannelli, dim_sottopannelli, dim_label, dim_taNomeColonna, dim_creaTabella,
    dim_infoColonna, dim_aggiungiDettagli, dim_sp;


    //********************************** VARIABILI CREA_COLONNA **********************************//
    
    private final int ICON_SIZE = 35;
    
    /**
     * Rispettivamente:
     * - Label che servirà come titolo per il sottopanel dedicato alla creazione delle colonne. Conterrà
     *   la frase: "CREA COLONNE"
     * - Label che servirà ad indicare la sezione per il nome della colonna. 
     *   Conterrà la frase: "NOME COLONNA"
     * - Label che servirà ad indicare la sezione per la scelta della tipologia della colonna. Conterrà
     *   la frase: "TIPOLOGIA"
     * - Label che servirà ad indicare la sezione per la scelta delle caratteristiche della colonna.
     *   Conterrà la frase: "CARATTERISTICHE"
     * - Label che intitola il TextArea assegnato per le informazioni aggiuntive dovute alla caratteristica
     *   Default
     * - Label che intitola il TextArea assegnato per le informazioni aggiuntive dovute alla caratteristica
     *   Collate
     * - Label che intitola il TextArea assegnato per le informazioni aggiuntive dovute alla caratteristica
     *   Check
     */
    private Label lbl_creaColonna, lbl_nomeColonna, lbl_tipologia, lbl_caratteristiche, lbl_aggiungiDettagli,
    lbl_default, lbl_collate, lbl_check;

    /**
     * Rispettivamente:
     * - Il sottopanel che si occuperà di creare le colonne. Al suo interno conterrà la label lbl_creaColonna
     *   ed un ulteriore panel che conterrà i RadioButtons per scegliere le caratteristiche della colonna
     * - L'ulteriore sottopanel che contiene i RadioButtons per scegliere le caratteristiche e la tipologua
     *   della colonna
     * - Panel che conterrà i RadioButtons che permetteranno di scegliere le caratteristiche delle colonne
     * - Panel che conterrà i pulsanti per la Creazione della colonna, per il reset delle informazioni e per proseguire nel processo
     * - Panel che conterrà le informazioni ed il TextArea per l'inserimento del nome della colonna durante la sua creazione
     * - Panel che conterrà le informazioni ed i RadioButtons per la scelta della tipologia della nuova colonna
     * - Panel dove verranno richieste le informazioni (se presenti) aggiuntive per la colonna
     */
    private Panel pnl_creaColonna, pnl_creaColonnaInformazioni, pnl_BtnCaratteristiche, pnl_creaColonnaBtnFinali, 
    pnl_nomeColonna, pnl_tipologiaColonna, pnl_aggiungiDettagli;

    /**
     * Istanza della classe TextArea (Progetto.Main.Strumenti.TextArea) che servirà per:
     * - L'inserimento del nome della colonna che si sta creando. Questa non potrà contenere valori nulli
     * - L'inserimento delle informazioni per la caratteristica Default
     * - L'inserimento delle informazioni per la caratteristica Collate
     * - L'inserimento delle informazioni per la caratteristica Check
     */
    private TextArea ta_nomeColonna, ta_default, ta_collate, ta_check;

    /**
     * Istanza della classe ButtonGroup con lo scopo di riunire tutti i RadioButtons per la scelta della 
     * tipologia della colonna
     */
    private ButtonGroup bg_tipologia;

    /**
     * Istanza della classe ScrollPane (Progetto.Main.Strumenti.ScrollPane) con lo scopo di contenere il Panel
     * pnl_creaColonna in modo da garantirgli una barra di scorrimento nel caso si superino le 
     * dimensioni del panel stesso a causa degli elementi che inseriremo
     */
    private ScrollPane sp_creaColonna;

    /**
     * Array con lo scopo di contenere tutti i RadioButton che creo. L'ordine di questi sarà indicato
     * dal loro indice
     */
    private RadioButton[] radioButtons;

    /**
     * Rispettivamente:
     * - Pulsante con lo scopo di confermare la creazione della colonna, di inserire la colonna nel Panel 
     *   per le colonne già create e di resettare successivamente le sue informazioni, preparando 
     *   pnl_creaColonnaInformazioni per una nuova colonna
     * - Pulsante con lo scopo di resettare tutte le informazioni che abbiamo inserito sulla colonna corrente
     *   senza però crearla
     */
    private BtnText btn_creaColonna, btn_resetColonna;

    /**
     * Barra che separa il titolo (lbl_creaColonna) con il Panel (pnl_creaColonneInformazioni)
     */
    private BarraDiSeparazione bds_creaColonnaAlta, bds_creaColonnaBassa;

    /**
     * Array che contiene tutti i nomi dei RadioButton che inseriremo (0 INTEGER, 1 REAL, 2 TEXT, 3 BLOB, 
     * 4 PRIMARY KEY, 5 UNIQUE, 6 NOT NULL, 7 AUTO INCREMENT, 8 DEFAULT, 9 CHECK, 10 COLLATE)
     */
    private static String[] radioButtonNames = {
        "INTEGER", "REAL", "TEXT", "BLOB", "PRIMARY KEY", "UNIQUE", "NOT NULL",
        "AUTO INCREMENT", "DEFAULT", "CHECK", "COLLATE"
    };

    /**
     * Array che contiene tutte le frasi destionate ad essere i "messaggi" per ogni RadioButton che inseriremo
     */
    private static String[] descrizioneRadioButtons = {
        "Permette l'inserimento dei soli valori numerici interi. <br> <br> Esclude: collate", 
        "Permette l'inserimento di numeri anche con una parte decimale, con un massimo di 2 cifre dopo la virgola. <br> <br> Esclude: collate | autoincrement", 
        "Rappresenta stringhe di caratteri ed include spazi, simboli, numeri (trattati come caratteri) e punteggiatura. <br> <br> Esclude: autoincrement",
        "È usato per memorizzare dati binari senza alcuna conversione o interpretazione, come immagini, file audio, file video e file binari. <br> <br> Esclude: collate | autoincrement | default",
        "Impone l'inserimento di un record unico e non nullo rispetto agli altri elementi della colonna. <br> <br> Impone: Unique | Not Null <br> Uso: tutte le tipologie",
        "Assicura che tutti i record di una colonna siano unici, senza duplicati. <br> <br> Impone: nessuna imposizione <br> Uso: tutte le tipologie",
        "Impedisce a questa colonna di possedere valori nulli. Ogni riga ha, quindi, un valore. <br> <br> Impone: Nessuna imposizione <br> Uso: tutte le tipologie",
        "Genera automaticamente valori sequenziali unici per la colonna. Ogni nuovo record riceve un valore maggiore del precedente. <br> <br> Impone: Primary Key <br> Uso: Integer",
        "Specifica un valore predefinito per una colonna se non viene fornito alcun valore durante l'inserimento di un record. <br> <br> Impone: nessuna imposizione <br> Uso: Integer | Real | Text",
        "impone un vincolo personalizzato su una colonna, assicurando che i dati inseriti soddisfino una determinata condizione <br> <br> Impone: nessuna imposizione <br> Uso: tutte le tipologie",
        "definisce la modalità di confronto delle stringhe per una colonna di tipo \"TEXT\", influenzando l'ordinamento e le ricerche. <br> <br> Impone: nessuna imposizione <br> Uso: text"
    };

    //********************************* VARIABILI MOSTRA_COLONNE *********************************//
    /**
     * Rispettivamente:
     * - Il Panel che conterrà tutti gli elementi necessari a mostrare le colonne già create
     * - Il Panel figlio del primo che conterrà l'elenco delle colonne create
     * - Panel che conterrà il pulsante per la creazione della tabella (utile per riempire tutta la lunghezza del Panel
     *   genitore per inserire correttamente il bds_colonneBassa)
     */
    private JPanel pnl_colonne, pnl_mostraColonne, pnl_creaTabella;

    /*
     * Label che indicherà la sezione. Mostrerà la scritta: "COLONNE"
     */
    private JLabel lbl_colonne;

    /**
     * Barra che separa il titolo (lbl_colonne) con il Panel (pnl_mostraColonne)
     */
    private BarraDiSeparazione bds_colonneAlta, bds_colonneBassa;

    //********************************* VARIABILI CREA_TABELLA *********************************//

    /**
     * Istanza della classe BtnText (Progetto.Barre.BtnText) che, appunto, creerà la tabella
     */
    private BtnText btn_creaTabella;
    
    public CreaTabella() {
        dim_generale = new Dimension(GENERAL_WIDTH, GENERAL_HEIGHT);
        dim_pannelli = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
        dim_sottopannelli = new Dimension(PANEL_WIDTH, (int)(PANEL_HEIGHT * 0.69));
        dim_sp = new Dimension(PANEL_WIDTH, (int)(PANEL_HEIGHT * 0.69));
        dim_label = new Dimension(LABEL_WIDTH, LABEL_HEIGHT);
        dim_taNomeColonna = new Dimension((int)(LABEL_WIDTH * 0.6), LABEL_HEIGHT);
        dim_creaTabella = new Dimension(PANEL_WIDTH, (int)(PANEL_HEIGHT * 0.2));
        dim_infoColonna = new Dimension(PANEL_WIDTH, (int)(PANEL_HEIGHT * 0.178));
        dim_aggiungiDettagli = new Dimension(PANEL_WIDTH, 50);

        bg_tipologia = new ButtonGroup();
        radioButtons = new RadioButton[radioButtonNames.length];

        setPreferredSize(dim_generale);
        setBackground(BACKGROUND_COLOR);
        setOpaque(false);
        setLayout(Global.FL_L_15_10);
        
        /*
         * Creazione del Panel dedicato alla creazione delle colonne
         */
        setUpCreazioneColonne();

        add(pnl_creaColonna);
        
        /*
         * Creazione del Panel dedicato alle colonne già create
         */
        setUpColonne();

        add(pnl_colonne);

        setVisible(false);

    }

    private void setUpColonne() {
        /**
         * Creazione del Panel che conterrà tutti gli elementi per mostrare le colonne create
         */
        pnl_colonne = new JPanel(Global.FL_C_10_10);
        pnl_colonne.setPreferredSize(dim_pannelli);
        pnl_colonne.setBackground(BACKGROUND_COLOR);

        lbl_colonne = new JLabel("COLONNE:", SwingConstants.CENTER);
        lbl_colonne.setPreferredSize(dim_label);
        lbl_colonne.setForeground(FOREGROUND_COLOR);
        lbl_colonne.setFont(Global.FONT_GRANDE);

        bds_colonneAlta = new BarraDiSeparazione(BDS_WIDTH, FOREGROUND_COLOR, true);
        bds_colonneBassa = new BarraDiSeparazione(BDS_WIDTH, FOREGROUND_COLOR, true);

        pnl_mostraColonne = new JPanel(Global.FL_C_10_10);
        pnl_mostraColonne.setPreferredSize(dim_sottopannelli);
        pnl_mostraColonne.setBackground(BACKGROUND_COLOR);

        pnl_creaTabella = new JPanel(Global.FL_C_10_10);
        pnl_creaTabella.setPreferredSize(dim_creaTabella);
        pnl_creaTabella.setBackground(BACKGROUND_COLOR);

        btn_creaTabella = new BtnText(Tipologia.CREA_TABELLA, this, "CREA TABELLA");
        btn_creaTabella.disableButton();

        pnl_creaTabella.add(btn_creaTabella);

        pnl_colonne.add(lbl_colonne);
        pnl_colonne.add(bds_colonneAlta);
        pnl_colonne.add(Box.createVerticalStrut(30));
        pnl_colonne.add(pnl_mostraColonne);
        pnl_colonne.add(bds_colonneBassa);
        pnl_colonne.add(pnl_creaTabella);

    }

    private void setUpCreazioneColonne() {
        /*
         * Creazione del Panel che conterrà tutti gli elementi per la creazione delle colonne
         */

        //Creazione del ScrollPane
        sp_creaColonna = new ScrollPane(dim_sp, Color.WHITE);
        
        //Creazione dei Panels
        pnl_creaColonna = new Panel(Global.FL_C_10_10, dim_pannelli, BACKGROUND_COLOR);
        pnl_creaColonnaInformazioni = new Panel(Global.FL_C_10_10, dim_sottopannelli, BACKGROUND_COLOR);
        pnl_nomeColonna = new Panel(Global.FL_C_0_0, dim_infoColonna, BACKGROUND_COLOR);
        pnl_tipologiaColonna = new Panel(Global.FL_C_0_0, dim_infoColonna, BACKGROUND_COLOR);
        pnl_BtnCaratteristiche = new Panel(Global.FL_C_0_0, dim_infoColonna, BACKGROUND_COLOR);
        pnl_aggiungiDettagli = new Panel(Global.FL_C_10_10, dim_aggiungiDettagli, BACKGROUND_COLOR, false);
        pnl_creaColonnaBtnFinali = new Panel(Global.FL_C_10_10, dim_creaTabella, BACKGROUND_COLOR);


        //Creazione dei Labels
        lbl_creaColonna = new Label("CREA COLONNE:", Global.FONT_GRANDE, dim_label, FOREGROUND_COLOR);
        lbl_nomeColonna = new Label("NOME COLONNA:", Global.FONT_MEDIO, dim_label, FOREGROUND_COLOR);
        lbl_tipologia = new Label("TIPOLOGIA:", Global.FONT_MEDIO, dim_label, FOREGROUND_COLOR);
        lbl_caratteristiche = new Label("CARATTERISTICA:", Global.FONT_MEDIO, dim_label, FOREGROUND_COLOR);
        lbl_aggiungiDettagli = new Label("AGGIUNGI DETTAGLI", Global.FONT_MEDIO, dim_label, FOREGROUND_COLOR);
        lbl_default = new Label("ELEMENTO DI DEFAULT:", Global.FONT_PICCOLO, dim_label, FOREGROUND_COLOR);


        //Creazione delle BarreDiScorrimento
        bds_creaColonnaAlta = new BarraDiSeparazione(BDS_WIDTH, FOREGROUND_COLOR, true);
        bds_creaColonnaBassa = new BarraDiSeparazione(BDS_WIDTH, FOREGROUND_COLOR, true);


        //Creazione dei TextAreas
        ta_nomeColonna = new TextArea(Global.FONT_MEDIO, dim_taNomeColonna);
        ta_nomeColonna.setCharacterLimit(MAX_CHAR_NOME_COLONNA);

        ta_default = new TextArea(Global.FONT_MEDIO, dim_taNomeColonna);
        ta_default.setCharacterLimit(MAX_CHAR_NOME_COLONNA);


        //Creazione dei RadioButtons
        for(int i = 0; i < 4; i++) radioButtons[i] = new RadioButton(i, radioButtonNames[i], descrizioneRadioButtons[i], bg_tipologia, this);
        
        for(int i = 4; i < radioButtonNames.length; i++) {
            //Gli ultimi tre RadioButton hanno necessità di maggiori informazioni, quini needWord = true
            if(i >= 8) radioButtons[i] = new RadioButton(i, radioButtonNames[i], descrizioneRadioButtons[i], true, this);
            else radioButtons[i] = new RadioButton(i, radioButtonNames[i], descrizioneRadioButtons[i], false, this);
        }


        //Creazione dei BtnTexts
        btn_creaColonna = new BtnText(Tipologia.CREA_COLONNA, this, "PROSEGUI");
        btn_resetColonna = new BtnText(Tipologia.RESET_COLONNA, this, "RESET");
        btn_resetColonna.disableButton();


        //Aggiunta degli elementi
        sp_creaColonna.setViewportView(pnl_creaColonnaInformazioni);

        pnl_nomeColonna.add(lbl_nomeColonna);
        pnl_nomeColonna.add(ta_nomeColonna);

        pnl_tipologiaColonna.add(lbl_tipologia);

        pnl_aggiungiDettagli.add(lbl_aggiungiDettagli);
        pnl_aggiungiDettagli.add(lbl_default);
        pnl_aggiungiDettagli.add(ta_default);

        pnl_creaColonnaBtnFinali.add(btn_creaColonna);
        pnl_creaColonnaBtnFinali.add(btn_resetColonna);

        pnl_creaColonnaInformazioni.add(pnl_nomeColonna);

        for(int i = 0; i < 4; i++) pnl_tipologiaColonna.add(radioButtons[i]);

        pnl_creaColonnaInformazioni.add(pnl_tipologiaColonna);
        pnl_BtnCaratteristiche.add(lbl_caratteristiche);

        for(int i = 4; i < radioButtons.length; i++) pnl_BtnCaratteristiche.add(radioButtons[i]);

        pnl_creaColonnaInformazioni.add(pnl_BtnCaratteristiche);
        pnl_creaColonnaInformazioni.add(pnl_aggiungiDettagli);

        pnl_creaColonna.add(lbl_creaColonna);
        pnl_creaColonna.add(bds_creaColonnaAlta);
        pnl_creaColonna.add(Box.createVerticalStrut(30));
        pnl_creaColonna.add(sp_creaColonna);
        pnl_creaColonna.add(bds_creaColonnaBassa);
        pnl_creaColonna.add(pnl_creaColonnaBtnFinali);

        checkRadioButtonsTipologia();
    }

    public void creaTabella() {
        //TODO popolare la funzione per la creazione della tabella
    }

    public void reset() {

        ta_nomeColonna.setText("");
        radioButtons[0].setSelected(true);

        //Settiamo non visibili tutti gli elementi del pnl_aggiungiDettagli, incluso il panel stesso

        pnl_aggiungiDettagli.setVisible(false);
        lbl_default.setVisible(false);
        //lbl_check.setVisible(false);
        //lbl_collate.setVisible(false);
        ta_default.setVisible(false);
        //ta_check.setVisible(false);
        //ta_collate.setVisible(false);

        for(int i = 4; i < radioButtons.length; i++) {
            radioButtons[i].setSelected(false);
        }

        aggiungiDettagli();

        checkRadioButtonCaratteristica();
    }

    /**
     * Funzione richiamata solo nel caso in cui tra le caratteristiche scelte fosse necessario
     * aggiungere ulteriori dettagli
     */
    public void aggiungiDettagli() {

        double changeHeight = 0;

        if(radioButtons[8].isSelected()) changeHeight = dim_label.getHeight() + dim_taNomeColonna.getHeight() + 60;
        else changeHeight = -(dim_label.getHeight() + dim_taNomeColonna.getHeight() + 60);
        
        if(radioButtons[9].isSelected());
        if(radioButtons[10].isSelected());


        lbl_default.setVisible(radioButtons[8].isSelected());
        ta_default.setVisible(radioButtons[8].isSelected());
        pnl_aggiungiDettagli.setVisible(radioButtons[8].isSelected());


        dim_aggiungiDettagli.setSize(dim_aggiungiDettagli.getWidth(), changeHeight);
        dim_sottopannelli.setSize(dim_sottopannelli.getWidth(), dim_sottopannelli.getHeight() + changeHeight);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RadioButton rb = (RadioButton)e.getSource();
        
        checkRadioButtonCaratteristica();

        if(rb == radioButtons[8] || rb == radioButtons[9] || rb == radioButtons[10]) aggiungiDettagli();

        btn_resetColonna.ableButton();
    }

    private void checkRadioButtonsTipologia() {
        if(radioButtons[0].isSelected()) {
            
            radioButtons[8].abilita();
            radioButtons[7].abilita();
            
            radioButtons[10].disabilita();

        } else if(radioButtons[1].isSelected()) {

            radioButtons[8].abilita();
            
            radioButtons[7].disabilita();
            radioButtons[10].disabilita();

        } else if(radioButtons[2].isSelected()) {
            
            radioButtons[8].abilita();
            radioButtons[10].abilita();

            
            radioButtons[7].disabilita();
        } else {
            
            radioButtons[7].disabilita();
            radioButtons[8].disabilita();
            radioButtons[10].disabilita();
        }

        aggiornaRadioButtons();
    }

    private void checkRadioButtonCaratteristica() {
        
        if(radioButtons[7].isSelected()) {

            radioButtons[4].setSelected(true);

        } 
        
        if(radioButtons[4].isSelected()) {

            radioButtons[5].setSelected(true);
            radioButtons[6].setSelected(true);

        }

        checkRadioButtonsTipologia();
    }

    private void aggiornaRadioButtons() {
        
        for(int i = 0; i < radioButtons.length; i++) {

            if(radioButtons[i].isSelected()) radioButtons[i].setIcon(Global.getIcon("RadioButtonSelected.png", 10));
            else radioButtons[i].setIcon(Global.getIcon("RadioButtonNotSelected.png", 10));

            if(!radioButtons[i].isEnabled()) radioButtons[i].setIcon(Global.getIcon("Annulla.png", 10));
        }
    }
}
