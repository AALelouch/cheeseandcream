package com.lelouch.cheeseandcream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller para servir el SPA de React.
 * Redirige todas las rutas que no son API al index.html
 * para que React Router maneje el routing del frontend.
 */
@Controller
public class SpaController {

    @RequestMapping(value = {
            "/",
            "/agents",
            "/products",
            "/invoices",
            "/invoices/new",
            "/invoices/edit/**"
    })
    public String forward() {
        return "forward:/index.html";
    }
}


