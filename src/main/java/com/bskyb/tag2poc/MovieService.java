package com.bskyb.tag2poc;

import com.bskyb.platform_config.commons.Territory;
import com.bskyb.platform_config.commons.TestEnv;
import com.bskyb.tag.MovieTao;
import com.bskyb.tag.Tag;
import com.bskyb.tag.UpdateAssetBuilder;
import com.bskyb.tag.datatypes.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MovieService {

    @Autowired
    private PayloadRepository payloadRepository;

    @Autowired
    private MovieTaoRepository movieTaoRepository;

    public MovieTao publishMovie(HashMap<String, String> headers, CustomMovieValues customMovieValues) throws Exception {
        Tag tag = new Tag(Client.valueOf(headers.get("client")), headers.get("secret"),
                Territory.valueOf(headers.get("territory")),
                TestEnv.STABLE_INT);

        MovieTao movieTao = tag.getMovieAssetBuilder()
                .isMultiTerritory(true)
                .setTitle(customMovieValues.getMovieTitle())
                .buildAsset();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.convertValue(movieTaoRepository.save(new MovieTaoEntity(movieTao.getContentID(), movieTao)).getAssetTao(), MovieTao.class);
    }

    public MovieTao updateMovie(String contentId, HashMap<String, String> headers, CustomMovieValues movieUpdateValues) throws Exception {
        Tag tag = new Tag(Client.valueOf(headers.get("client")), headers.get("secret"),
                Territory.valueOf(headers.get("territory")),
                TestEnv.STABLE_INT);

        if(movieTaoRepository.existsById(contentId)) {
            System.out.println("record exists with contentId " + contentId);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();

            MovieTao movieTao = objectMapper.convertValue(movieTaoRepository.findById(contentId).get().getAssetTao(), MovieTao.class);

            UpdateAssetBuilder updateAssetBuilder = tag.getUpdateAssetBuilder(movieTao);
            MovieTao updatedMovieTao = updateAssetBuilder.updateCAparentalRatings("2").buildUpdatedAsset();

            return objectMapper.convertValue(movieTaoRepository.save(new MovieTaoEntity(contentId, updatedMovieTao)).getAssetTao(), MovieTao.class);
        } else {
            return null;
        }
    }
}
