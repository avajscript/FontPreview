import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FavoriteFonts extends JPanel{
    ArrayList<String> savedFonts;
    private int fontSize = 24;
    private int fontWeight = Font.PLAIN;

    public FavoriteFonts() {
        // read favorite fonts file
        savedFonts = FontFileWriter.readFileAsArray(FontPreview.FOLDER_PATH + FontPreview.FILE_PATH);

        // heading label
        JLabel heading = new JLabel("Favorite Fonts");
        heading.setFont(FontPreview.HEADING_FONT);

        // top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBorder(FontPreview.BASE_BORDER);
        topPanel.add(heading);

        // Alignment for fonts
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(4, 0, 4, 0);

        // Panel for fonts
        JPanel fontsPanel = new JPanel();
        fontsPanel.setBorder(FontPreview.BASE_BORDER_SMALL);
        fontsPanel.setLayout(new GridBagLayout());

        // add all fonts to font panel
        for (int i = 0; i < savedFonts.size(); i++) {
            JLabel fontLabel = new JLabel(savedFonts.get(i));
            Font font = new Font(savedFonts.get(i), fontWeight, fontSize);
            fontLabel.setFont(font);
            fontsPanel.add(fontLabel, gbc);
        }

        // add all components to main panel
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(fontsPanel, BorderLayout.CENTER);


    }


}
