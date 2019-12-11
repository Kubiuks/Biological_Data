package org.spe.biologicaldata.webapplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseTest {

    private DatabaseController databaseController;

    @Autowired
    DatabaseTest(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }


    @Test
    void testStoreImageWithTwoArguments(){
        Image image = new Image("","File1","Auth1","1930-1940","4","Some description");
        MultipartFile file = new MockMultipartFile("File1", "filename1", "image/jpg",new byte[]{4,6,100,127, 34});
        databaseController.storeImage(image,file);
        assertTrue(databaseController.getImageById(image.getId()).isPresent());
        assertFalse(databaseController.getImageById((long) -12).isPresent());
        Image result = databaseController.getImageById(image.getId()).get();
        assertEquals(image.getTitle(), result.getTitle());
        assertEquals(image.getAuthor(), result.getAuthor());
        assertEquals(image.getWrittenDate(), result.getWrittenDate());
        assertEquals(image.getPage(), result.getPage());
        assertEquals(image.getDescription(), result.getDescription());
        databaseController.deleteImage(image);
        assertTrue(databaseController.getImageById(image.getId()).isEmpty());
    }



}

