/* Name: Md. Zubayer Ahmed
 * ID: 202160438
 * Assigned CA: Sim453 (140*140, Random, By Flop & Crab)
 * Comp-3201, Winter-2024
 * E-mail: mzahmed@mun.ca
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;


public class Sim453 extends JFrame {
    private static final int grid_size = 140; // The Grid size will be 140 * 140 (square shape)
    private int magnification = 5; 
    private int[][] grid;
    private JPanel Panel;

    public Sim453(int iterations, String type) {
    	   if (iterations < 0) {
               throw new IllegalArgumentException("Number of Iterations must be >=0"); // Checking whether number of iteration is a non-negative number
           }
        grid = new int[grid_size][grid_size]; //initializing empty 140 * 140 cells
        Panel = new GridPanel();

       // Specifying type variable for Random, By flop and Crab pattern
        if (type.equals("R")) {
            randomized();
        } else if (type.equals("B")) {
            By_Flop();
        } else if (type.equals("C")) {
            Crab();
        }

        // Magnifying the cells by 5
        setSize(grid_size * magnification, grid_size * magnification);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        add(Panel);

        for (int i = 0; i < iterations; i++) {
            update();
            Panel.repaint(); // After updating the cells we need to repaint aka draw the updated patterns
            delay();   // calling delay() to make a  delay for visual representation
        }
    }
    // For Random initial pattern we call randomized()
    public void randomized() {
        for(int i = 0; i < this.grid_size; ++i) {
           for(int j = 0; j < this.grid_size; ++j) {
              this.grid[i][j] = Math.random() <=0.5D ? 1 : 0; //We will consider 0 as dead and 1 as alive, with initial 50% possibility
           }
        }

     }

    private void By_Flop() {
        
        // Initial Pattern for By Flop pattern. Cells counted from left to right as j and top to bottom as i. Only recording the "Alive" cells
        grid[1][4] = 1;
        grid[2][2] = 1;
        grid[2][4] = 1;
        grid[3][6] = 1;
        grid[4][1] = 1;
        grid[4][2] = 1;
        grid[4][3] = 1;
        grid[4][4] = 1;
        grid[4][5] = 1;
        grid[5][6] = 1;
        grid[6][2] = 1;
        grid[6][4] = 1;
        grid[7][4] = 1;      
      
    }

    private void Crab() {
        // For the Crab Pattern we will again initialize by implementing the given Alive cells as 1's
        grid[1][9] = 1;
        grid[1][10] = 1;
        grid[2][8] = 1;
        grid[2][9] = 1;
        grid[3][10] = 1;
        grid[4][12] = 1;
        grid[4][13] = 1;
        grid[5][11] = 1;
        grid[7][10] = 1;
        grid[7][13] = 1;
        grid[8][2] = 1;
        grid[8][3] = 1;
        grid[8][9] = 1;
        grid[8][10] = 1;
        grid[9][1] = 1;
        grid[9][2] = 1;
        grid[9][8] = 1;
        grid[10][3] = 1;
        grid[10][8] = 1;
        grid[10][10] = 1;
        grid[11][5] = 1;
        grid[11][6] = 1;
        grid[11][9] = 1;
        grid[12][5] = 1;
        grid[12][6] = 1;

      
    }
    // UpdateCells will update the cell's next stage. If there is two or three neighbors then the cell survive, otherwise die, and if there is exactly three alive neighbors then the state becomes Alive
    private void update() {
        int[][] newGrid = new int[grid_size][grid_size];

        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                int count = countNeighbors(i, j);
                if (grid[i][j] == 1) {
                    newGrid[i][j] = (count == 2 || count == 3) ? 1 : 0;
                } else {
                    newGrid[i][j] = (count == 3) ? 1 : 0;
                }
            }
        }

        grid = newGrid;
    }

    private int countNeighbors(int x, int y) { // Counting the neighbors for the udate() method
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int new_x = (x + i + grid_size) % grid_size;
                int new_y = (y + j + grid_size) % grid_size;

                if (!(i == 0 && j == 0) && grid[new_x][new_y] == 1) {
                    count++;
                }
            }
        }

        return count;
    }

    // Each iterations will be delayed by 80 milliseconds
    private void delay() {
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Setting up a GridPanel for showing B-W grids overrides JPanel
     private class GridPanel extends JPanel { 
        protected void paintComponent(Graphics cell) { 
            super.paintComponent(cell);

            //painting each cells black/white as Alive/Dead
            for (int i = 0; i < grid_size; i++) {
                for (int j = 0; j < grid_size; j++) {
                    if (grid[i][j] == 1) {
                        cell.setColor(java.awt.Color.BLACK);
                        cell.fillRect(j * magnification, i * magnification, magnification, magnification);// Since each cells are magnified by 5 including the cell we are coloring now
                    } else {
                        cell.setColor(java.awt.Color.WHITE);
                        cell.fillRect(j * magnification, i * magnification, magnification, magnification);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Sim453iterations <type>");
            System.exit(1);
        }

        int iterations = Integer.parseInt(args[0]);
        System.out.println(iterations);
        String type = args[1];

        new Sim453(iterations, type);
    }
}