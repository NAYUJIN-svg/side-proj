package com.nyg.sideproj.service;

import com.nyg.sideproj.dto.KamcoRequest;
import com.nyg.sideproj.dto.KamcoResponse;
import com.nyg.sideproj.mapper.KamcoMapper;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KamcoService {

    private final KamcoMapper kamcoMapper;

    @Value("${kamco.api.serviceKey}") // 환경변수 이름과 매핑
    private String serviceKey;

    public KamcoResponse getRawData(KamcoRequest request) {
        request.setServiceKey(serviceKey); // 여기에 자동 주입
        return new KamcoResponse() {{
            setResultCode("00");
            setResultMsg("NORMAL SERVICE.");
            setItems(kamcoMapper.findRawData(request));
        }};
    }

    public KamcoResponse getByMnmtNo(KamcoRequest request) {
        request.setServiceKey(serviceKey);
        return new KamcoResponse() {{
            setResultCode("00");
            setResultMsg("NORMAL SERVICE.");
            setItems(kamcoMapper.findByMnmtNo(request));
        }};
    }

    public KamcoResponse getByCltrNm(KamcoRequest request) {
        request.setServiceKey(serviceKey);
        return new KamcoResponse() {{
            setResultCode("00");
            setResultMsg("NORMAL SERVICE.");
            setItems(kamcoMapper.findByCltrNm(request));
        }};
    }
}
