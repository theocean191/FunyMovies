package com.lhmai.funnytube.controller;

import com.lhmai.funnytube.service.MovieService;
import com.lhmai.funnytube.service.dto.MovieDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
@Slf4j
public class
HomeController {

    @Autowired
    private MovieService movieService;

    @ModelAttribute("movies")
    public List<MovieDto> shareAMovieRequest() {
        return movieService.listAll();
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/")
    public String root(Model model) {
        return "index";
    }
}
