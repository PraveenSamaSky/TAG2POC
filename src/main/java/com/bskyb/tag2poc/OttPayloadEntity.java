package com.bskyb.tag2poc;

import com.bskyb.tag.OttPayload;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "OttPayloadEntity_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class OttPayloadEntity implements Serializable {

    @Id
    private  String movie_contentId;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private OttPayload ott_Payload;
}
