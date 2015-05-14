

USE bank_term;
commit;






/*

delimiter //

DROP PROCEDURE IF EXISTS checkPasswd;
commit;

CREATE PROCEDURE checkPasswd (
    IN C_NUMBER INT,
    IN C_PIN INT,
    OUT EXISTING INT)
begin
  set existing = 0;
  select number FROM Card
  WHERE Card.number=c_number and Card.pin=c_pin
  into existing;
end //

*/


DELIMITER //

DROP PROCEDURE IF EXISTS checkPasswd;
commit;
CREATE PROCEDURE checkPasswd(C_NUMBER INT,
	C_PIN INT)
   SWL_return:
begin
-- This procedure was converted on Sun May 10 19:51:51 2015 using Ispirer SQLWays Express 7.0 Build 2682 32bit Licensed to I.

   DECLARE EXISTING INT;
   DECLARE NO_DATA INT DEFAULT 0;
   DECLARE SWV_CURSOR_VAR1 cursor FOR select 'number' FROM Card
   WHERE Card.number = C_NUMBER and Card.pin = C_PIN;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET NO_DATA = -1;
   DROP TEMPORARY TABLE IF EXISTS SWT_CHECKPASSWD;
   CREATE TEMPORARY TABLE IF NOT EXISTS SWT_CHECKPASSWD
   (
      EXISTING INT
   );
   SET EXISTING = 0;
   OPEN SWV_CURSOR_VAR1;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR1 INTO EXISTING;
   WHILE NO_DATA = 0 DO
      begin
         SET @SWV_Null_Var = 0;
      end;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR1 INTO EXISTING;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR1;
   INSERT INTO SWT_CHECKPASSWD  VALUES(EXISTING);

   LEAVE SWL_return; 
end;
//



/*DECLARE a, b INT DEFAULT 0;*/ 


/*call checkPasswd (100000000,7944,@spVar);*/


/*

CREATE PROCEDURE `proc_IN` (IN var1 INT) 

BEGIN 

  SELECT var1 + 2 AS result; 

END 

*/





/*delimiter //
DROP PROCEDURE IF EXISTS getRurChange;
commit;
CREATE PROCEDURE getRurChange (
	IN src_currency INT,
    	OUT ratio FLOAT)
begin
	DECLARE rur INT DEFAULT -1;
	set ratio = -1.0;
	select Currencies.cur_id from Currencies 
	where Currencies.abbr = 'RUR'
	INTO rur;
	
	IF (rur<0) THEN 
	BEGIN
		SUSPEND;
	END;
	END IF;

	IF (src_currency=rur) then begin
		ratio = 1;
		suspend;
	end; end if;

	for select Excange.ratio from Excange
	where Excange.currency_sell=src_currency AND Excange.currency_buy=rur
	into ratio;
	suspend;
end //

commit;
exit;*/

/* Перегенерено */

DELIMITER //
DROP PROCEDURE IF EXISTS getRurChange;
commit;
CREATE PROCEDURE getRurChange(src_currency INT)
   SWL_return:
begin
-- This procedure was converted on Sun May 10 17:12:12 2015 using Ispirer SQLWays Express 7.0 Build 2682 32bit Licensed to I.

   DECLARE rur INT DEFAULT -1;
   DECLARE ratio FLOAT;
   DECLARE NO_DATA INT DEFAULT 0;
   DECLARE SWV_CURSOR_VAR1 cursor FOR select Currencies.cur_id from Currencies 
   where Currencies.abbr = 'RUR';
   DECLARE SWV_CURSOR_VAR2 cursor FOR select Excange.ratio from Excange
   where Excange.currency_sell = src_currency AND Excange.currency_buy = rur;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET NO_DATA = -1;
   DROP TEMPORARY TABLE IF EXISTS SWT_GETRURCHANGE;
   CREATE TEMPORARY TABLE IF NOT EXISTS SWT_GETRURCHANGE
   (
      ratio FLOAT
   );
   SET ratio = -1;
   OPEN SWV_CURSOR_VAR1;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR1 INTO rur;
   WHILE NO_DATA = 0 DO
      BEGIN 
         SET @SWV_Null_Var = 0;
      END;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR1 INTO rur;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR1;
   IF (rur < 0) THEN
      INSERT INTO SWT_GETRURCHANGE  VALUES(ratio);
		
      LEAVE SWL_return;
   END IF;
   IF (src_currency = rur) THEN
      SET ratio = 1;
      INSERT INTO SWT_GETRURCHANGE  VALUES(ratio);
		
      LEAVE SWL_return;
   END IF;
   OPEN SWV_CURSOR_VAR2;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR2 INTO ratio;
   WHILE NO_DATA = 0 DO
      begin 
         SET @SWV_Null_Var = 0;
      end;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR2 INTO ratio;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR2;
   INSERT INTO SWT_GETRURCHANGE  VALUES(ratio);

   LEAVE SWL_return;
