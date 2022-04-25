package com.nology;

public class Book {
  private int number;
  private String title;
  private String author;
  private String genre;
  private String subGenre;
  private String publisher;
  private Boolean onLoan = false;
  private int timesLoaned = 0;

  public Book(
      int number, String title, String author, String genre, String subGenre, String publisher) {
    this.number = number;
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.subGenre = subGenre;
    this.publisher = publisher;
  }

  public void setOnLoan() {
    this.onLoan = !onLoan;
  }

  public String loanBook() {
    if (!onLoan) {
      setOnLoan();
      timesLoaned++;
      return "You have loaned this book";
    }
    return "Sorry, this book is already on loan";
  }
}
