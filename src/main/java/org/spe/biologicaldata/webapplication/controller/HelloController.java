package org.spe.biologicaldata.webapplication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spe.biologicaldata.webapplication.model.Image;
import org.spe.biologicaldata.webapplication.model.User;
import org.spe.biologicaldata.webapplication.service.DatabaseService;
import org.spe.biologicaldata.webapplication.service.TextRecognitionService;
import org.spe.biologicaldata.webapplication.wrapper.ImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class HelloController {

    private final DatabaseService databaseService;
    private final TextRecognitionService textRecognitionService;
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    public HelloController(DatabaseService databaseService, TextRecognitionService textRecognitionService) {
        this.databaseService = databaseService;
        this.textRecognitionService = textRecognitionService;
    }

    @GetMapping(value = {"/", "index"})
    public  String index() {
        return "index";
    }


    @RequestMapping(value = "/home")
    public  String home() {
        return "index";
    }

    @RequestMapping(value = "/home2")
    public  String home2() {
        return "home2";
    }


    @GetMapping("/gallery")
    public String gallery(Model model) {
        List<Image> images = databaseService.getImages();
        model.addAttribute("images",images);
        model.addAttribute("imageFile", new ImageFile());
        return "gallery"; }

    @PostMapping("/gallery/upload")
    //TODO change to a an error message instead
    public String uploadImage(@Valid @ModelAttribute("imageFile") ImageFile imageFile, Model model) {
        try {
            if (!Objects.requireNonNull(imageFile.getFile().get(0).getContentType()).split("/")[0].equals("image")) {
                return "error";
            }System.out.println(imageFile.getDescription());
            if(!databaseService.storeImage(imageFile.getTitle(), imageFile.getAuthor(),
                    imageFile.getWrittenDate(), imageFile.getPage(), imageFile.getDescription(),
                    imageFile.getFile().get(0), true)) {
                logger.error("[error: could not save file " + imageFile.getFile().get(0).getName() + "]");
                return "error";
            }
            return "redirect:/gallery";
        } catch (NullPointerException e) {
            logger.error("[error: " + e + " happened while saving file " + imageFile.getFile().get(0).getName() + "]");
            return "error";
        }
    }

}
