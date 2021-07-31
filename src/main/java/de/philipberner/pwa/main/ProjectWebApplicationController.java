package de.philipberner.pwa.main;

import de.philipberner.pwa.storage.Storage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author Philip Berner
 * @version 1.0
 */
@Controller
public class ProjectWebApplicationController {


    /**
     * @param model is used by Spring Boot
     * @return returns the altered table.html
     */
    @GetMapping("/table")
    public String table(Model model) {
        //gives Data to Thymeleaf
        model.addAttribute("tableData", Storage.getData());
        return "table";
    }
}
