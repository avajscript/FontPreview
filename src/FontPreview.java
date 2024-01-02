import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FontPreview extends JFrame {
    // list of all system fonts
    private static final String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    // list of favorited fonts
    String[] savedFonts = FontFileWriter.readFileAsArray(FOLDER_PATH + FILE_PATH);
    // Heading one font
    private static final Font HEADING_FONT = new Font("Rockwell", Font.BOLD, 24);
    // Heading two font
    private static final Font HEADING_FONT_2 = new Font("Rockwell", Font.PLAIN, 20);
    // Default border for JPanels
    private static final EmptyBorder BASE_BORDER = new EmptyBorder(8, 16, 8, 16);
    private static final EmptyBorder BASE_BORDER_SMALL = new EmptyBorder(4, 8, 4, 8);
    // List object for fonts
    private static final JList<String> fontList = new JList<String>(fonts);
    private int fontSize = 32;
    private String fontText = fonts[0];
    JLabel previewLabel;
    JSlider fontSlider;
    JLabel fontSizeLabel;
    JButton saveButton;
    private int fontWeight = Font.PLAIN;
    Font previewFont = new Font(fontText, fontWeight, fontSize);
    // used to see if favorited fonts needs to be updated
    private boolean favCacheRequired = false;
    private boolean fontIsSaved = false;
    // file paths
    private static final String FOLDER_PATH = "data/";
    private static final String FILE_PATH = "favFonts.txt";
    public FontPreview() {
        setTitle("Font Previewer");
        // Menu
        FontMenu fontMenu = new FontMenu();
        setJMenuBar(fontMenu);
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(BASE_BORDER);
        JLabel headingLabel = new JLabel("Font Previewer");
        headingLabel.setFont(HEADING_FONT);
        headerPanel.add(headingLabel);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanel.setBorder(BASE_BORDER);

        // left center panel
        JPanel leftCenterPanel = new JPanel();
        leftCenterPanel.setLayout(new BoxLayout(leftCenterPanel, BoxLayout.Y_AXIS));
        // left center panel label
        JLabel centerLabel = new JLabel("Select a font");
        centerLabel.setFont(HEADING_FONT_2);
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
        rightCenterPanel.setBorder(BASE_BORDER);
        rightCenterPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(4, 0, 4, 0);

        // save button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        saveButton = getSaveButton();
        buttonPanel.add(saveButton);

        // right center label
        previewLabel = new JLabel(fontText);
        previewLabel.setFont(previewFont);
        previewLabel.setOpaque(true);
        previewLabel.setBorder(BASE_BORDER);
        previewLabel.setBackground(DesignPalette.WHITE_BG);

        // font size scroll section
        JPanel scrollPanel = new JPanel();
        scrollPanel.setBorder(BASE_BORDER_SMALL);
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.X_AXIS));
        // slider
        fontSizeLabel = new JLabel(fontSize + "px");
        fontSlider = new JSlider(8, 60, fontSize);
        fontSlider.setMaximumSize(new Dimension(120, 30));
        fontSlider.addChangeListener(fontSliderChangeListener());
        // add to scroll label
        scrollPanel.add(fontSizeLabel);
        scrollPanel.add(fontSlider);
        // add to right center panel
        rightCenterPanel.add(buttonPanel, gbc);
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
        setVisible(true);
    }

    public JButton getSaveButton() {
        saveButton = new JButton();
        // iterate over savedFonts to see if it contains the current one
        boolean fontSaved = false;
        for (int i = 0; i < savedFonts.length; i++) {
            // font matches
            if (savedFonts[i].equals(fontText)) {
                fontSaved = true;
                break;
            }
        }
        if (fontSaved) {
           saveButton.setText("Remove Favorite");
           saveButton.addActionListener(removeSavedFont());
        } else {
            saveButton.setText("Save Font");
            saveButton.addActionListener(saveFont());
        }
        return saveButton;
    }

    public ActionListener removeSavedFont() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FontFileWriter.removeFontFromFile(FOLDER_PATH + FILE_PATH, fontText);
                saveButton.removeActionListener(removeSavedFont());
                saveButton.addActionListener(saveFont());
                saveButton.setText("Save Font");
                favCacheRequired = true;
            }
        };
    }

    public ActionListener toggleFont() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //font is currently saved
                if (fontIsSaved) {
                    FontFileWriter.removeFontFromFile()
                    saveButton.setText("Remove Font");
                    // font is not saved
                } else {

                }
            }
        };
    }
    public ActionListener saveFontToggle() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FontFileWriter.appendToFile(FOLDER_PATH + FILE_PATH, fontText);
                saveButton.removeActionListener(saveFont());
                saveButton.addActionListener(removeSavedFont());
                saveButton.setText("Remove Favorite");
                favCacheRequired = true;
            }
        };
    }

    /**
     * checks if the favorited fonts needs to be updated
     * and updates it, then resets the cache required boolean state
     */
    public void checkAndUpdateFontCache() {
        if (favCacheRequired) {
            // update the favorite fonts and reset the cache state
            savedFonts = FontFileWriter.readFileAsArray(FOLDER_PATH + FILE_PATH);
            favCacheRequired = false;
        }
        boolean favorited = false;
        // iterate over all favorited fonts to see if it matches current one
        for (int i = 0; i < savedFonts.length; i++) {
            // if match
            if (savedFonts[i].equals(fontText)) {
                saveButton.setText("Remove Font");
                saveButton.removeActionListener(removeSavedFont());
                saveButton.addActionListener(removeSavedFont());
                favorited = true;
            }
        }
        System.out.println(favorited);
        // add event listener to save font if not font isn't favorited
        if (!favorited) {
            saveButton.setText("Save Font");
            System.out.println("Bugg");
            saveButton.removeActionListener(saveFont());
            saveButton.addActionListener(saveFont());
        }

    }

    /**
     * Update the selected font when one of the list items is selected.
     * Also, updates the saveButton favorite state
     * @return mouseListener for list that runs on mouse click
     */
    public MouseListener listSelectListener() {
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // change the font to the selected font in the list
                fontText = fontList.getSelectedValue();
                previewFont = new Font(fontText, fontWeight, fontSize);
                previewLabel.setFont(previewFont);
                previewLabel.setText(fontText);
                checkAndUpdateFontCache();
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
                // update the font size text and the actual font size
                fontSize = fontSlider.getValue();
                fontSizeLabel.setText(fontSize + "px");
                previewLabel.setFont(previewFont.deriveFont((float) fontSize));
            }
        };
        return fontSliderChange;
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(()-> new FontPreview());

    }
}

