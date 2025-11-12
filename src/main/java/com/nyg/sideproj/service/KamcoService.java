package com.nyg.sideproj.service;

import com.nyg.sideproj.config.KamcoApiConfig;
import com.nyg.sideproj.dto.response.KamcoResponse;
import com.nyg.sideproj.entity.KamcoItem;
import com.nyg.sideproj.mapper.KamcoMapper;
import com.nyg.sideproj.util.KamcoItemMapper;
import com.nyg.sideproj.util.XmlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KamcoService {
    
    private final KamcoMapper mapper;
    private final KamcoApiClient apiClient;
    private final KamcoApiConfig config;

    @Transactional
    public KamcoResponse getMnmtList(int pageNo, int numOfRows) {
        log.info("=== /mnmt API 시작 ===");
        recreateTable("kamco_by_mnmt_no", mapper::dropMnmtTable, mapper::createMnmtTable);
        List<KamcoItem> items = fetchUniqueItems(KamcoItem::getCLTR_MNMT_NO);
        saveItems(items, mapper::insertByMnmtNo);
        log.info("=== /mnmt API 종료: {} 건 ===", items.size());
        return buildResponse(items, true);
    }

    @Transactional
    public KamcoResponse getCltrList(int pageNo, int numOfRows) {
        log.info("=== /cltr API 시작 ===");
        recreateTable("kamco_by_cltr_nm", mapper::dropCltrTable, mapper::createCltrTable);
        List<KamcoItem> items = fetchUniqueItems(KamcoItem::getCLTR_NM);
        saveItems(items, mapper::insertByCltrNm);
        log.info("=== /cltr API 종료: {} 건 ===", items.size());
        return buildResponse(items, false);
    }

    private void recreateTable(String tableName, Runnable drop, Runnable create) {
        drop.run();
        create.run();
        log.debug("{} 테이블 재생성 완료", tableName);
    }
    
    private List<KamcoItem> fetchUniqueItems(java.util.function.Function<KamcoItem, String> keyExtractor) {
        List<KamcoItem> result = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        
        for (int page = 1; page <= config.getMaxPages() && result.size() < config.getLimit(); page++) {
            processPage(page, result, seen, keyExtractor);
        }
        
        return result;
    }
    
    private void processPage(int page, List<KamcoItem> result, Set<String> seen,
                            java.util.function.Function<KamcoItem, String> keyExtractor) {
        String xml = apiClient.fetchData(page, config.getItemsPerPage());
        List<KamcoItem> items = parseXml(xml);
        filterAndAddItems(items, result, seen, keyExtractor);
    }
    
    private List<KamcoItem> parseXml(String xml) {
        List<KamcoItem> items = XmlParser.parse(xml);
        
        if (items.isEmpty()) {
            String errorMsg = XmlParser.getErrorMessage(xml);
            if (errorMsg != null) {
                log.warn("API 오류: {}", errorMsg);
            }
        }
        return items;
    }
    
    private void filterAndAddItems(List<KamcoItem> items, List<KamcoItem> result, Set<String> seen,
                                  java.util.function.Function<KamcoItem, String> keyExtractor) {
        items.stream()
            .filter(this::isValid)
            .forEach(item -> addIfUnique(result, seen, item, keyExtractor));
    }
    
    private void addIfUnique(List<KamcoItem> result, Set<String> seen, KamcoItem item,
                            java.util.function.Function<KamcoItem, String> keyExtractor) {
        String key = keyExtractor.apply(item);
        if (key != null && seen.add(key) && result.size() < config.getLimit()) {
            result.add(item);
        }
    }
    

    
    private boolean isValid(KamcoItem item) {
        return (item.getCLTR_MNMT_NO() != null && !item.getCLTR_MNMT_NO().trim().isEmpty())
            || (item.getCLTR_NM() != null && !item.getCLTR_NM().trim().isEmpty());
    }



    private void saveItems(List<KamcoItem> items, java.util.function.Consumer<KamcoItem> insertFunc) {
        items.forEach(item -> {
            try {
                insertFunc.accept(item);
            } catch (Exception e) {
                log.error("INSERT 실패: {}", e.getMessage());
            }
        });
    }
    


    private KamcoResponse buildResponse(List<KamcoItem> items, boolean isMnmt) {
        return new KamcoResponse(items.stream()
            .map(item -> convertToDto(item, isMnmt))
            .collect(Collectors.toList()));
    }
    
    private KamcoResponse.Item convertToDto(KamcoItem item, boolean isMnmt) {
        KamcoResponse.Item dto = KamcoItemMapper.toResponse(item);
        if (isMnmt) {
            dto.setCLTR_NM(null);
        } else {
            dto.setCLTR_MNMT_NO(null);
        }
        return dto;
    }
    

}
