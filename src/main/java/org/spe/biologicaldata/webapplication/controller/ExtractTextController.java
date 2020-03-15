package org.spe.biologicaldata.webapplication.controller;

import org.spe.biologicaldata.webapplication.Textextract;
import org.spe.biologicaldata.webapplication.wrapper.StringWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@ControllerAdvice
public class ExtractTextController {

    @GetMapping(value = "/extractText")
    public  ModelAndView extractTextPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("extractText");
        return modelAndView;
    }

    @PostMapping(value = "/extractText", consumes = "application/json", produces = "application/json")
    public StringWrapper extractText(@RequestBody StringWrapper imageFile) throws IOException {
        String extracted_text = Textextract.getText("src/main/resources/static/images/" + imageFile.getValue());
        StringWrapper response = new StringWrapper();
        response.setValue(extracted_text);
        return response;
    }

}
