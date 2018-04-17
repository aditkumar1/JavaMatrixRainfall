import java.awt.*;
import java.util.*;
import javax.swing.*;
public class Main extends JFrame {

    private static final int FONT_SIZE = 15;
    private static final String TEXT = new String("あたアカサザジズゼゾシスセソキクケコイウエオジャな0123456789");
    private static Random random = new Random();
    private static JPanel[] rows;

    public void initializeComponent(){
        int totalNoOfRows= (this.getHeight()/FONT_SIZE)-1;
        rows= new JPanel[totalNoOfRows];
        for(int i=0;i<totalNoOfRows;i++){
            rows[i]=initializeRow();
        }
        addRowsToFrame();
    }
    public JPanel initializeRow(){
        JPanel row =new JPanel();
        row.setBackground(Color.BLACK);
        int totalNoOfCharacters= (this.getWidth() / FONT_SIZE)-1;
        row.setLayout(new GridLayout(0,totalNoOfCharacters));
        JLabel[] characters= new JLabel[totalNoOfCharacters];
        for(JLabel character: characters){
            int character_initial = random.nextInt(TEXT.length());
            character = new JLabel("" + TEXT.charAt(character_initial));
            character.setFont(new Font("monospaced", Font.PLAIN, FONT_SIZE));
            character.setForeground(new Color(0, 80, 0));
            row.add(character);
        }
        return row;
    }
    public void updateFrame(){
        for(int i=rows.length-1;i>0;i--){
            rows[i]=rows[i-1];
        }
        rows[0]=initializeRow();
        this.getContentPane().removeAll();
        this.addRowsToFrame();
    }
    public void addRowsToFrame(){
        for(JPanel row: rows){
            this.add(row);
        }
    }
    public static void main(String[] args) {
        Main frame = new Main();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
                frame.setResizable(false);
                frame.setMinimumSize(new Dimension(300, 200));
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setBackground(Color.black);
                frame.setLayout(new FlowLayout());
                frame.initializeComponent();
                frame.setVisible(true);
                Thread updateFrame = new Thread() {
                    public void run() {
                        try {
                            while (true) {
                                frame.updateFrame();
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