package edu.java.scrapper.db;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class IntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");
        POSTGRES.start();
        try {
            runMigrations(POSTGRES);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void runMigrations(JdbcDatabaseContainer<?> c) throws Exception {
        Connection connection = POSTGRES.createConnection("");

        Database database = DatabaseFactory.getInstance()
            .findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Path changeLogPath = new File(".").toPath()
            .toAbsolutePath()
            .getParent()
            .getParent()
            .resolve("database/migrations");
        ResourceAccessor resourceAccessor = new DirectoryResourceAccessor(changeLogPath);
        Liquibase liquibase = new Liquibase("master.xml", resourceAccessor, database);
        liquibase.update(new Contexts(), new LabelExpression());
    }
}
