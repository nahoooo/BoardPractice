--유저테이블 생성--
create table users(
id varchar2(20) primary key,   --ID
password varchar2(8) not null, --패스워드
name varchar2(30) not null,	   --이름
role varchar2(5) default 'User'--사용자 유형
)



--게시판 테이블 생성--
create table board(
seq number(5) primary key,          --글번호
title varchar2(200) not null,		--글제목
nickname varchar2(30) not null,		--글쓴이 닉네임
content varchar2(2000) not null,	--본문내용
regdate date default sysdate,  		--오늘날짜가 들어가게 하는 메소드
cnt number(5) default 0,			--조회수
userid varchar(8) 					--글 쓴 사람 id
)



INSERT INTO USERS VALUES('hong','hong123','홍길동','Admin');
INSERT INTO USERS VALUES('abc','abc123','임꺽정','User');
INSERT INTO USERS VALUES('guest','guest123','일지매','Guest');

select * from USERS;


INSERT INTO BOARD(SEQ,TITLE,NICKNAME,CONTENT,REGDATE,USERID)
VALUES(1,'첫 번째 게시물','홍길동','첫 번쨰 게시물 내용','2022-02-05','hong');
INSERT INTO BOARD(SEQ,TITLE,NICKNAME,CONTENT,REGDATE,USERID)
VALUES(2,'두 번째 게시물','홍길동','두 번쨰 게시물 내용','2022-02-10','hong');
INSERT INTO BOARD(SEQ,TITLE,NICKNAME,CONTENT,REGDATE,USERID)
VALUES(3,'세 번째 게시물','홍길동','세 번쨰 게시물 내용','2022-02-13','hong');
INSERT INTO BOARD(SEQ,TITLE,NICKNAME,CONTENT,REGDATE,USERID)
VALUES(4,'네 번째 게시물','홍길동','네 번쨰 게시물 내용','2022-02-14','hong');
INSERT INTO BOARD(SEQ,TITLE,NICKNAME,CONTENT,REGDATE,USERID)
VALUES(5,'다섯 번째 게시물','일지매','다섯 번쨰 게시물 내용','2022-02-16','guest');
INSERT INTO BOARD(SEQ,TITLE,NICKNAME,CONTENT,REGDATE,USERID)
VALUES(6,'여섯 번째 게시물','일지매','여섯 번쨰 게시물 내용','2022-02-20','guest');


INSERT INTO BOARD(SEQ,TITLE,NICKNAME,CONTENT,REGDATE,USERID)
VALUES((select nvl(max(seq),0)+1 from board),'일곱 번째 게시물','일지매','일곱 번쨰 게시물 내용','2022-02-21','guest');


select * from board;
--게시물 내림차순, 최신 게시글 부터 확인
select * from board order by seq desc;
--게시물 수정
update board set title='A',content='B' where seq=7

--게시물 삭제
delete from board where seq=7


select count(*) from board;

select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, USERID from 
(select * from board order by SEQ desc)) where rn between 2 and 10;

INSERT INTO BOARD(SEQ,TITLE,NICKNAME,CONTENT,REGDATE,USERID)
VALUES((select nvl(max(seq),0)+1 from board),'하하20','일지매','하하','2022-02-28','guest');

select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board where TITLE like '%게시물%' order by seq desc)) where rn between 1 and 10;

"select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board where title like '%하하%'order by seq desc)) where rn between 1 and 10;"


select count(*) from board where title like '%하하%' order by seq desc

select count(*) as cnt from board where title like '%하하%' order by seq desc