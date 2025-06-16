----------------------------------member �θ� ���̺�-----------------------------------------
drop table from member;

create table member(
	mno number(5) not null,
	mname nvarchar2(10) not null,
	id nvarchar2(10) primary key, -- board ���̺��� bwriter�� fk ����� ��������
	pw nvarchar2(10) not null,
	regidate date default sysdate not null
);
select * from member;
select mno,mname,id,regidate from member;

create sequence member_seq increment by 1 start with 1 nocycle nocache;

-----------------------------member�� �ڽ��̸鼭 board �ܷ�Ű ����------------------------------
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

-----------------------------join�� ����ؼ� member�� board ���� �����ؼ� �����´�------------------------------
select b.*,m.mname from member m inner join board b on m.id = b.bwriter;