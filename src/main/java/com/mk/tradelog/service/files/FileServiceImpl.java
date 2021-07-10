package com.mk.tradelog.service.files;

import com.mk.tradelog.config.AppProperties;
import com.mk.tradelog.service.event.UpdateDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final AppProperties properties;

    @Override
    public List<Path> getFiles() throws IOException {
        return Files.list(Paths.get(properties.getHtmlParser().getFilespath())).filter(p -> p.toString().endsWith(".htm") || p.toString().endsWith(".html")).collect(Collectors.toList());
    }

    @Override
    public void backupFiles(List<Path> files) throws IOException {
        Path destPath = Paths.get(properties.getHtmlParser().getFilespath(), "/backup");
        Files.createDirectories(destPath);

        for(Path path : files){
            String fileName =  path.getFileName().toString();
            Path newPath = destPath.resolve(fileName.substring(0, fileName.length() -4) + "_"+ LocalDateTime.now() + ".htm");
            while (Files.exists(newPath)){
                newPath = destPath.resolve(fileName.substring(0, fileName.length() -4) + "_"+ LocalDateTime.now() + ".htm");
            }
            Files.move(path, newPath);
        }

    }

    @Override
    public void writeFile(String fileName, byte[] content) throws IOException {
       Path destPath = Paths.get(properties.getHtmlParser().getFilespath(), fileName);
       Files.write(destPath, content);
    }
}