end;
//

DELIMITER //
DROP PROCEDURE IF EXISTS getbalancerur;
commit;
CREATE PROCEDURE getbalancerur(C_NUMBER INT,
	C_PIN INT)
   SWL_return:
begin
-- This procedure was converted on Sun May 10 17:16:16 2015 using Ispirer SQLWays Express 7.0 Build 2682 32bit Licensed to I.

   DECLARE login INT DEFAULT 0;
   DECLARE currency INT DEFAULT 0;
   DECLARE changeRur FLOAT DEFAULT 0;
   DECLARE amount FLOAT;
   DECLARE NO_DATA INT DEFAULT 0;
   DECLARE SWV_CURSOR_VAR1 cursor FOR SELECT Bill.balance, Bill.currency_id FROM Bill, Card 
   WHERE Card.number = C_NUMBER AND Bill.id = Card.bill_id;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET NO_DATA = -1;
   DROP TEMPORARY TABLE IF EXISTS SWT_GETBALANCERUR;
   CREATE TEMPORARY TABLE IF NOT EXISTS SWT_GETBALANCERUR
   (
      amount FLOAT
   );
   SET login = 0;
   SET amount = -1;
   CALL checkPasswd(C_NUMBER,C_PIN);
   SELECT * INTO login FROM SWT_CHECKPASSWD LIMIT 1;
   IF (login = 0) THEN
      INSERT INTO SWT_GETBALANCERUR  VALUES(amount);
		
      LEAVE SWL_return;
   END IF;
   OPEN SWV_CURSOR_VAR1;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR1 INTO amount,currency;
   WHILE NO_DATA = 0 DO
      BEGIN 
         SET @SWV_Null_Var = 0;
      END;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR1 INTO amount,currency;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR1;
   CALL getRurChange((currency));
   SELECT * INTO changeRur FROM SWT_GETRURCHANGE LIMIT 1;
   SET amount = amount*changeRur;
   INSERT INTO SWT_GETBALANCERUR  VALUES(amount);

   LEAVE SWL_return;
end;
//

DELIMITER //
DROP PROCEDURE IF EXISTS WITHDRAWRUR;
commit;
CREATE PROCEDURE WITHDRAWRUR(C_NUMBER INT,
    C_PIN INT,
    AMOUNT FLOAT)
   SWL_return:
