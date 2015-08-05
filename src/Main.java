/**
 * Created by Jenejkee on 28.07.15.
 */

import java.io.IOException;
import java.text.ParseException;

public class Main extends CommandLineParsing {
    public static void main(String[] args) throws ParseException, IOException {

        CommandLineParsing clp = new CommandLineParsing();
        String s = "";
        for (int i = 0; i<args.length; i++) {
            s += args[i];
//            System.out.println(s);
        }

        clp.fetchedRequest(s);

    }

}
