package org.soyaga.examples.Tango.MathModelTango.UI.Buttons;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
     * Images used as icons in the buttons.
     */
    private BufferedImage sun, moon;
    /**
     * String with the type of icon to draw.
     */
    @Setter
    private String iconType;
    /**
     * String with the type of icon to draw.
     */
    private String northType;
    /**
     * String with the type of icon to draw.
     */
    private String westType;
    /**
     * String with the type of icon to draw.
     */
    private String eastType;
    /**
     * String with the type of icon to draw.
     */
    private String southType;
    /**
     * Integer with the click detection thickness.
     */
    private int thick = 10;
    /**
     * Boolean that indicates whether this button has been manually selected.
     */
    private boolean manuallySelected = false;

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
        this.setMargin(new Insets(thick, thick, thick, thick));
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setPreferredSize(dim);
        try {
        this.sun = ImageIO.read(Objects.requireNonNull(SquareButton.class.getResource("/DefaultImages/TangoSunIcon.png")));
        this.moon = ImageIO.read(Objects.requireNonNull(SquareButton.class.getResource("/DefaultImages/TangoMoonIcon.png")));
        } catch (IOException ex) {
            System.out.println("Image not found!");
            ex.printStackTrace();
        }
        this.iconType = "None";
        this.northType = "None";
        this.westType = "None";
        this.eastType = "None";
        this.southType = "None";

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickedOn(e.getX(), e.getY());
            }
        });
    }

    /**
     * Function that manages the behavior of the click event.
     * @param x integer relative to the component
     * @param y integer relative to the component
     */
    private void clickedOn(int x, int y) {
        switch (isClickNearTo(x, y)) {
            case "Center" -> {
                switch (getIconType()){
                    case("None") -> {
                        iconType = "Sun";
                        manuallySelected = true;
                    }
                    case("Sun") -> {
                        iconType = "Moon";
                        manuallySelected = true;
                    }
                    case("Moon") -> {
                        iconType = "None";
                        manuallySelected = false;
                    }
                }
            }
            case "East_inside" -> {
                switch (getEastType()){
                    case("None") -> eastType = "=";
                    case("=") -> eastType = "x";
                    case("x") -> eastType = "None";
                }
                if (col < gridButtons[row].length-1) {
                    gridButtons[row][col+1].clickedOn(x - getWidth(), y);
                }
            }
            case "East_outside" -> {
                switch (getEastType()){
                    case("None") -> eastType = "=";
                    case("=") -> eastType = "x";
                    case("x") -> eastType = "None";
                }
            }
            case "South_inside" -> {
                switch (getSouthType()){
                    case("None") -> southType = "=";
                    case("=") -> southType = "x";
                    case("x") -> southType = "None";
                }
                if (row < gridButtons.length-1) {
                    gridButtons[row+1][col].clickedOn(x, y - getHeight());
                }
            }
            case "South_outside" -> {
                switch (getSouthType()){
                    case("None") -> southType = "=";
                    case("=") -> southType = "x";
                    case("x") -> southType = "None";
                }
            }
            case "North_inside" -> {
                switch (getNorthType()){
                    case("None") -> northType = "=";
                    case("=") -> northType = "x";
                    case("x") -> northType = "None";
                }
                if (row>0) {
                    gridButtons[row-1][col].clickedOn(x, y+ getHeight());
                }
            }
            case "North_outside" -> {
                switch (getNorthType()){
                    case("None") -> northType = "=";
                    case("=") -> northType = "x";
                    case("x") -> northType = "None";
                }
            }
            case "West_Inside" -> {
                switch (getWestType()){
                    case("None") -> westType = "=";
                    case("=") -> westType = "x";
                    case("x") -> westType = "None";
                }
                if (col>0) {
                    gridButtons[row][col-1].clickedOn(x + getWidth(), y);
                }
            }
            case "West_Outside" -> {
                switch (getWestType()){
                    case("None") -> westType = "=";
                    case("=") -> westType = "x";
                    case("x") -> westType = "None";
                }
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
        this.thick = min(this.getWidth(), this.getHeight())/6;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        if(!this.manuallySelected){
            g2.setColor(Color.WHITE);
            g2.fillRect(0,0,this.getWidth(),this.getHeight());
        }
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        switch (this.iconType){
            case "Sun" -> g2.drawImage(this.sun.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),
                    0, 0, this);
            case "Moon" -> g2.drawImage(this.moon.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),
                    0, 0, this);
        }
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
        // Set a larger font
        Font originalFont = g2.getFont();
        Font bigFont = originalFont.deriveFont(this.thick*2.F);
        g2.setFont(bigFont);
        FontMetrics metrics = g2.getFontMetrics(bigFont);
        int textHeight = metrics.getAscent()/2;
        //West
        g2.setStroke(new BasicStroke(4));
        if ((this.col == 0)) {
            g2.setStroke(new BasicStroke(7));
            g2.drawLine(0, 0, 0, getHeight()); //West frontier line
        } else if (!"None".equals(this.westType)) {
            int textWidth = metrics.stringWidth(this.westType);
            g2.drawLine(0, 0, 0, getHeight()/2-this.thick); //East frontier 1st half Line
            g2.setColor(super.getBackground().darker());
            g2.drawString(this.westType, -textWidth/2, (getHeight()+textHeight)/2); //East text
            g2.setColor(super.getBackground());
            g2.drawLine(0, getHeight()/2+this.thick, 0, getHeight()); //East frontier 2nd halfLine
        }else {
            g2.drawLine(0, 0, 0, getHeight()); //East frontier Line
        }
        //East
        g2.setStroke(new BasicStroke(4));
        if ((this.col == this.gridButtons[this.row].length-1)) {
            g2.setStroke(new BasicStroke(7));
            g2.drawLine(getWidth(), 0, getWidth(), getHeight()); //East frontier Line
        } else if (!"None".equals(this.eastType)) {
            int textWidth = metrics.stringWidth(this.eastType);
            g2.drawLine(getWidth(), 0, getWidth(), getHeight()/2-this.thick); //East frontier 1st half Line
            g2.setColor(super.getBackground().darker());
            g2.drawString(this.eastType, getWidth()-textWidth/2, (getHeight()+textHeight)/2); //East text
            g2.setColor(super.getBackground());
            g2.drawLine(getWidth(), getHeight()/2+this.thick, getWidth(), getHeight()); //East frontier 2nd halfLine
        }else {
            g2.drawLine(getWidth(), 0, getWidth(), getHeight()); //East frontier Line
        }
        //North
        g2.setStroke(new BasicStroke(4));
        if ((this.row == 0)) {
            g2.setStroke(new BasicStroke(7));
            g2.drawLine(0, 0, getWidth(), 0); //North frontier line
        } else if (!"None".equals(this.northType)) {
            int textWidth = metrics.stringWidth(this.northType);
            g2.drawLine(0, 0, getWidth()/2-this.thick, 0); //South frontier 1st half Line
            g2.setColor(super.getBackground().darker());
            g2.drawString(this.northType, (getWidth()-textWidth)/2, textHeight/2+2); //South text
            g2.setColor(super.getBackground());
            g2.drawLine(getWidth()/2+this.thick, 0, getWidth(), 0); //South frontier 2nd halfLine
        }else {
            g2.drawLine(0, 0, getWidth(), 0); //South frontier Line
        }
        //South
        g2.setStroke(new BasicStroke(4));
        if ((this.row == this.gridButtons.length-1)) {
            g2.setStroke(new BasicStroke(7));
            g2.drawLine(0, getHeight(), getWidth(), getHeight()); //South frontier Line
        } else if (!"None".equals(this.southType)) {
            int textWidth = metrics.stringWidth(this.southType);
            g2.drawLine(0, getHeight(), getWidth()/2-this.thick, getHeight()); //South frontier 1st half Line
            g2.setColor(super.getBackground().darker());
            g2.drawString(this.southType, (getWidth()-textWidth)/2, getHeight()+textHeight/2+2); //South text
            g2.setColor(super.getBackground());
            g2.drawLine(getWidth()/2+this.thick, getHeight(), getWidth(), getHeight()); //South frontier 2nd halfLine
        }else {
            g2.drawLine(0, getHeight(), getWidth(), getHeight()); //South frontier Line
        }
        g2.dispose();
    }

}