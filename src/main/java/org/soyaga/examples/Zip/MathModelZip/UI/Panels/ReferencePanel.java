package org.soyaga.examples.Zip.MathModelZip.UI.Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Panel where to set the problem, and the solution from LinkedIn.
 */
public class ReferencePanel extends JPanel {
    /**
     * Image used to draw in the component
     */
    private BufferedImage image;
    /**
     * Image of the problem from LinkedIn
     */
    private BufferedImage start;
    /**
     * Image of the solution from LinkedIn
     */
    private BufferedImage solution;

    /**
     * Constructor
     */
    public ReferencePanel() {
        try {
            this.start = ImageIO.read(Objects.requireNonNull(ReferencePanel.class.getResource("/CurrentImages/Zip.png")));
            this.solution = ImageIO.read(Objects.requireNonNull(ReferencePanel.class.getResource("/CurrentSolutionImages/ZipSolve.png")));
        } catch (IOException ex) {
            System.out.println("Image not found!");
            ex.printStackTrace();
        }
    }

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {
        this.image = this.start;
        TitledBorder title = BorderFactory.createTitledBorder(
                new EmptyBorder(20, 20, 20, 20), "LinkedIn Reference");
        title.setTitlePosition(TitledBorder.TOP);
        this.setBorder(title);
        this.setPreferredSize(new Dimension(500,500));
        //Set layout and add components
        this.setLayout(new BorderLayout());
    }

    /**
     * Function that sets the image to the solution image.
     */
    public void setSolutionAsImage(){
        this.image= this.solution;
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
    public void paintComponent(Graphics g) {
        this.paintBorder(g);
        if (image != null) {
            g.drawImage(this.image.getScaledInstance(this.getWidth()-40, this.getHeight()-40, Image.SCALE_SMOOTH), this.getX()+20, this.getY()+20, this); // Draw the image
        }
        g.dispose();
    }
}