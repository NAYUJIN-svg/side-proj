package com.nyg.sideproj.service;

import com.nyg.sideproj.dto.request.KamcoHistoryRequest;
import com.nyg.sideproj.dto.response.KamcoHistoryResponse;
import com.nyg.sideproj.mapper.KamcoHistoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * KAMCO 이력조회 서비스 테스트
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class KamcoHistoryServiceTest {

    @Autowired
    private KamcoHistoryService historyService;

    @Autowired
    private KamcoHistoryMapper historyMapper;

    @BeforeEach
    void setUp() {
        // 테스트 시작 전 항상 초기화
        historyMapper.deleteAll();
    }

    @Test
    void 이력_조회_기본_테스트() {
        // given
        KamcoHistoryRequest request = new KamcoHistoryRequest();
        request.setPageNo(1);
        request.setNumOfRows(10);

        // when
        KamcoHistoryResponse response =
                historyService.getHistoryDataWithAutoInit(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getItems());
        assertEquals(0, response.getItems().size());
        assertEquals(1, response.getPageNo());
        assertEquals(10, response.getNumOfRows());
    }

    @Test
    void 샘플_데이터_생성_테스트() {
        // when
        KamcoHistoryResponse response =
                historyService.createSampleData();

        // then
        assertNotNull(response);
        assertNotNull(response.getItems());
        assertEquals(10, response.getItems().size());
        assertEquals(10, response.getTotalCount());
    }

    @Test
    void 샘플_데이터_생성_후_조회_테스트() {
        // given
        historyService.createSampleData();

        KamcoHistoryRequest request = new KamcoHistoryRequest();
        request.setPageNo(1);
        request.setNumOfRows(5);

        // when
        KamcoHistoryResponse response =
                historyService.getHistoryData(request);

        // then
        assertNotNull(response);
        assertEquals(5, response.getItems().size());
        assertEquals(10, response.getTotalCount());
        assertEquals(1, response.getPageNo());
        assertEquals(5, response.getNumOfRows());
    }

//    @Test
//    void 테이블_존재_확인_메서드_테스트() {
//        // when
//        int tableCount = historyMapper.checkTableExists();
//
//        // then
//        assertTrue(tableCount >= 0);
//    }
}
