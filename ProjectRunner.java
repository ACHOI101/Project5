import java.io.FileNotFoundException;
import java.text.ParseException;
/**
 * Project Runner
 * 
 * @author achoi
 * @author wbogusz24
 * @version 11/19/2021
 * 
 *
 */
public class ProjectRunner {
    public static <T> void main(String[] args) {
        try {
            if (args.length == 1) {
                new DataReader<T>(args[0]);
            }
            else {
                new DataReader<T>("Cases_and_Deaths_by_race_CRDT_Sep2020.csv");

            }
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception);
        }
        catch (ParseException exception) {
            System.out.println(exception);
        }
    }
}
