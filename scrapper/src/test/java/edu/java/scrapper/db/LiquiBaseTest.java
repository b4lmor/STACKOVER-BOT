package edu.java.scrapper.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LiquiBaseTest extends IntegrationTest {

    @Test
    public void sampleTest() throws Exception {
        Connection connection = DriverManager.getConnection(
            POSTGRES.getJdbcUrl(),
            POSTGRES.getUsername(),
            POSTGRES.getPassword()
        );
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO links (value) VALUES ('https://sample/link')");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM links");

        String actualUrl = null;
        while (resultSet.next()) {
            actualUrl = resultSet.getString("value");
        }

        assertThat(actualUrl).isEqualTo("https://sample/link");
    }

}
