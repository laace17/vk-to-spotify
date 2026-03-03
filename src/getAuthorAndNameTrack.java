import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class getAuthorAndNameTrack {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/all_tracks"));
        BufferedWriter author = new BufferedWriter(new FileWriter("src/author.txt"));
        BufferedWriter name = new BufferedWriter(new FileWriter("src/name.txt"));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

        for (int i = lines.size() - 1; i >= 0; i--) {
            String[] part = lines.get(i).split(" — ", 2);
            if (part.length == 2) {
                author.write(part[0]);
                author.newLine();
                name.write(part[1]);
                name.newLine();
            }
        }
        author.close();
        name.close();

    }
}