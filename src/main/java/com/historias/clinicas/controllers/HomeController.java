package com.historias.clinicas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")
  public String home(Model m) {
    m.addAttribute("mensaje","¡Bienvenido a Historias Clínicas!");
    return "index";
  }
}
