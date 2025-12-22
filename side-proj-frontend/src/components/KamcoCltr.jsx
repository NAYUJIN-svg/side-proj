import { useState, useEffect } from 'react';

const KamcoCltr = () => {
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
        const backupData = JSON.parse(localStorage.getItem('kamco_backup_cltr') || '[]');
        setItems(backupData);
        setLoading(false);
        return;
      }
      const url = 'http://localhost:8001/api/kamco/cltr';
      
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
      const itemsArray = data.Items || data.items || [];
      if (itemsArray.length > 0) {
        console.log('첫 번째 아이템:', itemsArray[0]);
        console.log('첫 번째 아이템 키들:', Object.keys(itemsArray[0]));
      }
      setItems(itemsArray);
      
      // 백업 데이터에 누적 저장
      const existingBackup = JSON.parse(localStorage.getItem('kamco_backup_cltr') || '[]');
      const newBackup = [...existingBackup, ...itemsArray];
      localStorage.setItem('kamco_backup_cltr', JSON.stringify(newBackup));
    } catch (error) {
      console.error('데이터 로딩 실패:', error);
      setError(error.message);
      setItems([]);
    } finally {
      setLoading(false);
    }
  };

  const clearBackup = () => {
    localStorage.removeItem('kamco_backup_cltr');
    setItems([]);
  };

  useEffect(() => {
    fetchData();
  }, [pageNo]);

  return (
    <div className="kamco-container">
      <h2>물건명 목록</h2>
      
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
              <th>물건명</th>
              <th>공매번호</th>
              <th>최저입찰가</th>
              <th>입찰마감일시</th>
              <th>유찰횟수</th>
            </tr>
          </thead>
          <tbody>
            {items?.map((item, index) => (
              <tr key={index}>
                <td>{item.Cltr_NM}</td>
                <td>{item.Pbct_NO}</td>
                <td>{item.Min_BID_PRC?.toLocaleString()}원</td>
                <td>{item.Pbct_CLS_DTM}</td>
                <td>{item.Uscbd_CNT}회</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default KamcoCltr;