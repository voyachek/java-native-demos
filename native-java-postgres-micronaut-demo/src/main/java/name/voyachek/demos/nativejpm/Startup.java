package name.voyachek.demos.nativejpm;

import io.micronaut.runtime.Micronaut;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.jdbc.SslMode;
import sun.misc.Signal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Startup {

    public static void main(String[] args) {
        // Need for native image https://github.com/oracle/graal/issues/465
        Signal.handle(new Signal("INT"), sig -> System.exit(0));

        Micronaut.run(Startup.class, args);
    }

}
