package com.template.controllers.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/templateController"})
public class TemplateController {
    static final Logger log = LoggerFactory.getLogger(TemplateController.class);

    @GetMapping({"/"})
    String getTemplatePage() {
        log.info("template page exposed");
        return "template";
    }

    @GetMapping("/secured")
    String getSecuredPage() {
        log.info("secured page exposed");
        return "template_secured";
    }
}
