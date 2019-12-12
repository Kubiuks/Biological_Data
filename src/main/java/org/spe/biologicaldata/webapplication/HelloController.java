package org.spe.biologicaldata.webapplication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

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


    @RequestMapping(value = "/extractText")
    public  String extractText() {
        return "extractText";
    }

    @RequestMapping(value = "/extractInfo")
    public  String extractInfo() {
        return "extractInfo";
    }
}
