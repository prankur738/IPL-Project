package io.mountblue.ipl;

public class Delivery {
    private String  matchId;
    private String extraRuns;
    private String totalRuns;
    private String battingTeam;
    private String bowlingTeam;
    private String batsman;
    private String bowler;

    private boolean isPlayerDismissed;

    public boolean isPlayerDismissed() {
        return isPlayerDismissed;
    }

    public void setPlayerDismissed(boolean playerDismissed) {
        this.isPlayerDismissed = playerDismissed;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getExtraRuns() {
        return extraRuns;
    }

    public void setExtraRuns(String extraRuns) {
        this.extraRuns = extraRuns;
    }

    public String getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(String totalRuns) {
        this.totalRuns = totalRuns;
    }

    public String getBattingTeam() {
        return battingTeam;
    }

    public void setBattingTeam(String battingTeam) {
        this.battingTeam = battingTeam;
    }

    public String getBowlingTeam() {
        return bowlingTeam;
    }

    public void setBowlingTeam(String bowlingTeam) {
        this.bowlingTeam = bowlingTeam;
    }

    public String getBatsman() {
        return batsman;
    }

    public void setBatsman(String batsman) {
        this.batsman = batsman;
    }

    public String getBowler() {
        return bowler;
    }

    public void setBowler(String bowler) {
        this.bowler = bowler;
    }
}

