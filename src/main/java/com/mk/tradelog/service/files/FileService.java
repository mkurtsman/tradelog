package com.mk.tradelog.service.files;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileService {
    List<Path> getFiles() throws IOException;
    void backupFiles(List<Path> paths) throws IOException ;
    void writeFile(String fileName, byte[] content) throws IOException;
}
