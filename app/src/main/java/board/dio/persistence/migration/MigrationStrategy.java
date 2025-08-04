package board.dio.persistence.migration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import static board.dio.persistence.config.ConnectionConfig.getConnection;

public class MigrationStrategy {

    public void executeMigration() {
        // Guarda os prints padrão
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        try (FileOutputStream fos = new FileOutputStream("liquibase.log")) {
            // Redireciona saída para o log
            System.setOut(new PrintStream(fos));
            System.setErr(new PrintStream(fos));

            // Cria conexão JDBC e executa o Liquibase
            try (Connection connection = getConnection()) {
                JdbcConnection jdbcConnection = new JdbcConnection(connection);

                try (Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.yml", // Caminho relativo ao classpath
                    new ClassLoaderResourceAccessor(),
                    jdbcConnection
                )) {
                    liquibase.update(); // Aplica as migrações
                }

                connection.commit();
            } catch (SQLException | LiquibaseException e) {
                e.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Restaura saída padrão
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }
}
