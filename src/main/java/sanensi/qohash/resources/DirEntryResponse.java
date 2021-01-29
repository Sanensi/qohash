package sanensi.qohash.resources;

public class DirEntryResponse {
    public String path;
    public long size;
    public String lastModified;
    public boolean isDirectory;

    public DirEntryResponse(String path, long size, String lastModified, boolean isDirectory) {
        this.path = path;
        this.size = size;
        this.lastModified = lastModified;
        this.isDirectory = isDirectory;
    }
}
