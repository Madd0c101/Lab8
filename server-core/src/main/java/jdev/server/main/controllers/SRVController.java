package jdev.server.main.controllers;

import jdev.domain.Jform;
import jdev.server.jpa.JpaApplication;
import jdev.server.services.Msgpost;
import jdev.server.services.getcoord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SRVController {
  //  public static RestRequest request;
    @Autowired
   private getcoord mes ;

    @Autowired
    private Msgpost post ;

    @RequestMapping("/solve")
    public String solve(@RequestParam(value="name", required=false, defaultValue="Unauthorized") String name, Model model) throws Exception{
        model.addAttribute("name", name);
        Msgpost.send();
        model.addAttribute("num", getcoord.push());
        return "solving";
    }

}
