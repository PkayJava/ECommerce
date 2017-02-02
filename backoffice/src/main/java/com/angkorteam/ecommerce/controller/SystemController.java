package com.angkorteam.ecommerce.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by socheatkhauv on 22/1/17.
 */
@Controller
public class SystemController {

    @RequestMapping(path = "/system", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> execute(HttpServletRequest request) throws Throwable {
        Date date = new Date();
        return ResponseEntity.ok(date);
    }

}
