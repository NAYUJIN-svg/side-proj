package com.nyg.sideproj.mapper;

import com.nyg.sideproj.dto.KamcoRequest;
import com.nyg.sideproj.entity.KamcoData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KamcoMapper {
    List<KamcoData> findRawData(KamcoRequest request);
    List<KamcoData> findByMnmtNo(KamcoRequest request);
    List<KamcoData> findByCltrNm(KamcoRequest request);
    
    @Insert("INSERT INTO public_data_raw (mnmt_no, cltr_nm, dpsl_mtd_cd, nmrd_adrs, pbct_begn_dtm, pbct_end_dtm, other_info) " +
            "VALUES (#{mnmtNo}, #{cltrNm}, #{dpslMtdCd}, #{nmrdAdrs}, #{pbctBegnDtm}, #{pbctEndDtm}, #{otherInfo}) " +
            "ON DUPLICATE KEY UPDATE cltr_nm = #{cltrNm}")
    int insertRawData(KamcoData data);
    
    @Insert("INSERT IGNORE INTO public_data_by_mnmt_no (mnmt_no, dpsl_mtd_cd, nmrd_adrs, pbct_begn_dtm, pbct_end_dtm, other_info) " +
            "VALUES (#{mnmtNo}, #{dpslMtdCd}, #{nmrdAdrs}, #{pbctBegnDtm}, #{pbctEndDtm}, #{otherInfo})")
    int insertByMnmtNo(KamcoData data);
    
    @Insert("INSERT IGNORE INTO public_data_by_cltr_nm (cltr_nm, dpsl_mtd_cd, nmrd_adrs, pbct_begn_dtm, pbct_end_dtm, other_info) " +
            "VALUES (#{cltrNm}, #{dpslMtdCd}, #{nmrdAdrs}, #{pbctBegnDtm}, #{pbctEndDtm}, #{otherInfo})")
    int insertByCltrNm(KamcoData data);
}
