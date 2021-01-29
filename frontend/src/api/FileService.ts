export interface DirEntry {
  path: string,
  size: number,
  lastModified: string,
  isDirectory: boolean,
}

const FileService = {
  getFiles(path: string): Promise<DirEntry[]> {
    return fetch("http://localhost:8080/files" + path)
      .then(r => r.json());
  }
}

export default FileService;