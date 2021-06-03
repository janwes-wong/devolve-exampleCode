package com.janwes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.controller
 * @date 2021/6/2 18:04
 * @description
 */
@Controller
public class WebSocketController {

    @RequestMapping("/index")
    public String index() {
        return "index.html";
    }
}
