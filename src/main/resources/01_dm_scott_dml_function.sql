CREATE OR REPLACE FUNCTION dm_scott.get_attr_seqno
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
   counter := dm_scott.seq_attr_no.NEXTVAL;
   counterlen := LENGTH (counter);
   seqno := datestr || SUBSTR (seqnoinit, 0, 5 - counterlen) || counter;
   RETURN seqno;
END;
/