package support;

import app.config.DatabaseConfig;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DriverManagerDataSourceTest {

    DatabaseConfig config = new DatabaseConfig("jdbc:h2:mem:test_stellar", "admin", "admin");

    @Test
    public void canConnectToDatabase() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(config);
        assertThat(dataSource.getConnection(), notNullValue());
    }
}