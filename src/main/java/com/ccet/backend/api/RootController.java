/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author SAGAR MAHOBIA
 */
@RestController
public class RootController {

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String getRoot() {

        return "working";

    }
}
