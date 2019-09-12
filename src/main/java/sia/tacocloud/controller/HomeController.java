package sia.tacocloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")        // handle all requests of the root path "/"
    public String home() {
        return "home";      // returns view name
    }
}
