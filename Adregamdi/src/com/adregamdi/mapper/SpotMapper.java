package com.adregamdi.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.adregamdi.dto.SpotLikeDTO;

public interface SpotMapper {
	
	@SelectKey(statement="select spot_like_seq.nextval from dual", keyProperty="like_idx", before=true, resultType=int.class)
	
	@Insert("INSERT INTO SPOT_LIKE_INFO VALUES ( #{like_idx}, #{content_id}, #{like_cnt}, #{review_cnt})")
	void inputContentId(SpotLikeDTO likeDTO);
	
	@Update("UPDATE SPOT_LIKE_INFO SET LIKE_CNT = LIKE_CNT + 1 WHERE CONTENT_ID= #{content_id}")
	void plusLikeCnt(String content_id);
	
	@Select("SELECT * FROM SPOT_LIKE_INFO")
	ArrayList<SpotLikeDTO> getLikeInfo();
}
