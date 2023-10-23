DROP SEQUENCE USER_SEQ;

CREATE SEQUENCE USER_SEQ NOCACHE;

DROP TABLE INACTIVE_USER_T;
DROP TABLE LEAVE_USER_T;
DROP TABLE ACCESS_T;
DROP TABLE USER_T;

CREATE TABLE USER_T (
    USER_NO         NUMBER              NOT NULL,
    EMAIL           VARCHAR2(100 BYTE)  NOT NULL UNIQUE,
    PW              VARCHAR2(64 BYTE)   NOT NULL,
    NAME            VARCHAR2(50 BYTE),
    GENDER          VARCHAR2(3 BYTE),
    MOBILE          VARCHAR2(15 BYTE),
    POSTCODE        VARCHAR2(5 BYTE),
    ROAD_ADDRESS    VARCHAR2(100 BYTE),
    JIBUN_ADDRESS   VARCHAR2(100 BYTE),
    DETAIL_ADDRESS  VARCHAR2(100 BYTE),
    AGREE           NUMBER              NOT NULL,
    PW_MODIFIED_AT  DATE,
    JOINED_AT       DATE,
    CONSTRAINT PK_USER PRIMARY KEY(USER_NO)
);

CREATE TABLE ACCESS_T (
    EMAIL       VARCHAR2(100 BYTE)  NOT NULL,
    LOGIN_AT    DATE,
    CONSTRAINT FK_USER_ACCESS FOREIGN KEY(EMAIL) REFERENCES USER_T(EMAIL) ON DELETE CASCADE
);

CREATE TABLE LEAVE_USER_T (
    EMAIL       VARCHAR2(100 BYTE) NOT NULL,
    JOINED_AT   DATE,
    LEAVED_AT    DATE
);
    
CREATE TABLE INACTIVE_USER_T (
    USER_NO         NUMBER              NOT NULL,
    EMAIL           VARCHAR2(100 BYTE)  NOT NULL UNIQUE,
    PW              VARCHAR2(64 BYTE)   NOT NULL,
    NAME            VARCHAR2(50 BYTE),
    GENDER          VARCHAR2(3 BYTE),
    MOBILE          VARCHAR2(15 BYTE),
    POSTCODE        VARCHAR2(5 BYTE),
    ROAD_ADDRESS    VARCHAR2(100 BYTE),
    JIBUN_ADDRESS   VARCHAR2(100 BYTE),
    DETAIL_ADDRESS  VARCHAR2(100 BYTE),
    AGREE           NUMBER              NOT NULL,
    PW_MODIFIED_AT  DATE,
    JOINED_AT       DATE,
    INACTIVED_AT    DATE,
    CONSTRAINT PK_INACTIVE_USER PRIMARY KEY(USER_NO)
);

-- 샘플 데이터
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'minji@gmail.com', STANDARD_HASH('1111', 'SHA256'), '김민지', 'GOD', '01011111111', '11111', '뉴진스로 뉴진스길', '뉴진스동', '101호', 0, TO_DATE('2023/10/19', 'YYYY/MM/DD'), TO_DATE('2004/05/07', 'YYYY/MM/DD'));
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, '해린@gmail.com', STANDARD_HASH('2222', 'SHA256'), '강해린', 'GOD', '01022222222', '11111', '뉴진스로 뉴진스길', '뉴진스동', '101호', 0, TO_DATE('2023/08/19', 'YYYY/MM/DD'), TO_DATE('2006/05/15', 'YYYY/MM/DD'));
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, '팜하니@gmail.com', STANDARD_HASH('1111', 'SHA256'), '팜하니', 'GOD', '01011111111', '11111', '뉴진스로 뉴진스길', '뉴진스동', '101호', 0, TO_DATE('2023/06/19', 'YYYY/MM/DD'), TO_DATE('2004/10/06', 'YYYY/MM/DD'));

