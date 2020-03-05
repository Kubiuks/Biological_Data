package org.spe.biologicaldata.webapplication;

import org.junit.jupiter.api.Test;
import org.spe.biologicaldata.webapplication.model.Image;
import org.spe.biologicaldata.webapplication.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseServiceTest {

   private DatabaseService databaseService;

    @Autowired
    DatabaseServiceTest(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Test
    void testStoreImageWithTwoArguments(){
        Image image = new Image("","File1","Auth1","1930-1940","4","Some description");
        MultipartFile file = new MockMultipartFile("File1", "filename1",
                "image/jpg",new byte[]{4, 6, 100, 127, 34});

        assertTrue(databaseService.storeImage(image,file));

        assertTrue(databaseService.getImageById(image.getId()).isPresent());
        assertFalse(databaseService.getImageById((long) -12).isPresent());

        Image result = databaseService.getImageById(image.getId()).get();

        assertEquals(image.getTitle(), result.getTitle());
        assertEquals(image.getAuthor(), result.getAuthor());
        assertEquals(image.getWrittenDate(), result.getWrittenDate());
        assertEquals(image.getPage(), result.getPage());
        assertEquals(image.getDescription(), result.getDescription());

        assertTrue(databaseService.deleteImage(image));
        assertTrue(databaseService.getImageById(image.getId()).isEmpty());
    }

}

