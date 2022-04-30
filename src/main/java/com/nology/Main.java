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

  public static void bookListToJson() throws IOException {
    writer.writeValue(new File("src/books_data.json"), bookList);
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
        System.out.println("Welcome " + existingUser + ". Press Enter to continue.");
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
    System.out.println("Press Enter to continue.");
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
    System.out.println("Press Enter to return to the login page.");
  }

  public static void login() throws IOException {
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
        System.out.println("Invalid input. Press Enter to return to the Welcome page.");
        scanner.nextLine();
        login();
    }
  }

  public static void loanBook(String existingUser) throws IOException {
    System.out.println("Loan a Book.");
    System.out.println("Press Enter to view our available books.");
    scanner.nextLine();
    for (Book book : bookList) {
      if (!book.getOnLoan()) {
        System.out.println(
            "Number: "
                + book.getNumber()
                + " - "
                + book.getTitle()
                + " by "
                + book.getAuthor()
                + ". Genre: "
                + book.getGenre()
                + ", Sub-genre: "
                + book.getSubGenre()
                + ". Publisher: "
                + book.getPublisher()
                + ".");
      }
    }
    System.out.println(" ");
    System.out.println(
        "To loan a book, enter the book's Number below. Or enter X to return to the"
            + " User Options page.");
    System.out.println("Book number:");
    String bookOrExit = scanner.nextLine();

    if (bookOrExit.equals("x") || bookOrExit.equals("X")) {
      userOptions(existingUser);
      return;
    }

    for (Book book : bookList) {
      if (bookOrExit.equals(book.getNumber()) && !book.getOnLoan()) {
        book.loanBook();
        for (User user : userList) {
          if (user.getName().equals(existingUser)) {
            user.getCurrentLoans().add(book);
            bookListToJson();
            bookJsonToList();
            userListToJson();
            userJsonToList();
            return;
          }
        }
      }
    }
    System.out.println("Invalid input. Press Enter to return to the Loan a Book page.");
    scanner.nextLine();
    loanBook(existingUser);
  }

  public static void returnBook(String existingUser) throws IOException {
    System.out.println("Please enter the Number of the book you would like to return.");
    System.out.println("Book number:");
    String bookNumber = scanner.nextLine();
    for (User user : userList) {
      if (user.getName().equals(existingUser)) {
        for (Book book : bookList) {
          if (bookNumber.equals(book.getNumber())) {
            for (Book userBook : user.getCurrentLoans()) {
              if (userBook.getNumber().equals(bookNumber)) {
                user.getCurrentLoans().remove(userBook);
                book.setOnLoan();
                System.out.println(
                    "Thank you for returning " + book.getTitle() + " by " + book.getAuthor() + ".");
                break;
              }
            }
            bookListToJson();
            bookJsonToList();
            userListToJson();
            userJsonToList();
            System.out.println("Press Enter to return to the User Options page.");
            scanner.nextLine();
            userOptions(existingUser);
          }
        }
      }
    }
  }

  public static void userOptions(String existingUser) throws IOException {
    System.out.println("Hello, " + existingUser + ".");
    System.out.println("Press 1 to view your Current Loans.");
    System.out.println("Press 2 to Loan a Book");
    System.out.println("Press 3 to Logout.");
    String userOption = scanner.nextLine();

    switch (userOption) {
      case "1":
        for (User user : userList) {
          if (user.getName().equals(existingUser)) {
            user.printCurrentLoans();
            if (user.getCurrentLoans().size() > 0) {
              System.out.println(
                  "Press 1 to return to the User Options page or press 2 to Return a book.");
              String exitOrReturnBook = scanner.nextLine();
              switch (exitOrReturnBook) {
                case "1":
                  userOptions(existingUser);
                  break;
                case "2":
                  returnBook(existingUser);
                  break;
              }
            } else {
              System.out.println(" ");
              System.out.println("Press Enter to return to the User Options page.");
              scanner.nextLine();
              userOptions(existingUser);
            }
          }
        }
        break;
      case "2":
        loanBook(existingUser);
        System.out.println("Press Enter to return to the User Options Page");
        scanner.nextLine();
        userOptions(existingUser);
        break;
      case "3":
        System.out.println("Thank you for using the Library System.");
        System.out.println("Press Enter to continue.");
        scanner.nextLine();
        login();
        break;
      default:
        System.out.println("Invalid input. Press Enter to return to the User Options page.");
        scanner.nextLine();
        userOptions(existingUser);
    }
  }

  public static void main(String[] args) throws IOException {
    // bookCsvToJson();
    bookJsonToList();
    bookListToJson();
    userJsonToList();
    userListToJson();
    login();
  }

  /*
  TODO:
    Admin password.
    Admin report method.
  */

}
