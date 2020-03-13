package org.spe.biologicaldata.webapplication.service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spe.biologicaldata.webapplication.configuration.GoogleVisionConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@ConditionalOnBean(GoogleVisionConfiguration.class)
public class GoogleTextRecognition implements  TextRecognitionService {
    private static final Logger logger = LoggerFactory.getLogger(GoogleTextRecognition.class);

    ImageAnnotatorClient imageAnnotatorClient;

    @Autowired
    GoogleTextRecognition(Credentials googleVisionCredentials, ApplicationContext applicationContext){
        try {
            this.imageAnnotatorClient = ImageAnnotatorClient.create(
                    ImageAnnotatorSettings.newBuilder()
                            .setCredentialsProvider(FixedCredentialsProvider.create(googleVisionCredentials)).build()
            );
        } catch (IOException e){
            logger.error("error: [" + e + "] happened while loading Image Annotator Client");
            SpringApplication.exit(applicationContext, () -> 1);
        }
    }

    @Override
    public Optional<String> getTextFromMultipartFile(MultipartFile file){
        try {
            Image image = Image.newBuilder().setContent(ByteString.copyFrom(file.getBytes())).build();
            return Optional.of(getStringFromResponse(image));
        } catch (Exception e){
            logger.error("error: [" + e + "] happened while loading extracting text from image:" + file.getName());
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<String>> getTextFromMultipartFiles(List<MultipartFile> files){
        try {
            List<Image> images = new ArrayList<>();
            for (MultipartFile file : files) {
                images.add(Image.newBuilder().setContent(ByteString.copyFrom(file.getBytes())).build());
            }
            return Optional.of(getListOfStringsFromResponses(images));
        } catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getTextFromLink(String imageLink){
        try {
            ImageSource imgSource = ImageSource.newBuilder().setImageUri(imageLink).build();
            Image image = Image.newBuilder().setSource(imgSource).build();
            return Optional.of(getStringFromResponse(image));
        } catch (Exception e) {
            logger.error("error: [" + e + "] happened while loading extracting text from link:" + imageLink);
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<String>> getTextFromLinks(List<String> imageLinks){
        try {
            List<Image> images = new ArrayList<>();
            for (String imageLink : imageLinks) {
                ImageSource imgSource = ImageSource.newBuilder().setImageUri(imageLink).build();
                images.add(Image.newBuilder().setSource(imgSource).build());
            }
            return Optional.of(getListOfStringsFromResponses(images));
        } catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getTextFromBytes(byte[] bytes){
        try {
            Image image = Image.newBuilder().setContent(ByteString.copyFrom(bytes)).build();
            return Optional.of(getStringFromResponse(image));
        } catch (Exception e){
            logger.error("error: [" + e + "] happened while loading extracting text from bytes");
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<String>> getTextFromListOfBytes(List<byte[]> listOfBytes){
        try {
            List<Image> images = new ArrayList<>();
            for (byte[] bytes : listOfBytes) {
                images.add(Image.newBuilder().setContent(ByteString.copyFrom(bytes)).build());
            }
            return Optional.of(getListOfStringsFromResponses(images));
        } catch (Exception e){
            return Optional.empty();
        }
    }

    private String getStringFromResponse(Image image) {
        return getAnnotateResponses(Collections.singletonList(image))
                .get(0)
                .getTextAnnotationsList()
                .get(0)
                .getDescription();
    }

    private List<String> getListOfStringsFromResponses(List<Image> images) {
        List<String> texts = new ArrayList<>();
        getAnnotateResponses(images)
                .forEach( (res) -> texts
                                    .add( res
                                            .getTextAnnotationsList()
                                            .get(0)
                                            .getDescription()
                                    )
                );
        return texts;
    }

    private List<AnnotateImageResponse> getAnnotateResponses(List<Image> images) throws IllegalStateException{
        List<AnnotateImageRequest> requests = new ArrayList<>();

        for(Image image : images){
            Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(image).build();
            requests.add(request);
        }

        BatchAnnotateImagesResponse response = imageAnnotatorClient.batchAnnotateImages(requests);
        List<AnnotateImageResponse> responses = response.getResponsesList();

        for (AnnotateImageResponse res : responses) {
            if (res.hasError()) {
                logger.error("error: [" + res.getError() + "] happened while Annotating Image");
                throw new IllegalStateException();
            }
        }
        return responses;
    }

}
