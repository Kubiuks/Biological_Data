package org.spe.biologicaldata.webapplication.service;

import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spe.biologicaldata.webapplication.configuration.DiskStorageConfiguration;
import org.spe.biologicaldata.webapplication.wrapper.ImagePathWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
@ConditionalOnBean(DiskStorageConfiguration.class)
public class StoreToDisk implements StorageService {

    String folderPath;
    private static final Logger logger = LoggerFactory.getLogger(StoreToDisk.class);

    @Autowired
    public StoreToDisk(String diskStorageFolderPath){
        this.folderPath = diskStorageFolderPath;
    }

    @Override
    public Optional<ImagePathWrapper> store(MultipartFile file, Boolean enablePublicReadAccess) {
        try {
            String pathUrl = folderPath + RandomString.make(10) + "." + Objects.requireNonNull(file.getContentType()).split("/")[1];
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathUrl);
            Files.write(path, bytes);
            String imagePath = pathUrl.substring(pathUrl.indexOf("images"));
            return Optional.of(new ImagePathWrapper(imagePath, imagePath));
        } catch (IOException | NullPointerException e) {
            logger.error("[error: " + e + " happened while saving file " + file.getName() + "]");
            return Optional.empty();
        }
    }

    @Override
    public Boolean delete(String path) {
        try {
            String fullPath = folderPath + path.substring(path.indexOf("\\") + 1);
            System.out.println(fullPath);
            Path pathToFile = Paths.get(fullPath);
            Files.delete(pathToFile);
            return true;
        } catch (IOException | IndexOutOfBoundsException e) {
           logger.error("[error: " + e + " happened while deleting file " + path + "]");
           return false;
        }
    }

//    @Override
//    public Boolean deleteAll() {
//        try {
//            File folder = new File(folderPath);
//            List<File> files = Arrays.asList(Objects.requireNonNull(folder.listFiles()));
//
//            for (File file : files) {
//                if (file.isFile()) {
//                    String path = file.getPath();
//                    try {
//                        Path pathToFile = Paths.get(path);
//                        Files.delete(pathToFile);
//                    } catch (IOException e) {
//                        logger.error("[error: " + e + " happened while deleting file " + path + "]");
//                        return false;
//                    }
//
//                } else if (file.isDirectory()) {
//                    files.addAll(Arrays.asList(Objects.requireNonNull(file.listFiles())));
//                }
//            }
//            return true;
//        } catch(NullPointerException e) {
//            logger.error("[error: " + e + " happened while deleting all files]");
//            return false;
//        }
//    }

    @Override
    public Optional<byte[]> retrieveFile(String path) {
        try {
            File file = new File(folderPath + path.substring(path.indexOf("\\")));
            if(file.isFile()) {
                FileInputStream fis = new FileInputStream(file);
                byte[] bytes = fis.readAllBytes();
                fis.close();
                return Optional.of(bytes);
            }
            else {
                throw new FileNotFoundException("The pathname is not a file");
            }
        } catch(IOException | IndexOutOfBoundsException e) {
            logger.error("[error: " + e + " happened while retrieving file " + path + "]");
            return Optional.empty();
        }
    }

}
