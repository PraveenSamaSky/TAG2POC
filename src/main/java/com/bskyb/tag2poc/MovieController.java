package com.bskyb.tag2poc;

import com.bskyb.platform_config.commons.Territory;
import com.bskyb.platform_config.commons.TestEnv;
import com.bskyb.tag.MovieTao;
import com.bskyb.tag.Tag;
import com.bskyb.tag.datatypes.Client;
import com.bskyb.tag.ddi.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping("/welcome")
    public String helloWorld(){
        return "helloworld";
    }

    @PostMapping("/api/assets/movie/publish")
    public ResponseEntity<MovieTao> publishMovie(
            @RequestHeader HashMap<String, String> headers,
            @RequestBody CustomMovieValues customMovieValues) throws Exception {

        return new ResponseEntity<>(movieService.publishMovie(headers, customMovieValues), HttpStatus.OK);
    }

    @PutMapping(path = "/api/assets/movie/update/{contentId}")
    public ResponseEntity<MovieTao> publishMovie(
            @PathVariable String contentId,
            @RequestHeader HashMap<String, String> headers,
            @RequestBody CustomMovieValues movieUpdateValues) throws Exception {

        return new ResponseEntity<>(movieService.updateMovie(contentId, headers, movieUpdateValues), HttpStatus.OK);
    }
}
