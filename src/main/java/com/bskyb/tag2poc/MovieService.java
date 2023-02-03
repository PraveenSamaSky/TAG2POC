package com.bskyb.tag2poc;

import com.bskyb.platform_config.commons.Territory;
import com.bskyb.platform_config.commons.TestEnv;
import com.bskyb.tag.MovieTao;
import com.bskyb.tag.Tag;
import com.bskyb.tag.datatypes.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MovieService {

    @Autowired
    private PayloadRepository payloadRepository;

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
System.out.println("movieTao.getContentID = " + movieTao.getContentID());
System.out.println("movieTao.getOttTitlePayLoad = " + movieTao.getOttDeletePayLoad());

        payloadRepository.save(new OttPayloadEntity(movieTao.getContentID(), movieTao.getOttDeletePayLoad()));
//        payloadRepository.save(new OttPayloadEntity(movieTao.getContentID(), "ottpayload"));
        return true;
//        return (movieTao.publish_asset());
    }

}
