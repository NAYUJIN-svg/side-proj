/**
 * 공통 포맷팅 유틸리티
 */

// 숫자를 천 단위 콤마로 포맷팅
export const formatNumber = (number) => {
  if (number === null || number === undefined) return '-';
  return new Intl.NumberFormat('ko-KR').format(number);
};

// 가격을 원화 형식으로 포맷팅
export const formatPrice = (price) => {
  if (price === null || price === undefined) return '-';
  return `${formatNumber(price)}원`;
};

// 날짜 문자열을 읽기 쉬운 형식으로 포맷팅
export const formatDateTime = (dateTimeStr) => {
  if (!dateTimeStr) return '-';
  
  try {
    // YYYYMMDDHHMMSS 형식을 YYYY-MM-DD HH:MM:SS로 변환
    if (dateTimeStr.length === 14) {
      const year = dateTimeStr.substring(0, 4);
      const month = dateTimeStr.substring(4, 6);
      const day = dateTimeStr.substring(6, 8);
      const hour = dateTimeStr.substring(8, 10);
      const minute = dateTimeStr.substring(10, 12);
      const second = dateTimeStr.substring(12, 14);
      
      return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
    }
    
    // 이미 포맷된 날짜인 경우 그대로 반환
    return dateTimeStr;
  } catch (error) {
    console.error('날짜 포맷팅 오류:', error);
    return dateTimeStr;
  }
};

// 날짜만 포맷팅 (시간 제외)
export const formatDate = (dateTimeStr) => {
  if (!dateTimeStr) return '-';
  
  try {
    if (dateTimeStr.length === 14) {
      const year = dateTimeStr.substring(0, 4);
      const month = dateTimeStr.substring(4, 6);
      const day = dateTimeStr.substring(6, 8);
      
      return `${year}-${month}-${day}`;
    }
    
    // 이미 포맷된 날짜에서 날짜 부분만 추출
    return dateTimeStr.split(' ')[0] || dateTimeStr;
  } catch (error) {
    console.error('날짜 포맷팅 오류:', error);
    return dateTimeStr;
  }
};

// 퍼센트 포맷팅
export const formatPercent = (percent) => {
  if (percent === null || percent === undefined) return '-';
  
  // 이미 %가 포함된 경우
  if (typeof percent === 'string' && percent.includes('%')) {
    return percent;
  }
  
  return `${percent}%`;
};

// 상태명 포맷팅 (색상 클래스 포함)
export const formatStatus = (status) => {
  if (!status) return { text: '-', className: '' };
  
  const statusMap = {
    '공매진행중': { text: '공매진행중', className: 'status-active' },
    '공매완료': { text: '공매완료', className: 'status-completed' },
    '공매중지': { text: '공매중지', className: 'status-stopped' },
    '공매예정': { text: '공매예정', className: 'status-scheduled' }
  };
  
  return statusMap[status] || { text: status, className: 'status-default' };
};

// 문자열 길이 제한 및 말줄임표 처리
export const truncateText = (text, maxLength = 50) => {
  if (!text) return '-';
  
  if (text.length <= maxLength) {
    return text;
  }
  
  return text.substring(0, maxLength) + '...';
};

// 빈 값 처리
export const formatEmptyValue = (value, defaultText = '-') => {
  if (value === null || value === undefined || value === '') {
    return defaultText;
  }
  return value;
};

// 조회수 포맷팅
export const formatViewCount = (count) => {
  if (count === null || count === undefined) return '-';
  
  if (count >= 10000) {
    return `${Math.floor(count / 1000)}K`;
  }
  
  return formatNumber(count);
};

// 유찰횟수 포맷팅
export const formatFailureCount = (count) => {
  if (count === null || count === undefined) return '-';
  
  if (count === 0) {
    return '신규';
  }
  
  return `${count}회`;
};