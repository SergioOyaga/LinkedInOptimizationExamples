package org.soyaga.examples.Zip.ACOZip.UI.Panels;

import lombok.Setter;
import org.soyaga.examples.Zip.ACOZip.UI.Buttons.SquareButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * JPanel with the stats from the optimization.
 */
public class StatsPanel extends JPanel {
    /**
     * BufferedImage that with the default image.
     */
    private BufferedImage defaultImage;
    /**
     * SquareButton[][] with the grid of buttons sorted as [row][col]
     */
    @Setter
    private SquareButton[][] gridButtons;
    /**
     * HashMap&lt;SquareButton, HashMap&lt;SquareButton, Double&gt;&gt; with the current pheromone to print in the image.
     */
    private HashMap<SquareButton, HashMap<SquareButton, Double>> pheromoneByButton;
    /**
     * Integer with the iteration number.
     */
    private Integer iteration;
    /**
     * Integer with the iteration number of the solution.
     */
    private Integer solutionIteration;
    /**
     * Double with the solution time.
     */
    private Double solutionTime;
    /**
     * Integer with the number of rows
     */
    @Setter
    private Integer numberOfRows;
    /**
     * Integer with the number of columns
     */
    @Setter
    private Integer numberOfCols;
    /**
     * Integer with the X length of each button
     */
    private int xLength;
    /**
     * Integer with the Y length of each button
     */
    private int yLength;
    /**
     * Integer with the arch each button
     */
    private int arch;

    /**
     * Constructor
     */
    public StatsPanel() {
        super();
        try {
            this.defaultImage = ImageIO.read(Objects.requireNonNull(org.soyaga.examples.Queens.GeneticQueens.UI.Panels.StatsPanel.class.getResource("/DefaultImages/Default_ACO_Image.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.setPreferredSize(new Dimension(200,200));
    }

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {
        //initialize components

        //Set layout and add components
        this.setLayout(new BorderLayout());

        //JPanel properties
    }

    /**
     * Function that adds a new iteration to the pheromone image.
     * @param iteration Integer with the iteration.
     * @param pheromoneByButton hashmap with the pheromone by node
     */
    public void addIteration(Integer iteration, HashMap<SquareButton, HashMap<SquareButton, Double>> pheromoneByButton){
        this.pheromoneByButton = pheromoneByButton;
        this.iteration = iteration;
        this.repaint();
    }

    /**
     * Function call in the graphical context to display the component.
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        if(this.iteration!=null) {
            this.xLength = this.getWidth()/(this.numberOfCols+1);
            this.yLength = this.getHeight()/(this.numberOfRows+1);
            this.arch =Math.min(this.xLength, this.yLength)/4;
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            if(this.pheromoneByButton !=null){
                this.drawPheromones(g2);
            }
            if(this.gridButtons != null){
                this.drawButtons(g2);
            }
            String text = "";
            if(this.solutionIteration !=null) {
                text += "Solution iteration: "+ this.solutionIteration +" ; ";
            }
            if(this.solutionTime!=null) {
                text += "Solution time: "+ this.solutionTime +" s; ";
            }
            if(this.iteration!=null) {
                text += "Iteration: "+ this.iteration;
            }
            if(!text.isEmpty()){
                g2.setColor(Color.RED);
                Font bigFont = g2.getFont().deriveFont((float) (Math.min(this.getWidth(), this.getHeight()) / 20.));
                g2.setFont(bigFont);
                FontMetrics metrics = g2.getFontMetrics(bigFont);
                int textHeight = metrics.getAscent() / 2;
                int textWidth = metrics.stringWidth(text);
                g2.drawString(text, this.getWidth() - textWidth - textHeight, textHeight * 2);
            }
        }
        else{
            g2.drawImage(this.defaultImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH), 0, 0, this); // Draw the image
        }
    }

    /**
     * Function that draws the buttons.
     * @param g2 Graphics2D with the graphical context.
     */
    private void drawButtons(Graphics2D g2) {
        for(SquareButton[] buttonsRow:this.gridButtons){
            for(SquareButton button:buttonsRow){
                // Draw circle
                g2.setColor(Color.GREEN);
                g2.drawRoundRect((int) ((button.getCol()+0.5) * this.xLength), (int) ((button.getRow()+0.5) * this.yLength), this.xLength, this.yLength, this.arch, this.arch);
            }
        }
    }

    /**
     * Function that draws the pheromones.
     * @param g2 Graphics2D with the graphical context.
     */
    private void drawPheromones(Graphics2D g2) {
        Stroke currentStroke = g2.getStroke();
        for(HashMap.Entry<SquareButton,HashMap<SquareButton,Double>> buttonOrigEntry:this.pheromoneByButton.entrySet()){
            SquareButton origButton = buttonOrigEntry.getKey();
            for(HashMap.Entry<SquareButton,Double> buttonDestEntry:buttonOrigEntry.getValue().entrySet()){
                SquareButton destButton = buttonDestEntry.getKey();
                double value = Math.tanh(buttonDestEntry.getValue())/2;
                // Draw line
                g2.setStroke( new BasicStroke((float) (Math.min(this.xLength, this.yLength)*value), BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_MITER, 1.0f));
                g2.setColor( new Color(255,255,255,(int) (255*value)));
                g2.drawLine(
                        (origButton.getCol()+1) * this.xLength, (origButton.getRow()+1) * this.yLength,
                        (destButton.getCol()+1) * this.xLength, (destButton.getRow()+1) * this.yLength
                );
            }
        }
        g2.setStroke(currentStroke);
    }

    /**
     * Function that sets the solution found time and iteration
     * @param iteration double with the iteration
     * @param time double with the time
     */
    public void setSolutionFound(int iteration, double time) {
        time = ((int)(time*100.))/100.;
        this.solutionIteration = iteration;
        this.solutionTime = time;
    }
}