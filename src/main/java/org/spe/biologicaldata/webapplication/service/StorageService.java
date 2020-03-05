package org.spe.biologicaldata.webapplication.service;

import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface for a Storage Service, every storage method must implement this interface
 */
public interface StorageService {

        /**
         * Stores a file in the storage
         * @param file the file to be stored
         * @param enablePublicReadAccess enables public read access
         * @return a string representing the path to the created file
         */
        Optional<String> store(MultipartFile file, Boolean enablePublicReadAccess);

        /**
         * Deletes a file
         * @param path the path to that file
         * @return true if the process was a success, false otherwise
         */
//        @Async
        Boolean delete(String path);

        /**
         * Deletes all files inside the storage
         * @return true if the process was a success, false otherwise
         */
//        @Async
        Boolean deleteAll();

        /**
         * Returns a file from the path, it it exist
         * @return a file, if there is one at the path, or empty otherwise
         */
        Optional<byte[]> retrieveFile(String path);

}
