package name.voyachek.demos.nativejp;

import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.jdbc.SslMode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Startup {

    public static void main(String[] args)
            throws SQLException {
        final PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl("jdbc:postgresql://localhost/demo_nativem");
        ds.setUser("test");
        ds.setPassword("test");
        ds.setSslMode(SslMode.DISABLE.value);

        try (
                Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM \"public\".\"user\"");
        ) {
            while (rs.next()) {
                System.out.print("ID: " + rs.getLong("id"));
                System.out.println(", Name: " + rs.getString("name"));
            }
        }
    }

}
