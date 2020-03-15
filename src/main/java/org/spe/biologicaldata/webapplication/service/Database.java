package org.spe.biologicaldata.webapplication.service;

import org.spe.biologicaldata.webapplication.model.Image;
import org.spe.biologicaldata.webapplication.repository.ImageRepository;
import org.spe.biologicaldata.webapplication.wrapper.ImagePathWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class Database implements DatabaseService {

    private StorageService storageService;
    private ImageRepository imageRepository;

    @Autowired
    public Database(StorageService storageService, ImageRepository imageRepository) {
        this.storageService = storageService;
        this.imageRepository = imageRepository;
    }

    @Override
    public Boolean storeImage(Image imageInfo, MultipartFile image, Boolean enablePublicReadAccess) {
        Optional<ImagePathWrapper> pathUrl = storageService.store(image, enablePublicReadAccess);
        if(pathUrl.isPresent()) {
            imageInfo.setImageUrl(pathUrl.get().getLink());
            imageInfo.setBucketPathId(pathUrl.get().getPathId());
            imageRepository.save(imageInfo);
            return true;
        }
        return false;
    }

    @Override
    public Boolean storeImage(MultipartFile image, Boolean enablePublicReadAccess) {
        Optional<ImagePathWrapper> pathUrl = storageService.store(image, enablePublicReadAccess);
        if(pathUrl.isPresent()) {
            Image imageRow = new Image(pathUrl.get().getLink(), pathUrl.get().getPathId(),
                    image.getName(), "", "", "", "");
            imageRepository.save(imageRow);
            return  true;
        }
        return false;
    }

    @Override
    public Boolean storeImage(String title, String author, String writtenDate,
                              String page, String description, MultipartFile image, Boolean enablePublicReadAccess) {
        Optional<ImagePathWrapper> pathUrl = storageService.store(image, enablePublicReadAccess);
        if(pathUrl.isPresent()){
            Image imageRow = new Image(pathUrl.get().getLink(), pathUrl.get().getPathId(),
                    title, author, writtenDate, page, description);
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
            storageService.delete(image.get().getBucketPathId());
            imageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteImage(Image image) {
        if(!storageService.delete(image.getBucketPathId()))
            return false;
        if(imageRepository.existsById(image.getId())) {
            imageRepository.delete(image);
            return true;
        }
        return false;
    }

}
