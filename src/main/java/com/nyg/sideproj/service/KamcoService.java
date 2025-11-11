package com.nyg.sideproj.service;

import com.nyg.sideproj.dto.KamcoRequest;
import com.nyg.sideproj.dto.KamcoResponse;
import com.nyg.sideproj.entity.KamcoData;
import com.nyg.sideproj.mapper.KamcoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KamcoService {

    private final KamcoMapper kamcoMapper;
    private final WebClient.Builder webClientBuilder;

    @Value("${kamco.api.url}")
    private String apiUrl;

    @Value("${kamco.api.serviceKey}")
    private String serviceKey;

    public KamcoResponse getRawData(KamcoRequest request) {
        log.info("[getRawData] Request: pageNo={}, numOfRows={}, dpslMtdCd={}, sido={}, offset={}", 
                request.getPageNo(), request.getNumOfRows(), request.getDpslMtdCd(), request.getSido(), request.getOffset());
        log.info("[getRawData] Calling Mapper.findRawData...");
        List<KamcoData> items = kamcoMapper.findRawData(request);
        log.info("[getRawData] Mapper returned {} items", items.size());
        if (!items.isEmpty()) {
            log.info("[getRawData] First item: {}", items.get(0));
        }
        return createResponse(items);
    }

    public KamcoResponse getByMnmtNo(KamcoRequest request) {
        log.info("[getByMnmtNo] Request: pageNo={}, numOfRows={}, dpslMtdCd={}, sido={}, offset={}", 
                request.getPageNo(), request.getNumOfRows(), request.getDpslMtdCd(), request.getSido(), request.getOffset());
        log.info("[getByMnmtNo] Calling Mapper.findByMnmtNo...");
        List<KamcoData> items = kamcoMapper.findByMnmtNo(request);
        log.info("[getByMnmtNo] Mapper returned {} items", items.size());
        if (!items.isEmpty()) {
            log.info("[getByMnmtNo] First item: {}", items.get(0));
        }
        return createResponse(items);
    }

    public KamcoResponse getByCltrNm(KamcoRequest request) {
        log.info("[getByCltrNm] Request: pageNo={}, numOfRows={}, dpslMtdCd={}, sido={}, offset={}", 
                request.getPageNo(), request.getNumOfRows(), request.getDpslMtdCd(), request.getSido(), request.getOffset());
        log.info("[getByCltrNm] Calling Mapper.findByCltrNm...");
        List<KamcoData> items = kamcoMapper.findByCltrNm(request);
        log.info("[getByCltrNm] Mapper returned {} items", items.size());
        if (!items.isEmpty()) {
            log.info("[getByCltrNm] First item: {}", items.get(0));
        }
        return createResponse(items);
    }

    public int syncFromApi(int pageNo, int numOfRows) {
        log.info("[syncFromApi] Calling Kamco API: pageNo={}, numOfRows={}", pageNo, numOfRows);
        
        try {
            String xmlResponse = webClientBuilder.build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("openapi.onbid.co.kr")
                            .path("/openapi/services/KamcoPblsalThingInquireSvc/getKamcoPbctCltrList")
                            .queryParam("serviceKey", serviceKey)
                            .queryParam("pageNo", pageNo)
                            .queryParam("numOfRows", numOfRows)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("[syncFromApi] API Response received");
            
            List<KamcoData> dataList = parseXmlResponse(xmlResponse);
            log.info("[syncFromApi] Parsed {} items from XML", dataList.size());
            
            int savedCount = 0;
            for (KamcoData data : dataList) {
                try {
                    kamcoMapper.insertRawData(data);
                    kamcoMapper.insertByMnmtNo(data);
                    kamcoMapper.insertByCltrNm(data);
                    savedCount++;
                    log.info("[syncFromApi] Saved to all tables: mnmtNo={}, cltrNm={}", data.getMnmtNo(), data.getCltrNm());
                } catch (Exception e) {
                    log.error("[syncFromApi] Failed to save item: {}", data.getMnmtNo(), e);
                }
            }
            
            log.info("[syncFromApi] Sync completed: {} items saved", savedCount);
            return savedCount;
        } catch (Exception e) {
            log.error("[syncFromApi] Error calling Kamco API", e);
            throw new RuntimeException("Failed to sync data from Kamco API", e);
        }
    }
    
    private List<KamcoData> parseXmlResponse(String xml) {
        List<KamcoData> dataList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            
            NodeList items = doc.getElementsByTagName("item");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            
            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                KamcoData data = new KamcoData();
                
                data.setMnmtNo(getTagValue("bidMnmtNo", item));
                data.setCltrNm(getTagValue("cltrNm", item));
                data.setDpslMtdCd(getTagValue("dpslMtdCd", item));
                data.setNmrdAdrs(getTagValue("nmrdAdrs", item));
                
                String begnDtm = getTagValue("pbctBegnDtm", item);
                if (begnDtm != null && !begnDtm.isEmpty()) {
                    data.setPbctBegnDtm(LocalDateTime.parse(begnDtm, formatter));
                }
                
                String clsDtm = getTagValue("pbctClsDtm", item);
                if (clsDtm != null && !clsDtm.isEmpty()) {
                    data.setPbctEndDtm(LocalDateTime.parse(clsDtm, formatter));
                }
                
                data.setOtherInfo(getTagValue("ldnmAdrs", item));
                dataList.add(data);
            }
        } catch (Exception e) {
            log.error("[parseXmlResponse] Error parsing XML", e);
        }
        return dataList;
    }
    
    private String getTagValue(String tag, Element element) {
        try {
            NodeList nodeList = element.getElementsByTagName(tag);
            if (nodeList.getLength() > 0) {
                return nodeList.item(0).getTextContent();
            }
        } catch (Exception e) {
            log.warn("[getTagValue] Failed to get tag: {}", tag);
        }
        return null;
    }

    private KamcoResponse createResponse(List<KamcoData> items) {
        KamcoResponse response = new KamcoResponse();
        response.setResultCode("00");
        response.setResultMsg("NORMAL SERVICE.");
        response.setItems(items);
        return response;
    }
}
