package com.example.gameproject.api;

import com.example.gameproject.bean.model.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationRepository extends JpaRepository<Relationship,Long> {


}
