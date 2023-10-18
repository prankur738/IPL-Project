import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashMap;

public class MatchesPerYear {
    public static void main(String[] args) {
        HashMap<String, Integer> map = getMatchesPerYear();
        System.out.println("Number of matches played per year :");
        for (String year : map.keySet()) {
            int numberOfMatches = map.get(year);
            System.out.println(year + " : " + numberOfMatches);
        }
    }

    public static HashMap<String, Integer> getMatchesPerYear() {
        String path = "matches.csv";
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                int numberOfMatches = map.getOrDefault(columns[1], 0) + 1;
                map.put(columns[1], numberOfMatches);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
 