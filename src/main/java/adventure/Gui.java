package adventure;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JTextArea;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

public class Gui{
private Game theGame;
private Player p;
private JTextArea textArea;
private JScrollPane scrollPane;
private JLabel name;
private JTextField mainTextField;
private static final int FIVE = 5;
private static final int ONE_THOUSAND = 1000;
private static final int FIVE_HUNDRED = 500;
private static final int ONE_HUNDRED = 100;
/**
* Launch the application
* @param args the arguments
*/
public static void main(String[] args){
    EventQueue.invokeLater(new Runnable(){
        public void run(){
            try{
                Gui window = new Gui();
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Gui() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     * @return the frame
     */
    private JFrame initializeFrame(){
        JFrame frame = new JFrame();
        frame.setBounds(ONE_HUNDRED, ONE_HUNDRED, ONE_THOUSAND, FIVE_HUNDRED);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeFrameLayout(frame);
        return frame;
    }
    /**
     * Initialize the layout of the frame.
     * @param frame the frame
     */
    private void initializeFrameLayout(JFrame frame){
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[]{0, 0, 0};
        gbl.rowHeights = new int[]{0, 0, 0};
        gbl.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gbl);
    }
    /**
     * Initialize Panel1
     * @param frame the frame
     * @return the panel
     */
    private JPanel initializePanel1(JFrame frame){
        JPanel panel1 = new JPanel();
        initializePanel1Constraints(frame,panel1);
        initializePanel1Layout(panel1);
        initializeName(panel1);
        initializeScrollPane(panel1);
        return panel1;
    }
    /**
     * Initialize Panel1 constraints
     * @param frame the frame
     * @param panel1 the panel
     */
    private void initializePanel1Constraints(JFrame frame,JPanel panel1){
        GridBagConstraints gbcPanel1 = new GridBagConstraints();
        gbcPanel1.anchor = GridBagConstraints.WEST;
        gbcPanel1.insets = new Insets(0, 0, FIVE, FIVE);
        gbcPanel1.fill = GridBagConstraints.VERTICAL;
        gbcPanel1.gridx = 0;
        gbcPanel1.gridy = 0;
        frame.getContentPane().add(panel1, gbcPanel1);
    }
    /**
     * Initialize Panel1 layout
     * @param panel1 the panel
     */
    private void initializePanel1Layout(JPanel panel1){
        GridBagLayout gblPanel1 = new GridBagLayout();
        gblPanel1.columnWidths = new int[]{0, 0};
        gblPanel1.rowHeights = new int[]{0, 0, 0};
        gblPanel1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gblPanel1.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        panel1.setLayout(gblPanel1);
    }
    /**
     * Initialize the name label
     * @param panel1 the panel
     */
    private void initializeName(JPanel panel1){
        this.name = new JLabel(" Name: "+this.p.getName());
        GridBagConstraints gbcName = new GridBagConstraints();
        gbcName.anchor = GridBagConstraints.NORTHWEST;
        gbcName.insets = new Insets(0, 0, FIVE, 0);
        gbcName.gridx = 0;
        gbcName.gridy = 0;
        panel1.add(this.name,gbcName);
    }
    /**
     * Initialize the scroll pane
     * @param panel1 the panel
     */
    private void initializeScrollPane(JPanel panel1){
        this.scrollPane = new JScrollPane();
        GridBagConstraints gbcScrollPane = new GridBagConstraints();
        gbcScrollPane.fill = GridBagConstraints.BOTH;
        gbcScrollPane.gridx = 0;
        gbcScrollPane.gridy = 1;
        panel1.add(this.scrollPane, gbcScrollPane);
        initializeScrollPaneInven(this.scrollPane);
    }
    /**
     * Initialize the scroll pane for the inventory label
     * @param scroll the scroll pane
     */
    private void initializeScrollPaneInven(JScrollPane scroll){
        JLabel inven = new JLabel("Inventory");
        GridBagConstraints gbcInven = new GridBagConstraints();
        gbcInven.fill = GridBagConstraints.HORIZONTAL;
        gbcInven.gridx = 0;
        gbcInven.gridy = 0;
        scroll.setColumnHeaderView(inven);
    }
    /**
     * Initialize the scroll pane for the inventory text
     * @param scroll the scroll pane
     */
    private void initializeScrollPaneText(JScrollPane scroll){
        JTextArea invenText = new JTextArea();
        invenText.setEditable(false);
        invenText.setText(p.getInventoryText(p.getInventory()));
        scroll.setViewportView(invenText);
    }
    /**
     * Initialize panel constraints
     * @param frame the frame
     * @param panel the panel
     */
    private void initializePanelConstraints(JFrame frame,JPanel panel){
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.insets = new Insets(0, 0, FIVE, 0);
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.gridx = 1;
        gbcPanel.gridy = 0;
        frame.getContentPane().add(panel, gbcPanel);
    }
    /**
     * Initialize panel 
     * @param frame the frame
     * @return the panel
     */
    private JPanel initializePanel(JFrame frame){
        JPanel panel = new JPanel();
        initializePanelConstraints(frame,panel);
        initializePanelLayout(panel);
        initializePanelText(panel);
        return panel;
    }
    /**
     * Initialize panel layout
     * @param panel the panel
     */
    private void initializePanelLayout(JPanel panel){
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[]{0, 0};
        gblPanel.rowHeights = new int[]{0, 0};
        gblPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        panel.setLayout(gblPanel);
    }
    /**
     * Initialize panel text
     * @param panel the panel
     */
    private void initializePanelText(JPanel panel){
        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.textArea.setText(this.theGame.getIntro(this.theGame));
        GridBagConstraints gbcTextArea = new GridBagConstraints();
        gbcTextArea.fill = GridBagConstraints.BOTH;
        gbcTextArea.gridx = 0;
        gbcTextArea.gridy = 0;
        panel.add(this.textArea, gbcTextArea);
    }
    /**
     * Initialize the command label
     * @param frame the frame
     */
    private void initializeCommand(JFrame frame){
        JLabel command = new JLabel("Command");
        GridBagConstraints gbcCommand = new GridBagConstraints();
        gbcCommand.insets = new Insets(0, 0, 0, FIVE);
        gbcCommand.gridx = 0;
        gbcCommand.gridy = 1;
        frame.getContentPane().add(command, gbcCommand);
    }
    /**
     * Initialize the command text
     * @param frame the frame
     */
    private void initializeCommandText(JFrame frame){
        this.mainTextField = new JTextField();
        Action action5 = new ShowText(this.theGame,this.p,this.mainTextField,this.textArea,this.scrollPane);
        this.mainTextField.setAction(action5);
        GridBagConstraints gbcMainTextField = new GridBagConstraints();
        gbcMainTextField.fill = GridBagConstraints.HORIZONTAL;
        gbcMainTextField.gridx = 1;
        gbcMainTextField.gridy = 1;
        frame.getContentPane().add(this.mainTextField, gbcMainTextField);
        this.mainTextField.setColumns(FIVE+FIVE);
    }
    /**
     * Initialize the menu
     * @param frame the frame
     */
    private void initializeMenu(JFrame frame){
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        initializeMenuBar(menuBar);
    }
    /**
     * Initialize the menu bar
     * @param menuBar the menu
     */
    private void initializeMenuBar(JMenuBar menuBar){
        JMenu open = new JMenu("OPEN");
        menuBar.add(open);
        initializeMenuOpen(open);
        JMenu save =new JMenu("SAVE");
        menuBar.add(save);
        initializeMenuSave(save);
        JMenu player = new JMenu("PLAYER");
        menuBar.add(player);
        initializeMenuPlayer(player);
    }
    /**
     * Initialize the menu open
     * @param open the jmenu
     */
    private void initializeMenuOpen(JMenu open){
        JMenuItem loadSave = new JMenuItem("Load save");
        Action action3 = new LoadSave(this.theGame,this.p,textArea,scrollPane);
        loadSave.setAction(action3);
        open.add(loadSave);
        JMenuItem loadJson = new JMenuItem("Load Json");
        Action action4 = new LoadJson(this.theGame,this.p,textArea);
        loadJson.setAction(action4);
        open.add(loadJson);
    }
    /**
     * Initialize the menu save
     * @param save the jmenu
     */
    private void initializeMenuSave(JMenu save){
        JMenuItem saveGame = new JMenuItem("Save");
        Action action1 = new Save(this.theGame.getP1(),this.theGame);
        saveGame.setAction(action1);
        save.add(saveGame);
        JMenuItem saveGameAs = new JMenuItem("Save as");
        Action action2 = new SaveAs(this.theGame.getP1(),this.theGame);
        saveGameAs.setAction(action2);
        save.add(saveGameAs);
    }
    /**
     * Initialize the menu player
     * @param player the jmenu
     */
    private void initializeMenuPlayer(JMenu player){
        JMenuItem setName = new JMenuItem("Set name");
        Action action = new SetName(this.p,this.name);
        setName.setAction(action);
        player.add(setName);
    }
    /**
     * Initalizes the gui;
     */
    private void initialize() {
        this.theGame = new Game();
        this.p = new Player("Player");
        this.theGame.setPlayer(this.p);
        JFrame frame = initializeFrame();
        JPanel panel1 = initializePanel1(frame);
        JPanel panel = initializePanel(frame);
        initializeCommand(frame);
        initializeCommandText(frame);
        initializeMenu(frame);
    }
    private class ShowText extends AbstractAction{
        private JTextField f;
        private Game game;
        private Player p;
        private JTextArea a;
        private JScrollPane s;
        /**
         * Constructs action with the needed variables
         * @param pGame the game
         * @param player the player
         * @param field the command field
         * @param area the main text area
         * @param scroll the inventory scrollpane
         */
        ShowText(Game pGame,Player player, JTextField field,JTextArea area,JScrollPane scroll) {
            this.p = player;
            this.f = field;
            this.game = pGame;
            this.a = area;
            this.s = scroll;
        }
        /**
         * This method gets the string for the new commands
         * @param com the command
         * @return The string for the new commands
         */
        private String getCommandTextNew(Command com){
            String str2 ="";
            if(com.getActionWord().equals("wear")){
                str2 = str2 + this.game.getWearText(this.p)+"\n";
            }else if(com.getActionWord().equals("eat")){
                str2 = str2 + this.game.getEatText(this.p)+"\n";
            }else if(com.getActionWord().equals("read")){
                str2 = str2 + this.game.getReadText(this.p)+"\n";
            }else if(com.getActionWord().equals("toss")){
                str2 = str2 + this.game.getTossText(this.p)+"\n";
            }
            return str2;}
            /**
             * This method gets the string for quit/inventory command
             * @param com the command
             * @return the string for quit/inventory
             */
        private String getCommandTextQuitInven(Command com){
            String str2 ="";
            String str ="";
            if(com.getActionWord().equals("inventory")){
                str2 = str2+"Inventory: \n"+this.p.getInventoryText(this.p.getInventory());
            }else if(!com.getActionWord().equals("quit")){
                str = this.game.getText(this.game,com);
            }else{
                System.exit(0);
            }
            return str+str2;
        }
        /**
             * This method gets the string for the look command
             * @param com the command
             * @return the string for look command
             */
        private String getCommandTextLook(Command com){
            String str="";
            if(com.getActionWord().equals("look")&&com.getNoun()==null){
                str = str+this.p.getCurrentRoom().getLongDescription()+"\n";
            }else if(com.getActionWord().equals("look")&&com.getNoun()!=null){
                for(Item item: this.p.getInventory()){
                    if(item.getName().equals(com.getNoun())){
                        str = str + item.getLongDescription()+"\n";
                    }
                }
            }
            return str;}
    /**
     * This method updates the inventory
     */
    private void setInven(){
    JTextArea inven = new JTextArea(this.game.getP1().getInventoryText(this.game.getP1().getInventory()));
        inven.setEditable(false);
        this.s.setViewportView(inven);
        this.s.paintImmediately(this.s.getVisibleRect());
    }
    /**
     * This method executes the action
     * @param field the command text field
     * @param str the main string
     * @param str2 the ending string
     * @param parse the parser
     * @param command the command
     */
        private void runAction(JTextField field,String str,String str2,Parser parse,String command) throws Exception{
                field.setText("");
                Command com = parse.parseUserCommand(command);
                if(!com.getActionWord().equals("look")&&com.getNoun()!=null){
                    com.validNoun(com.getNoun(),this.p.getCurrentRoom(),this.p);
                }
                this.p = this.game.getP1();
                str2 = getCommandTextNew(com);
                str = getCommandTextQuitInven(com)+getCommandTextLook(com);
                this.a.setText(str+str2);
                setInven();
        }
        /**
         * This method sets up the action to be executed
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e){
            String command = f.getText();
            String str = "";
            String str2 ="";
            Parser parse = new Parser();
            try{
                runAction(f,str,str2,parse,command);
            }catch(Exception i){
                JFrame incom = new JFrame();
                JOptionPane.showMessageDialog(incom,"Invalid Command\n");
            }
    }
}
    private class SetName extends AbstractAction {
        private Player player;
        private JLabel label;
        /**
         * Constructs setName with the needed variables
         * @param p1 the player
         * @param nameLabel the name label
         */
        SetName(Player p1,JLabel nameLabel) {
            putValue(NAME, "Set name");
            this.player = p1;
            this.label = nameLabel;
        }
        /**
         * This method sets the players name
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e){
            JFrame f = new JFrame();
            String playerName = JOptionPane.showInputDialog(f,"Enter name: ");
            this.player.setName(playerName);
            this.label.setText("Name: "+player.getName());
            this.label.paintImmediately(this.label.getVisibleRect());
        }
    }
    private class Save extends AbstractAction {
        private Game game;
        private Player p;
        /**
         * Constructor for save
         * @param player the player
         * @param pGame the game
         */
        Save(Player player,Game pGame) {
            putValue(NAME, "Save");
            this.p = pGame.getP1();
            this.game = pGame;
        }
        /**
         * This method saves the game
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e){
        boolean quit = this.game.saveGame(this.game,this.p);
        if(!quit){
        JFrame f = new JFrame();
        JOptionPane.showMessageDialog(f,"Game was not saved properly\n make sure you set your name");
        }
        }
    }
    private class SaveAs extends AbstractAction {
        private Game game;
        private Player p;
        /**
         * Constructor for save
         * @param player the player
         * @param pGame the game
         */
        SaveAs(Player player,Game pGame) {
            putValue(NAME, "Save as");
            this.p = theGame.getP1();
            this.game = pGame;
        }
        /**
         * This method saves the game with a custom name
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e){
        JFrame j = new JFrame();
        String str = JOptionPane.showInputDialog(j,"Enter the name of your save");
        boolean quit = this.game.saveAsGame(this.game,str);
        if(!quit){
        JFrame f = new JFrame();
        JOptionPane.showMessageDialog(f,"Game was not saved properly\n make sure you set your name");
        }
        }
    }
    private class LoadSave extends AbstractAction {
        private Game game;
        private Player p;
        private JTextArea a;
        private JScrollPane s;
        /**
         * Constructor for loadSave
         * @param pGame the game
         * @param player the player
         * @param area the main text area
         * @param scroll the inventory scrollpane
         */
        LoadSave(Game pGame,Player player,JTextArea area,JScrollPane scroll) {
            putValue(NAME, "Load save");
            this.game = pGame;
            this.p = player;
            this.a = area;
            this.s = scroll;
        }
        /**
         * This method loads the save
         * @param fileName the name of the save
         */
        private void runAction(String fileName)throws Exception{
            ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File(fileName)));
            this.game = (Game) oi.readObject();
            a.setText(this.game.getFirstTextSave(this.game));
            this.p = this.game.getP1();
            JTextArea inven = new JTextArea(this.p.getInventoryText(this.p.getInventory()));
            inven.setEditable(false);
            this.s.setViewportView(inven);
            this.s.paintImmediately(this.s.getVisibleRect());
        }
        /**
         * This method sets up the action to be executed
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
        JFrame f = new JFrame();
        String fileName = JOptionPane.showInputDialog(f,"Enter save file Name: ");
        try{
            runAction(fileName);
        }catch(Exception g){
        JFrame j = new JFrame();
        JOptionPane.showMessageDialog(j,"Failed to load save file, make sure you entered the right name");
        g.printStackTrace();
        }
        }
    }
    private class LoadJson extends AbstractAction {
        private Game game;
        private Player p;
        private JTextArea a;
        /**
         * Constructor of LoadJson
         * @param pGame the game
         * @param player the player
         * @param area the main text area
         */
        LoadJson(Game pGame,Player player,JTextArea area){
            putValue(NAME, "Load JSON");
            this.game = pGame;
            this.p = player;
            this.a = area;
        }
        /**
         * This method loads the jsonfile
         * @param f the frame to get the text from
         */
        private void loadJson(JFrame f){
            String jsonName = JOptionPane.showInputDialog(f,"Enter JSON file Name: ");
            this.game.setAdventure(this.game,jsonName);
            this.a.setText(this.game.getFirstText(this.game,this.p));
            this.p = this.game.getP1();
        }
        /**
         * This method finds the errors in the json
         */
        private void checkJson(){
        JFrame j = new JFrame();
        try{
            this.game.checkJsonErrors();
        }catch(InvalidJsonException z){
            JOptionPane.showMessageDialog(j,"InvlaidJsonException occured, see terminal for issue");
        }
        }
        /**
         * This method performes the action
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e){
        JFrame f = new JFrame();
        try{
            loadJson(f);
        }catch(Exception h){
        checkJson();
        JFrame j = new JFrame();
        JOptionPane.showMessageDialog(j,"Failed to load save file, make sure you entered the right name");
        }
        }
    }
}
