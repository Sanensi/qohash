import { Typography, Paper, TableContainer, Table, TableHead, TableRow, TableCell, TableBody, Container, Box, Link } from "@material-ui/core";
import { useEffect, useState } from "react";
import FileService, { DirEntry } from "./api/FileService";

interface Props {
  path: string;
  onFolderChange: (path: string) => void;
}

function App(props: Props) {
  const [files, setFiles] = useState(new Array<DirEntry>());

  useEffect(() => {
    FileService.getFiles(props.path).then(setFiles);
  },
    [props.path]
  );

  return (
    <Container>
      <Typography variant="h3">
        Qohash Filesystem
      </Typography>

      <Box component={Paper} style={{ padding: 10, margin: "10px 0px" }}>
        <Typography>
          Current directory: {props.path}
        </Typography>
        <Typography>
          Number of entries: {files.length}
        </Typography>
        <Typography>
          Total size: {files.reduce((acc, f) => acc + f.size, 0)} bytes
        </Typography>
      </Box>

      <TableContainer component={Paper}>
        <Table size="small" style={{ minWidth: 700 }}>
          <TableHead style={{ backgroundColor: "gray" }}>
            <TableRow>
              <TableCell style={{ color: "white" }}>Path</TableCell>
              <TableCell style={{ color: "white" }} align="right">Size (bytes)</TableCell>
              <TableCell style={{ color: "white" }} align="right">Last modified</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {files.map((f) => (
              <TableRow key={f.path}>
                <TableCell component="th" scope="row">
                  {!f.isDirectory
                    ? f.path
                    : <Link style={{cursor: "pointer"}} onClick={() => props.onFolderChange(f.path)}>{f.path}</Link>}
                </TableCell>
                <TableCell align="right">{f.size}</TableCell>
                <TableCell align="right">{new Date(f.lastModified).toDateString()}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Container>
  );
}

export default App;
