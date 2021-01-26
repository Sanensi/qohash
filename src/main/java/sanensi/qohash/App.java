package sanensi.qohash;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "qohash")
public class App implements Callable<Integer> {
    @CommandLine.Parameters(paramLabel = "ROOT", description = "source folder from which the app is served")
    String root;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean help;

    @Override
    public Integer call() {


        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
