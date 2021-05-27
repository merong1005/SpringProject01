package com.adregamdi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.adregamdi.dto.NoticeDTO;

public interface NoticeMapper {
	
	  // 게시글 리스트
	  @Select("SELECT ROWNUM NOTICE_ROWNUM, N.NOTICE_NO, N.NOTICE_TITLE, N.NOTICE_CNT, " + 
	          "TO_CHAR(N.NOTICE_DATE, 'YYYY-MM-DD HH24:MI:SS') NOTICE_DATE, " + 
	          "U.USER_ID CONTENT_NOTICE_USER_NO " +
	          "FROM (SELECT * FROM NOTICE ORDER BY NOTICE_NO ASC) N, USER_INFO U " +
	          "WHERE N.NOTICE_USER_NO = U.USER_NO ORDER BY ROWNUM DESC ")
	  List<NoticeDTO> getNoticeList(RowBounds rowBounds);
	 
	  // 게시글 이전글, 다음글
	  @Select("SELECT N.* " + 
	  		  "FROM (SELECT NOTICE_NO, TO_CHAR(NOTICE_DATE, 'YYYY-MM-DD HH24:MI:SS') NOTICE_DATE, " + 
	  		  "LEAD(NOTICE_NO, 1) OVER (ORDER BY NOTICE_NO DESC) AS NEXT_NO, " + 
	  		  "LEAD(NOTICE_TITLE, 1, '다음글이 없습니다') OVER (ORDER BY NOTICE_NO DESC) AS NEXT_TITLE, " + 
	  		  "LAG(NOTICE_NO, 1) OVER (ORDER BY NOTICE_NO DESC) AS PRE_NO, " + 
	  		  "LAG(NOTICE_TITLE, 1, '이전글이 없습니다') OVER (ORDER BY NOTICE_NO DESC) AS PRE_TITLE " + 
	  		  "FROM NOTICE) N WHERE N.NOTICE_NO = #{content_idx}")
	  NoticeDTO getNextPrev(int content_idx);
	  
    // 게시글 본문 내용
	@Select("SELECT N.NOTICE_NO, U.USER_NO NOTICE_USER_NO, U.USER_ID CONTENT_NOTICE_USER_NO, " + 
	        "TO_CHAR(N.NOTICE_DATE, 'YYYY-MM-DD HH24:MI:SS') NOTICE_DATE, " + 
			"N.NOTICE_TITLE, N.NOTICE_CONTENT, N.NOTICE_CNT FROM USER_INFO U, NOTICE N " + 
			"WHERE U.USER_NO = N.NOTICE_USER_NO AND N.NOTICE_NO = #{content_idx}")
	NoticeDTO getNoticeContent(int content_idx);
	
	// 게시글 페이징
	@Select("SELECT COUNT(*) FROM NOTICE ")
	int GetNoticeContentCount();
    
	// 게시글 글쓰기
	@Insert("INSERT INTO NOTICE(NOTICE_NO, NOTICE_USER_NO, NOTICE_TITLE, NOTICE_CNT, NOTICE_DATE, NOTICE_CONTENT) "
			+ "VALUES(NOTICE_SEQ.nextval, #{notice_user_no}, #{notice_title}, 0, SYSDATE, #{notice_content}) ")
	void InsertNoticeContent(NoticeDTO noticeDTO);
    
	// 게시글 수정
	@Update("UPDATE NOTICE SET NOTICE_TITLE=#{notice_title}, NOTICE_CONTENT=#{notice_content}, " + 
			"notice_date=SYSDATE WHERE NOTICE_NO = #{notice_no}")
	void modifyNoticeContent(NoticeDTO noticeModifyDTO);
	
	// 게시글 삭제
	@Delete("DELETE FROM NOTICE WHERE NOTICE_NO=#{content_idx}")
	void deleteNoticeContent(int content_idx);
	
	// 게시글 조회수
	@Update("UPDATE NOTICE SET notice_cnt = notice_cnt + 1 WHERE notice_no = #{notice_cnt}")
	void viewCount(int content_idx);
	
	}