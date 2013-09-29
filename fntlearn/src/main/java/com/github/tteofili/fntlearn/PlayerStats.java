package com.github.tteofili.fntlearn;

/**
 *
 */
public class PlayerStats {
  private int bookings;
  private int redCards;
  private int scoredGoals;
  private int ownGoals;
  private int goalsTaken;
  private double avgRatingBasedVote;
  private double avgOverallVote;

  public int getBookings() {
    return bookings;
  }

  public void setBookings(int bookings) {
    this.bookings = bookings;
  }

  public int getRedCards() {
    return redCards;
  }

  public void setRedCards(int redCards) {
    this.redCards = redCards;
  }

  public int getScoredGoals() {
    return scoredGoals;
  }

  public void setScoredGoals(int scoredGoals) {
    this.scoredGoals = scoredGoals;
  }

  public int getOwnGoals() {
    return ownGoals;
  }

  public void setOwnGoals(int ownGoals) {
    this.ownGoals = ownGoals;
  }

  public int getGoalsTaken() {
    return goalsTaken;
  }

  public void setGoalsTaken(int goalsTaken) {
    this.goalsTaken = goalsTaken;
  }

  public double getAvgRatingBasedVote() {
    return avgRatingBasedVote;
  }

  public void setAvgRatingBasedVote(double avgRatingBasedVote) {
    this.avgRatingBasedVote = avgRatingBasedVote;
  }

  public double getAvgOverallVote() {
    return avgOverallVote;
  }

  public void setAvgOverallVote(double avgOverallVote) {
    this.avgOverallVote = avgOverallVote;
  }
}
