package org.spe.biologicaldata.webapplication.service;

import org.spe.biologicaldata.webapplication.wrapper.ImagePathWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Interface for a Storage Service, every storage method must implement this interface
 */
public interface StorageService {

        /**
         * Stores a file in the storage
         * @param file the file to be stored
         * @param enablePublicReadAccess enables public read access to the image
         * @return a string representing the path to the created file
         */
        Optional<ImagePathWrapper> store(MultipartFile file, Boolean enablePublicReadAccess);

        /**
         * Deletes a file
         * @param path the path to that file
         * @return true if the process was a success, false otherwise
         */
        Boolean delete(String path);

//        /**
//         * Deletes all files inside the storage
//         * Do not use, unless strictly necessary
//         * @return true if the process was a success, false otherwise
//         */
//        Boolean deleteAll();

        /**
         * Returns a file from the path, it it exist
         * @return a file, if there is one at the path, or empty otherwise
         */
        Optional<byte[]> retrieveFile(String path);

}
