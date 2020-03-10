package org.spe.biologicaldata.webapplication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spe.biologicaldata.webapplication.model.Image;
import org.spe.biologicaldata.webapplication.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Controller
public class HelloController {

    private final DatabaseService databaseService;
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    public HelloController(DatabaseService databaseService) {
        this.databaseService = databaseService;
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
        return "gallery"; }

    @PostMapping("/gallery/upload")
    //TODO change to a an error message instead
    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile, Model model) {
        try {
            if (!Objects.requireNonNull(imageFile.getContentType()).split("/")[0].equals("image")) {
                return "error";
            }
            if(!databaseService.storeImage(new Image(), imageFile, true)) {
                logger.error("[error: could not save file " + imageFile.getName() + "]");
                return "error";
            }
            return gallery(model);
        } catch (NullPointerException e) {
            logger.error("[error: " + e + " happened while saving file " + imageFile.getName() + "]");
            return "error";
        }
    }

}
