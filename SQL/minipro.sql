----------------------------------member 부모 테이블-----------------------------------------
drop table from member;

create table member(
	mno number(5) not null,
	mname nvarchar2(10) not null,
	id nvarchar2(10) primary key, -- board 테이블의 bwriter와 fk 관계로 설정예정
	pw nvarchar2(10) not null,
	regidate date default sysdate not null
);
select * from member;
select mno,mname,id,regidate from member;

create sequence member_seq increment by 1 start with 1 nocycle nocache;

-----------------------------member의 자식이면서 board 외래키 생성------------------------------
drop table from board;

create table board(
	bno number(5) primary key,
	btitle nvarchar2(20) not null,
	bcontent nvarchar2(1000) not null,
	bdate date not null,
	bwriter nvarchar2(10) not null
);

alter table board add constraint board_member_fk foreign key (bwriter) references member(id);

create sequence board_seq increment by 1 start with 1 nocycle nocache;

select * from board;

-----------------------------join을 사용해서 member와 board 값을 연결해서 가져온다------------------------------
select b.*,m.mname from member m inner join board b on m.id = b.bwriter;