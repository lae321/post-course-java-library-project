package com.nology;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class User {
  @JsonProperty("name")
  private String name;

  @JsonProperty("currentLoans")
  private ArrayList<Book> currentLoans = new ArrayList <> ();

  public User() {}

  public User(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public ArrayList<Book> getCurrentLoans() {
    return currentLoans;
  }

  public String returnBook(String bookName) {
    for (Book book : currentLoans) {
      if (currentLoans.contains(book)) {
        book.setOnLoan();
        return "Thank you for returning this book";
      }
    }
    return "You cannot return this book as you do not have it on loan";
  }

  @Override
  public String toString() {
    return "User{" + "name='" + name + '\'' + ", currentLoans=" + currentLoans + '}';
  }
}