INSERT INTO ACCESS_T VALUES('minji@gmail.com', TO_DATE('2023/10/15', 'YYYY/MM/DD'));
INSERT INTO ACCESS_T VALUES('해린@gmail.com', TO_DATE('2022/01/15', 'YYYY/MM/DD'));

COMMIT;


-- 로그인
SELECT USER_NO, EMAIL, PW, NAME, GENDER, MOBILE, POSTCODE, ROAD_ADDRESS, JIBUN_ADDRESS, DETAIL_ADDRESS, AGREE, PW_MODIFIED_AT, JOINED_AT
  FROM USER_T
 WHERE EMAIL = 'minji@gmail.com'
   AND PW = (SELECT STANDARD_HASH('1111', 'SHA256') FROM DUAL);
   
INSERT INTO ACCESS_T VALUES('minji@gmail.com', SYSDATE);
COMMIT;

SELECT USER_NO, EMAIL, PW, NAME, GENDER, MOBILE, POSTCODE, ROAD_ADDRESS, JIBUN_ADDRESS, DETAIL_ADDRESS, AGREE, PW_MODIFIED_AT, JOINED_AT
  FROM INACTIVE_USER_T
 WHERE EMAIL = 'minji@gmail.com'
   AND PW = (SELECT STANDARD_HASH('1111', 'SHA256') FROM DUAL);
-- 복원으로 이동

-- 이메일 중복 체크
SELECT EMAIL
  FROM USER_T
 WHERE EMAIL = '다니엘@gmail.com';
 
SELECT EMAIL
  FROM LEAVE_USER_T
 WHERE EMAIL = '다니엘@gmail.com';
 
SELECT EMAIL
  FROM INACTIVE_USER_T
 WHERE EMAIL = '다니엘@gmail.com';
 
 
-- 휴면 전환
INSERT INTO INACTIVE_USER_T
    (SELECT USER_NO, U.EMAIL, PW, NAME, GENDER, MOBILE, POSTCODE, ROAD_ADDRESS, JIBUN_ADDRESS, DETAIL_ADDRESS, AGREE, PW_MODIFIED_AT, JOINED_AT, SYSDATE
       FROM USER_T U LEFT OUTER JOIN ACCESS_T A
         ON U.EMAIL = A.EMAIL
      WHERE MONTHS_BETWEEN(SYSDATE, A.LOGIN_AT) >= 12
         OR A.LOGIN_AT IS NULL AND MONTHS_BETWEEN(SYSDATE, U.JOINED_AT) >= 12
    );

DELETE
  FROM USER_T
 WHERE EMAIL IN(SELECT U.EMAIL
                FROM USER_T U LEFT OUTER JOIN ACCESS_T A
                 ON U.EMAIL = A.EMAIL
              WHERE MONTHS_BETWEEN(SYSDATE, A.LOGIN_AT) >= 12
                 OR A.LOGIN_AT IS NULL AND MONTHS_BETWEEN(SYSDATE, U.JOINED_AT) >= 12);
                 
COMMIT;


-- 휴면 복원
INSERT INTO USER_T (SELECT USER_NO, EMAIL, PW, NAME, GENDER, MOBILE, POSTCODE, ROAD_ADDRESS, JIBUN_ADDRESS, DETAIL_ADDRESS, AGREE, PW_MODIFIED_AT, JOINED_AT
                    FROM INACTIVE_USER_T
                   WHERE EMAIL = '해린@gmail.com');

DELETE
  FROM INACTIVE_USER_T
 WHERE EMAIL = '해린@gmail.com';
 
SELECT *
  FROM USER_T
 WHERE EMAIL = '해린@gmail.com';
   
INSERT INTO ACCESS_T VALUES('해린@gmail.com', SYSDATE);

COMMIT;


-- 탈퇴
INSERT INTO LEAVE_USER_T VALUES('minji@gmail.com', TO_DATE('2004/05/07', 'YYYY/MM/DD'), SYSDATE);
DELETE FROM USER_T WHERE USER_NO = 1;
COMMIT; 


