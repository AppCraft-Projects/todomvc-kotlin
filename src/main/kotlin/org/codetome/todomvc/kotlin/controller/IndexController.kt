package org.codetome.todomvc.kotlin.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class IndexController {

    @RequestMapping("/")
    fun welcome(model: MutableMap<String, Any>): String {
        return "index"
    }

}