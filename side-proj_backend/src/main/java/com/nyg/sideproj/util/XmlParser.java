package com.nyg.sideproj.util;

import com.nyg.sideproj.entity.KamcoItem;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * XML 파싱 유틸리티
 */
@Slf4j
public class XmlParser {
    
    public static List<KamcoItem> parse(String xml) {
        List<KamcoItem> items = new ArrayList<>();
        try {
            if (xml == null || xml.isEmpty()) {
                return items;
            }
            
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            NodeList nodeList = doc.getElementsByTagName("item");
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element el = (Element) nodeList.item(i);
                KamcoItem item = new KamcoItem();
                item.setCltrMnmtNo(getTag("CLTR_MNMT_NO", el));
                item.setPbctNo(toLong(getTag("PBCT_NO", el)));
                item.setCltrNm(getTag("CLTR_NM", el));
                item.setMinBidPrc(toLong(getTag("MIN_BID_PRC", el)));
                item.setPbctBegnDtm(getTag("PBCT_BEGN_DTM", el));
                item.setPbctClsDtm(getTag("PBCT_CLS_DTM", el));
                item.setApslAsesAvgAmt(toLong(getTag("APSL_ASES_AVG_AMT", el)));
                item.setUscbdCnt(toInt(getTag("USCBD_CNT", el)));
                items.add(item);
            }
        } catch (Exception e) {
            log.error("XML 파싱 오류", e);
        }
        return items;
    }
    
    /**
     * API 오류 메시지 추출
     */
    public static String getErrorMessage(String xml) {
        try {
            if (xml == null || xml.isEmpty()) return null;
            
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            NodeList resultCode = doc.getElementsByTagName("resultCode");
            NodeList resultMsg = doc.getElementsByTagName("resultMsg");
            
            if (resultCode.getLength() > 0) {
                String code = resultCode.item(0).getTextContent();
                String msg = resultMsg.getLength() > 0 ? resultMsg.item(0).getTextContent() : "N/A";
                return "resultCode=" + code + ", resultMsg=" + msg;
            }
        } catch (Exception e) {
            log.error("오류 메시지 추출 실패", e);
        }
        return null;
    }
    
    private static String getTag(String tag, Element el) {
        try {
            NodeList nodes = el.getElementsByTagName(tag);
            return nodes.getLength() > 0 ? nodes.item(0).getTextContent() : null;
        } catch (Exception e) {
            return null;
        }
    }
    
    private static Long toLong(String val) {
        try {
            return val != null && !val.isEmpty() ? Long.parseLong(val) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private static Integer toInt(String val) {
        try {
            return val != null && !val.isEmpty() ? Integer.parseInt(val) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}