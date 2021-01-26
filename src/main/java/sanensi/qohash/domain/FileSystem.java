package sanensi.qohash.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileSystem {
    public static List<DirEntry> getDirEntries(Path path) {
        try {
            return Files.walk(path, 1)
                .filter(p -> !path.equals(p))
                .map(FileSystem::toDirEntry)
                .sorted(Comparator.comparingLong((DirEntry e) -> e.size).reversed())
                .collect(Collectors.toList());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static DirEntry toDirEntry(Path path) {
        try {
            return new DirEntry(
                path,
                Files.size(path),
                Files.getLastModifiedTime(path)
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
