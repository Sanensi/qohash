package sanensi.qohash.resources;

public class DirEntryResponse {
    public String path;
    public long size;
    public String lastModified;

    public DirEntryResponse(String path, long size, String lastModified) {
        this.path = path;
        this.size = size;
        this.lastModified = lastModified;
    }
}
