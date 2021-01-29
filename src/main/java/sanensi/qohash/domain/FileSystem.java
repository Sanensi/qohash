package sanensi.qohash.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileSystem {
    private final Path root;
    private final boolean recursiveSize;

    public FileSystem(Path root, boolean recursiveSize) {
        this.root = root;
        this.recursiveSize = recursiveSize;
    }

    public List<DirEntry> getDirEntries(Path path) {
        Path fullPath = root.resolve(path);

        try {
            return Files.walk(fullPath, 1)
                .filter(p -> !fullPath.equals(p))
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
                recursiveSize ? calculateFileSizeRecursively(path) : getFileSize(path),
                Files.getLastModifiedTime(path),
                Files.isDirectory(path)
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // https://stackoverflow.com/questions/33596618/how-can-i-get-a-parallel-stream-of-files-walk#comment54977780_33597291
    private long calculateFileSizeRecursively(Path path) throws IOException {
        return Files.walk(path)
            .collect(Collectors.toList())
            .parallelStream()
            .mapToLong(this::getFileSize)
            .sum();
    }

    private long getFileSize(Path path) {
        try {
            return Files.size(path);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
