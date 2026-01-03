package com.nyg.sideproj.mapper;

import com.nyg.sideproj.entity.KamcoItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * KAMCO MyBatis Mapper
 */
@Mapper
public interface KamcoMapper {

    List<KamcoItem> findAllCltr();
    void insertByCltrNm(KamcoItem item);
    void dropCltrTable();
    void createCltrTable();
}