begin
-- This procedure was converted on Sun May 10 17:21:21 2015 using Ispirer SQLWays Express 7.0 Build 2682 32bit Licensed to I.

   DECLARE ID INT;
   DECLARE SOURCE_BILL INT;
   DECLARE ISCREDIT INT;
   DECLARE CURRENCY INT;
   DECLARE COMMISION FLOAT;
   DECLARE CHANGERATIO FLOAT;
   DECLARE EQUITY FLOAT DEFAULT 0;
   DECLARE SUCCESS FLOAT;
   DECLARE NO_DATA INT DEFAULT 0;
   DECLARE SWV_CURSOR_VAR1 cursor FOR select Credits.bill_id from Credits, Card
   where Credits.bill_id = Card.bill_id and Card.number = C_NUMBER;
   DECLARE SWV_CURSOR_VAR2 cursor FOR SELECT Bill.currency_id FROM Bill, Card
   WHERE Card.number = C_NUMBER AND Bill.id = Card.bill_id;
   DECLARE SWV_CURSOR_VAR3 cursor FOR select count(*) from Operations;
   DECLARE SWV_CURSOR_VAR4 cursor FOR select Card.bill_id from Card where Card.number = C_NUMBER;
   DECLARE SWV_CURSOR_VAR5 cursor FOR select OperationType.commission from OperationType where OperationType.type_id = 0;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET NO_DATA = -1;
   DROP TEMPORARY TABLE IF EXISTS SWT_WITHDRAWRUR;
   CREATE TEMPORARY TABLE IF NOT EXISTS SWT_WITHDRAWRUR
   (
      SUCCESS FLOAT
   );
   SET SUCCESS = 0;
   CALL getbalancerur(C_NUMBER,C_PIN);
   SELECT * INTO EQUITY FROM SWT_GETBALANCERUR LIMIT 1;

   SET ISCREDIT = -1;
   OPEN SWV_CURSOR_VAR1;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR1 INTO ISCREDIT;
   WHILE NO_DATA = 0 DO
      begin 
         SET @SWV_Null_Var = 0;
      end;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR1 INTO ISCREDIT;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR1;
   IF (AMOUNT < 0 or (EQUITY < AMOUNT and ISCREDIT < 0)) THEN
      INSERT INTO SWT_WITHDRAWRUR  VALUES(SUCCESS);
        
      LEAVE SWL_return;
   END IF;
   OPEN SWV_CURSOR_VAR2;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR2 INTO CURRENCY;
   WHILE NO_DATA = 0 DO
      BEGIN 
         SET @SWV_Null_Var = 0;
      END;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR2 INTO CURRENCY;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR2;

   CALL getrurchange((CURRENCY));
   SELECT * INTO CHANGERATIO FROM SWT_GETRURCHANGE LIMIT 1;

   UPDATE Bill SET Bill.balance =(EQUITY -AMOUNT)/CHANGERATIO
   WHERE Bill.id =(SELECT Card.bill_id FROM Card WHERE Card.number = C_NUMBER);

   OPEN SWV_CURSOR_VAR3;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR3 INTO ID;
   WHILE NO_DATA = 0 DO
      BEGIN 
         SET @SWV_Null_Var = 0;
      END;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR3 INTO ID;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR3;

   OPEN SWV_CURSOR_VAR4;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR4 INTO SOURCE_BILL;
   WHILE NO_DATA = 0 DO
      BEGIN 
         SET @SWV_Null_Var = 0;
      END;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR4 INTO SOURCE_BILL;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR4;

   INSERT INTO Operations(for_id,op_type,id_local_side,id_foreign_side,id_foreign_bank,amount,foreign_currency,`date`,info)
    VALUES(ID+10,0, SOURCE_BILL, null, null,AMOUNT,2, CURRENT_TIMESTAMP,'From terminal');

    
   OPEN SWV_CURSOR_VAR5;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR5 INTO COMMISION;
   WHILE NO_DATA = 0 DO
      BEGIN 
         SET @SWV_Null_Var = 0;
      END;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR5 INTO COMMISION;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR5;

   SET SUCCESS = AMOUNT/CHANGERATIO*COMMISION;
   INSERT INTO SWT_WITHDRAWRUR  VALUES(SUCCESS);

   LEAVE SWL_return;
end;
//


DELIMITER //
DROP PROCEDURE IF EXISTS GETOPERATIONS;
commit;
CREATE PROCEDURE GETOPERATIONS(BILL_ID INT)
   SWL_return:
