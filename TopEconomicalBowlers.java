
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Comparator;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class TopEconomicalBowlers {
    public static HashSet<String> getMatchesIn2015() {
        String path = "matches.csv";
        HashSet<String> set = new HashSet<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine();
            String line;
            while ((line = br.readLine()) != null){
                String[] columns = line.split(",");
                if(columns[1].equals("2015")){
                    set.add(columns[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public static HashMap<String,Pair> getBowlersData(){
        String deliveriesPath = "deliveries.csv";

        HashSet<String> set = getMatchesIn2015();
        HashMap<String,Pair> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(deliveriesPath))){
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {

                String[] columns = line.split(",");
                String matchId = columns[0];
                String bowler = columns[8];

                if(set.contains(matchId)){
                    if(map.containsKey(bowler)){
                      map.get(bowler).balls +=  1;
                      map.get(bowler).runs += Integer.parseInt(columns[17]);
                    }
                    else{
                        int runs = Integer.parseInt(columns[17]);
                        Pair p = new Pair(1,runs);
                        map.put(bowler,p);
                    }
                }

            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return map;
    }
    public static void getTopEconomicBowlers(){
        HashMap<String,Pair> map = getBowlersData();
        ArrayList<Pair2> list = new ArrayList<>();

        for(String bowlerName : map.keySet()){
            int totalBalls = map.get(bowlerName).balls;
            int totalRuns = map.get(bowlerName).runs;
            float economy = (6f*totalRuns)/totalBalls;
            list.add( new Pair2(bowlerName,economy) );
        }

        Collections.sort(list, new SortbyEconomy());
        System.out.println("Top economic bowlers for the year 2015 :");
        int rank = 0;
        for(Pair2 p : list){
            System.out.println((++rank) + ". " +p.bowlerName );
        }
    }
    public static void main(String[] args) {

        getTopEconomicBowlers();



    }

}

class SortbyEconomy implements Comparator<Pair2>{
    public int compare(Pair2 a, Pair2 b) {
        float epsilon = 0.001f; // Define a small epsilon value
        if (Math.abs(a.economy - b.economy) < epsilon) {
            return 0; // Consider them equal within the epsilon value
        } else if (a.economy < b.economy) {
            return -1;
        } else {
            return 1;
        }
    }
}
class Pair{
    int balls;
    int runs;

    Pair(int ball, int run){
        this.balls = ball;
        this.runs = run;
    }
}

class Pair2{
    String bowlerName;
    float economy;

    Pair2(String name, float eco){
        this.bowlerName = name;
        this.economy = eco;
    }
}