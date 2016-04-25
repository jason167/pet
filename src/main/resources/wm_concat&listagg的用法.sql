
-- wm_concat(高版本的oracle已经不在兼容wm_concat，且在查询结果中如果数据较多，也会出现问题。可由listagg替代）
SELECT   chd.contract_id ID, ci.contract_id contractid,
         ci.contract_number cotractnumber,
         TO_CHAR (wm_concat (chd.file_name)) filenames,
         TO_CHAR (wm_concat (chd.file_path)) paths, cc.equip_amt,
         tdd.name_ leas_form, cust.cust_name
    FROM contract_his_document chd LEFT JOIN contract_info ci
         ON chd.contract_id = ci.ID
         LEFT JOIN cust_info cust ON cust.ID = ci.cust_id
         LEFT JOIN contract_condition cc ON cc.contract_id = ci.ID
         LEFT JOIN t_dicts_datas tdd ON tdd.id_ = ci.leas_form
   WHERE 1 = 1
GROUP BY chd.contract_id,
         ci.contract_id,
         ci.contract_number,
         cust.cust_name,
         cc.equip_amt,
         tdd.name_
         
-- listagg:
SELECT CHD.CONTRACT_ID ID,
       CI.CONTRACT_ID CONTRACTID,
       CI.CONTRACT_NUMBER COTRACTNUMBER,
       LISTAGG(CHD.FILE_NAME,',') WITHIN GROUP (ORDER BY CHD.FILE_NAME)  FILENAMES,
       LISTAGG(CHD.FILE_PATH,',') WITHIN GROUP (ORDER BY CHD.FILE_PATH) PATHS,
       cc.equip_amt,
       TDD.NAME_ LEAS_FORM,
       CUST.CUST_NAME
  FROM sczl.CONTRACT_HIS_DOCUMENT CHD
  LEFT JOIN sczl.CONTRACT_INFO CI
    ON CHD.CONTRACT_ID = CI.ID
  LEFT JOIN sczl.CUST_INFO CUST
    ON CUST.ID = CI.CUST_ID
  LEFT JOIN sczl.Contract_Condition cc
    on cc.Contract_Id = CI.ID
  LEFT JOIN sczl.T_DICTS_DATAS TDD
    ON TDD.ID_ = CI.LEAS_FORM
 WHERE 1 = 1
 GROUP BY CHD.CONTRACT_ID,
          CI.CONTRACT_ID,
          CI.CONTRACT_NUMBER,
          CUST.CUST_NAME,
          cc.equip_amt,
          TDD.NAME_

