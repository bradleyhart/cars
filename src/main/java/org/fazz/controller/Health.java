package org.fazz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Health {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseBody
    public String index() {
        return "I am running";
    }

}
