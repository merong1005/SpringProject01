DROP TABLE REVIEW_INFO PURGE;
CREATE TABLE REVIEW_INFO (
    review_idx          NUMBER          CONSTRAINT review_pk primary key,
    content_id          VARCHAR2(10)    NOT NULL,
    user_no             INT             CONSTRAINT review_fk2 REFERENCES user_info(user_no),
    user_name           VARCHAR2(15)    NOT NULL,
    review_date         VARCHAR2(100)   NOT NULL,
    review_content      VARCHAR2(200)   NOT NULL
);

DROP SEQUENCE REVIEW_SEQ;
CREATE SEQUENCE REVIEW_SEQ
START WITH 1
INCREMENT BY 1
MINVALUE 0;

ROLLBACK;

INSERT INTO REVIEW_INFO VALUES (REVIEW_SEQ.NEXTVAL, '126438', 1, '김영은', to_char(SYSDATE, 'YYYY.MM.DD HH24:MI'), '고등학교 때 수학여행으로 다녀왔어요');
INSERT INTO REVIEW_INFO VALUES (REVIEW_SEQ.NEXTVAL, '126438', 1, '김영은', to_char(SYSDATE, 'YYYY.MM.DD HH24:MI'), 'ㅋㅋㅋㅋㅋ나도');
INSERT INTO REVIEW_INFO VALUES (REVIEW_SEQ.NEXTVAL, '126438', 1, '김영은', to_char(SYSDATE, 'YYYY.MM.DD HH24:MI'), '제대로 못 본 명소ㅜ');
INSERT INTO REVIEW_INFO VALUES (REVIEW_SEQ.NEXTVAL, '126438', 1, '김영은', to_char(SYSDATE, 'YYYY.MM.DD HH24:MI'), '폭포 보고싶다');
INSERT INTO REVIEW_INFO VALUES (REVIEW_SEQ.NEXTVAL, '126438', 1, '김영은', to_char(SYSDATE, 'YYYY.MM.DD HH24:MI'), 'ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ');

SELECT * FROM REVIEW WHERE CONTENT_ID = 126438;
SELECT * FROM REVIEW WHERE CONTENT_ID = 126438;
SELECT * FROM REVIEW WHERE CONTENT_ID = 126438;
SELECT * FROM REVIEW_INFO;

SELECT user_name FROM USER_INFO U, REVIEW R WHERE U.user_no = R.USER_NO;
SELECT DISTINCT user_name  FROM USER_INFO U, REVIEW R WHERE U.user_no = R.USER_NO;

SELECT * FROM REVIEW_INFO;

COMMIT;

DELETE FROM REVIEW_INFO WHERE REVIEW_IDX = 1;
ROLLBACK;