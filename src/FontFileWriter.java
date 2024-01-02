import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FontFileWriter {
    private FontFileWriter() {

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

    public static String[] readFileAsArray(String path) {
        // list that holds strings of a file
        List<String> listofStrings = new ArrayList<String>();
        String[] fileArray;

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
            fileArray = listofStrings.toArray(new String[0]);
            // returns file array if there were no errors
            return fileArray;
        } catch(Exception e) {
            e.printStackTrace();
        }
        // returns empty string array if there was an error
        return new String[0];
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
