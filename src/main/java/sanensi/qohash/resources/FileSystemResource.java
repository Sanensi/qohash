package sanensi.qohash.resources;

import sanensi.qohash.domain.DirEntry;
import sanensi.qohash.domain.FileSystem;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@javax.ws.rs.Path("/")
public class FileSystemResource {
    @Inject
    private FileSystem fs;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listFiles() {
        return this.listFiles("");
    }

    @GET
    @javax.ws.rs.Path("{path: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listFiles(@PathParam("path") String path) {
        List<DirEntryResponse> entries = fs
            .getDirEntries(Path.of(path)).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());

        return Response.ok(entries).build();
    }

    private DirEntryResponse toResponse(DirEntry entry) {
        return new DirEntryResponse(
            entry.path.toString(),
            entry.size,
            entry.lastModified.toString(),
            entry.isDir
        );
    }
}
