import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main extends JPanel {
    private static final int FONT_SIZE = 10;
    private static final String TEXT = new String(" あたアカサザジズゼゾシスセソキクケコイウエオジャな0123456789");
    private static Random random = new Random();
    private static JPanel[] rows;
    private static Color[] columnColors;

    public void initializeComponent(){
        this.setBackground(Color.black);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        int totalNoOfRows= (this.getHeight()/FONT_SIZE)-1;
        rows= new JPanel[totalNoOfRows];
        for(int i=0;i<totalNoOfRows;i++){
            rows[i]=initializeRow();
        }
        addRowsToPanel();
    }
    public JPanel initializeRow(){
        JPanel row =new JPanel();
        row.setBackground(Color.BLACK);
        int totalNoOfCharacters= (this.getWidth() / FONT_SIZE)-1;
        row.setLayout(new GridLayout(0,totalNoOfCharacters));
        JLabel[] characters= new JLabel[totalNoOfCharacters];
        if(columnColors==null)columnColors=new Color[totalNoOfCharacters];
        int i=-1;
        for(JLabel character: characters){
            i=i+1;
            int character_initial = random.nextInt(TEXT.length());
            character = new JLabel("" + TEXT.charAt(character_initial));
            character.setFont(new Font("monospaced", Font.PLAIN, FONT_SIZE));
            if(columnColors[i]==null)columnColors[i]=new Color(0, random.nextInt(255), 0);
            character.setForeground(columnColors[i]);
            columnColors[i]=new Color(0,(columnColors[i].getGreen()+3)%255,0);
            row.add(character);
        }
        return row;
    }
    public void updatePanel(){
        for(int i=rows.length-1;i>0;i--){
            rows[i]=rows[i-1];
        }
        rows[0]=initializeRow();
        this.removeAll();
        this.addRowsToPanel();
    }
    public void addRowsToPanel(){
        for(JPanel row: rows){
            this.add(row);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Main panel=new Main();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
                frame.setResizable(false);
                frame.setMinimumSize(new Dimension(300, 200));
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setBackground(Color.black);
                frame.getContentPane().add(panel);
                panel.setLayout(new FlowLayout());
                panel.initializeComponent();
                frame.setVisible(true);
                Thread updateFrame = new Thread() {
                    public void run() {
                        try {
                            while (true) {
                                panel.updatePanel();
                                SwingUtilities.updateComponentTreeUI(frame);
                                Thread.sleep(50);
                            }
                        } catch(Exception v) {
                            System.out.println(v);
                        }
                    }
                };
                updateFrame.start();
            }
        });
    }
}