package sanensi.qohash;

import picocli.CommandLine;
import sanensi.qohash.domain.DirEntry;
import sanensi.qohash.domain.FileSystem;

import java.nio.file.Path;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "qohash")
public class App implements Callable<Integer> {
    @CommandLine.Parameters(
        paramLabel = "ROOT",
        description = "source folder from which the app is served",
        defaultValue = ""
    )
    Path root;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean help;

    @Override
    public Integer call() {
        display();

        return 0;
    }

    private void display() {
        List<DirEntry> dirEntries = FileSystem.getDirEntries(root);
        long totalSize = dirEntries.stream().mapToLong(e -> e.size).sum();

        System.out.println("\n" + root.toAbsolutePath());
        System.out.println("Number of entries: " + dirEntries.size());
        System.out.println("Total size: " + totalSize + " bytes\n");
        dirEntries.forEach(e -> System.out.println(this.formatDirEntry(e)));
    }

    private String formatDirEntry(DirEntry e) {
        DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.systemDefault());

        return String.format("%8d  %s %s", e.size, formatter.format(e.lastModified.toInstant()), e.path);
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
