package org.spe.biologicaldata.webapplication.service;

import org.spe.biologicaldata.webapplication.model.Image;
import org.spe.biologicaldata.webapplication.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class Database implements DatabaseService {

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
        } catch(NullPointerException e) {
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
    public Boolean storeImage(Image imageInfo, MultipartFile image) {
        Optional<String> pathUrl = storageService.store(image, true);
        if(pathUrl.isPresent()) {
            imageInfo.setImageUrl(pathUrl.get());
            imageRepository.save(imageInfo);
            return true;
        }
        return false;
    }

    @Override
    public Boolean storeImage(MultipartFile image) {
        Optional<String> pathUrl = storageService.store(image, true);
        if(pathUrl.isPresent()) {
            Image imageRow = new Image(pathUrl.get(), image.getName(), "", "", "", "");
            imageRepository.save(imageRow);
            return  true;
        }
        return false;
    }

    @Override
    public Boolean storeImage(String title, String author, String writtenDate,
                              String page, String description, MultipartFile image) {
        Optional<String> pathUrl = storageService.store(image, true);
        if(pathUrl.isPresent()){
            Image imageRow = new Image(pathUrl.get(), title, author, writtenDate, page, description);
            imageRepository.save(imageRow);
            return true;
        }
     return false;
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
    public Boolean deleteImageById(Long id) {
        Optional<Image> image= imageRepository.findById(id);
        if(image.isPresent()) {
            storageService.delete(image.get().getImageUrl());
            imageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteImage(Image image) {
        if(!storageService.delete(image.getImageUrl()))
            return false;
        if(imageRepository.existsById(image.getId())) {
            imageRepository.delete(image);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteAll() {
        imageRepository.deleteAll();
        return true;
    }
}
