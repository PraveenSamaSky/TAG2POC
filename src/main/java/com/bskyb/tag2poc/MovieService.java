package com.bskyb.tag2poc;

import com.bskyb.platform_config.commons.Territory;
import com.bskyb.platform_config.commons.TestEnv;
import com.bskyb.tag.MovieTao;
import com.bskyb.tag.OttPayload;
import com.bskyb.tag.Tag;
import com.bskyb.tag.datatypes.Client;
import com.bskyb.tag.ddi.models.Movie;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private PayloadRepository payloadRepository;
    @Autowired
    private MovieTaoRepository movieTaoRepository;

    public Boolean publishMovie(HashMap<String, String> headers, CustomMovieValues customMovieValues) throws Exception {
        Tag tag = new Tag(Client.valueOf(headers.get("client")), headers.get("secret"),
                Territory.valueOf(headers.get("territory")),
                TestEnv.STABLE_INT);

        MovieTao movieTao = tag.getMovieAssetBuilder()
                .isMultiTerritory(true)
                .setTitle(customMovieValues.getMovieTitle())
//                .setOfferDates(LocalDateTime.parse(customMovieValues.getOfferStartDate()),
//                               LocalDateTime.parse(customMovieValues.getOfferEndDate()))
                .buildAsset();

        payloadRepository.save(new OttPayloadEntity(movieTao.getContentID(), movieTao.getOttDeletePayLoad()));
        OttPayload updatedOttPayload = payloadRepository.getReferenceById(movieTao.getContentID()).getOtt_Payload();
        updatedOttPayload.getDeleteEntities().stream().map(t->{
                                                                t.setContentId("updated"+ t.getContentId());
                                                                return t;})
                                                        .collect(Collectors.toList());
        payloadRepository.save(new OttPayloadEntity(movieTao.getContentID(), updatedOttPayload));

        movieTaoRepository.save(new MovieTaoEntity(movieTao.getContentID(), movieTao.getMovie()));
        Movie movie = movieTaoRepository.getReferenceById(movieTao.getContentID()).getMovie();
        movie.getLocalizableInformation().stream().map(l-> {
                                                    l.setTitle("updateTitle");
                                                    return l;
                                                })
                                                .collect(Collectors.toList());
        movieTaoRepository.save(new MovieTaoEntity(movieTao.getContentID(), movie));
        return true;
    }

//    public Boolean updateMovie(String contentId, HashMap<String, String> headers, CustomMovieValues customMovieValues) throws Exception {
//
//        OttPayload updatedOttPayload = payloadRepository.getReferenceById(contentId).getOtt_Payload();
//        updatedOttPayload.getUpdateEntities().stream().forEach(e->);
//        updatedOttPayload.setUpdateEntities(Arrays.asList(new OttPayload[] {updatedOttPayload}));
//        payloadRepository.save(new OttPayloadEntity(movieTao.getContentID()+"_u", updatedOttPayload));
//
//        return true;
//    }

}
