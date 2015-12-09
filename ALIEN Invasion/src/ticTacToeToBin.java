import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author christy
 *
 */
public class ticTacToeToBin {

    /**
     * @param args
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        String line = "";
        Scanner input = new Scanner(new File("tic-tac-toe.data"));
        PrintWriter output = new PrintWriter("tic-tac-toe-stripped.data");
        
        while (input.hasNextLine()) {
            line = input.nextLine();
            
            if (!line.contains("b")) {
                
                String Str = new String(line);
                
                Str = Str.replaceAll("x,", "-1,");
                Str = Str.replaceAll("o,", "1,");
                Str = Str.replaceAll("b,", "0,");
                Str = Str.replaceAll(",positive", ",1");
                Str = Str.replaceAll(",negative", ",0");
                // Str = Str.replaceAll(",", " ");
                System.out.println(Str);
                output.println(Str);
            }
            
        }
        
        input.close();
        output.close();
        


    }

}
