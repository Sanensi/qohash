package sanensi.qohash.domain;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class DirEntry {
    public final Path path;
    public final long size;
    public final FileTime lastModified;

    public DirEntry(Path path, long size, FileTime fileTime) {
        this.path = path;
        this.size = size;
        this.lastModified = fileTime;
    }
}
