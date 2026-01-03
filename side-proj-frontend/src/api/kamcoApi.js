import axios from 'axios';

// Axios 인스턴스
const apiClient = axios.create({
  baseURL: 'http://localhost:8001/api/kamco',
  timeout: 60000,
  headers: {
    'Content-Type': 'application/json',
    Accept: 'application/json'
  }
});

// 요청 인터셉터
apiClient.interceptors.request.use(
  (config) => {
    console.log(
      'API 요청:',
      config.method?.toUpperCase(),
      config.url,
      config.params || ''
    );
    return config;
  },
  (error) => Promise.reject(error)
);

// ✅ 응답 정규화: Items/items, PageNo/pageNo 등 전부 커버
const normalizeHistoryResponse = (data) => {
  if (!data) return data;

  const items = data.items ?? data.Items;
  const pageNo = data.pageNo ?? data.PageNo;
  const numOfRows = data.numOfRows ?? data.NumOfRows;
  const totalCount = data.totalCount ?? data.TotalCount;

  // history 응답처럼 보이는 경우만 정규화
  if (Array.isArray(items) || typeof totalCount === 'number') {
    return {
      items: Array.isArray(items) ? items : [],
      pageNo: pageNo ?? 1,
      numOfRows: numOfRows ?? 10,
      totalCount: totalCount ?? 0
    };
  }

  return data;
};

// 응답 인터셉터
apiClient.interceptors.response.use(
  (response) => {
    response.data = normalizeHistoryResponse(response.data);
    return response;
  },
  (error) => {
    const errorMessage =
      error.response?.data?.message ||
      error.message ||
      '서버 오류가 발생했습니다';

    return Promise.reject(new Error(errorMessage));
  }
);

// ✅ KAMCO API (백엔드에 실제 존재하는 것만)
export const kamcoApi = {
  getCltrList: (pageNo = 1, numOfRows = 10) =>
    apiClient.get('/cltr', { params: { pageNo, numOfRows } }),

  getHistoryData: (pageNo = 1, numOfRows = 10, cltrMnmtNo = null) =>
    apiClient.get('/history', {
      params: { pageNo, numOfRows, ...(cltrMnmtNo ? { cltrMnmtNo } : {}) }
    }),

  deleteAllHistory: () => apiClient.delete('/history')
};

export default apiClient;
