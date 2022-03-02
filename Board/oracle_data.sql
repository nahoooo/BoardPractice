create table users(
id varchar2(28) primary key,   -- id
password varchar2(8) not null, -- 패스워드
name varchar2(30) not null, -- 이름
role varchar2(5) default 'User' -- 사용자 유형
)

drop table users -- users테이블 삭제

create table board(
seq number(5) primary key,     -- 글번호
title varchar2(200) not null,  -- 글제목
nickname varchar2(30) not null,-- 글쓴 사람 닉네임 
content varchar2(2000) not null,--본문내용
regdate date default sysdate, -- 글쓴 날짜
cnt number(5) default 0, -- 조회 수
userid varchar2(8) --글쓴 사람 ID 
)

drop table board -- board테이블 삭제

insert into users values('hong','hong123','홍길동','Admin');
insert into users values('abc','abc123','임꺽정','User');
insert into users values('guest','guest123','일지매','Guest');

select * from users

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'1 번째 게시물','일지매','1 번째 게시물 내용.','2022-02-01','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'2 번째 게시물','일지매','2 번째 게시물 내용.','2022-02-02','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'3 번째 게시물','일지매','3 번째 게시물 내용.','2022-02-03','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'4 번째 게시물','일지매','4 번째 게시물 내용.','2022-02-04','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'5 번째 게시물','일지매','5 번째 게시물 내용.','2022-02-05','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'6 번째 게시물','일지매','6 번째 게시물 내용.','2022-02-06','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'7 번째 게시물','일지매','7 번째 게시물 내용.','2022-02-07','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'8 번째 게시물','일지매','8 번째 게시물 내용.','2022-02-08','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'9 번째 게시물','일지매','9 번째 게시물 내용.','2022-02-09','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'10 번째 게시물','일지매','10 번째 게시물 내용.','2022-02-10','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'11 번째 게시물','일지매','11 번째 게시물 내용.','2022-02-11','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'12 번째 게시물','일지매','12 번째 게시물 내용.','2022-02-12','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'13 번째 게시물','일지매','13 번째 게시물 내용.','2022-02-13','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'14 번째 게시물','일지매','14 번째 게시물 내용.','2022-02-14','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'15 번째 게시물','일지매','15 번째 게시물 내용.','2022-02-15','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'16 번째 게시물','일지매','16 번째 게시물 내용.','2022-02-16','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'17 번째 게시물','일지매','17 번째 게시물 내용.','2022-02-17','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'18 번째 게시물','일지매','18 번째 게시물 내용.','2022-02-18','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'19 번째 게시물','일지매','19 번째 게시물 내용.','2022-02-19','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'20 번째 게시물','일지매','20 번째 게시물 내용.','2022-02-20','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'21 번째 게시물','일지매','21 번째 게시물 내용.','2022-02-21','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'22 번째 게시물','일지매','22 번째 게시물 내용.','2022-02-22','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'23 번째 게시물','일지매','23 번째 게시물 내용.','2022-02-23','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'24 번째 게시물','일지매','24 번째 게시물 내용.','2022-02-24','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'25 번째 게시물','일지매','25 번째 게시물 내용.','2022-02-25','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'26 번째 게시물','일지매','26 번째 게시물 내용.','2022-02-26','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'27 번째 게시물','일지매','27 번째 게시물 내용.','2022-02-27','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'28 번째 게시물','일지매','28 번째 게시물 내용.','2022-02-28','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'29 번째 게시물','일지매','29 번째 게시물 내용.','2022-02-28','guest');

insert into board(seq,title,nickname,content,regdate,userid)
values ((select nvl(max(seq),0)+1 from board),'30 번째 게시물','일지매','30 번째 게시물 내용.','2022-02-28','guest');

select * from board

select * from board order by seq desc

select * from users where id='abc' and password='abc123'

select * from board order by seq desc
-- 최근 게시물부터 출력.

select rownum as rnum,B.* from board B
-- rownum이라는 레코드의 논리적 번호를 함께 출력. board테이블의 모든 레코드와 rownum값을 같이 출력

select rownum as rnum,B.* from (select * from board order by seq desc) B
-- rownum을 기준으로  seq를 활용 글 최신번호를 먼저 출력되게 inline쿼리를 이용하여 쿼리문을 만듬
-- seq는 글의 순번을 나타냄. seq가 가장 최신 번호가 앞으로 오게 함. inline뷰를 활용하지 않으면 order by보다
-- 먼저 rownum이 번호가 매겨져 최신 번호부터 출력이 되지 않는다. 그래서 inline뷰를 사용. 
-- from (   )   ()안에 select하위쿼리를 사용하는 것을 인라인뷰라고 함.
select * from (select rownum as rnum,B.* from (select * from board order by seq desc) B)
where rnum between 1 and 10    -- 1 page     1 - 10    page : page*10-9 - page*10

select * from (select rownum as rnum,B.* from (select * from board order by seq desc) B)
where rnum between 11 and 20   -- 2 page     11 - 20

select * from (select rownum as rnum,B.* from (select * from board order by seq desc) B)
where rnum between 21 and 30   -- 3 page     21 - 30

select * from (select rownum as rnum,B.* from (select * from board where content like '%'게시글'%' order by seq desc) B)
		 where rnum between 1 and 10;

-- 게시판 전체 게시글 개수
select max(seq) from board

-- 게시물 수정
update board set title='A',content='B' where seq=7

-- 게시물 삭제
delete from board where seq=7


select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from 
(select * from board where TITLE like '%게시물%' order by seq desc)) 
where rn between 1 and 10;




