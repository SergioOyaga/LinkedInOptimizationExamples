package org.soyaga.examples.Queens.GeneticQueens.UI.Buttons;

import lombok.Getter;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

/**
 * Class that is a rounded button used to create the color selection palette.
 */
@Getter
public class RoundedButton extends JButton {
    /**
     * Color of the button, and used by the selection.
     */
    private final Color buttonColor;

    /**
     * Constructor
     * @param color Color to use in the rounded button
     */
    public RoundedButton(Color color) {
        super();
        this.buttonColor = color;
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(30, 30));
        this.setActionCommand("SelectColor");
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
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.buttonColor);
        g2.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, 20, 20);
        super.paintComponent(g);
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
        g2.setStroke(new BasicStroke(this.isSelected() ? 4 : 2)); // Thicker border when pressed
        g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 20, 20);
        g2.dispose();
    }
}