begin
-- This procedure was converted on Sun May 10 17:22:22 2015 using Ispirer SQLWays Express 7.0 Build 2682 32bit Licensed to I.

   DECLARE OPTYPE INT;
   DECLARE AMOUNT INT;
   DECLARE CUR_ABBR VARCHAR(3);
   DECLARE OPDATE DATETIME;
   DECLARE INFO VARCHAR(100);
   DECLARE TYPE INT;
   DECLARE ID_FOREIGN_BANK INT;
   DECLARE ID_FOREIGN_SIDE INT;
   DECLARE NO_DATA INT DEFAULT 0;
   DECLARE SWV_CURSOR_VAR1 cursor FOR SELECT Operations.op_type,
     Operations.amount,
     Operations.`date`,
     Operations.id_foreign_side,
     Operations.id_foreign_bank,
     Operations.info
   from Operations where Operations.id_local_side = BILL_ID;
   DECLARE SWV_CURSOR_VAR2 cursor FOR select OperationType.type_id from OperationType where OperationType.type_id = OPTYPE;
   DECLARE SWV_CURSOR_VAR3 cursor FOR select Currencies.abbr from Currencies, Bill where Currencies.cur_id = Bill.currency_id and Bill.id = BILL_ID;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET NO_DATA = -1;
   DROP TEMPORARY TABLE IF EXISTS SWT_GETOPERATIONS;
   CREATE TEMPORARY TABLE IF NOT EXISTS SWT_GETOPERATIONS
   (
      AMOUNT INT,
      CUR_ABBR VARCHAR(3),
      OPDATE DATETIME,
      INFO VARCHAR(100),
      TYPE INT,
      ID_FOREIGN_BANK INT,
      ID_FOREIGN_SIDE INT
   );
   OPEN SWV_CURSOR_VAR1;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR1 INTO OPTYPE,AMOUNT,OPDATE,ID_FOREIGN_SIDE,ID_FOREIGN_BANK,INFO;
   WHILE NO_DATA = 0 DO
      OPEN SWV_CURSOR_VAR2;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR2 INTO TYPE;
      WHILE NO_DATA = 0 DO
         begin 
            SET @SWV_Null_Var = 0;
         end;
         SET NO_DATA = 0;
         FETCH SWV_CURSOR_VAR2 INTO TYPE;
      END WHILE;
      SET NO_DATA = 0;
      CLOSE SWV_CURSOR_VAR2;
      OPEN SWV_CURSOR_VAR3;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR3 INTO CUR_ABBR;
      WHILE NO_DATA = 0 DO
         begin 
            SET @SWV_Null_Var = 0;
         end;
         SET NO_DATA = 0;
         FETCH SWV_CURSOR_VAR3 INTO CUR_ABBR;
      END WHILE;
      SET NO_DATA = 0;
      CLOSE SWV_CURSOR_VAR3;
      INSERT INTO SWT_GETOPERATIONS  VALUES(AMOUNT, CUR_ABBR, OPDATE, INFO, TYPE, ID_FOREIGN_BANK, ID_FOREIGN_SIDE);
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR1 INTO OPTYPE,AMOUNT,OPDATE,ID_FOREIGN_SIDE,ID_FOREIGN_BANK,INFO;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR1;
   LEAVE SWL_return;

  
end;
//




DELIMITER //
DROP PROCEDURE IF EXISTS LOCALMONEYTRANSFER;
commit;
CREATE PROCEDURE LOCALMONEYTRANSFER(INFO VARCHAR(100),
    AMOUNT FLOAT,
    DST_BILL INT,
    SRC_BILL INT)
   SWL_return:
