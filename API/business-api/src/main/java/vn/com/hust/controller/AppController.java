package vn.com.hust.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class AppController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        return "It's working!";
    }

}
