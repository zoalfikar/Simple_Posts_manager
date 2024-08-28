package core;

/**
 * ReadFile
 */
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.regex.Pattern;

public class ReadProps {
  public String path = "../src/main/resources/application.properties";

   public String username;
   public String password;
   public String database;
   public String url;
   public String userR ="spring.datasource.username";
   public String passwordR ="spring.datasource.password";
   public String databaseR ="spring.database.name";
   public String urlR = "spring.datasource.url";

   Pattern userPattern = Pattern.compile("^"+userR, Pattern.CASE_INSENSITIVE);
   Pattern passwordPattern = Pattern.compile("^"+passwordR, Pattern.CASE_INSENSITIVE);
   Pattern databasePattern = Pattern.compile("^"+databaseR, Pattern.CASE_INSENSITIVE);
   Pattern urlPattern = Pattern.compile("^"+urlR, Pattern.CASE_INSENSITIVE);

 public  ReadProps(){
  String urlTemp="";
  try {
    File myObj = new File(path);
    Scanner myReader = new Scanner(myObj);


    while (myReader.hasNextLine()) {
      String data = myReader.nextLine();
      if (userPattern.matcher(data).find()) {
        this.username = user(data);
      }
      if (passwordPattern.matcher(data).find()) {
        this.password = password(data);
      }
      if (urlPattern.matcher(data).find()) {
        urlTemp =data ;
      }
      if (databasePattern.matcher(data).find()) {
        this.database = database(data);
        this.url = url(urlTemp);
      }
    }
    myReader.close();
  } catch (FileNotFoundException e) {
    System.out.println("An error occurred.");
    e.printStackTrace();
  }
 }
 public String user(String u) {
  return (u.replace(this.userR, "")).replace("=", "");
 }
 public String password(String p) {
  return (p.replace(this.passwordR, "")).replace("=", "");
  
}
public String database(String d) {
  return (d.replace(this.databaseR, "")).replace("=", "");
  
}
public String url(String u) {
  return ((u.replace(this.urlR, "")).replace("=", "")).replace("${"+this.databaseR+"}", this.database);
}

    
}