package support.database;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMigrator {

    private Flyway flyway;

    public DatabaseMigrator(DataSource dataSource, String... extraLocations) {
        flyway = new Flyway();
        flyway.setDataSource(dataSource);

        List<String> locations = new ArrayList<>();
        locations.add("db/migration");
        if (extraLocations != null) {
            for (String location : extraLocations) {
                locations.add(location);
            }
        }
        flyway.setLocations(locations.toArray(new String[locations.size()]));
    }

    public void migrate() {
        flyway.migrate();
    }
}
