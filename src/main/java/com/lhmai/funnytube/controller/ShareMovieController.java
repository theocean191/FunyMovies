package com.lhmai.funnytube.controller;

import com.lhmai.funnytube.controller.dto.ShareAMovieRequest;
import com.lhmai.funnytube.exception.InvalidUrlException;
import com.lhmai.funnytube.service.MovieService;
import com.lhmai.funnytube.service.dto.MovieDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/share")
@Slf4j
public class ShareMovieController {

    @Autowired
    private MovieService movieService;

    @ModelAttribute("movie")
    public ShareAMovieRequest shareAMovieRequest() {
        return new ShareAMovieRequest();
    }

    @GetMapping("")
    public String login(Model model) {
        return "share";
    }

    @PostMapping
    public String shareMovie(@ModelAttribute("movie") @Valid ShareAMovieRequest movie,
                             BindingResult result) throws IOException {

        try {
            MovieDto movieDto = movieService.addYoutubeMovieFromURL(movie.getMovieUrl());
        } catch (InvalidUrlException e) {
            result.rejectValue("movieUrl", null, "The input url is invalid!");
        }


        if (result.hasErrors()) {
            return "share";
        } else {
            return "redirect:/share?success=true";
        }
    }
}
