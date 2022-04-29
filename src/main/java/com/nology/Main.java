package com.nology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Main {
  static Scanner scanner = new Scanner(System.in);
  static ObjectMapper mapper = new ObjectMapper();
  static ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
  static List<Book> bookList = new ArrayList<>();
  static List<User> userList = new ArrayList<>();

  public static void bookCsvToJson() throws IOException {
    String csvFile = "C:\\Users\\lae32\\Desktop\\sandbox\\java-library-project\\books_data.csv";

    try (InputStream in = new FileInputStream(csvFile)) {
      CSV csv = new CSV(true, ',', in);
      List<String> fieldNames = null;
      if (csv.hasNext()) fieldNames = new ArrayList<>(csv.next());
      List<Map<String, String>> list = new ArrayList<>();
      while (csv.hasNext()) {
        List<String> x = csv.next();
        Map<String, String> obj = new LinkedHashMap<>();
        for (int i = 0; i < Objects.requireNonNull(fieldNames).size(); i++) {
          obj.put(fieldNames.get(i), x.get(i));
        }
        list.add(obj);
      }

      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      mapper.writeValue(new File("src/books_data.json"), list);
    }
  }

  public static void bookJsonToList() throws IOException {
    bookList =
        mapper.readValue(
            new File(
                "C:\\Users\\lae32\\Desktop\\sandbox\\java-library-project\\src"
                    + "\\books_data.json"),
            new TypeReference<>() {});
  }

  public static void userJsonToList() throws IOException {
    userList =
        mapper.readValue(
            new File(
                "C:\\Users\\lae32\\Desktop\\sandbox\\java-library-project\\src"
                    + "\\user_data.json"),
            new TypeReference<>() {});
  }

  public static void userListToJson() throws IOException {
    writer.writeValue(new File("src/user_data.json"), userList);
  }

  public static void checkUser(String existingUser) throws IOException {
    for (User user : userList) {
      if (user.getName().equals(existingUser)) {
        System.out.println("Welcome " + existingUser + ". Press any key to continue.");
        scanner.nextLine();
        userOptions(existingUser);
        return;
      }
    }
    System.out.println(
        "User "
            + existingUser
            + " not found. Please check you entered the "
            + "correct username or create a new account. ");
    System.out.println("Press any key to continue.");
    login();
  }

  public static void createUser(String newUser) throws IOException {
    for (User user : userList) {
      if (user.getName().equals(newUser)) {
        System.out.println(
            "Sorry, the username "
                + newUser
                + " is already taken. Please choose "
                + "a different username.");
        return;
      }
    }
    User user = new User(newUser);
    userList.add(user);
    userListToJson();
    System.out.println("Success! A new account for " + newUser + " has been created.");
    System.out.println("Press any key to return to the login page.");
  }

  public static void login() throws IOException {
//    String testNum = scanner.nextLine();
//    for (Book book : bookList ) {
//      System.out.println(book.getNumber());
//      if (testNum.equals(book.getNumber())) {
//        System.out.println("This works m8");
//      }
//    }


    System.out.println(
        "Welcome to the Library System. Press 1 to login using an existing "
            + "account. Press 2 to create a new account."
            + " ");
    String loginOrSignup = scanner.nextLine();

    switch (loginOrSignup) {
      case "1":
        System.out.println("Hello, existing user.");
        System.out.println("Please enter your username below.");
        System.out.println("Username:");
        String existingUser = scanner.nextLine();
        checkUser(existingUser);
        break;
      case "2":
        System.out.println("Hello, new user.");
        System.out.println("Please enter your desired username below.");
        System.out.println("Username:");
        String newUser = scanner.nextLine();
        createUser(newUser);
        scanner.nextLine();
        login();
        break;
      default:
        System.out.println("Invalid input. Press any key to return to the welcome page.");
        scanner.nextLine();
        login();
    }
  }

  public static void loanBook(String existingUser) throws IOException {
    System.out.println("Loan a Book.");
    System.out.println("Press any key to view our available books.");
    scanner.nextLine();
    for (Book book : bookList) {
      if (!book.getOnLoan()) {
        System.out.println(book);
      }
    }
    System.out.println(
        "To loan a book, enter the book's number below. Or type x to return to the"
            + " User Options page");
    System.out.println("Book number:");
    String bookOrExit = scanner.nextLine();

    if (bookOrExit.equals("x")) {
      userOptions(existingUser);
    } else {
      for (Book book : bookList) {
        if (bookOrExit.equals(book.getNumber())) {
          book.loanBook();
          for (User user : userList) {
            if (user.getName().equals(existingUser)) {
              user.getCurrentLoans().add(book);
              break;
            }
          }
          bookJsonToList();
          userListToJson();
          userJsonToList();
          break;
        }
      }
      System.out.println("Invalid input. Press any key to return to the Loan Book page.");
      scanner.nextLine();
      loanBook(existingUser);
    }
  }

  public static void userOptions(String existingUser) throws IOException {
    System.out.println("Hello, " + existingUser + ".");
    System.out.println("Press 1 to view your Current Loans.");
    System.out.println("Press 2 to Loan a Book");
    System.out.println("Press 3 to logout.");
    String userOption = scanner.nextLine();

    switch (userOption) {
      case "1":
        for (User user : userList) {
          if (user.getName().equals(existingUser)) {
            System.out.println(user.getCurrentLoans());
          }
        }
        break;
      case "2":
        loanBook(existingUser);
        break;
      case "3":
        System.out.println("Thank you for using the Library System.");
        System.out.println("Press any key to continue.");
        scanner.nextLine();
        break;
      default:
        System.out.println("Invalid input. Press any key to return to the User Options page");
        scanner.nextLine();
        userOptions(existingUser);
    }
  }

  public static void main(String[] args) throws IOException {
    bookCsvToJson();
    bookJsonToList();
    userJsonToList();
    userListToJson();
    login();
  }

  /*
  TODO:
    Print a message if the user has no books loaned and allow them to return to the User Options
    page.


  */

}
