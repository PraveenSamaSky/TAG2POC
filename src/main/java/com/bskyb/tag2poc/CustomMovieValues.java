package com.bskyb.tag2poc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomMovieValues {
    private String movieTitle;
    private String offerStartDate;
    private String offerEndDate;

}
