package org.spe.biologicaldata.webapplication.controller;

import com.google.api.client.json.Json;
import org.spe.biologicaldata.webapplication.Textextract;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@ControllerAdvice
public class extractTextController {

    Textextract textextract = new Textextract();

    @GetMapping(value = "/extractText")
    public  ModelAndView extractTextPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("extractText");
        return modelAndView;
    }

    @PostMapping(value = "/extractText")
    public String extractText(@RequestBody String imageFile) throws IOException {
        String extracted_text = textextract.getText("src/main/resources/static/images/" + imageFile);
        return extracted_text;
    }


}
