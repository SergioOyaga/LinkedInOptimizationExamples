package org.soyaga.examples.Zip.GeneticZip.UI.Buttons;


import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Math.*;

/**
 * Class that is a Squared button used to create the color selection palette.
 */
@Getter
public class SquareButton extends JButton {
    /**
     * Integer associated with the row and column in the gid. (0 ->N)
     */
    private final int row, col;
    /**
     * List composed of lists of SquareButtons.
     */
    private final SquareButton[][] gridButtons;
    /**
     * Integer that contains the order number of the button
     */
    @Setter
    private Integer order;
    /**
     * Integer with the icon to draw.
     */
    @Setter
    private Integer iconNumber;
    /**
     * Boolean with the type north frontier.
     */
    private Boolean northFrontier;
    /**
     * Boolean with the type west frontier.
     */
    private Boolean westFrontier;
    /**
     * Boolean with the type of east frontier.
     */
    private Boolean eastFrontier;
    /**
     * Boolean with the type of south frontier.
     */
    private Boolean southFrontier;
    /**
     * Integer with the click detection thickness.
     */
    private int thick = 10;

    /**
     * Constructor
     * @param dim Dimension of the button. Computed by the grid
     * @param row Integer with the grid row position
     * @param col Integer with the grid column position
     * @param gridButtons List composed of lists of SquareButtons.
     */
    public SquareButton(Dimension dim, int row, int col, SquareButton[][] gridButtons) {
        super();
        this.row = row;
        this.col = col;
        this.gridButtons = gridButtons;
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setPreferredSize(dim);
        this.setMargin(new Insets(0,0,0,0));
        this.northFrontier = false;
        this.westFrontier = false;
        this.eastFrontier = false;
        this.southFrontier = false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickedOn(e.getX(), e.getY(),SwingUtilities.isLeftMouseButton(e));
            }
        });
    }

    /**
     * Function that manages the behavior of the click event.
     *
     * @param x               integer relative to the component
     * @param y               integer relative to the component
     * @param leftMouseButton boolean whether it is a left click or not
     */
    private void clickedOn(int x, int y, boolean leftMouseButton) {
        switch (isClickNearTo(x, y)) {
            case "Center" -> {
                if(this.iconNumber==null) this.iconNumber=0;
                if(leftMouseButton) this.iconNumber++;
                else this.iconNumber--;
                if(this.iconNumber<=0) this.iconNumber=null;
            }
            case "East_inside" -> {
                this.eastFrontier = !this.eastFrontier;
                if (this.col < this.gridButtons[this.row].length-1) {
                    this.gridButtons[this.row][this.col+1].clickedOn(x - getWidth(), y, leftMouseButton);
                }
            }
            case "East_outside" -> {
                this.eastFrontier = !this.eastFrontier;
            }
            case "South_inside" -> {
                this.southFrontier = !this.southFrontier;
                if (this.row < this.gridButtons.length-1) {
                    this.gridButtons[this.row+1][this.col].clickedOn(x, y - getHeight(), leftMouseButton);
                }
            }
            case "South_outside" -> {
                this.southFrontier = !this.southFrontier;
            }
            case "North_inside" -> {
                this.northFrontier = !this.northFrontier;
                if (this.row>0) {
                    this.gridButtons[this.row-1][this.col].clickedOn(x, y+ getHeight(), leftMouseButton);
                }
            }
            case "North_outside" -> {
                this.northFrontier = !this.northFrontier;
            }
            case "West_Inside" -> {
                this.westFrontier = !this.westFrontier;
                if (this.col>0) {
                    this.gridButtons[this.row][this.col-1].clickedOn(x + getWidth(), y, leftMouseButton);
                }
            }
            case "West_Outside" -> {
                this.westFrontier = !this.westFrontier;
            }
        }
        repaint();
    }

    private String isClickNearTo(int x, int y) {
        int width = getWidth();
        int height = getHeight();
        if((0 < x) && (abs(x) < max(10,this.thick))) return "West_Inside";
        if((0 > x) && (abs(x) < max(10,this.thick))) return "West_Outside";
        if ((0 < width-x) && (abs(x-width) < max(10,this.thick))) return "East_inside";
        if ((0 < x-width) && (abs(width-x) < max(10,this.thick))) return "East_outside";
        if((0 < y) && (abs(y) < max(10,this.thick))) return "North_inside";
        if((0 > y) && (abs(y) < max(10,this.thick))) return "North_outside";
        if ((0 < height-y) && (abs(y-height) < max(10,this.thick))) return "South_inside";
        if ((0 < y-height) && (abs(height-y) < max(10,this.thick))) return "South_outside";
        else return "Center";
    }

    /**
     * Function that paints the order.
     */
    public void paintIconNumber(Graphics2D g2){
        if (this.iconNumber != null) {
            int width = getWidth();
            int height = getHeight();
            int minDiameter = Math.min(width, height) - this.thick;
            int centerX = width / 2;
            int centerY = height / 2;
            int xDiameter= width-this.thick;
            int yDiameter= height-this.thick;
            float fontSize = (float) (minDiameter / 2.);
            // Draw circle
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.fillOval(centerX - xDiameter/2, centerY - yDiameter/2, xDiameter, yDiameter);
            // Draw centered number
            g2.setFont(g2.getFont().deriveFont(fontSize));
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(this.iconNumber.toString());
            int textHeight = fm.getAscent() - fm.getDescent();
            int textX = centerX - textWidth / 2;
            int textY = centerY + textHeight / 2;
            g2.setColor(Color.WHITE);
            g2.drawString(this.iconNumber.toString(), textX, textY);
        }
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
        int width = getWidth();
        int height = getHeight();
        this.thick = min(width, height)/4;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        paintIconNumber(g2);
        g2.dispose();
    }

    /**
     * Paint the button's border if <code>BorderPainted</code>
     * property is true and the button has a border.
     * @param g the <code>Graphics</code> context in which to paint
     *
     * @see #paint
     * @see #setBorder
     */
    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(super.getBackground());
        g2.setStroke(new BasicStroke((float) this.thick /4));
        g2.drawLine(0, 0, 0, getHeight()); //West frontier Line
        g2.drawLine(getWidth(), 0, getWidth(), getHeight()); //East frontier Line
        g2.drawLine(0, 0, getWidth(), 0); //North frontier line
        g2.drawLine(0, getHeight(), getWidth(), getHeight()); //South frontier Line
        //West
        if ((this.col != 0) && this.westFrontier) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(this.thick));
            g2.drawLine(0, 0, 0, getHeight()); //West frontier Line
        }

        //East
        if ((this.col != this.gridButtons[this.row].length-1) && this.eastFrontier) {
            g2.setStroke(new BasicStroke(this.thick));
            g2.setColor(Color.BLACK);
            g2.drawLine(getWidth(), 0, getWidth(), getHeight()); //East frontier Line
        }

        //North
        if ((this.row != 0) && this.northFrontier) {
            g2.setStroke(new BasicStroke(this.thick));
            g2.setColor(Color.BLACK);
            g2.drawLine(0, 0, getWidth(), 0); //North frontier line
        }

        //South
        if ((this.row != this.gridButtons.length-1) && this.southFrontier) {
            g2.setStroke(new BasicStroke(this.thick));
            g2.setColor(Color.BLACK);
            g2.drawLine(0, getHeight(), getWidth(), getHeight()); //South frontier Line
        }
        g2.dispose();
    }
}