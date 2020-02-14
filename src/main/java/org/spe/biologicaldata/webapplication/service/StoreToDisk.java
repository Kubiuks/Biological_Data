package org.spe.biologicaldata.webapplication.service;

import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class StoreToDisk implements StorageService {

    String folderPath = "src/main/resources/static/images/";

    @Override
    public String store(MultipartFile file, Boolean enablePublicReadAccess) {
        String pathUrl = folderPath + RandomString.make(10) + "." + Objects.requireNonNull(file.getContentType()).split("/")[1];
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathUrl);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("error saving file");
        }
        return pathUrl.substring(pathUrl.indexOf("images"));
    }

    @Override
    public void delete(String path) {
        String fullPath = folderPath + path.substring(path.indexOf("/"));
        try {
            Path pathToFile = Paths.get(fullPath);
            Files.delete(pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("error deleting file");
        }
    }

    @Override
    public void deleteAll() {
        File folder = new File("src/main/resources/static/images");
        List<File> files = Arrays.asList(Objects.requireNonNull(folder.listFiles()));

        for (File file : files) {
            if (file.isFile()) {
                String path = file.getPath();
                try {
                    Path pathToFile = Paths.get(path);
                    Files.delete(pathToFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("error deleting file");
                }

            } else if (file.isDirectory()) {
                files.addAll(Arrays.asList(Objects.requireNonNull(file.listFiles())));
            }
        }

    }
}
