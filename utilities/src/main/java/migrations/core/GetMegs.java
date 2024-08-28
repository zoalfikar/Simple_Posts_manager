package core;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class GetMegs {
    public List<File> migrations;
    String p = "../utilities/src/main/java/migrations/tables" ;
    public GetMegs (){
        try {
            this.migrations = Files.list(Paths.get(p))
            .map(Path::toFile)
            .filter(File::isFile)
            .filter(GetMegs::isMigration)
            .collect(Collectors.toList());
            if (migrations.size() > 1) {
                Collections.sort(this.migrations,  new Comparator<File>() {
                    public int compare (File f1, File f2) {
                        try {
                            FileTime creationTime1 =(FileTime) Files.getAttribute(f1.toPath(), "creationTime");
                            FileTime creationTime2 = (FileTime) Files.getAttribute(f2.toPath(), "creationTime");
                            return creationTime1.compareTo(creationTime2);
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                        }
                        return 2 - 1;
                    }
                });
              }
              

          } catch (IOException e) {
            e.printStackTrace();
          }
    }
    public static boolean isMigration(File p){
        Pattern MPattern = Pattern.compile(".migration$", Pattern.CASE_INSENSITIVE);
        if (MPattern.matcher(p.toString()).find()) {
            return true;
        } else {
            return false;            
        }          
    }
    public String getFileName(File file) {
        return file.getName() ;
        
    }
    public String getTableName(File f) {
        String name = this.getFileName(f).toLowerCase();
        return name.replace(".migration" , "") ;
    }
    public  String getSql(File f) {
    try{
        return new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
    } catch (IOException e) {
        System.out.println("An error occurred.");
    }
    return "not found";
    }
}
