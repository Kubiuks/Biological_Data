package org.spe.biologicaldata.webapplication;

import org.junit.jupiter.api.Test;
import org.spe.biologicaldata.webapplication.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StorageServiceTest {

    StorageService storageService;

    @Autowired
    StorageServiceTest(StorageService storageService){
        this.storageService = storageService;
    }

    @Test
    void testStore() {
        MultipartFile file1 = new MockMultipartFile("File1", "filename1",
                "image/jpg", new byte[]{0x21, 0x3f});
        MultipartFile file2 = new MockMultipartFile("File2", "filename2",
                "image/jpg", new byte[]{1, 3, 9, 27, 84});

        Optional<String> filePath1 = storageService.store(file1, true);
        Optional<String> filePath2 = storageService.store(file2, false);

        assertTrue(filePath1.isPresent());
        assertTrue(filePath2.isPresent());

        Optional<byte[]> resultBytes1 = storageService.retrieveFile(filePath1.get());
        Optional<byte[]> resultBytes2 = storageService.retrieveFile(filePath2.get());

        assertTrue(resultBytes1.isPresent());
        assertTrue(resultBytes2.isPresent());

        assertDoesNotThrow(
                () -> {
                    assertEquals(Arrays.toString(resultBytes1.get()), Arrays.toString(file1.getBytes()));
                    assertEquals(Arrays.toString(resultBytes2.get()), Arrays.toString(file2.getBytes()));
                }

        );

        assertTrue(storageService.delete(filePath1.get()));
        assertTrue(storageService.delete(filePath2.get()));
    }

    @Test
    void testStoreSameFileMultipleTimesReturnsDifferentNames(){
        MultipartFile file1 = new MockMultipartFile("File1", "filename1",
                "image/jpg", new byte[]{1, 2, 3, 4, 5});

        Optional<String> filePath1 = storageService.store(file1, true);
        Optional<String> filePath2 = storageService.store(file1, false);
        Optional<String> filePath3 = storageService.store(file1, true);
        Optional<String> filePath4 = storageService.store(file1, false);

        assertTrue(filePath1.isPresent());
        assertTrue(filePath2.isPresent());
        assertTrue(filePath3.isPresent());
        assertTrue(filePath4.isPresent());

        assertNotEquals(filePath1.get(), filePath2.get());
        assertNotEquals(filePath1.get(), filePath3.get());
        assertNotEquals(filePath1.get(), filePath4.get());
        assertNotEquals(filePath2.get(), filePath3.get());
        assertNotEquals(filePath2.get(), filePath4.get());
        assertNotEquals(filePath3.get(), filePath4.get());

        Optional<byte[]> resultBytes1 = storageService.retrieveFile(filePath1.get());
        Optional<byte[]> resultBytes2 = storageService.retrieveFile(filePath2.get());
        Optional<byte[]> resultBytes3 = storageService.retrieveFile(filePath3.get());
        Optional<byte[]> resultBytes4 = storageService.retrieveFile(filePath4.get());

        assertTrue(resultBytes1.isPresent());
        assertTrue(resultBytes2.isPresent());
        assertTrue(resultBytes3.isPresent());
        assertTrue(resultBytes4.isPresent());

        assertDoesNotThrow(
                () -> {
                    assertEquals(Arrays.toString(resultBytes1.get()), Arrays.toString(file1.getBytes()));
                    assertEquals(Arrays.toString(resultBytes2.get()), Arrays.toString(file1.getBytes()));
                    assertEquals(Arrays.toString(resultBytes3.get()), Arrays.toString(file1.getBytes()));
                    assertEquals(Arrays.toString(resultBytes4.get()), Arrays.toString(file1.getBytes()));
                }

        );

        assertTrue(storageService.delete(filePath1.get()));
        assertTrue(storageService.delete(filePath2.get()));
        assertTrue(storageService.delete(filePath3.get()));
        assertTrue(storageService.delete(filePath4.get()));
    }

    @Test
    void testDelete() {
        MultipartFile file1 = new MockMultipartFile("File1", "filename1",
                "image/jpg", new byte[]{0x21, 0x3f});
        MultipartFile file2 = new MockMultipartFile("File2", "filename2",
                "image/jpg", new byte[]{1, 3, 9, 27, 84});
        MultipartFile file3 = new MockMultipartFile("File3", "filename3",
                "image/jpg", new byte[]{0x2, 0x4, 0x8, 0xf, 0x12});

        Optional<String> filePath1 = storageService.store(file1, true);
        Optional<String> filePath2 = storageService.store(file2, false);
        Optional<String> filePath3 = storageService.store(file3, false);

        assertTrue(filePath1.isPresent());
        assertTrue(filePath2.isPresent());
        assertTrue(filePath3.isPresent());

        assertTrue(storageService.retrieveFile(filePath1.get()).isPresent());
        assertTrue(storageService.retrieveFile(filePath2.get()).isPresent());
        assertTrue(storageService.retrieveFile(filePath3.get()).isPresent());

        assertTrue(storageService.delete(filePath3.get()));
        assertTrue(storageService.delete(filePath1.get()));
        assertTrue(storageService.delete(filePath2.get()));

        assertTrue(storageService.retrieveFile(filePath1.get()).isEmpty());
        assertTrue(storageService.retrieveFile(filePath2.get()).isEmpty());
        assertTrue(storageService.retrieveFile(filePath3.get()).isEmpty());

        Optional<String> filePath4 = storageService.store(file3, false);
        Optional<String> filePath5 = storageService.store(file3, false);
        Optional<String> filePath6 = storageService.store(file3, false);

        assertTrue(filePath4.isPresent());
        assertTrue(filePath5.isPresent());
        assertTrue(filePath6.isPresent());

        assertTrue(storageService.retrieveFile(filePath4.get()).isPresent());
        assertTrue(storageService.retrieveFile(filePath5.get()).isPresent());
        assertTrue(storageService.retrieveFile(filePath6.get()).isPresent());

        assertTrue(storageService.delete(filePath4.get()));
        assertTrue(storageService.delete(filePath5.get()));
        assertTrue(storageService.delete(filePath6.get()));

        assertTrue(storageService.retrieveFile(filePath4.get()).isEmpty());
        assertTrue(storageService.retrieveFile(filePath5.get()).isEmpty());
        assertTrue(storageService.retrieveFile(filePath6.get()).isEmpty());

    }

    @Test
    void testDeleteSameFileMultipleTimesReturnsFalse() {
        MultipartFile file1 = new MockMultipartFile("File1", "filename1",
                "image/jpg", new byte[]{1, 2, 3, 4, 5});

        Optional<String> filePath1 = storageService.store(file1, true);

        assertTrue(filePath1.isPresent());
        assertTrue(storageService.retrieveFile(filePath1.get()).isPresent());

        assertTrue(storageService.delete(filePath1.get()));
        assertTrue(storageService.retrieveFile(filePath1.get()).isEmpty());
        assertFalse(storageService.delete(filePath1.get()));
        assertTrue(storageService.retrieveFile(filePath1.get()).isEmpty());
        assertFalse(storageService.delete(filePath1.get()));
        assertTrue(storageService.retrieveFile(filePath1.get()).isEmpty());
    }

    @Test
    void testDeleteAll() {
        MultipartFile file1 = new MockMultipartFile("File1", "filename1",
                "image/jpg", new byte[]{0x21, 0x3f});
        MultipartFile file2 = new MockMultipartFile("File2", "filename2",
                "image/jpg", new byte[]{1, 3, 9, 27, 84});
        MultipartFile file3 = new MockMultipartFile("File3", "filename3",
                "image/jpg", new byte[]{0x2, 0x4, 0x8, 0xf, 0x12});

        Optional<String> filePath1 = storageService.store(file1, true);
        Optional<String> filePath2 = storageService.store(file2, false);
        Optional<String> filePath3 = storageService.store(file3, false);

        assertTrue(filePath1.isPresent());
        assertTrue(filePath2.isPresent());
        assertTrue(filePath3.isPresent());
        assertTrue(storageService.retrieveFile(filePath1.get()).isPresent());
        assertTrue(storageService.retrieveFile(filePath2.get()).isPresent());
        assertTrue(storageService.retrieveFile(filePath3.get()).isPresent());

        assertTrue(storageService.deleteAll());

        assertTrue(storageService.retrieveFile(filePath1.get()).isEmpty());
        assertTrue(storageService.retrieveFile(filePath2.get()).isEmpty());
        assertTrue(storageService.retrieveFile(filePath3.get()).isEmpty());

        assertTrue(storageService.deleteAll());
        assertTrue(storageService.deleteAll());

        assertTrue(storageService.retrieveFile(filePath1.get()).isEmpty());
        assertTrue(storageService.retrieveFile(filePath2.get()).isEmpty());
        assertTrue(storageService.retrieveFile(filePath3.get()).isEmpty());
    }

}
