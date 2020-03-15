package org.spe.biologicaldata.webapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExtractInfoController {
    @RequestMapping(value = "/extractInfo")
    public  String home2() {
        return "extractInfo";
    }
}
