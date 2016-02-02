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
select * from dba_tablespaces;
select table_name from all_tables where TABLESPACE_NAME='表空间' 

-- 查看当前用户的角色
select * from user_role_privs;

-- 查看当前用户的系统权限和表级权限
select * from user_sys_privs;
select * from user_tab_privs;

-- 查看用户下所有的表
select * from user_tables;

-- 查看用户下的对象
select * from user_objects;

-- 当前用户下的视图
select view_name from user_views;

-- 查看用户下的序列
select * from user_sequences;

-- 查看数据库：
select name from v$database;
show parameter db_name；

-- 查看外键
SELECT t.owner, t.constraint_name, t.constraint_type, t.table_name
  FROM user_constraints t
 WHERE constraint_type = 'R'
 
-- 删除外键
 ALTER TABLE scott.emp
 DROP CONSTRAINT FK6F95988564E4721F;


-- 显示所有列名：
 select OWNER, TABLE_NAME, COLUMN_NAME
 from all_tab_columns
 where table_name = atablename;
 
 --查看某表的约束条件

　　select constraint_name, constraint_type,search_condition, r_constraint_name

　　from user_constraints where table_name = upper('&table_name');

　　SQL>select c.constraint_name,c.constraint_type,cc.column_name

　　from user_constraints c,user_cons_columns cc

　　where c.owner = upper('&table_owner') and c.table_name = upper('&table_name')

　　and c.owner = cc.owner and c.constraint_name = cc.constraint_name

　　order by cc.position;


--创建临时表：
CREATE GLOBAL TEMPORARY TABLE Table_name
    (startdate DATE,
      enddate DATE,
      class CHAR(20))
  ON COMMIT DELETE ROWS;

--创建函数
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

--导入DMP
su - oracle
imp dm_act/dm_act file=/home/oracle/02_dm_act_dml_createBlobData.dmp log=/home/oracle/logs/imp.log full=y ignore=y

--创建默认表空间
CREATE TABLESPACE ACT_DAT_TS1 DATAFILE 
  '/u01/app/oracle/oradata/ACT_DAT_TS1_01.dbf' SIZE 400M AUTOEXTEND ON
LOGGING
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO
FLASHBACK ON;

--创建索引表空间
CREATE TABLESPACE ACT_IDX_TS1 DATAFILE 
  '/u01/app/oracle/oradata/ACT_IDX_TS1_01.dbf' SIZE 100M AUTOEXTEND ON
LOGGING
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO
FLASHBACK ON;

-- 查询表空间
select file_name from dba_data_files where tablespace_name = 'OMS_DAT_TS1'

-- 修改表空间
ALTER DATABASE   
    DATAFILE '/u02/oradata/OMS_DAT_TS1_01.dbf' AUTOEXTEND   
    ON NEXT 200M MAXSIZE UNLIMITED

--删除用户
drop user dm_act cascade;

--创建用户
create user dm_act identified by "dm_act"  default tablespace ACT_DAT_TS1 ; 

--授权
-- Grant/Revoke role privileges 
grant connect to dm_act with admin option;
grant resource to dm_act with admin option;
-- Grant/Revoke system privileges 
grant unlimited tablespace to dm_act with admin option;
grant create any view to dm_act with admin option;


	一、EXP:
      有三种主要的方式（完全、用户、表）
      1、完全：
          EXP SYSTEM/MANAGER BUFFER=64000 FILE=C:\FULL.DMP FULL=Y
          如果要执行完全导出，必须具有特殊的权限
      2、用户模式：
          EXP SONIC/SONIC    BUFFER=64000 FILE=C:\SONIC.DMP OWNER=SONIC
          这样用户SONIC的所有对象被输出到文件中。
      3、表模式：
          EXP SONIC/SONIC    BUFFER=64000 FILE=C:\SONIC.DMP OWNER=SONIC TABLES=(SONIC)
          这样用户SONIC的表SONIC就被导出
    二、IMP:
      具有三种模式（完全、用户、表）
      1、完全：
          IMP SYSTEM/MANAGER BUFFER=64000 FILE=C:\FULL.DMP FULL=Y
      2、用户模式：
          IMP SONIC/SONIC    BUFFER=64000 FILE=C:\SONIC.DMP FROMUSER=SONIC TOUSER=SONIC
          这样用户SONIC的所有对象被导入到文件中。必须指定FROMUSER、TOUSER参数，这样才能导入数据。
      3、表模式：
          EXP SONIC/SONIC    BUFFER=64000 FILE=C:\SONIC.DMP OWNER=SONIC TABLES=(SONIC)
          这样用户SONIC的表SONIC就被导入。