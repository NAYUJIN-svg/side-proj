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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KamcoService {

    private final KamcoMapper mapper;
    private final KamcoApiConfig config;
    private final RestTemplate restTemplate = new RestTemplate();

    /* ===============================
       공공데이터 API 호출 + DB 갱신
       (REST / 배치용)
    =============================== */
    @Transactional
    public KamcoResponse getCltrList(int pageNo, int numOfRows) {

        if (!config.isEnabled()) {
            log.warn("KAMCO API 비활성화 상태");
            return new KamcoResponse(List.of());
        }

        log.info("KAMCO CLTR API 호출 시작");

        mapper.dropCltrTable();
        mapper.createCltrTable();

        String uri = UriComponentsBuilder
                .fromHttpUrl(config.getBaseUrl())
                .queryParam("serviceKey", config.getServiceKey())
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .build(true)
                .toUriString();

        String xml = restTemplate.getForObject(uri, String.class);
        List<KamcoItem> items = XmlParser.parse(xml);

        items.forEach(mapper::insertByCltrNm);

        log.info("CLTR 데이터 저장 완료: {}건", items.size());

        return buildResponse(items);
    }

    /* ===============================
       DB 백업 데이터 조회
       (Thymeleaf 화면용)
    =============================== */
    @Transactional(readOnly = true)
    public KamcoResponse getBackupCltrList() {

        List<KamcoItem> items = mapper.findAllCltr();
        return buildResponse(items);
    }

    /* ===============================
       공통 응답 변환
    =============================== */
    private KamcoResponse buildResponse(List<KamcoItem> items) {
        return new KamcoResponse(
                items.stream()
                        .map(KamcoItemMapper::toResponse)
                        .collect(Collectors.toList())
        );
    }
}
