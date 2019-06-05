package name.voyachek.demos.nativejpm;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller("/hello")
public class HelloController {

    private final DataSource dataSource;

    private final static Logger LOG = LoggerFactory.getLogger(HelloController.class);

    public HelloController(DataSource dataSource) {
        LOG.debug("Create HelloController, dataSource: {}",
                dataSource);

        this.dataSource = dataSource;
    }

    @Get("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse<String> hello(String name) throws SQLException {
        LOG.debug("hello, name: {}",
                name);


        final StringBuilder builder = new StringBuilder();
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM \"public\".\"user\"");
        ) {
            while (rs.next()) {
                builder.append("ID: ").append(rs.getLong("id"));
                builder.append(", Name: ").append(rs.getString("name"));
            }
        }

        return HttpResponse.ok(builder.toString());
    }

}
