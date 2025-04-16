package org.soyaga.examples.Queens.MathModelQueens.UI.Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
     * JTextArea where to write the optimization info.
     */
    private final JTextArea textArea;
    /**
     * JScrollPane used to contain the JTextArea
     */
    private final JScrollPane scrollPane;

    /**
     * Constructor
     */
    public StatsPanel() {
        super();
        try {
            this.defaultImage = ImageIO.read(Objects.requireNonNull(StatsPanel.class.getResource("/DefaultImages/Default_Math_Model_Image.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.setPreferredSize(new Dimension(200,200));
        this.textArea = new JTextArea();
        this.scrollPane = new JScrollPane(this.textArea);
    }

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {
        this.textArea.setEditable(false);
        this.scrollPane.setVisible(false);
        this.setLayout(new BorderLayout());
        this.add(this.scrollPane, BorderLayout.CENTER);
    }

    /**
     * Function to set the solution Text.
     * @param solutionText String with the solution text.
     */
    public void setSolutionText(String solutionText){
        SwingUtilities.invokeLater(() -> {
            this.textArea.append(solutionText);
            this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
            this.scrollPane.setVisible(true);
            this.revalidate();
            this.repaint();
        });
    }

    /**
     * Function call in the graphical context to display the component.
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.textArea.getText().trim().isEmpty()) {
            g.drawImage(this.defaultImage.getScaledInstance(
                            this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),
                    0, 0, this);
        }
    }
}