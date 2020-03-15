package org.spe.biologicaldata.webapplication.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TextRecognitionService {

    Optional<List<String>> getTextFromMultipartFiles(List<MultipartFile> files);

    Optional<String> getTextFromMultipartFile(MultipartFile file);

    Optional<String> getTextFromLink(String imageLink);

    Optional<List<String>> getTextFromLinks(List<String> imageLinks);

    Optional<String> getTextFromBytes(byte[] bytes);

    Optional<List<String>> getTextFromListOfBytes(List<byte[]> listOfBytes);
}
