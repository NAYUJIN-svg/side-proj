package com.nyg.sideproj.mapper;

import com.nyg.sideproj.dto.KamcoRequest;
import com.nyg.sideproj.dto.KamcoResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KamcoMapper {
    // 원본 데이터 조회
    List<KamcoResponse.KamcoItem> findRawData(@Param("request") KamcoRequest request);

    // 물건관리번호 기준 중복 제거 조회
    List<KamcoResponse.KamcoItem> findByMnmtNo(@Param("request") KamcoRequest request);

    // 물건명 기준 중복 제거 조회
    List<KamcoResponse.KamcoItem> findByCltrNm(@Param("request") KamcoRequest request);
}
