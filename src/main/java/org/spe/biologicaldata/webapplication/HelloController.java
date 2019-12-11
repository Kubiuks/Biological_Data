package org.spe.biologicaldata.webapplication;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class HelloController {

    private final DatabaseController databaseController;

    @Autowired
    public HelloController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public  String index() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String table(HttpServletRequest request, Model model) {

        String number = request.getParameter("number");

        if (number == null || number == "") {
            number = "0";
        }

        List<Integer> numbers = new ArrayList<>();
        for(int n = 0; n < Integer.parseInt(number); n++) {
            numbers.add(n+1);
        }


        model.addAttribute("numbers", numbers);
        model.addAttribute("numberOfRows", number);
        return "table";
    }

    @GetMapping("/upload")
    public String upload(Model model) {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile, Model model) {
        if(!Objects.requireNonNull(imageFile.getContentType()).split("/")[0].equals("image")) {
            return "error";
        }
        databaseController.storeImage(new Image(), imageFile);
        return upload(model);
    }

    @RequestMapping("/gallery")
    public String gallery(Model model) {
        List<Image> images = databaseController.getImages();
        model.addAttribute("images",images);
        return "gallery";
    }

}
