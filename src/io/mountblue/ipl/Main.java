package io.mountblue.ipl;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static final int MATCH_ID = 0;//matches and deliveries
    public static final int MATCH_YEAR = 1;//matches
    public static final int MATCH_WINNER = 10;//matches
    public static final int BATTING_TEAM = 2;//deliveries
    public static final int BOWLING_TEAM = 3;//deliveries
    public static final int BATSMAN = 6;//deliveries
    public static final int BOWLER = 8;//deliveries
    public static final int EXTRA_RUNS = 16;//deliveries
    public static final int TOTAL_RUNS = 17;//deliveries
    public static final int PLAYER_DISMISSED = 18;//deliveries

    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();

        findNumberOfMatchesPlayedPerYear(matches);
        findNumberOfMatchesWonOfAllTeamsOverAllTheYears(matches);
        findExtraRunsConcededPerTeamIn2016(matches, deliveries);
        findTheTopEconomicBowlersIn2015(matches,deliveries);
        findTheTopBowlersWithMaximumWicketsTakenOverAllTheYears(deliveries);
    }

    private static List<Match> getMatchesData(){
        List<Match> matches = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader("./data/matches.csv"))){
            reader.readLine(); //reading the table header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Match match = new Match();
                match.setId(data[MATCH_ID]);
                match.setYear(data[MATCH_YEAR]);
                match.setWinner(data[MATCH_WINNER]);

                matches.add(match);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return matches;
    }

    private static List<Delivery> getDeliveriesData(){
        List<Delivery> deliveries = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader("./data/deliveries.csv"))) {
            reader.readLine(); //reading the table header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Delivery delivery = new Delivery();
                delivery.setMatchId(data[MATCH_ID]);
                delivery.setBattingTeam(data[BATTING_TEAM]);
                delivery.setBowlingTeam(data[BOWLING_TEAM]);
                delivery.setBatsman(data[BATSMAN]);
                delivery.setBowler(data[BOWLER]);
                delivery.setExtraRuns(data[EXTRA_RUNS]);
                delivery.setTotalRuns(data[TOTAL_RUNS]);
                delivery.setPlayerDismissed(data.length>PLAYER_DISMISSED);

                deliveries.add(delivery);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return deliveries;
    }

    private static void findNumberOfMatchesPlayedPerYear(List<Match> matches){
        TreeMap<String, Integer> matchesPlayedPerYear = new TreeMap<>();

        for(Match match : matches){
            int numberOfMatches = matchesPlayedPerYear.getOrDefault(match.getYear(), 0) + 1;
            matchesPlayedPerYear.put(match.getYear(), numberOfMatches);
        }

        for(String year : matchesPlayedPerYear.keySet()){
            System.out.println(year + " " + matchesPlayedPerYear.get(year));
        }
    }

    private static void findNumberOfMatchesWonOfAllTeamsOverAllTheYears(List<Match> matches) {
        HashMap<String,Integer> matchesWonPerTeam = new HashMap<>();

        for(Match match : matches) {
            int numberOfMatchesWon = matchesWonPerTeam.getOrDefault(match.getWinner(), 0) + 1;
            matchesWonPerTeam.put(match.getWinner(), numberOfMatchesWon);
        }

        for(String team : matchesWonPerTeam.keySet()){
            if(team.isEmpty())
                continue;

            System.out.println(team + " " + matchesWonPerTeam.get(team));
        }
    }

    private static void findExtraRunsConcededPerTeamIn2016(List<Match> matches, List<Delivery> deliveries) {
        HashSet<String> matchesIn2016 = new HashSet<>();
        for(Match match : matches){
            if(match.getYear().equals("2016")){
                matchesIn2016.add(match.getId());
            }
        }

        HashMap<String,Integer> extraRunsPerTeam = new HashMap<>();//for year 2016
        for(Delivery delivery : deliveries) {
            if(matchesIn2016.contains(delivery.getMatchId())) {
                int extraRunsConceded = extraRunsPerTeam.getOrDefault(delivery.getBattingTeam(),0) +
                        Integer.parseInt(delivery.getExtraRuns());
                extraRunsPerTeam.put(delivery.getBattingTeam(),extraRunsConceded);
            }
        }

        for(String team : extraRunsPerTeam.keySet()){
            System.out.println(team + " " + extraRunsPerTeam.get(team));
        }
    }

    private static void findTheTopEconomicBowlersIn2015(List<Match> matches, List<Delivery> deliveries) {
        HashSet<String> matchesIn2015 = new HashSet<>();
        for(Match match : matches){
            if(match.getYear().equals("2015")){
                matchesIn2015.add(match.getId());
            }
        }
        HashMap<String, int[]> runsAndBallsPerBowler = new HashMap<>();//for year 2015
        for(Delivery delivery : deliveries){
            if(matchesIn2015.contains(delivery.getMatchId())){
                String bowlerName = delivery.getBowler();
                if(runsAndBallsPerBowler.containsKey(bowlerName)){
                    runsAndBallsPerBowler.get(bowlerName)[0] += Integer.parseInt(delivery.getTotalRuns());
                    runsAndBallsPerBowler.get(bowlerName)[1]++;
                }
                else{
                    int[] runsAndBalls = new int[2];
                    runsAndBalls[0] = Integer.parseInt(delivery.getTotalRuns());
                    runsAndBalls[1] = 1;
                    runsAndBallsPerBowler.put(bowlerName,runsAndBalls);
                }
            }
        }

        HashMap<String, Float> economyPerBowler = new HashMap<>();
        for(String bowler : runsAndBallsPerBowler.keySet()){
            float economy = (6f*runsAndBallsPerBowler.get(bowler)[0]) / (runsAndBallsPerBowler.get(bowler)[1]);
            economyPerBowler.put(bowler, economy);
        }
        runsAndBallsPerBowler.clear();

        List<Map.Entry<String, Float>> bowlerEconomyList = new ArrayList<>(economyPerBowler.entrySet());

        // Sort the bowlerEconomyList using the custom Comparator
        bowlerEconomyList.sort((b1, b2) -> Float.compare(b1.getValue(), b2.getValue()));

        for( Map.Entry<String, Float> economyOfBowler : bowlerEconomyList){
            System.out.printf("%s %.2f\n" ,economyOfBowler.getKey(),economyOfBowler.getValue() );
        }
    }

    private static void findTheTopBowlersWithMaximumWicketsTakenOverAllTheYears(List<Delivery> deliveries) {
        HashMap<String,Integer> wicketsTakenPerBowler = new HashMap<>();

        for(Delivery delivery : deliveries) {
            if(delivery.isPlayerDismissed()) {
                int wicketsTaken = wicketsTakenPerBowler.getOrDefault(delivery.getBowler(),0) + 1;
                wicketsTakenPerBowler.put(delivery.getBowler(), wicketsTaken);
            }
        }

        List<Map.Entry<String, Integer>> wicketsPerBowlerList = new ArrayList<>(wicketsTakenPerBowler.entrySet());
        wicketsPerBowlerList.sort( (b1,b2) -> b2.getValue()-b1.getValue());

        for( Map.Entry<String, Integer> wicketsByBowler : wicketsPerBowlerList){
            System.out.println(wicketsByBowler.getKey()+" "+wicketsByBowler.getValue());
        }
    }

}