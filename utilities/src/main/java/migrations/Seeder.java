package migrations;

import core.ReadProps;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.Iterator;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.sql.*;  

public class Seeder {
    public ReadProps r = new ReadProps();
    String seedname;
    JSONParser parser = new JSONParser();
    String path = "../utilities/src/main/java/migrations/seeds/";
    Connection con;
    public Seeder(String seedname){
        this.seedname=seedname.replace("SEED:", ""); 
        JSONParser parser = new JSONParser();
        try {
           Object seedO = parser.parse(new FileReader(path+this.seedname+".json"));
           JSONObject seed = (JSONObject)seedO;
           con=DriverManager.getConnection(r.url,r.username,r.password);  
           Statement stmt=con.createStatement();  
           for (Object table : seed.keySet()) {
            String tableName = (String)table;
            String sqlLeft ;
            String sqlRight;
            String sql;
            JSONArray  array = (JSONArray) seed.get(tableName);
            for (Object row : array)
            {
                sqlLeft ="INSERT INTO "+tableName+" (";
                sqlRight = " ) VALUES (";
                sql = "";
                JSONObject r = (JSONObject)row;
                for (Object column : r.keySet()) {
                    sqlLeft =sqlLeft + (String)column + " ," ;
                    if(r.get((String)column ) instanceof java.lang.String) {
                        sqlRight =sqlRight + " '"+ r.get((String)column ) + "' ," ;
                    } else {
                        sqlRight =sqlRight + r.get((String)column ) + " ," ;
                    }
                    System.out.println("insert " +(String)column +" = "+ r.get((String)column) + " into " + tableName);
                }
                sql = sqlLeft.substring(0,sqlLeft.length() - 1) + sqlRight.substring(0,sqlRight.length() - 1) + ")";
                stmt.executeUpdate(sql);
                System.out.println("data inserted");
            }
        }
        } catch(Exception e) {
           e.printStackTrace();
        }
    }
}
