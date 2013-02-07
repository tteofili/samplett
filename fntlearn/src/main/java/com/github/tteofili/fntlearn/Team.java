package com.github.tteofili.fntlearn;

import java.util.Collection;

/**
 *
 */
public class Team {
  private Collection<Player> players;

  public Team(Collection<Player> players) {
    this.players = players;
  }

  public Collection<Player> getPlayers() {
    return players;
  }
}
