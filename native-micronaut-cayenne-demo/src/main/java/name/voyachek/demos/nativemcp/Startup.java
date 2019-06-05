package name.voyachek.demos.nativemcp;

import io.micronaut.runtime.Micronaut;
import sun.misc.Signal;

public class Startup {

    public static void main(String[] args) {
        // Need for native image https://github.com/oracle/graal/issues/465
        Signal.handle(new Signal("INT"), sig -> System.exit(0));

        Micronaut.run(Startup.class, args);
    }

}
