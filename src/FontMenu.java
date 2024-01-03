import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FontMenu extends JMenuBar {
    public FontMenu(FontPreview mainFrame) {
        // create new file menu item
        JMenu fileMenu = new JMenu("File");
        // items for "file" dropdown
        JMenuItem favMenuItem = new JMenuItem("Favorites");
        favMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                File dataDirectory, favFile;
//                Path path;
//                try {
//                    // open file
//                    dataDirectory = new File("data");
//                    // if directory doesn't exist, create new one
//                    if(!dataDirectory.exists()) {
//                        path = Paths.get("data");
//                        Files.createDirectory(path);
//                        dataDirectory = new File("data");
//                        if(!dataDirectory.exists()) {
//                            throw new IOException("Error: Directory 'data' not created");
//                        }
//                    }
//                    // open fav font file
//                    favFile = new File("data/favFonts.txt");
//                    if(!favFile.exists()) {
//                        path = Paths.get("data/favFonts.txt");
//                        Files.createFile(path);
//                        favFile = new File("data/favFonts.txt");
//                        if(!favFile.exists()) {
//                            throw new IOException("Error: File 'favFonts.txt not created");
//                        }
//                    }
//
//
//                } catch (Exception fileErr) {
//                    fileErr.printStackTrace();
//                }
                // open the favorite panel
                mainFrame.openFavorites();
            }
        });
        fileMenu.add(favMenuItem);
        JMenu navMenu = new JMenu("Nav");
        JMenuItem homeMenuItem = new JMenuItem("All Fonts");
        homeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.openMain();
            }
        });
        navMenu.add(homeMenuItem);
        // add the file menu item to menubar
        add(fileMenu);
        add(navMenu);
    }
}
