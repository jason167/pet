su - oracle

-- 关闭
lsnrctl stop
sqlplus /nolog <<EOF
connect / as sysdba
shutdown immediate;
exit
EOF


-- 启动
lsnrctl start
sqlplus /nolog <<EOF
connnect / as sysdba
startup;
exit
EOF


-- 查看当前用户的缺省表空间
select username,default_tablespace from user_users;

-- 查看当前用户的角色
select * from user_role_privs;

-- 查看当前用户的系统权限和表级权限
select * from user_sys_privs;
select * from user_tab_privs;

-- 查看用户下所有的表
select * from user_tables;

-- 查看用户下的对象
select * from user_objects;

-- 查看用户下的序列
select * from user_sequences;

-- 查看数据库：
select name from v$database;
show parameter db_name；

-- 显示所有列名：
 select OWNER, TABLE_NAME, COLUMN_NAME
 from all_tab_columns
 where table_name = atablename;

创建临时表：
CREATE GLOBAL TEMPORARY TABLE Table_name
    (startdate DATE,
      enddate DATE,
      class CHAR(20))
  ON COMMIT DELETE ROWS;

创建函数
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

导入DMP
su - oracle
imp dm_act/dm_act file=/home/oracle/02_dm_act_dml_createBlobData.dmp log=/home/oracle/logs/imp.log full=y ignore=y

创建默认表空间
CREATE TABLESPACE ACT_DAT_TS1 DATAFILE 
  '/u01/app/oracle/oradata/ACT_DAT_TS1_01.dbf' SIZE 400M AUTOEXTEND OFF
LOGGING
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO
FLASHBACK ON;

创建索引表空间
CREATE TABLESPACE ACT_IDX_TS1 DATAFILE 
  '/u01/app/oracle/oradata/ACT_IDX_TS1_01.dbf' SIZE 100M AUTOEXTEND OFF
LOGGING
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO
FLASHBACK ON;

删除用户
drop user dm_act cascade;

创建用户
create user dm_act identified by "dm_act"  default tablespace ACT_DAT_TS1 ; 

授权
-- Grant/Revoke role privileges 
grant connect to dm_act with admin option;
grant resource to dm_act with admin option;
-- Grant/Revoke system privileges 
grant unlimited tablespace to dm_act with admin option;
grant create any view to dm_act with admin option;