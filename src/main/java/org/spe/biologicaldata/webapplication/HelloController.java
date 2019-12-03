package org.spe.biologicaldata.webapplication;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class HelloController {

    /*@RequestMapping("/")
    public String homePage() {
        return "HomePage!";
    }*/

    @RequestMapping("/signin")
    public String signInPage() {
        return "Sign in!";
    }

    @RequestMapping("/signup")
    public String signUpPage() {
        return "Sign up!";
    }

    @RequestMapping("/html")
    public String html() {
        return "test";
    }
    

}
