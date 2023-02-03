package com.bskyb.tag2poc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface PayloadRepository extends JpaRepository<OttPayloadEntity, String> {
}
