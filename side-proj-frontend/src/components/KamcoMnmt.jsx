import { useState, useEffect } from 'react';

const KamcoMnmt = () => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [pageNo, setPageNo] = useState(1);

  const fetchData = async (useBackup = true) => {
    setLoading(true);
    setError(null);
    try {
      if (useBackup) {
        // 백업 데이터 불러오기
        const backupData = JSON.parse(localStorage.getItem('kamco_backup_mnmt') || '[]');
        setItems(backupData);
        setLoading(false);
        return;
      }
      const url = 'http://localhost:8001/api/kamco/mnmt';
      
      const response = await fetch(url, {
        headers: {
          'Accept': 'application/json'
        }
      });
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      console.log('받은 데이터:', data);
      console.log('데이터 타입:', typeof data);
      console.log('데이터 키들:', Object.keys(data));
      const itemsArray = data.Items || data.items || [];
      if (itemsArray.length > 0) {
        console.log('첫 번째 아이템:', itemsArray[0]);
        console.log('첫 번째 아이템 키들:', Object.keys(itemsArray[0]));
      }
      setItems(itemsArray);
      
      // 백업 데이터에 누적 저장
      const existingBackup = JSON.parse(localStorage.getItem('kamco_backup_mnmt') || '[]');
      const newBackup = [...existingBackup, ...itemsArray];
      localStorage.setItem('kamco_backup_mnmt', JSON.stringify(newBackup));
    } catch (error) {
      console.error('데이터 로딩 실패:', error);
      setError(error.message);
      setItems([]);
    } finally {
      setLoading(false);
    }
  };

  const clearBackup = () => {
    localStorage.removeItem('kamco_backup_mnmt');
    setItems([]);
  };

  useEffect(() => {
    fetchData();
  }, [pageNo]);

  return (
    <div className="kamco-container">
      <h2>물건관리번호 목록</h2>
      
      <div className="controls">
        <button onClick={() => fetchData(true)}>백업 데이터</button>
        <button onClick={() => fetchData(false)}>새 데이터 가져오기</button>
        <button onClick={clearBackup}>백업 데이터 삭제</button>
      </div>

      {loading ? (
        <div>로딩 중...</div>
      ) : error ? (
        <div style={{ color: 'red' }}>오류: {error}</div>
      ) : (
        <table className="kamco-table">
          <thead>
            <tr>
              <th>물건관리번호</th>
              <th>공매번호</th>
              <th>최저입찰가</th>
              <th>감정평가금액</th>
              <th>입찰시작일시</th>
            </tr>
          </thead>
          <tbody>
            {items?.map((item, index) => (
              <tr key={index}>
                <td>{item.Cltr_MNMT_NO}</td>
                <td>{item.Pbct_NO}</td>
                <td>{item.Min_BID_PRC?.toLocaleString()}원</td>
                <td>{item.Apsl_ASES_AVG_AMT?.toLocaleString()}원</td>
                <td>{item.Pbct_BEGN_DTM}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default KamcoMnmt;