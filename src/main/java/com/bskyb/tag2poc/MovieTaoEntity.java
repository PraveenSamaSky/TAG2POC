package com.bskyb.tag2poc;

import com.bskyb.tag.MovieTao;
import com.bskyb.tag.OttPayload;
import com.bskyb.tag.ddi.models.Movie;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "MovieTaoEntity_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class MovieTaoEntity implements Serializable {

    @Id
    private  String movie_contentId;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Object asset;

}
