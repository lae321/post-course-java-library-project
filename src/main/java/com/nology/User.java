package com.nology;

import java.util.ArrayList;

public class User {
  private String name;
  private ArrayList<Book> currentLoans;

  public User(String name, ArrayList<Book> currentLoans) {
    this.name = name;
    this.currentLoans = currentLoans;
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
}
