-------------------------------------------------- ForkPanel.java --------------------------------------------------
import javax.swing.*;
import java.awt.*;
class ForkPanel extends JPanel {
    private int teethCount = 5;
    private Color forkColor = Color.RED;
    public ForkPanel() {
        setPreferredSize(new Dimension(400, 300));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawFork(g, teethCount, forkColor);
    }
    public void setTeethCount(int teethCount) {
        this.teethCount = teethCount;
        repaint();
    }
    public void setForkColor(Color color) {
        this.forkColor = color;
        repaint();
    }
    private void drawFork(Graphics g, int teethCount, Color color) {
        g.setColor(color);
        int width = getWidth();
        int height = getHeight();
        int horizontalRectWidth = width / 2;
        int horizontalRectHeight = height / 10;
        int horizontalRectX = width / 2 - horizontalRectWidth / 2;
        int horizontalRectY = height / 3 - horizontalRectHeight / 2;
        g.fillRect(horizontalRectX, horizontalRectY, horizontalRectWidth, horizontalRectHeight);
        int toothWidth = horizontalRectWidth / (2 * teethCount);
        int toothHeight = horizontalRectHeight * 2;
        int toothY = horizontalRectY - toothHeight + horizontalRectHeight;
        for (int i = 0; i < teethCount; i++) {
            int toothX = horizontalRectX + toothWidth * i * 2;
            g.fillRect(toothX, toothY, toothWidth, toothHeight);
        }
        int verticalRectWidth = horizontalRectWidth / 10;
        int verticalRectHeight = height / 3;
        int verticalRectX = width / 2 - verticalRectWidth / 2;
        int verticalRectY = height / 3;
        g.fillRect(verticalRectX, verticalRectY, verticalRectWidth, verticalRectHeight);
    }
}


-------------------------------------------------- ForkDrawingApp.java --------------------------------------------------
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ForkDrawingApp {
    private JPanel mainPanel;
    private JSlider teethSlider;
    private JComboBox<String> colorComboBox;
    private ForkPanel forkPanel;
    public ForkDrawingApp() {
        mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        JPanel sliderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        colorComboBox = new JComboBox<>(new String[] {"Red", "Green", "Blue"});
        teethSlider = new JSlider(2, 10, 5);
        sliderPanel.add(teethSlider);
        colorPanel.add(colorComboBox);
        topPanel.add(sliderPanel);
        topPanel.add(colorPanel);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        forkPanel = new ForkPanel();
        mainPanel.add(forkPanel, BorderLayout.CENTER);
        teethSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                forkPanel.setTeethCount(teethSlider.getValue());
            }});
        colorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forkPanel.setForkColor(getColorByName((String) colorComboBox.getSelectedItem()));
            }});
    }
    private Color getColorByName(String colorName) {
        switch (colorName) {
            case "Red": return Color.RED;
            case "Green": return Color.GREEN;
            case "Blue": return Color.BLUE;
            default: return Color.BLACK;
        }}
    public static void main(String[] args) {
        JFrame frame = new JFrame("ForkDrawingApp");
        frame.setContentPane(new ForkDrawingApp().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
