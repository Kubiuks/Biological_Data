package org.spe.biologicaldata.webapplication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class extractInfoController {
    @RequestMapping(value = "/extractInfo")
    public  String home2() {
        return "extractInfo";
    }
}
