package org.spe.biologicaldata.webapplication;

import org.spe.biologicaldata.webapplication.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * An interface for a Database Controller, takes care of both storing data in the Database Repository
 * and storing files in the Storage Service. The  Controller interacts with this Interface
 */
public interface DatabaseController {

    /**
     * Stores an image in the Storage Service and its information in the database
     * @param imageInfo an Image object containing the information of the image, apart from the path
     * @param image the image to be stored. its path will be added in imageInfo
     */
    void storeImage(Image imageInfo, MultipartFile image);

    /**
     * Stores an image in the Storage Service and its path in the database. The rest of the fields in
     * Image will be left empty
     * @param image the image to be stored
     */
    void storeImage(MultipartFile image);

    /**
     * Stores an image in the Storage Service and its information in the database
     * @param title the title of the image
     * @param author the author of the image
     * @param writtenDate the date when it was written
     * @param page the page from where it was taken
     * @param description the description of the image
     * @param image the image to be saved
     */
    void storeImage(String title, String author, String writtenDate,
                    String page, String description, MultipartFile image);

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
     */
    void deleteImageById(Long id);

    /**
     * Deletes an image from the Storage and the Database given its
     * object representation in the database
     * @param image the object representing the image in the Database
     */
    void deleteImage(Image image);

    /**
     * Deletes all entries in the Database and in the Storage
     */
    void deleteAll();
}
