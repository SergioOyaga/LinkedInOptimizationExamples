package org.soyaga.examples.Zip.MathModelZip.UI.Panels;

import lombok.Getter;
import lombok.Setter;
import org.soyaga.examples.Zip.MathModelZip.UI.Buttons.SquareButton;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Math.min;
import static java.lang.Math.random;

/**
 * Panel where to set the problem, and the solution for/from the optimization.
 */
@Getter
public class OptimizationPanel extends JPanel {
    /**
     * Array composed of Arrays of SquareButton with the buttons
     */
    private SquareButton[][] gridButtons;
    /**
     * Array composed SquareButton with the buttons
     */
    private final ArrayList<SquareButton> solutionArray;
    /**
     * Boolean that controls the draw of the solution.
     */
    @Setter
    private boolean isSolved;
    /**
     * Color to start the draw
     */
    private Color startColor;
    /**
     * Color to end the draw
     */
    private Color endColor;
    /**
     * Int with the red distance
     */
    private int redDistance;
    /**
     * Int with the green distance
     */
    private int greenDistance;
    /**
     * Int with the blue distance
     */
    private int blueDistance;
    /**
     * Integer with the number of segments to draw
     */
    private int segments;


    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public OptimizationPanel() {
        super();
        this.solutionArray = new ArrayList<>();
        this.isSolved = false;
    }

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {

        TitledBorder title = BorderFactory.createTitledBorder(
                new EmptyBorder(20, 20, 20, 20), "Optimization");
        title.setTitlePosition(TitledBorder.TOP);
        Border lineBorder =new LineBorder(Color.BLACK, 7, true);
        this.setBorder(new CompoundBorder(title, lineBorder));
    }

    /**
     * Function that sets the grid of buttons
     * @param colSize int with the col size of the grid
     * @param rowSize int with the row size of the grid
     */
    public void setGridButtons(int colSize, int rowSize){
        this.setLayout(new GridLayout(colSize, rowSize,0,0));
        this.gridButtons = new SquareButton[colSize][rowSize];
        int buttonWidth = this.getWidth()/colSize;
        int buttonHeight = this.getHeight()/rowSize;
        for (int col = 0; col < colSize; col++) {
            for (int row = 0; row < rowSize; row++) {
                SquareButton cell = new SquareButton(new Dimension(buttonWidth, buttonHeight), col, row, this.gridButtons);
                this.gridButtons[col][row] = cell;
                this.solutionArray.add(cell);
                this.add(cell);
            }
        }
        this.segments = colSize * rowSize -1;
    }

    /**
     * Function that sets a solution to the current grid.
     * @param solution ArrayList with the solution.
     */
    public void setSolution(Integer[][] solution){
        this.startColor = new Color((int) (random()*255), (int) (random()*255), (int) (random()*255));
        this.endColor = new Color((int) (random()*255), (int) (random()*255), (int) (random()*255));
        this.redDistance = (this.endColor.getRed() - this.startColor.getRed());
        this.greenDistance = (this.endColor.getGreen() - this.startColor.getGreen());
        this.blueDistance = (this.endColor.getBlue() - this.startColor.getBlue());
        for(SquareButton[] buttonsRow:this.gridButtons){
            for(SquareButton button: buttonsRow){
                button.setOrder(solution[button.getRow()][button.getCol()]);
            }
        }
        this.isSolved = true;
    }

    /**
     * Calls the UI delegate's paint method if the UI delegate
     * is non-<code>null</code>.  We pass the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes
     * (for example, <code>Graphics.translate</code>).
     * <p>
     * If you override this in a subclass, you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoke super's implementation, you must honor the opaque property, that is
     * if this component is opaque, you must fill in the background
     * in an opaque color. If you do not honor the opaque property, you
     * will likely see visual artifacts.
     * <p>
     * The passed in <code>Graphics</code> object might
     * have a transform other than the identification transform
     * installed on it.  In this case, you might get
     * unexpected results if you cumulatively apply
     * another transform.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     * @see ComponentUI
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.WHITE);
        g2.fillRect(27,27,getWidth()-54,getHeight()-54);
        this.drawSolution(g2);
        g2.dispose();
    }

    /**
     * Interpolates the segment start and end colors.
     * @param t segment percentage
     * @return Color of the percentage
     */
    private Color interpolateColor( float t) {
        int r = (int) (this.startColor.getRed() + t * this.redDistance);
        int g = (int) (this.startColor.getGreen() + t * this.greenDistance);
        int b = (int) (this.startColor.getBlue() + t * this.blueDistance);
        return new Color(r, g, b);
    }

    /**
     * Draws the solution if needed;
     * @param g2 Graphics2D used to draw the solution
     */
    private void drawSolution(Graphics2D g2) {
        if (this.isSolved) {
            this.solutionArray.sort(Comparator.comparing(SquareButton::getOrder));
            for (int i = 0; i < this.segments; i++) {
                float startPercentage = (float) i / this.segments;
                Color startPercentageColor = interpolateColor(startPercentage);
                float endPercentage = (float) (i + 1) / this.segments;
                Color endPercentageColor = interpolateColor(endPercentage);

                float x1 = this.solutionArray.get(i).getX() + ((float) this.solutionArray.get(i).getWidth() / 2);
                float y1 = this.solutionArray.get(i).getY() + (float) this.solutionArray.get(i).getHeight()/2;
                float x2 = this.solutionArray.get(i + 1).getX() + (float) this.solutionArray.get(i+1).getWidth()/2;
                float y2 = this.solutionArray.get(i + 1).getY() + (float) this.solutionArray.get(i+1).getHeight()/2;;

                GradientPaint gradient = new GradientPaint(x1, y1, startPercentageColor, x2, y2, endPercentageColor);
                g2.setPaint(gradient);

                int size = min(this.solutionArray.get(0).getSize().height, this.solutionArray.get(0).getSize().width) * 3 / 4;
                g2.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.draw(new Line2D.Float(x1, y1, x2, y2));
            }
        }
    }
}