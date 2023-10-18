import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MaxWicketsTaken {
    public static void main(String[] args){
        getMaximumWickets();
    }

    public static void getMaximumWickets(){
        String path = "deliveries.csv";
        HashMap<String,Integer> map = new HashMap<>();

        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            br.readLine();
            String line;
            while((line = br.readLine()) != null){
                String[] columns = line.split(",");
//                String playerDismissed = columns[18];
                if(columns.length > 18){
                    String bowler = columns[8];
                    int wicketsTaken = map.getOrDefault(bowler,0) + 1;
                    map.put(bowler,wicketsTaken);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        ArrayList<BowlerWicketsPair> list = new ArrayList<>();
        for(String bowler : map.keySet()){
            list.add(new BowlerWicketsPair(bowler,map.get(bowler)));
        }
        Collections.sort(list, new SortByWickets());
        System.out.println("Maximum wickets taken by bowler : ");
        for(BowlerWicketsPair a : list){
            System.out.println(a.bowler+" : "+a.wickets);
        }
    }
}

class BowlerWicketsPair{
    String bowler;
    int wickets;

    BowlerWicketsPair(String b, int w){
        this.bowler = b;
        this.wickets = w;
    }
}

class SortByWickets implements Comparator<BowlerWicketsPair>{
    public int compare(BowlerWicketsPair a, BowlerWicketsPair b){
        return (b.wickets - a.wickets);
    }
}
