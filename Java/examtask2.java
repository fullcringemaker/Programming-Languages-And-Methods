-------------------------------------------------- BottleDrawingPanel.java --------------------------------------------------
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class BottleDrawingPanel extends JPanel {
    private int bottleHeight;
    private int bottleDiameter;
    private int neckHeight;
    private int neckDiameter;
    private int fillPercentage;
    private Color liquidColor;

    public BottleDrawingPanel(int bottleHeight, int bottleDiameter, int neckHeight, int neckDiameter, int fillPercentage, Color liquidColor) {
        this.bottleHeight = bottleHeight;
        this.bottleDiameter = bottleDiameter;
        this.neckHeight = neckHeight;
        this.neckDiameter = neckDiameter;
        this.fillPercentage = fillPercentage;
        this.liquidColor = liquidColor;
    }

    public void setBottleHeight(int bottleHeight) {
        this.bottleHeight = bottleHeight;
        repaint();
    }

    public void setBottleDiameter(int bottleDiameter) {
        this.bottleDiameter = bottleDiameter;
        repaint();
    }

    public void setFillPercentage(int fillPercentage) {
        this.fillPercentage = fillPercentage;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int bottleX = (width - bottleDiameter) / 2;
        int bottleY = getHeight() - bottleHeight - neckHeight;

        // Draw bottle body
        g2d.setColor(Color.BLACK);
        g2d.drawRect(bottleX, bottleY, bottleDiameter, bottleHeight);

        // Draw bottle neck
        int neckX = (width - neckDiameter) / 2;
        int neckY = bottleY - neckHeight;
        g2d.drawRect(neckX, neckY, neckDiameter, neckHeight);

        // Calculate liquid height based on fill percentage
        int liquidHeight = bottleHeight * fillPercentage / 100;

        // Draw liquid in the bottle body
        g2d.setColor(liquidColor);
        g2d.fillRect(bottleX + 1, bottleY + bottleHeight - liquidHeight + 1, bottleDiameter - 1, liquidHeight - 1);

        // Draw liquid in the neck if necessary
        int neckLiquidHeight = Math.min(neckHeight, liquidHeight);
        g2d.fillRect(neckX + 1, neckY + neckHeight - neckLiquidHeight + 1, neckDiameter - 1, neckLiquidHeight - 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        int bottleHeight = 300;
        int bottleDiameter = 100;
        int neckHeight = 100;
        int neckDiameter = 30;
        int fillPercentage = 50;
        Color liquidColor = Color.RED;

        JFrame frame = new JFrame("Bottle Drawing");
        BottleDrawingPanel bottlePanel = new BottleDrawingPanel(bottleHeight, bottleDiameter, neckHeight, neckDiameter, fillPercentage, liquidColor);

        JSlider heightSlider = new JSlider(JSlider.HORIZONTAL, 100, 500, bottleHeight);
        heightSlider.setMajorTickSpacing(100);
        heightSlider.setMinorTickSpacing(10);
        heightSlider.setPaintTicks(true);
        heightSlider.setPaintLabels(true);
        heightSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                bottlePanel.setBottleHeight(heightSlider.getValue());
            }
        });

        JSlider bottleDiameterSlider = new JSlider(JSlider.HORIZONTAL, 50, 200, bottleDiameter);
        bottleDiameterSlider.setMajorTickSpacing(50);
        bottleDiameterSlider.setMinorTickSpacing(10);
        bottleDiameterSlider.setPaintTicks(true);
        bottleDiameterSlider.setPaintLabels(true);
        bottleDiameterSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                bottlePanel.setBottleDiameter(bottleDiameterSlider.getValue());
            }
        });

        JSlider fillPercentageSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, fillPercentage);
        fillPercentageSlider.setMajorTickSpacing(10);
        fillPercentageSlider.setMinorTickSpacing(1);
        fillPercentageSlider.setPaintTicks(true);
        fillPercentageSlider.setPaintLabels(true);
        fillPercentageSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                bottlePanel.setFillPercentage(fillPercentageSlider.getValue());
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(bottlePanel, BorderLayout.CENTER);
        frame.add(heightSlider, BorderLayout.NORTH);
        frame.add(bottleDiameterSlider, BorderLayout.SOUTH);
        frame.add(fillPercentageSlider, BorderLayout.WEST);

        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
