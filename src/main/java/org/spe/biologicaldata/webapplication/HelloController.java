package org.spe.biologicaldata.webapplication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String index(@RequestParam(name="number", required=false, defaultValue="5") String number, Model model) {
        List<Integer> numbers = new ArrayList<>();
        for(int n = 0; n < Integer.parseInt(number); n++) {
            numbers.add(n+1);
        }
        model.addAttribute("numbers", numbers);
        model.addAttribute("numberOfRows", number);
        return "index";
    }

}
