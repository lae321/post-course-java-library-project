package com.nology;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
  @JsonProperty("number")
  private String number;

  @JsonProperty("title")
  private String title;

  @JsonProperty("author")
  private String author;

  @JsonProperty("genre")
  private String genre;

  @JsonProperty("subGenre")
  private String subGenre;

  @JsonProperty("publisher")
  private String publisher;

  private Boolean onLoan = false;
  private int timesLoaned = 0;

  public String getNumber() {
    return number;
  }

  public Boolean getOnLoan() {
    return onLoan;
  }

  public void setOnLoan() {
    this.onLoan = !onLoan;
  }

  public void loanBook() {
    if (!onLoan) {
      setOnLoan();
      timesLoaned++;
      System.out.println("You have loaned " + title + " by " + author + ". Happy reading!");
    }
  }

  @Override
  public String toString() {
    return "Book{"
        + "number='"
        + number
        + '\''
        + ", title='"
        + title
        + '\''
        + ", author='"
        + author
        + '\''
        + ", genre='"
        + genre
        + '\''
        + ", subGenre='"
        + subGenre
        + '\''
        + ", publisher='"
        + publisher
        + '\''
        + ", onLoan="
        + onLoan
        + ", timesLoaned="
        + timesLoaned
        + '}';
  }
}
