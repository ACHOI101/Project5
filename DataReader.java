import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Scanner;

public class DataReader {
    private LinkedList<Data> listDC;
    private LinkedList<Data> listVA;
    private LinkedList<Data> listMD;
    private LinkedList<Data> listGA;
    private LinkedList<Data> listNC;
    private LinkedList<Data> listTN;
    private LinkedList<Data> ethnicity;

    /**
     * 
     * @param path
     * @throws FileNotFoundException
     * @throws ParseException
     */
    public DataReader(String path) throws FileNotFoundException, ParseException {
        //Read in the data from the provided path
        this.readData(path);

        //Launch GUI from here given the 6 or 7 lists
    }

    /**
     * 
     * @param path
     * @throws FileNotFoundException
     */
    private void readData(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scan = new Scanner(file);

        // Pull the string headers out
        String header = scan.nextLine();

        // For each for in the data, zero indexed...
        for (int row = 0; row < 6; row++) {
            // Line holds the line of raw text
            String line = scan.nextLine();
            String[] elements = line.split(" ");
            String[] races = new String[] { "White", "Black", "LatinX", "Asian", "Other" };
            String state = elements[0];

            // Parse out state to determine which list to write to
            // Element 0 of the list is the two letter state code, e.g. "VA"
            switch (state) {
                case "DC":
                    return;
                case "GA":
                    return;
                case "MD":
                    return;
                case "NC":
                    return;
                case "TN":
                    return;
                case "VA":
                    return;
            }

            /*
             * For each race, create a Data Object and append it to the list
             * 0 1 2 3 4 5 6 7 8 9 10
             * State White Black LatinX Asian Other White Black LatinX Asian Other
             * DC 70678 179563 97118 5407 108784 1924 13365 2269 254 170
             * 
             * Index = 0
             * cases = 1
             * deaths = 6
             */
            for (int index = 0; index < 5; index++) {
                int cases = Integer.parseInt(elements[index + 1]);
                int deaths = Integer.parseInt(elements[index + 6]);
                String race = races[index];
                // String state exists
                //List.append(new Data(state, race, cases, deaths))
            }
        }
        scan.close();
    }
}
