package name.voyachek.demos.nativemcp;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import name.voyachek.demos.nativemcp.db.User;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.ObjectSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/hello")
public class HelloController {

    private final ServerRuntime cayenneRuntime;

    private final static Logger LOG = LoggerFactory.getLogger(HelloController.class);

    public HelloController(ServerRuntime cayenneRuntime) {
        LOG.debug("Create HelloController, cayenneRuntime: {}",
                cayenneRuntime);

        this.cayenneRuntime = cayenneRuntime;
    }

    @Get("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse<String> hello(String name) {
        LOG.debug("hello, name: {}",
                name);

        final ObjectContext context = cayenneRuntime.newContext();

        final List<User> result = ObjectSelect.query(User.class).select(context);
        if (result.size() > 0) {
            result.get(0).setName(name);
        }

        context.commitChanges();

        return HttpResponse.ok(result.stream()
                .map(x -> MessageFormat.format("{0}.{1}", x.getObjectId(), x.getName()))
                .collect(Collectors.joining(",")));
    }

}
