package org.soyaga.examples.Queens.MathModelQueens.UI.Buttons;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Class that is a Squared button used to create the color selection palette.
 */
@Getter
public class SquareButton extends JButton {
    /**
     * Color to use to paint the button.
     */
    @Setter
    private Color buttonColor;
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
    private BufferedImage queenImage, noQueenImage;
    /**
     * String with the type of icon to draw.
     */
    private String iconType;

    /**
     * Constructor
     * @param dim Dimension of the button. Computed by the grid
     * @param row Integer with the grid row position
     * @param col Integer with the grid column position
     * @param gridButtons List composed of lists of SquareButtons.
     */
    public SquareButton(Dimension dim, int row, int col, SquareButton[][] gridButtons) {
        super();
        this.buttonColor = super.getBackground();
        this.row = row;
        this.col = col;
        this.gridButtons = gridButtons;
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setPreferredSize(dim);
        this.setActionCommand("SetCellColor");
        try {
        this.queenImage = ImageIO.read(Objects.requireNonNull(SquareButton.class.getResource("/DefaultImages/QueenIcon.png")));
        this.noQueenImage = ImageIO.read(Objects.requireNonNull(SquareButton.class.getResource("/DefaultImages/NoQueenIcon.png")));
        } catch (IOException ex) {
            System.out.println("Image not found!");
            ex.printStackTrace();
        }
        this.iconType = "NoneQueen";
    }

    /**
     * Function to set a queen as icon
     */
    public void setQueen(){
        this.iconType = "Queen";
    }

    /**
     * Function to set a no queen as icon
     */
    public void setNoQueen(){
        this.iconType = "NoQueen";
    }

    /**
     * Function to remove the icon
     */
    public void setNoneQueen(){
        this.iconType = "NoneQueen";
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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.buttonColor);
        g2.fillRect(0, 0, getWidth(),getHeight());
        switch (this.iconType){
            case "Queen" -> g2.drawImage(this.queenImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),
                    0, 0, this);
            case "NoQueen" -> g2.drawImage(this.noQueenImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),
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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(0.5F));
        if(this.buttonColor == null){
            g2.drawRect(0, 0, getWidth(), getHeight());
        }else {
            if ((this.col == 0) || !this.buttonColor.equals(this.gridButtons[this.row][this.col - 1].getButtonColor())) {
                g2.setStroke(new BasicStroke(4));
            }
            g2.drawLine(0, 0, 0, getHeight()); //West frontier line

            g2.setStroke(new BasicStroke(2));
            if ((this.col == this.gridButtons[this.row].length-1) || !this.buttonColor.equals(this.gridButtons[this.row][this.col + 1].getButtonColor())) {
                g2.setStroke(new BasicStroke(4));
            }
            g2.drawLine(getWidth(), 0, getWidth(), getHeight()); //East frontier Line

            g2.setStroke(new BasicStroke(2));
            if ((this.row == 0) || !this.buttonColor.equals(this.gridButtons[this.row - 1][this.col].getButtonColor())) {
                g2.setStroke(new BasicStroke(4));
            }
            g2.drawLine(0, 0, getWidth(), 0); //North frontier line

            g2.setStroke(new BasicStroke(2));
            if ((this.row == this.gridButtons.length-1) || !this.buttonColor.equals(this.gridButtons[this.row + 1][this.col].getButtonColor())) {
                g2.setStroke(new BasicStroke(4));
            }
            g2.drawLine(0, getHeight(), getWidth(), getHeight()); //East frontier Line
        }
        g2.dispose();
    }
}