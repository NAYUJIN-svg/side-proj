package com.nyg.sideproj.mapper;

import com.nyg.sideproj.entity.KamcoHistoryItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KamcoHistoryMapper {

    void createHistoryTable();

    int countAll();

    List<KamcoHistoryItem> findAll(int limit, int offset);

    KamcoHistoryItem findByPbctNo(String pbctNo);

    void insert(KamcoHistoryItem history);

    void deleteAll();
}
