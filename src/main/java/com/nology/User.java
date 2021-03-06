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

  public void printCurrentLoans() {
    if (currentLoans.size() > 0) {
      System.out.println("Your current loans are:");
      for (Book book : currentLoans ) {
        System.out.println("Number: " + book.getNumber() + " - " + book.getTitle() + " by " + book.getAuthor() + ".");
      }
      System.out.println(" ");
    } else {
      System.out.println("You currently have no books on loan.");
    }
  }

  @Override
  public String toString() {
    return "User{" + "name='" + name + '\'' + ", currentLoans=" + currentLoans + '}';
  }
}
