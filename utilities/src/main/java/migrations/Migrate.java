package migrations;
import core.ReadProps;
import core.GetMegs;
import core.GetTrigs;
import java.io.File;
import java.sql.*;
import java.util.Collections;  
/**
 * Migrate
 */

public class Migrate {

    public ReadProps r = new ReadProps();
    public GetMegs g ;
    public GetTrigs gt;
    public Connection con;
    public boolean refresh=false;
    public  Migrate() {
    }
    public  Migrate(boolean refresh) {
        this.refresh = refresh;
    }

    public void run() {
        this.g  = new GetMegs();
        if (this.refresh) {
            this.DropTables(this.g);
        }
        for (File m : g.migrations) {
            try{  
                con=DriverManager.getConnection(r.url,r.username,r.password);  
                Statement stmt=con.createStatement();  
                String sql =g.getSql(m) ; 
                stmt.executeUpdate(sql);
                System.out.println("table " + g.getTableName(m) + " has been created");   	    
                con.close();  
            }catch(Exception e){
                System.out.println(e);
            }  
        }
    }

    public void runForTriggers() {
        this.gt = new GetTrigs();

        for (File t : gt.triggers) {
            try{  
                con=DriverManager.getConnection(r.url,r.username,r.password);  
                Statement stmt=con.createStatement();  
                if (this.refresh) {
                    String sql ="DROP TRIGGER IF EXISTS "+gt.getTriggerName(t); 
                    stmt.executeUpdate(sql);
                    System.out.println("Trigger " + gt.getTriggerName(t) + " has been deleted");   	    
                }
                String sql =gt.getSql(t) ; 
                stmt.executeUpdate(sql);
                System.out.println("Trigger " + gt.getTriggerName(t) + " has been created");   	    
                con.close();  
            }catch(Exception e){
                System.out.println(e);
            }  
        }
    }


    private void DropTables(GetMegs g) {
        Collections.reverse(g.migrations); 
        for (File m : g.migrations) {
            try{  
                con=DriverManager.getConnection(r.url,r.username,r.password);  
                Statement stmt=con.createStatement();  
                if (this.refresh) {
                    String sql ="DROP TABLE IF EXISTS "+g.getTableName(m); 
                    stmt.executeUpdate(sql);
                    System.out.println("table " + g.getTableName(m) + " has been deleted");   	    
                }
                con.close();  
            }catch(Exception e){
                System.out.println(e);
            }  
        }
        Collections.reverse(g.migrations); 
    }
}



 
            