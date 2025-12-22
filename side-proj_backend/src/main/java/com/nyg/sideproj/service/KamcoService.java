package com.nyg.sideproj.service;

import com.nyg.sideproj.config.KamcoApiConfig;
import com.nyg.sideproj.dto.response.KamcoResponse;
import com.nyg.sideproj.entity.KamcoItem;
import com.nyg.sideproj.mapper.KamcoMapper;
import com.nyg.sideproj.util.KamcoItemMapper;
import com.nyg.sideproj.util.XmlParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;


/**
 * KAMCO 공공데이터 API 서비스
 * - 물건관리번호/물건명 기준으로 데이터 조회 및 DB 동기화
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Data

public class KamcoService {
    
    private final KamcoMapper mapper;
    private final KamcoApiClient apiClient;
    private final KamcoApiConfig config;

    /**
     * 물건관리번호 기준 데이터 조회
     * 1. 테이블 재생성 (DROP + CREATE)
     * 2. API 호출하여 중복 제거된 10건 수집
     * 3. DB에 저장
     * 4. XML 응답 반환 (CLTR_NM 제외)
     */
    @Transactional
    public KamcoResponse getMnmtList(int pageNo, int numOfRows) {
        log.info("=== /mnmt API 시작 ===");
        recreateTable("kamco_by_mnmt_no", mapper::dropMnmtTable, mapper::createMnmtTable);
        List<KamcoItem> items = fetchUniqueItems(KamcoItem::getCLTR_MNMT_NO);
        saveItems(items, mapper::insertByMnmtNo);
        log.info("=== /mnmt API 종료: {} 건 ===", items.size());
        return buildResponse(items, true);
    }

    /**
     * 물건명 기준 데이터 조회
     * 1. 테이블 재생성 (DROP + CREATE)
     * 2. API 호출하여 중복 제거된 10건 수집
     * 3. DB에 저장
     * 4. XML 응답 반환 (CLTR_MNMT_NO 제외)
     */
    @Transactional
    public KamcoResponse getCltrList(int pageNo, int numOfRows) {
        log.info("=== /cltr API 시작 ===");
        recreateTable("kamco_by_cltr_nm", mapper::dropCltrTable, mapper::createCltrTable);
        List<KamcoItem> items = fetchUniqueItems(KamcoItem::getCLTR_NM);
        saveItems(items, mapper::insertByCltrNm);
        log.info("=== /cltr API 종료: {} 건 ===", items.size());
        return buildResponse(items, false);
    }

    /**
     * 테이블 재생성: 기존 테이블 삭제 후 새로 생성
     */
    private void recreateTable(String tableName, Runnable drop, Runnable create) {
        drop.run();
        create.run();
        log.debug("{} 테이블 재생성 완료", tableName);
    }
    
    /**
     * 중복 제거된 데이터 수집
     * - 최대 10페이지까지 API 호출
     * - keyExtractor로 중복 판단 (물건관리번호 or 물건명)
     * - 최대 10건 수집
     */
    private List<KamcoItem> fetchUniqueItems(java.util.function.Function<KamcoItem, String> keyExtractor) {
        List<KamcoItem> result = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        
        for (int page = 1; page <= config.getMaxPages() && result.size() < config.getLimit(); page++) {
            processPage(page, result, seen, keyExtractor);
        }
        
        return result;
    }
    
    /**
     * 페이지별 처리: API 호출 -> XML 파싱 -> 필터링 및 추가
     */
    private void processPage(int page, List<KamcoItem> result, Set<String> seen,
                            java.util.function.Function<KamcoItem, String> keyExtractor) {
        String xml = apiClient.fetchData(page, config.getItemsPerPage());
        List<KamcoItem> items = parseXml(xml);
        filterAndAddItems(items, result, seen, keyExtractor);
    }
    
    /**
     * XML 파싱 및 오류 처리
     */
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
    
    /**
     * 유효한 데이터만 필터링하여 중복 제거 후 추가
     */
    private void filterAndAddItems(List<KamcoItem> items, List<KamcoItem> result, Set<String> seen,
                                  java.util.function.Function<KamcoItem, String> keyExtractor) {
        items.stream()
            .filter(this::isValid)
            .forEach(item -> addIfUnique(result, seen, item, keyExtractor));
    }
    
    /**
     * 중복되지 않은 데이터만 추가 (최대 10건)
     */
    private void addIfUnique(List<KamcoItem> result, Set<String> seen, KamcoItem item,
                            java.util.function.Function<KamcoItem, String> keyExtractor) {
        String key = keyExtractor.apply(item);
        if (key != null && seen.add(key) && result.size() < config.getLimit()) {
            result.add(item);
        }
    }
    

    
    /**
     * 데이터 유효성 검증: 물건관리번호 또는 물건명이 있어야 함
     */
    private boolean isValid(KamcoItem item) {
        return (item.getCLTR_MNMT_NO() != null && !item.getCLTR_MNMT_NO().trim().isEmpty())
            || (item.getCLTR_NM() != null && !item.getCLTR_NM().trim().isEmpty());
    }



    /**
     * DB에 데이터 저장 (오류 발생 시 로그만 기록하고 계속)
     */
    private void saveItems(List<KamcoItem> items, java.util.function.Consumer<KamcoItem> insertFunc) {
        items.forEach(item -> {
            try {
                insertFunc.accept(item);
            } catch (Exception e) {
                log.error("INSERT 실패: {}", e.getMessage());
            }
        });
    }
    


    /**
     * XML 응답 생성
     * @param isMnmt true: CLTR_NM 제외, false: CLTR_MNMT_NO 제외
     */
    private KamcoResponse buildResponse(List<KamcoItem> items, boolean isMnmt) {
        return new KamcoResponse(items.stream()
            .map(item -> convertToDto(item, isMnmt))
            .collect(Collectors.toList()));
    }
    
    /**
     * Entity를 DTO로 변환 (테이블별 불필요한 필드 제거)
     */
    private KamcoResponse.Item convertToDto(KamcoItem item, boolean isMnmt) {
        KamcoResponse.Item dto = KamcoItemMapper.toResponse(item);
        if (isMnmt) {
            dto.setCLTR_NM(null);  // mnmt 테이블은 물건명 제외
        } else {
            dto.setCLTR_MNMT_NO(null);  // cltr 테이블은 물건관리번호 제외
        }
        return dto;
    }


    public List<KamcoItem> getByMnmt() {
        return mapper.findAllMnmt();
    }

    public List<KamcoItem> getByCltr() {
        return mapper.findAllCltr();
    }

    /**
     * 물건관리번호 백업 데이터 조회 (DB에서 직접 조회)
     */
    public KamcoResponse getBackupMnmtList() {
        List<KamcoItem> items = mapper.findAllMnmt();
        return buildResponse(items, true);
    }

    /**
     * 물건명 백업 데이터 조회 (DB에서 직접 조회)
     */
    public KamcoResponse getBackupCltrList() {
        List<KamcoItem> items = mapper.findAllCltr();
        return buildResponse(items, false);
    }

}
