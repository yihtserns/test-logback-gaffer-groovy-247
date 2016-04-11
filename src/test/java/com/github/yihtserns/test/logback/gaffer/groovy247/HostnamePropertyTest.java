package com.github.yihtserns.test.logback.gaffer.groovy247;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.gaffer.GafferConfigurator;
import ch.qos.logback.core.ConsoleAppender;
import java.net.InetAddress;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author yihtserns
 */
public class HostnamePropertyTest {

    @Test
    public void canResolveHostnameInLogbackGroovyScript() throws Exception {
        LoggerContext context = new LoggerContext();
        GafferConfigurator configurator = new GafferConfigurator(context);
        configurator.run("import ch.qos.logback.classic.encoder.PatternLayoutEncoder\n"
                + "import ch.qos.logback.core.ConsoleAppender\n"
                + "import ch.qos.logback.classic.net.SMTPAppender\n"
                + "import ch.qos.logback.core.status.OnConsoleStatusListener\n"
                + "import ch.qos.logback.classic.PatternLayout\n"
                + "import ch.qos.logback.classic.html.HTMLLayout\n"
                + "\n"
                + "import static ch.qos.logback.classic.Level.TRACE\n"
                + "import static ch.qos.logback.classic.Level.DEBUG\n"
                + "import static ch.qos.logback.classic.Level.INFO\n"
                + "import static ch.qos.logback.classic.Level.WARN\n"
                + "import static ch.qos.logback.classic.Level.ERROR\n"
                + "\n"
                + "statusListener(OnConsoleStatusListener)\n"
                + "\n"
                + "appender(\"CONSOLE\", ConsoleAppender) {\n"
                + "    encoder(PatternLayoutEncoder) {\n"
                + "        pattern = \"%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - ${hostname} - %msg%n\"\n"
                + "    }\n"
                + "}\n"
                + "\n"
                + "logger(\"test\", INFO)\n"
                + "\n"
                + "root(ERROR, [\"CONSOLE\"])");

        Logger root = context.getLogger(Logger.ROOT_LOGGER_NAME);
        ConsoleAppender appender = (ConsoleAppender) root.getAppender("CONSOLE");
        PatternLayoutEncoder encoder = (PatternLayoutEncoder) appender.getEncoder();

        String hostname = InetAddress.getLocalHost().getHostName();
        assertEquals("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - " + hostname + " - %msg%n", encoder.getPattern());
    }
}
