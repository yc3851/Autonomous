import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class World extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final int ROWS = 6;
    
    private static final int COLS = 6;
    
    private Item[][] map;
    private JButton[][] buttons;
    
    public World() {
        map = new Item[ROWS][COLS];
        buttons = new JButton[ROWS][COLS];
    }
    
    /**
     * build the world
     */
    private void buildWorld() {
        Random ran = new Random(System.currentTimeMillis());
        int count = 0;
        while(count < 5) {
            int x = ran.nextInt(ROWS);
            int y = ran.nextInt(COLS);
            Immovable item = new Immovable(x, y, 'I', "immovable");
            if(add(item, x, y)) {
                count++;
            }
        }
        
        count = 0;
        while(count < 3) {
            int x = ran.nextInt(ROWS);
            int y = ran.nextInt(COLS);
            Moveable item = new Moveable(x, y, 'M', "movable");
            if(add(item, x, y)) {
                count++;
            }
        }
        
        count = 0;
        while(count < 2) {
            int x = ran.nextInt(ROWS);
            int y = ran.nextInt(COLS);
            Autonomous item = new Autonomous(x, y, 'A', "autonomous", ran);
            if(add(item, x, y)) {
                count++;
            }
        }
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(ROWS, COLS, 2, 2));
        panel.setBackground(Color.white);
        
        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                String token = "";
                if(map[x][y] != null) {
                    token = map[x][y].getToken() + "";
                }
                buttons[x][y] = new JButton(token);
                buttons[x][y].setName(x + "_" + y);
                buttons[x][y].setBackground(Color.white);
                buttons[x][y].setOpaque(true);
                buttons[x][y].setPreferredSize(new Dimension(50, 15));
                panel.add(buttons[x][y]);
            }
        }
        
        this.setLayout(new BorderLayout());
        this.add(panel);
        this.setSize(600, 600);
    }
    
    /**
     * display the world
     */
    public void display() {
        
        this.setVisible(true);
        
    }
    
    /**
     * update the world
     */
    private void updateWorld() {
        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                if(map[x][y] != null) {
                    buttons[x][y].setText(map[x][y].getToken() + "");
                } else {
                    buttons[x][y].setText("");
                }
            }
        }
        this.repaint();
    }
    
    /**
     * add a item to the map
     * @param item
     * @param x
     * @param y
     * @return
     */
    public boolean add(Item item, int x, int y) {
        if(x < 0 || y < 0 || x >= map.length || y >= map[0].length) {
            return false;
        }
        
        if(map[x][y] != null) {
            return false;
        }
        
        map[x][y] = item;
        return true;
    }
    
    /**
     * whether run the next simulation
     * @return true or false
     */
    public boolean hasNext() {
        Object[] options = { "yes", "no"};
        int choosed = JOptionPane.showOptionDialog(null, "Would you like to run the simulation again", "Info", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if(choosed == 0) {
            return true;
        } else {
            return false;  
        }      
    }
    
    /**
     * change the state of the world by call the step method of Autonomous items
     */
    public void step() {
        List<Autonomous> list = new ArrayList<Autonomous>();
        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                if(map[x][y] instanceof Autonomous) {
                    list.add((Autonomous)map[x][y]);
                }
            }
        }
        for(Autonomous auto : list) {
            auto.step(map);
        }
    }
    
    /**
     * main method
     */
    public static void main(String[] args) {
        World world = new World();
        boolean next = true;
        world.buildWorld();
        world.display();
        while(next) {
            for(int i=0; i<100; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                world.step();
                world.updateWorld();
            }
            next = world.hasNext();
        }
        System.exit(0);
    }
}
