import React, { useEffect, useState, useCallback } from 'react';
import { kamcoApi } from '../api/kamcoApi';
import {
  formatPrice,
  formatDateTime,
  formatPercent,
  formatStatus,
  formatViewCount,
  formatFailureCount,
  truncateText
} from '../utils/formatUtils';
import './KamcoHistory.css';

const KamcoHistory = () => {
  const [historyData, setHistoryData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [pageInfo, setPageInfo] = useState({
    pageNo: 1,
    numOfRows: 10,
    totalCount: 0
  });
  const [searchMnmtNo, setSearchMnmtNo] = useState('');

  /* ===============================
     공통 조회
  =============================== */
  const fetchHistoryData = useCallback(
    async (pageNo = 1, numOfRows = 10, cltrMnmtNo = null) => {
      setLoading(true);
      setError(null);

      try {
        const res = await kamcoApi.getHistoryData(pageNo, numOfRows, cltrMnmtNo);
        const data = res.data || {};

        setHistoryData(Array.isArray(data.items) ? data.items : []);
        setPageInfo({
          pageNo: data.pageNo ?? pageNo,
          numOfRows: data.numOfRows ?? numOfRows,
          totalCount: data.totalCount ?? 0
        });
      } catch (e) {
        console.error(e);
        setError(e.message || '조회 실패');
        setHistoryData([]);
        setPageInfo({ pageNo: 1, numOfRows: 10, totalCount: 0 });
      } finally {
        setLoading(false);
      }
    },
    []
  );

  /* ===============================
     최초 로딩
  =============================== */
  useEffect(() => {
    fetchHistoryData(1, 10);
  }, [fetchHistoryData]);

  /* ===============================
     새로고침(재조회) - update API 없음(A안)
  =============================== */
  const reloadLatestData = () => {
    fetchHistoryData(1, pageInfo.numOfRows, searchMnmtNo.trim() || null);
  };

  /* ===============================
     전체 삭제
  =============================== */
  const deleteAllHistory = async () => {
    if (!window.confirm('모든 이력 데이터를 삭제하시겠습니까?')) return;

    setLoading(true);
    setError(null);

    try {
      await kamcoApi.deleteAllHistory();
      setHistoryData([]);
      setPageInfo({ pageNo: 1, numOfRows: 10, totalCount: 0 });
    } catch (e) {
      console.error(e);
      setError(e.message || '삭제 실패');
    } finally {
      setLoading(false);
    }
  };

  /* ===============================
     검색 / 페이징
  =============================== */
  const handleSearch = (e) => {
    e.preventDefault();
    fetchHistoryData(1, pageInfo.numOfRows, searchMnmtNo.trim() || null);
  };

  const handlePageChange = (newPage) => {
    fetchHistoryData(newPage, pageInfo.numOfRows, searchMnmtNo.trim() || null);
  };

  const handlePageSizeChange = (e) => {
    fetchHistoryData(1, Number(e.target.value), searchMnmtNo.trim() || null);
  };

  /* ===============================
     렌더
  =============================== */
  return (
    <div className="kamco-history">
      <h2>KAMCO 이력조회</h2>

      {/* 버튼 */}
      <div className="control-buttons">
        <button onClick={reloadLatestData} disabled={loading}>
          새로고침
        </button>
        <button onClick={deleteAllHistory} disabled={loading}>
          전체 삭제
        </button>
      </div>

      {/* 검색 */}
      <form onSubmit={handleSearch}>
        <input
          placeholder="물건관리번호 검색"
          value={searchMnmtNo}
          onChange={(e) => setSearchMnmtNo(e.target.value)}
        />
        <button type="submit" disabled={loading}>
          검색
        </button>
      </form>

      {/* 페이지 크기 */}
      <select value={pageInfo.numOfRows} onChange={handlePageSizeChange}>
        <option value={10}>10개</option>
        <option value={20}>20개</option>
        <option value={50}>50개</option>
      </select>

      {loading && <p>로딩 중...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {/* 테이블 */}
      <div className="history-table-container">
        <table className="history-table">
          <thead>
            <tr>
              <th>공매번호</th>
              <th>물건관리번호</th>
              <th>상태</th>
              <th>최저입찰가</th>
              <th>수수료율</th>
              <th>입찰시작</th>
              <th>입찰마감</th>
              <th>유찰</th>
              <th>조회수</th>
              <th>등록일</th>
            </tr>
          </thead>

          <tbody>
            {historyData.map((raw, idx) => {
              // 🔥 백엔드 최종 응답은 camelCase 기준이지만, 혹시 모를 키 변형도 안전 처리
              const item = {
                pbctNo: raw.pbctNo ?? raw.PBCT_NO,
                cltrMnmtNo: raw.cltrMnmtNo ?? raw.CLTR_MNMT_NO,
                pbctCltrStatNm: raw.pbctCltrStatNm ?? raw.PBCT_CLTR_STAT_NM,
                minBidPrc: raw.minBidPrc ?? raw.MIN_BID_PRC,
                feeRate: raw.feeRate ?? raw.FEE_RATE,
                // 아래 필드들은 백엔드에 없으면 null로 처리 (표시는 공란)
                pbctBegnDtm: raw.pbctBegnDtm ?? raw.PBCT_BEGN_DTM ?? null,
                pbctClsDtm: raw.pbctClsDtm ?? raw.PBCT_CLS_DTM ?? null,
                uscbdCnt: raw.uscbdCnt ?? raw.USCBD_CNT ?? null,
                iqryCnt: raw.iqryCnt ?? raw.IQRY_CNT ?? null,
                createdAt: raw.createdAt ?? raw.CREATED_AT
              };

              const status = formatStatus(item.pbctCltrStatNm);

              return (
                <tr key={idx}>
                  <td>{item.pbctNo}</td>
                  <td>{truncateText(item.cltrMnmtNo, 20)}</td>
                  <td className={status.className}>{status.text}</td>
                  <td>{formatPrice(item.minBidPrc)}</td>
                  <td>{formatPercent(item.feeRate)}</td>
                  <td>{formatDateTime(item.pbctBegnDtm)}</td>
                  <td>{formatDateTime(item.pbctClsDtm)}</td>
                  <td>{formatFailureCount(item.uscbdCnt)}</td>
                  <td>{formatViewCount(item.iqryCnt)}</td>
                  <td>{formatDateTime(item.createdAt)}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

      {/* 페이지네이션 */}
      {pageInfo.totalCount > pageInfo.numOfRows && (
        <div className="pagination">
          <button
            onClick={() => handlePageChange(pageInfo.pageNo - 1)}
            disabled={pageInfo.pageNo <= 1}
          >
            이전
          </button>

          <span>
            {pageInfo.pageNo} / {Math.ceil(pageInfo.totalCount / pageInfo.numOfRows)}
          </span>

          <button
            onClick={() => handlePageChange(pageInfo.pageNo + 1)}
            disabled={
              pageInfo.pageNo >= Math.ceil(pageInfo.totalCount / pageInfo.numOfRows)
            }
          >
            다음
          </button>
        </div>
      )}

      {!loading && pageInfo.totalCount === 0 && (
        <p>조회된 이력 데이터가 없습니다.</p>
      )}
    </div>
  );
};

export default KamcoHistory;
