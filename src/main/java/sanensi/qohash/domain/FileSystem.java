package sanensi.qohash.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileSystem {
    private final Path root;

    public FileSystem(Path root) {
        this.root = root;
    }

    public List<DirEntry> getDirEntries(Path path) {
        try {
            return Files.walk(root.resolve(path), 1)
                .filter(p -> !path.equals(p))
                .map(this::toDirEntry)
                .sorted(Comparator.comparingLong((DirEntry e) -> e.size).reversed())
                .collect(Collectors.toList());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DirEntry toDirEntry(Path path) {
        try {
            return new DirEntry(
                root.relativize(path),
                Files.size(path),
                Files.getLastModifiedTime(path),
                Files.isDirectory(path)
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
