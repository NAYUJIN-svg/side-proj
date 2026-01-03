package com.nyg.sideproj.dto.response;

import com.nyg.sideproj.entity.KamcoHistoryItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class KamcoHistoryResponse {

    private List<HistoryItem> items;
    private int totalCount;
    private int pageNo;
    private int numOfRows;

    /* ===== Factory Method ===== */
    public static KamcoHistoryResponse of(
            List<KamcoHistoryItem> entities,
            int totalCount,
            int pageNo,
            int numOfRows
    ) {
        return new KamcoHistoryResponse(
                entities.stream()
                        .map(HistoryItem::from)
                        .collect(Collectors.toList()),
                totalCount,
                pageNo,
                numOfRows
        );
    }

    /* ===== Response Item ===== */
    @Data
    @AllArgsConstructor
    public static class HistoryItem {

        private String pbctNo;
        private String cltrMnmtNo;
        private String pbctCltrStatNm;
        private Long minBidPrc;
        private String feeRate;
        private LocalDateTime createdAt;

        public static HistoryItem from(KamcoHistoryItem e) {
            return new HistoryItem(
                    e.getPbctNo(),
                    e.getCltrMnmtNo(),
                    e.getPbctCltrStatNm(),
                    e.getMinBidPrc(),
                    e.getFeeRate(),
                    e.getCreatedAt()
            );
        }
    }
}
