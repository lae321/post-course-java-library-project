package com.nology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Main {
  static Scanner scanner = new Scanner(System.in);

  public static void login() {
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
        // checkUser(existingUser);
        break;
      case "2":
        System.out.println("Hello, new user.");
        System.out.println("Please enter your desired username below.");
        System.out.println("Username:");
        String newUser = scanner.nextLine();
        // createUser(newUser);
        System.out.println("Success! A new account for " + newUser + " has been created.");
        System.out.println("Press any key to return to the login page.");
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
      ObjectMapper mapper = new ObjectMapper();
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      mapper.writeValue(new File("src/books_data.json"), list);

      List<Book> bookList =
          mapper.readValue(
              new File(
                  "C:\\Users\\lae32\\Desktop\\sandbox\\java-library-project\\src"
                      + "\\books_data.json"),
              new TypeReference<>() {});
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
    login();
  }
}
