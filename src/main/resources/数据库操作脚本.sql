su - oracle

-- �ر�
lsnrctl stop
sqlplus /nolog <<EOF
connect / as sysdba
shutdown immediate;
exit
EOF


-- ����
lsnrctl start
sqlplus /nolog <<EOF
connnect / as sysdba
startup;
exit
EOF


-- �鿴��ǰ�û���ȱʡ��ռ�
select username,default_tablespace from user_users;

-- �鿴��ǰ�û��Ľ�ɫ
select * from user_role_privs;

-- �鿴��ǰ�û���ϵͳȨ�޺ͱ�Ȩ��
select * from user_sys_privs;
select * from user_tab_privs;

-- �鿴�û������еı�
select * from user_tables;

-- �鿴�û��µĶ���
select * from user_objects;

-- �鿴�û��µ�����
select * from user_sequences;

-- �鿴���ݿ⣺
select name from v$database;
show parameter db_name��

-- ��ʾ����������
 select OWNER, TABLE_NAME, COLUMN_NAME
 from all_tab_columns
 where table_name = atablename;

������ʱ��
CREATE GLOBAL TEMPORARY TABLE Table_name
    (startdate DATE,
      enddate DATE,
      class CHAR(20))
  ON COMMIT DELETE ROWS;

��������
CREATE OR REPLACE FUNCTION dm_fsp.get_attr_seqno
   RETURN VARCHAR2
AS
   sqlstr       VARCHAR2 (100);
   seqnoinit    VARCHAR2 (5)   := '00000';
   seqno        VARCHAR (30);
   counter      NUMBER         := 0;
   counterlen   NUMBER;
   datestr      VARCHAR (30);
BEGIN
   datestr := 'P' || TO_CHAR (SYSDATE, 'yyyyMMddHHmiss');
   counter := dm_fsp.seq_fsp_attr_no.NEXTVAL;
   counterlen := LENGTH (counter);
   seqno := datestr || SUBSTR (seqnoinit, 0, 5 - counterlen) || counter;
   RETURN seqno;
END;
/

����DMP
su - oracle
imp dm_act/dm_act file=/home/oracle/02_dm_act_dml_createBlobData.dmp log=/home/oracle/logs/imp.log full=y ignore=y

����Ĭ�ϱ�ռ�
CREATE TABLESPACE ACT_DAT_TS1 DATAFILE 
  '/u01/app/oracle/oradata/ACT_DAT_TS1_01.dbf' SIZE 400M AUTOEXTEND OFF
LOGGING
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO
FLASHBACK ON;

����������ռ�
CREATE TABLESPACE ACT_IDX_TS1 DATAFILE 
  '/u01/app/oracle/oradata/ACT_IDX_TS1_01.dbf' SIZE 100M AUTOEXTEND OFF
LOGGING
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO
FLASHBACK ON;

ɾ���û�
drop user dm_act cascade;

�����û�
create user dm_act identified by "dm_act"  default tablespace ACT_DAT_TS1 ; 

��Ȩ
-- Grant/Revoke role privileges 
grant connect to dm_act with admin option;
grant resource to dm_act with admin option;
-- Grant/Revoke system privileges 
grant unlimited tablespace to dm_act with admin option;
grant create any view to dm_act with admin option;