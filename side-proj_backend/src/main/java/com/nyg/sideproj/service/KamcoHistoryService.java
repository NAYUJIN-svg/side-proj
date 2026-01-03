package com.nyg.sideproj.service;

import com.nyg.sideproj.config.KamcoApiConfig;
import com.nyg.sideproj.dto.request.KamcoHistoryRequest;
import com.nyg.sideproj.dto.response.KamcoHistoryResponse;
import com.nyg.sideproj.entity.KamcoHistoryItem;
import com.nyg.sideproj.mapper.KamcoHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KamcoHistoryService {

    private final KamcoHistoryMapper historyMapper;
    private final KamcoApiConfig config;

    /* ===============================
       이력 조회 (정식)
    =============================== */
    @Transactional(readOnly = true)
    public KamcoHistoryResponse getHistoryData(KamcoHistoryRequest request) {

        int pageNo = request.getPageNo();
        int size = request.getNumOfRows();
        int offset = (pageNo - 1) * size;

        List<KamcoHistoryItem> list =
                historyMapper.findAll(size, offset);

        int total = historyMapper.countAll();

        // ❗ new 생성자 ❌ / of()만 사용
        return KamcoHistoryResponse.of(list, total, pageNo, size);
    }

    /* ===============================
       테스트용: 테이블 자동 대응 조회
    =============================== */
    @Transactional
    public KamcoHistoryResponse getHistoryDataWithAutoInit(KamcoHistoryRequest request) {
        try {
            return getHistoryData(request);
        } catch (Exception e) {
            log.warn("kamco_history 테이블 미존재 → 빈 응답 반환");
            return KamcoHistoryResponse.of(
                    List.of(),
                    0,
                    request.getPageNo(),
                    request.getNumOfRows()
            );
        }
    }

    /* ===============================
       테스트용: 샘플 데이터 생성
    =============================== */
    @Transactional
    public KamcoHistoryResponse createSampleData() {

        List<KamcoHistoryItem> samples = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            KamcoHistoryItem item = new KamcoHistoryItem();
            item.setPbctNo("TEST-PBCT-" + i);
            item.setCltrMnmtNo("TEST-CLTR-" + i);
            item.setPbctCltrStatNm("테스트상태");
            item.setMinBidPrc(100_000_000L + i);
            item.setFeeRate("3%");
            item.setCreatedAt(LocalDateTime.now());

            historyMapper.insert(item);
            samples.add(item);
        }

        return KamcoHistoryResponse.of(
                samples,
                samples.size(),
                1,
                samples.size()
        );
    }

    @Transactional
    public void deleteAll() {
        historyMapper.deleteAll();
    }
}
