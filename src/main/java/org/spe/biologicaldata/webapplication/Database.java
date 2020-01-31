package org.spe.biologicaldata.webapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class Database implements DatabaseController {

    private StorageService storageService;
    private ImageRepository imageRepository;

    @Autowired
    public Database(StorageService storageService, ImageRepository imageRepository) {
        this.storageService = storageService;
        this.imageRepository = imageRepository;

        //Load files from static/images into the in memory database
        File folder = new File("src/main/resources/static/images");
        List<File> files;
        try{
        
        files = Arrays.asList(Objects.requireNonNull(folder.listFiles()));
    }
        catch(NullPointerException e){
            boolean bool = folder.mkdir();
            files = Arrays.asList(Objects.requireNonNull(folder.listFiles()));
        
        }

        for (File file : files) {
            if (file.isFile()) {
                String path = file.getPath();
                imageRepository.save(new Image(path.substring(path.indexOf("images")),file.getName(),"","","",""));
            } else if (file.isDirectory()) {
                files.addAll(Arrays.asList(Objects.requireNonNull(file.listFiles())));
            }
        }
    }

    @Override
    public void storeImage(Image imageInfo, MultipartFile image) {
        String pathUrl = storageService.store(image);
        imageInfo.setImageUrl(pathUrl);
        imageRepository.save(imageInfo);
    }

    @Override
    public void storeImage(MultipartFile image) {
        String pathUrl = storageService.store(image);
        Image imageRow = new Image(pathUrl, image.getName(), "", "", "", "");
        imageRepository.save(imageRow);
    }

    @Override
    public void storeImage(String title, String author, String writtenDate,
                           String page, String description, MultipartFile image) {
        String pathUrl = storageService.store(image);
        Image imageRow = new Image(pathUrl, title, author, writtenDate, page, description);
        imageRepository.save(imageRow);
    }

    @Override
    public List<Image> getImages() {
        return new ArrayList<>((Collection<Image>) imageRepository.findAll()) ;
    }

    @Override
    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public void deleteImageById(Long id) {
        Optional<Image> image= imageRepository.findById(id);
        if(image.isPresent()) {
            storageService.delete(image.get().getImageUrl());
            imageRepository.deleteById(id);
        }
    }

    @Override
    public void deleteImage(Image image) {
        storageService.delete(image.getImageUrl());
        imageRepository.delete(image);
    }

    @Override
    public void deleteAll() {
        List<Image> images = new ArrayList<>((Collection<Image>) imageRepository.findAll());
        for(Image image : images) {
            deleteImage(image);
        }
    }
}
