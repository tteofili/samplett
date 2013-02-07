package com.github.tteofili.fntlearn;

import java.util.Map;

/**
 *
 */
public class TeamMatchStats {
  private Team team;
  private Map<Player, Double> playersStats;
  private String disposition;

  public TeamMatchStats(Team team, Map<Player, Double> playersStats, String disposition) {
    this.team = team;
    this.playersStats = playersStats;
    this.disposition = disposition;
  }

  public String getDisposition() {
    return disposition;
  }


  public Team getTeam() {
    return team;
  }

  public Map<Player, Double> getPlayersStats() {
    return playersStats;
  }
}
