import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class ExtraRunsPerTeam {
    public static HashSet<String> getMathchesIn2016() {
        String path = "matches.csv";
        HashSet<String> set = new HashSet<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine();
            String line;
            while ((line = br.readLine()) != null){
                String[] columns = line.split(",");
                if(columns[1].equals("2016")){
                    set.add(columns[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public static HashMap<String,Integer> getExtraRunsPerTeam(){
        String path = "deliveries.csv";

        HashSet<String> set = getMathchesIn2016();
        
        HashMap<String,Integer> map = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            br.readLine();
            
            String line;
            while ((line = br.readLine()) != null) {

                String[] columns = line.split(",");

                if(set.contains(columns[0])){
                    int runs = map.getOrDefault(columns[3],0) + Integer.parseInt(columns[16]);
                    map.put(columns[3],runs);
                }
            }         
        } catch (IOException e){
            e.printStackTrace();
        }
        return map;
    }
    public static void main(String[] args) {
        HashMap<String,Integer> map = getExtraRunsPerTeam();
        System.out.println("Extra runs conceded per team for the year 2016 :");
        for(String teamName : map.keySet()){
                System.out.println(teamName + " : " + map.get(teamName));
            }
    }

}
