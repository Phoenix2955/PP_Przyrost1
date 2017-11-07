package example;
import java.io.*;


public class SaveFile{

    public static void save(String zad, String query) throws IOException{
    FileWriter file = new FileWriter("Odp.txt", true);
    BufferedWriter out = new BufferedWriter(file);
        out.write("Zadanie nr: " + zad + " | Odpowiedź: " + query + "\n\r");
        out.close();

    }
}