package org.example.backend1.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HTMLController {


    @RequestMapping("/formGreeting")
    public String formGreeting(){
        return "formGreetingStart.html";
    }


    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/testing")
    public String testing(@RequestParam String name, Model model){
        model.addAttribute("name", name);
        return "index";
    }


}
