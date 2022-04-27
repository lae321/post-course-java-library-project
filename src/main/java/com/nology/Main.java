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
        for (int i = 0; i < fieldNames.size(); i++) {
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
    System.out.println(userList);
  }

  public static void userListToJson() throws IOException {
    writer.writeValue(new File("src/user_data.json"), userList);
  }

  public static void checkUser(String existingUser) {
    for (User user : userList ) {
      if (user.getName().equals(existingUser)) {
        System.out.println("Welcome " + existingUser + " press any key to continue.");
        return;
      }
    }
    System.out.println("User " + existingUser + " not found. Please check you entered the " +
       "correct username or create a new account. ");
    System.out.println("Press any key to continue.");
  }

  public static void createUser(String newUser) throws IOException {
    for (User user : userList ) {
      if (user.getName().equals(newUser)) {
        System.out.println("Sorry, the username " + newUser + " is already taken. Please choose " +
          "a different username.");
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

  public static void main(String[] args) throws IOException {
    bookCsvToJson();
    bookJsonToList();
    userJsonToList();
    userListToJson();
    login();
  }

  /*
  TODO:
    Login functions:
      existingUser() - takes entered username and presents options including view loans and
      loan book. If username entered is admin, the user is prompted for the admin password.
      Admin options are presented following authentication including report.
      newUser() - takes entered username and checks whether it already exists in the users List
      . If not, the user is created.
  */

}
