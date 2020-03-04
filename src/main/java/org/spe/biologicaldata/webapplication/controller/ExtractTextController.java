package org.spe.biologicaldata.webapplication.controller;

import com.google.api.client.json.Json;
import org.spe.biologicaldata.webapplication.Textextract;
import org.spe.biologicaldata.webapplication.wrapper.TextExtracted;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@ControllerAdvice
public class ExtractTextController {

    Textextract textextract = new Textextract();

    @GetMapping(value = "/extractText")
    public  ModelAndView extractTextPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("extractText");
        return modelAndView;
    }

    @PostMapping(value = "/extractText", consumes = "application/json", produces = "application/json")
    public TextExtracted extractText(@RequestBody String imageFile) throws IOException {
        String extracted_text = textextract.getText("src/main/resources/static/images/" + imageFile);
        TextExtracted response = new TextExtracted();
        response.setExtractedText(extracted_text);
        return response;
    }

}
