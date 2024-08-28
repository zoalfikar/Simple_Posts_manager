import java.sql.*;
import java.time.LocalDateTime;

import migrations.Migrate;
import migrations.Seeder;
import java.util.regex.Pattern;

public class UtilitiesM {
    public static void main(String[] args) {
        if (args.length > 0) {
            boolean CF = false;
            String command = args[0].toUpperCase();
            Pattern seed = Pattern.compile("^SEED:", Pattern.CASE_INSENSITIVE);

            if (command.equals("MIGRATE")) {
                Migrate m = new Migrate();
                m.run();
                CF=true;

            }
            if (command.equals("MIGRATE:REFRESH")) {
                Migrate m = new Migrate(true);
                m.run();
                CF=true;
            }
            if (command.equals("MIGRATE:TRIGGERS")) {
                Migrate m = new Migrate(true);
                m.runForTriggers();
                CF=true;
            }

            if (seed.matcher(command).find()) {
                Seeder s = new Seeder(command);
                CF=true;
            }
            if (!CF) {
                System.out.println("command not found") ;
            }
        }

    }
}
