import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashMap;
public class MatchesWonPerTeam {
    public static HashMap<String,Integer> getNumberOfMatchesWonPerTeam(){
        HashMap<String,Integer> map = new HashMap<>();
        String path = "matches.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                int numberOfMatchesWon = map.getOrDefault(columns[10], 0) + 1;
                map.put(columns[10], numberOfMatchesWon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args){
        HashMap<String,Integer> map = getNumberOfMatchesWonPerTeam();
        System.out.println("Number of matches won of all teams over all years of IPL :");
        for(String teamName : map.keySet()){
            System.out.println(teamName+" : "+map.get(teamName));
        }
    }
}
