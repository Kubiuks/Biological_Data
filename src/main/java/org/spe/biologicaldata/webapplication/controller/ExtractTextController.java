package org.spe.biologicaldata.webapplication.controller;

import org.spe.biologicaldata.webapplication.Textextract;
import org.spe.biologicaldata.webapplication.service.TextRecognitionService;
import org.spe.biologicaldata.webapplication.wrapper.MultipartFileWrapper;
import org.spe.biologicaldata.webapplication.wrapper.StringWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@ControllerAdvice
public class ExtractTextController {

    private TextRecognitionService textRecognitionService;

    @Autowired
    ExtractTextController(TextRecognitionService textRecognitionService) {
        this.textRecognitionService = textRecognitionService;
    }

    @GetMapping(value = "/extractText")
    public  ModelAndView extractTextPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("extractText");
        return modelAndView;
    }

    @PostMapping(value = "/extractText", consumes = {"multipart/form-data"}, produces = "application/json")
    public StringWrapper extractText(@RequestBody @ModelAttribute MultipartFileWrapper formData) {
        try {
            String extracted_text = textRecognitionService.getTextFromMultipartFile(formData.getFile()).orElseThrow();
            StringWrapper response = new StringWrapper();
            response.setValue(extracted_text);
            return response;
        } catch (NoSuchElementException e) {
            StringWrapper response = new StringWrapper();
            response.setValue("Error while extracting the text:" );
            return response;
        }
    }

}
