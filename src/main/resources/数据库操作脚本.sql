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
select * from dba_tablespaces;
select table_name from all_tables where TABLESPACE_NAME='��ռ�' 

-- �鿴��ǰ�û��Ľ�ɫ
select * from user_role_privs;

-- �鿴��ǰ�û���ϵͳȨ�޺ͱ�Ȩ��
select * from user_sys_privs;
select * from user_tab_privs;

-- �鿴�û������еı�
select * from user_tables;

-- �鿴�û��µĶ���
select * from user_objects;

-- ��ǰ�û��µ���ͼ
select view_name from user_views;

-- �鿴�û��µ�����
select * from user_sequences;

-- �鿴���ݿ⣺
select name from v$database;
show parameter db_name��


-- ��ʾ����������
 select OWNER, TABLE_NAME, COLUMN_NAME
 from all_tab_columns
 where table_name = atablename;
 
 --�鿴ĳ���Լ������

����select constraint_name, constraint_type,search_condition, r_constraint_name

����from user_constraints where table_name = upper('&table_name');

����SQL>select c.constraint_name,c.constraint_type,cc.column_name

����from user_constraints c,user_cons_columns cc

����where c.owner = upper('&table_owner') and c.table_name = upper('&table_name')

����and c.owner = cc.owner and c.constraint_name = cc.constraint_name

����order by cc.position;


--������ʱ��
CREATE GLOBAL TEMPORARY TABLE Table_name
    (startdate DATE,
      enddate DATE,
      class CHAR(20))
  ON COMMIT DELETE ROWS;

--��������
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

--����DMP
su - oracle
imp dm_act/dm_act file=/home/oracle/02_dm_act_dml_createBlobData.dmp log=/home/oracle/logs/imp.log full=y ignore=y

--����Ĭ�ϱ�ռ�
CREATE TABLESPACE ACT_DAT_TS1 DATAFILE 
  '/u01/app/oracle/oradata/ACT_DAT_TS1_01.dbf' SIZE 400M AUTOEXTEND ON
LOGGING
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO
FLASHBACK ON;

--����������ռ�
CREATE TABLESPACE ACT_IDX_TS1 DATAFILE 
  '/u01/app/oracle/oradata/ACT_IDX_TS1_01.dbf' SIZE 100M AUTOEXTEND ON
LOGGING
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO
FLASHBACK ON;

-- ��ѯ��ռ�
select file_name from dba_data_files where tablespace_name = 'OMS_DAT_TS1'

-- �޸ı�ռ�
ALTER DATABASE   
    DATAFILE '/u02/oradata/OMS_DAT_TS1_01.dbf' AUTOEXTEND   
    ON NEXT 200M MAXSIZE UNLIMITED

--ɾ���û�
drop user dm_act cascade;

--�����û�
create user dm_act identified by "dm_act"  default tablespace ACT_DAT_TS1 ; 

--��Ȩ
-- Grant/Revoke role privileges 
grant connect to dm_act with admin option;
grant resource to dm_act with admin option;
-- Grant/Revoke system privileges 
grant unlimited tablespace to dm_act with admin option;
grant create any view to dm_act with admin option;


	һ��EXP:
      ��������Ҫ�ķ�ʽ����ȫ���û�����
      1����ȫ��
          EXP SYSTEM/MANAGER BUFFER=64000 FILE=C:\FULL.DMP FULL=Y
          ���Ҫִ����ȫ������������������Ȩ��
      2���û�ģʽ��
          EXP SONIC/SONIC    BUFFER=64000 FILE=C:\SONIC.DMP OWNER=SONIC
          �����û�SONIC�����ж���������ļ��С�
      3����ģʽ��
          EXP SONIC/SONIC    BUFFER=64000 FILE=C:\SONIC.DMP OWNER=SONIC TABLES=(SONIC)
          �����û�SONIC�ı�SONIC�ͱ�����
    ����IMP:
      ��������ģʽ����ȫ���û�����
      1����ȫ��
          IMP SYSTEM/MANAGER BUFFER=64000 FILE=C:\FULL.DMP FULL=Y
      2���û�ģʽ��
          IMP SONIC/SONIC    BUFFER=64000 FILE=C:\SONIC.DMP FROMUSER=SONIC TOUSER=SONIC
          �����û�SONIC�����ж��󱻵��뵽�ļ��С�����ָ��FROMUSER��TOUSER�������������ܵ������ݡ�
      3����ģʽ��
          EXP SONIC/SONIC    BUFFER=64000 FILE=C:\SONIC.DMP OWNER=SONIC TABLES=(SONIC)
          �����û�SONIC�ı�SONIC�ͱ����롣