package com.github.tteofili.fntlearn;

/**
 *
 */
public class MatchStats {

  private TeamMatchStats houseTeamStats;
  private TeamMatchStats outTeamStats;
  private Team winner;


  public MatchStats(TeamMatchStats houseTeamStats, TeamMatchStats outTeamStats, Team winner) {
    this.houseTeamStats = houseTeamStats;
    this.outTeamStats = outTeamStats;
    this.winner = winner;
  }

  public Team getWinner() {
    return winner;
  }

  public TeamMatchStats getHouseTeamStats() {
    return houseTeamStats;
  }

  public TeamMatchStats getOutTeamStats() {
    return outTeamStats;
  }
}
