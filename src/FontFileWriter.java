import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FontFileWriter {
    private FontFileWriter() {

    }

    /**
     * Created a folder with the given path and files for every element in given files array.
     * It will not create the directory/files if the directory already exists
     * @param path the directory path to be created
     * @param files string array of file names to be created
     * @return true if no errors and false if errors
     */
    public static boolean createFolderWithFiles(String path, String[] files) {
        boolean success = true, fileState;
        Path locPath;
        File createdDirectory, newFile;

        try {
            // check to see if user given directory exists
            // if not then create it and throw error if it still wasn't created
            createdDirectory = new File(path);
            if(!(createdDirectory.exists() && createdDirectory.isDirectory())) {
                locPath = Paths.get(path);
                Files.createDirectory(locPath);
                createdDirectory = new File(path);
                if (!createdDirectory.exists()) {
                    throw new IOException("Error: Directory '" + "path' " + "not created");
                }
            } else {
                // returns if the directory exists because we don't want to create new files at this point
                return success;
            }


            // iterates over each string user given filename and created a
            // file in the user given directory previously created
            for (String file : files) {
                newFile = new File(path + file);
                if (!(newFile.exists() && newFile.isFile())) {
                    fileState = newFile.createNewFile();
                    if (!fileState) {
                        throw new IOException("Error: File '" + file + "' not created");
                    }
                }
            }
        } catch (IOException e) {
            success = true;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return success;

    }

    /**
     * Appends a string of text to a given file (preferably .txt)
     * @param path the path of the file to be written
     * @param text the textual content to be append to the file
     * @return the state of the operation, true if success and false otherwise
     */
    public static boolean appendToFile(String path, String text) {
        text += "\n";
        boolean success = true;
        try {
            // create new file from given path and try to open
            // return false and output error if doesn't exist
            File file = new File(path);
            if(!file.exists()) {
                throw new IOException("Error: File doesn't exist");
            }
            // write to the file
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(text);
            fileWriter.close();

        } catch(Exception e) {
            System.out.println(e);
            success = false;
        }
        // returns state of the file write
        return success;
    }

    public static ArrayList<String> readFileAsArray(String path) {
        // list that holds strings of a file
        ArrayList<String> listofStrings = new ArrayList<String>();

        BufferedReader bufferedReader;
        try {
            // load data from file
            bufferedReader = new BufferedReader(new FileReader(path));

            // read entire line as string
            String line = bufferedReader.readLine();

            // checking for end of file
            while (line != null) {
                listofStrings.add(line);
                line = bufferedReader.readLine();
            }

            // close the buffered reader
            bufferedReader.close();
            // returns file array if there were no errors
        } catch(Exception e) {
            e.printStackTrace();
        }
        // returns empty string array if there was an error
        return listofStrings;
    }

    public static boolean removeFontFromFile(String path, String text) {
        boolean removeState = true;

        try {
            // read the contents of the favFonts.txt file (possibly other)
            // and don't copy line if it equals given text
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // add line if it doesn't equal the one to remove
                    if (!line.equals(text)) {
                        content.append(line).append("\n");
                    }
                }
            }

            // overwrite the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                writer.write(content.toString());
            }
        } catch(IOException e) {
            removeState = false;
            e.printStackTrace();
        }
        return removeState;
    }
}
