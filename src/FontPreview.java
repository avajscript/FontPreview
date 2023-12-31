import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FontPreview extends JFrame {
    private static final String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    // Heading one font
    private static final  Font headingFont = new Font("Rockwell", Font.BOLD, 24);
    // Heading two font
    private static final Font headingFont2 = new Font("Rockwell", Font.PLAIN, 20);
    // Default border for JPanels
    private EmptyBorder baseBorder = new EmptyBorder(8, 16, 8, 16);
    private EmptyBorder smallBorder = new EmptyBorder(4, 8, 4, 8);
    // List object for fonts
    private static final JList<String> fontList = new JList<String>(fonts);
    private int fontSize = 32;
    private String fontText = fonts[0];
    JLabel previewLabel;
    JSlider fontSlider;
    JLabel fontSizeLabel;
    private int fontWeight = Font.PLAIN;
    Font previewFont = new Font(fontText, fontWeight, fontSize);
    public FontPreview() {
        setTitle("Font Previewer");
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(baseBorder);
        JLabel headingLabel = new JLabel("Font Previewer");
        headingLabel.setFont(headingFont);
        headerPanel.add(headingLabel);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanel.setBorder(baseBorder);

        // left center panel
        JPanel leftCenterPanel = new JPanel();
        leftCenterPanel.setLayout(new BoxLayout(leftCenterPanel, BoxLayout.Y_AXIS));
        // left center panel label
        JLabel centerLabel = new JLabel("Select a font");
        centerLabel.setFont(headingFont2);
        // font list scroll pane
        fontList.addMouseListener(listSelectListener());
        JScrollPane scrollableFontList = new JScrollPane(fontList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableFontList.setMinimumSize(new Dimension(200, 400));
        scrollableFontList.setMaximumSize(new Dimension(240, 400));
        // add to left center panel
        leftCenterPanel.add(centerLabel);
        leftCenterPanel.add(scrollableFontList);

        // right center panel
        JPanel rightCenterPanel = new JPanel();
        rightCenterPanel.setBorder(baseBorder);
        rightCenterPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        // right center label
        previewLabel = new JLabel(fontText);
        previewLabel.setFont(previewFont);
        previewLabel.setOpaque(true);
        previewLabel.setBorder(baseBorder);
        previewLabel.setBackground(DesignPalette.WHITE_BG);

        // font size scroll section
        JPanel scrollPanel = new JPanel();
        scrollPanel.setBorder(smallBorder);
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.X_AXIS));
        // slider
        fontSlider = new JSlider(8, 60, fontSize);
        fontSlider.addChangeListener(fontSliderChangeListener());
        fontSizeLabel = new JLabel(fontSize + "px");
        // add to scroll label
        scrollPanel.add(fontSizeLabel);
        scrollPanel.add(fontSlider);
        // add to right center panel
        rightCenterPanel.add(previewLabel, gbc);
        rightCenterPanel.add(scrollPanel, gbc);

        // add center panels to center panel
        centerPanel.add(leftCenterPanel);
        centerPanel.add(rightCenterPanel);

        // Left panel
        JPanel leftPanel = new JPanel();

        // Right panel
        JPanel rightPanel = new JPanel();

        // Bottom panel
        JPanel bottomPanel = new JPanel();

       // add components and set default behaviour/values for JFrame
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.EAST);
        add(rightPanel, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setVisible(true);
//        for (String font : fonts) {
//            System.out.println(font);
//        }
    }

    public MouseListener listSelectListener() {
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fontText = fontList.getSelectedValue();
                previewFont = new Font(fontText, fontWeight, fontSize);
                previewLabel.setFont(previewFont);
                previewLabel.setText(fontText);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        return mouseListener;
    }
    public ChangeListener fontSliderChangeListener() {
        ChangeListener fontSliderChange = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                fontSize = fontSlider.getValue();
                System.out.println(fontSize);
                fontSizeLabel.setText(fontSize + "px");
                previewLabel.setFont(previewFont.deriveFont((float) fontSize));
            }
        };
        return fontSliderChange;
    }

    public static void main (String[] args) {
        new FontPreview();

    }
}

class DesignPalette {
    public static final Color WHITE_BG = new Color(251, 251, 251);
    public static final Color BLACK_BG = new Color(52, 52, 52);
}