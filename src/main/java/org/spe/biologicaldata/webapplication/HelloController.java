package org.spe.biologicaldata.webapplication;

import org.spe.biologicaldata.webapplication.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Controller
public class HelloController {

    private final DatabaseController databaseController;
    private final Textextract textextract = new Textextract();

    @Autowired
    public HelloController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @GetMapping(value = {"/", "index"})
    public  String index() {
        return "index";
    }


    @RequestMapping(value = "/home")
    public  String home() {
        return "home";
    }

    @RequestMapping(value = "/home2")
    public  String home2() {
        return "home2";
    }


    @GetMapping("/gallery")
    public String gallery(Model model) {
        List<Image> images = databaseController.getImages();
        model.addAttribute("images",images);
        return "gallery"; }

    @PostMapping("/gallery")
    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile, Model model) {
        if(!Objects.requireNonNull(imageFile.getContentType()).split("/")[0].equals("image")) {
            return "error";
        }
        databaseController.storeImage(new Image(), imageFile);
        return gallery(model);
    }

    // For now only returns home
    @PostMapping("/")
    public String login(){
        return "redirect:/home";
    }



}