begin
-- This procedure was converted on Sun May 10 17:25:25 2015 using Ispirer SQLWays Express 7.0 Build 2682 32bit Licensed to I.

   DECLARE DST_EQUTY INT;
   DECLARE SRC_EQUITY FLOAT;
   DECLARE ISCREDIT INT;
   DECLARE ID INT;
   DECLARE CHANGERAIO FLOAT;
   DECLARE SRC_CURRENCY INT;
   DECLARE DST_AMOUNT INT;
   DECLARE DST_CURRENCY INT;
   DECLARE COMMISION FLOAT;
   DECLARE NO_DATA INT DEFAULT 0;
   DECLARE SWV_CURSOR_VAR1 cursor FOR select Bill.currency_id from Bill where Bill.id = SRC_BILL;
   DECLARE SWV_CURSOR_VAR2 cursor FOR select Bill.currency_id from Bill where Bill.id = DST_BILL;
   DECLARE SWV_CURSOR_VAR3 cursor FOR select Excange.ratio from Excange where Excange.currency_sell = SRC_CURRENCY and Excange.currency_buy = DST_CURRENCY;
   DECLARE SWV_CURSOR_VAR4 cursor FOR select Bill.balance from Bill where Bill.id = SRC_BILL;
   DECLARE SWV_CURSOR_VAR5 cursor FOR select Bill.balance from Bill where Bill.id = DST_BILL;
   DECLARE SWV_CURSOR_VAR6 cursor FOR select Credits.bill_id from Credits
   where Credits.bill_id = SRC_BILL;
   DECLARE SWV_CURSOR_VAR7 cursor FOR select count(*) from Operations;
   DECLARE SWV_CURSOR_VAR8 cursor FOR select OperationType.commission from OperationType where OperationType.type_id = 2;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET NO_DATA = -1;
   DROP TEMPORARY TABLE IF EXISTS SWT_LOCALMONEYTRANSFER;
   CREATE TEMPORARY TABLE IF NOT EXISTS SWT_LOCALMONEYTRANSFER
   (
      COMMISION FLOAT
   );
   SET COMMISION = -1;
   OPEN SWV_CURSOR_VAR1;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR1 INTO SRC_CURRENCY;
   WHILE NO_DATA = 0 DO
      begin  
         SET @SWV_Null_Var = 0;
      end;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR1 INTO SRC_CURRENCY;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR1;
   OPEN SWV_CURSOR_VAR2;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR2 INTO DST_CURRENCY;
   WHILE NO_DATA = 0 DO
      begin  
         SET @SWV_Null_Var = 0;
      end;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR2 INTO DST_CURRENCY;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR2;
   OPEN SWV_CURSOR_VAR3;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR3 INTO CHANGERAIO;
   WHILE NO_DATA = 0 DO
      begin 
         SET @SWV_Null_Var = 0;
      end;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR3 INTO CHANGERAIO;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR3;

   OPEN SWV_CURSOR_VAR4;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR4 INTO SRC_EQUITY;
   WHILE NO_DATA = 0 DO
      begin  
         SET @SWV_Null_Var = 0;
      end;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR4 INTO SRC_EQUITY;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR4;
   OPEN SWV_CURSOR_VAR5;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR5 INTO DST_EQUTY;
   WHILE NO_DATA = 0 DO
      begin  
         SET @SWV_Null_Var = 0;
      end;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR5 INTO DST_EQUTY;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR5;

   SET ISCREDIT = -1;
   OPEN SWV_CURSOR_VAR6;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR6 INTO ISCREDIT;
   WHILE NO_DATA = 0 DO
      begin 
         SET @SWV_Null_Var = 0;
      end;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR6 INTO ISCREDIT;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR6;
   IF (AMOUNT < 0 or (SRC_EQUITY < AMOUNT and ISCREDIT < 0)) THEN
      INSERT INTO SWT_LOCALMONEYTRANSFER  VALUES(COMMISION);
        
      LEAVE SWL_return;
   END IF;

   OPEN SWV_CURSOR_VAR7;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR7 INTO ID;
   WHILE NO_DATA = 0 DO
      BEGIN 
         SET @SWV_Null_Var = 0;
      END;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR7 INTO ID;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR7;

   OPEN SWV_CURSOR_VAR8;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR8 INTO COMMISION;
   WHILE NO_DATA = 0 DO
      begin 
         SET @SWV_Null_Var = 0;
      end;
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR8 INTO COMMISION;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR8;
   SET DST_AMOUNT = AMOUNT*CHANGERAIO;
   SET COMMISION = DST_AMOUNT*COMMISION;
   UPDATE Bill SET Bill.balance =(SRC_EQUITY -AMOUNT) WHERE Bill.id = SRC_BILL;
   UPDATE Bill SET Bill.balance =(DST_EQUTY+DST_AMOUNT -COMMISION) WHERE Bill.id = DST_BILL;

   INSERT INTO Operations(for_id,op_type,id_local_side,id_foreign_side,id_foreign_bank,amount,foreign_currency,`date`,info)
    VALUES(ID+10,2, SRC_BILL, DST_BILL, null,AMOUNT,null, CURRENT_TIMESTAMP,INFO);
  
   INSERT INTO Operations(for_id,op_type,id_local_side,id_foreign_side,id_foreign_bank,amount,foreign_currency,`date`,info)
    VALUES(ID+11,3, DST_BILL, SRC_BILL, null,DST_AMOUNT, null, CURRENT_TIMESTAMP,INFO);

  
   SET COMMISION = COMMISION/CHANGERAIO;
   INSERT INTO SWT_LOCALMONEYTRANSFER  VALUES(COMMISION);

   LEAVE SWL_return;

end;
//






DELIMITER //
DROP PROCEDURE IF EXISTS ADD_PERCENTS_CREDIT;
commit;
CREATE PROCEDURE ADD_PERCENTS_CREDIT(BILL_ID INT)
   SWL_return:
