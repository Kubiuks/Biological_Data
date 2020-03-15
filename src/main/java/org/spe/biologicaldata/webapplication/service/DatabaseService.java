package org.spe.biologicaldata.webapplication.service;

import org.spe.biologicaldata.webapplication.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * An interface for a Database Controller, takes care of both storing data in the Database Repository
 * and storing files in the Storage Service. The  Controller interacts with this Interface
 */
public interface DatabaseService {

    /**
     * Stores an image in the Storage Service and its information in the database
     * @param imageInfo an Image object containing the information of the image, apart from the path
     * @param image the image to be stored. its path will be added in imageInfo
     * @param enablePublicReadAccess enables public read access to the image
     * @return returns true if the process was a success, false otherwise
     */
    Boolean storeImage(Image imageInfo, MultipartFile image, Boolean enablePublicReadAccess);

    /**
     * Stores an image in the Storage Service and its path in the database. The rest of the fields in
     * Image will be left empty
     * @param image the image to be stored
     * @param enablePublicReadAccess enables public read access to the image
     * @return returns true if the process was a success, false otherwise
     */
    Boolean storeImage(MultipartFile image, Boolean enablePublicReadAccess);

    /**
     * Stores an image in the Storage Service and its information in the database
     * @param title the title of the image
     * @param author the author of the image
     * @param writtenDate the date when it was written
     * @param page the page from where it was taken
     * @param description the description of the image
     * @param image the image to be saved
     * @param enablePublicReadAccess enables public read access to the image
     * @return returns true if the process was a success, false otherwise
     */
    Boolean storeImage(String title, String author, String writtenDate,
                    String page, String description, MultipartFile image, Boolean enablePublicReadAccess);

    /**
     * Returns a list of all the images
     * @return a list with all the images stored
     */
    List<Image> getImages();

    /**
     * Returns the image with the given id
     * @param id the id of the image to look for
     * @return the image with that id
     */
    Optional<Image> getImageById(Long id);

    /**
     * Deletes an image from the Storage and the Database given its id
     * @param id the id of the image to be deleted
     * @return returns true if the process was a success, false otherwise
     */
    Boolean deleteImageById(Long id);

    /**
     * Deletes an image from the Storage and the Database given its
     * object representation in the database
     * @param image the object representing the image in the Database
     * @return returns true if the process was a success, false otherwise
     */
   Boolean deleteImage(Image image);

}
