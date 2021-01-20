package eg.edu.alexu.csd.oop.db.cs58;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        //BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String query = br.readLine();
            Parser parser = new Parser();
            parser.gotoDatabase(query);
        }

    }

}