begin
-- This procedure was converted on Sun May 10 18:05:05 2015 using Ispirer SQLWays Express 7.0 Build 2682 32bit Licensed to I.

   DECLARE BALANCE FLOAT;
   DECLARE DAYS INT;
   DECLARE LAST_UPDATE DATE;
   DECLARE RATE FLOAT;
   DECLARE AMOUNT FLOAT;
   DECLARE NO_DATA INT DEFAULT 0;
   DECLARE SWV_CURSOR_VAR1 cursor FOR select Credits.rate, Credits.last_update, Bill.balance from Credits, Bill
   where Credits.bill_id = BILL_ID  and Bill.id = BILL_ID;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET NO_DATA = -1;
   DROP TEMPORARY TABLE IF EXISTS SWT_ADD_PERCENTS_CREDIT;
   CREATE TEMPORARY TABLE IF NOT EXISTS SWT_ADD_PERCENTS_CREDIT
   (
      AMOUNT FLOAT
   );
   SET AMOUNT = 0;
   OPEN SWV_CURSOR_VAR1;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR1 INTO RATE,LAST_UPDATE,BALANCE;
   WHILE NO_DATA = 0 DO
      
      /*
      if (BALANCE > 0) THEN
         INSERT INTO SWT_ADD_PERCENTS_CREDIT  VALUES(AMOUNT);
          
         LEAVE SWL_return;
      END IF;
      */



      
      SET DAYS =(CURRENT_DATE -LAST_UPDATE);
      SET RATE = RATE/365;
      SET AMOUNT = DAYS*RATE*BALANCE;
      UPDATE Bill SET Bill.balance = BALANCE+AMOUNT
      WHERE Bill.id = BILL_ID;
      update Credits SET Credits.last_update = CURRENT_TIMESTAMP
      where Credits.bill_id = BILL_ID;
      INSERT INTO SWT_ADD_PERCENTS_CREDIT  VALUES(AMOUNT);
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR1 INTO RATE,LAST_UPDATE,BALANCE;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR1;
   LEAVE SWL_return;
end;
//



DELIMITER //
DROP PROCEDURE IF EXISTS ADD_PERCENT_DEPOSIT;
commit;
CREATE PROCEDURE ADD_PERCENT_DEPOSIT()
   SWL_return:
begin
-- This procedure was converted on Sun May 10 19:47:47 2015 using Ispirer SQLWays Express 7.0 Build 2682 32bit Licensed to I.

   DECLARE BILL_ID INT;
   DECLARE AMOUNT FLOAT;
   DECLARE BILL INT;
   DECLARE NO_DATA INT DEFAULT 0;
   DECLARE SWV_CURSOR_VAR1 cursor FOR select Credits.bill_id from Credits;
   DECLARE SWV_CURSOR_VAR2 cursor FOR select Deposits.bill_id from Deposits;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET NO_DATA = -1;
   DROP TEMPORARY TABLE IF EXISTS SWT_ADD_ALL_PERCENTS;
   CREATE TEMPORARY TABLE IF NOT EXISTS SWT_ADD_ALL_PERCENTS
   (
      AMOUNT FLOAT,
      BILL INT
   );
   OPEN SWV_CURSOR_VAR1;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR1 INTO BILL_ID;
   WHILE NO_DATA = 0 DO
      CALL add_percents_credit((BILL_ID));
      SELECT * INTO AMOUNT FROM SWT_ADD_PERCENTS_CREDIT LIMIT 1;
      SET BILL = BILL_ID;
      INSERT INTO SWT_ADD_ALL_PERCENTS  VALUES(AMOUNT, BILL);
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR1 INTO BILL_ID;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR1;

   OPEN SWV_CURSOR_VAR2;
   SET NO_DATA = 0;
   FETCH SWV_CURSOR_VAR2 INTO BILL_ID;
   WHILE NO_DATA = 0 DO
      CALL add_percent_deposit((BILL_ID));
      SELECT * INTO AMOUNT FROM SWT_ADD_PERCENT_DEPOSIT LIMIT 1;
      SET BILL = BILL_ID;
      INSERT INTO SWT_ADD_ALL_PERCENTS  VALUES(AMOUNT, BILL);
      SET NO_DATA = 0;
      FETCH SWV_CURSOR_VAR2 INTO BILL_ID;
   END WHILE;
   SET NO_DATA = 0;
   CLOSE SWV_CURSOR_VAR2;
   LEAVE SWL_return;
end;
//






/*

create or alter procedure ADD_ALL_PERCENTS
returns (
    AMOUNT float,
    BILL integer)
as
declare variable BILL_ID integer;
begin
  for select "Credits"."bill_id" from "Credits" into :bill_id do
  begin
      EXECUTE PROCEDURE add_percents_credit(:bill_id) RETURNING_VALUES :amount;
      bill = :bill_id;
      suspend;
  end

  for select "Deposits"."bill_id" from "Deposits" into :bill_id do
  begin
      EXECUTE PROCEDURE add_percent_deposit(:bill_id) RETURNING_VALUES :amount;
      bill = :bill_id;
      suspend;
  end
end^
*/
