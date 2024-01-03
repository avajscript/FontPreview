import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;


public class FontPreview extends JFrame {
    // components
    JPanel focusPanel;
    JPanel mainPanel;
    FavoriteFonts favoriteFonts;
    // list of all system fonts
    private static final String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    // list of favorited fonts
    ArrayList<String> savedFonts;
    // Heading one font
    public static final Font HEADING_FONT = new Font("Rockwell", Font.BOLD, 24);
    // Heading two font
    public static final Font HEADING_FONT_2 = new Font("Rockwell", Font.PLAIN, 20);
    // Default border for JPanels
    public static final EmptyBorder BASE_BORDER = new EmptyBorder(8, 16, 8, 16);
    public static final EmptyBorder BASE_BORDER_SMALL = new EmptyBorder(4, 8, 4, 8);
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
    public static final String FOLDER_PATH = "data/";
    public static final String FILE_PATH = "favFonts.txt";
    public FontPreview() {
        setTitle("Font Previewer");
        // make sure the file directory is properly created
        FontFileWriter.createFolderWithFiles("data/", new String[] {"favFonts.txt"} );
        // get a string array of all the favorited fonts
        savedFonts = FontFileWriter.readFileAsArray(FOLDER_PATH + FILE_PATH);
        // Menu
        FontMenu fontMenu = new FontMenu(this);
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
        mainPanel = new JPanel();
        focusPanel = mainPanel;
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.EAST);
        mainPanel.add(rightPanel, BorderLayout.WEST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(focusPanel);
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JButton getSaveButton() {
        saveButton = new JButton();
        saveButton.setText("Add Font");
        saveButton.addActionListener(toggleFont());
        // iterate over savedFonts to see if it contains the current one
        checkFontSavedAndUpdateState();
        return saveButton;
    }

    public void openFavorites() {
        favoriteFonts = new FavoriteFonts();
        remove(focusPanel);
        focusPanel = favoriteFonts;
        add(focusPanel);
        this.validate();
        this.repaint();
    }

    public void openMain() {
        remove(focusPanel);
        focusPanel = mainPanel;
        add(focusPanel);
        this.validate();
        this.repaint();
    }

    public ActionListener toggleFont() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // delete font from file and update button/state
                if (fontIsSaved) {
                    System.out.println("delete");
                    FontFileWriter.removeFontFromFile(FOLDER_PATH + FILE_PATH, fontText);
                    saveButton.setText("Add Font");
                    fontIsSaved = false;
                    // delete font from local font array list and update button/state
                    for (int i = 0; i < savedFonts.size(); i++) {
                        if (savedFonts.get(i).equals(fontText)) {
                            savedFonts.remove(i);
                            break;
                        }
                    }

                } else {
                    // add selected font to file
                    FontFileWriter.appendToFile(FOLDER_PATH + FILE_PATH, fontText);
                    saveButton.setText("Remove Font");
                    fontIsSaved = true;
                    // add selected font to local font array list
                    savedFonts.add(fontText);

                }
            }
        };
    }

    /**
     * Checks the local String ArrayList to see if the selected font is already saved
     * in order to update the
     */
    public void checkFontSavedAndUpdateState() {
        boolean localFontIsSaved = false;
        // check if font is saved and update the state
        for (String font : savedFonts) {
            if (fontText.equals(font)) {
                localFontIsSaved = true;
                fontIsSaved = true;
                saveButton.setText("Remove favorite");
                break;
            }
        }
        // font isn't saved after loop
        if (!localFontIsSaved) {
            fontIsSaved = false;
            saveButton.setText("Save Font");
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
                System.out.println(Math.random());
                // change the font to the selected font in the list
                fontText = fontList.getSelectedValue();
                previewFont = new Font(fontText, fontWeight, fontSize);
                previewLabel.setFont(previewFont);
                previewLabel.setText(fontText);
                // update the save font state
                checkFontSavedAndUpdateState();

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

