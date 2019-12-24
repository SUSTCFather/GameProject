package com.example.gameproject.mapper;

import com.example.gameproject.bean.model.GameRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRecordRepository extends JpaRepository<GameRecord,Long> {


}
