connect 'localhost:C:\Users\Danya\Documents\BD\Bank_term.FDB' user 'SYSDBA' password 'masterkey';
commit;



SET TERM ^ ;
create or alter procedure checkPasswd (
    C_NUMBER integer,
	C_PIN integer)
returns (
    EXISTING integer)
as
begin
  existing = 0;
  for select "number" FROM "Card"
  WHERE "Card"."number"=:c_number and "Card"."pin"=:c_pin
  into :existing
  do begin 
  end
  suspend; 
end^
SET TERM ; ^


SET TERM ^ ;
create or alter procedure getRurChange (
	src_currency INTEGER)
returns (
    ratio float)
as
DECLARE VARIABLE rur integer = -1;
begin
	ratio = -1;
	for select "Currencies"."cur_id" from "Currencies" 
	where "Currencies"."abbr" = 'RUR'
	INTO :rur
	DO BEGIN END
	IF (rur<0) THEN
	BEGIN
		SUSPEND;
		EXIT;
	END
	IF (:src_currency=:rur) then
	begin
		ratio = 1;
		suspend;
		exit;
	end
	for select "Excange"."ratio" from "Excange"
	where "Excange"."currency_sell"=:src_currency AND "Excange"."currency_buy"=:rur
	into :ratio
	do begin end
	suspend;
end^
SET TERM ; ^






SET TERM ^ ;
create or alter procedure getbalancerur (
    C_NUMBER integer,
	C_PIN integer)
returns (
    amount float)
as
DECLARE VARIABLE login INTEGER = 0;
DECLARE VARIABLE currency INTEGER = 0;
DECLARE VARIABLE changeRur FLOAT = 0;
begin
	login = 0;
	amount = -1;
    EXECUTE PROCEDURE checkPasswd(C_NUMBER, C_PIN) RETURNING_VALUES :login;
	IF (:login =0) THEN BEGIN
		SUSPEND;
		EXIT;
	END
	FOR SELECT "Bill"."balance", "Bill"."currency_id" FROM "Bill", "Card" 
	WHERE "Card"."number"=:c_number AND "Bill"."id" = "Card"."bill_id"
	INTO :amount, :currency
	DO BEGIN END
	EXECUTE PROCEDURE getRurChange(:currency) RETURNING_VALUES :changeRur;
	amount = :amount* :changeRur;
	SUSPEND;
end^
SET TERM ; ^






SET TERM ^ ;

create or alter procedure WITHDRAWRUR (
    C_NUMBER integer,
    C_PIN integer,
    AMOUNT float)
returns (
    SUCCESS float)
as
declare variable ID integer;
declare variable SOURCE_BILL integer;
declare variable ISCREDIT integer;
declare variable CURRENCY integer;
declare variable COMMISION float;
declare variable CHANGERATIO float;
declare variable EQUITY float = 0;
begin
    success = 0;
    EXECUTE PROCEDURE getbalancerur(C_NUMBER, C_PIN) RETURNING_VALUES :equity;

    iscredit = -1;
    for select "Credits"."bill_id" from "Credits", "Card"
        where "Credits"."bill_id"="Card"."bill_id" and "Card"."number"=:c_number
        into :iscredit do begin end
    IF (amount<0 or (equity<amount and :iscredit<0)) THEN
    BEGIN
        SUSPEND;
        EXIT;
    END
    FOR SELECT "Bill"."currency_id" FROM "Bill", "Card"
    WHERE "Card"."number"=:c_number AND "Bill"."id" = "Card"."bill_id"
    INTO :currency
    DO BEGIN END

    EXECUTE PROCEDURE getrurchange(:currency) RETURNING_VALUES :changeratio;

    UPDATE "Bill" SET "Bill"."balance" = (:equity-:amount)/:changeratio
    WHERE "Bill"."id" IN (SELECT "Card"."bill_id" FROM "Card" WHERE "Card"."number"=:C_NUMBER);

    FOR select count (*) from "Operations" into :id  DO BEGIN END

    FOR select "Card"."bill_id" from "Card" where "Card"."number" = :c_number into :source_bill DO BEGIN END

    INSERT INTO "Operations" ("for_id","op_type","id_local_side","id_foreign_side","id_foreign_bank","amount","foreign_currency","date","info")
    VALUES (:id+10,0, :source_bill, null, null,:amount,2, current_timestamp,'From terminal');

    FOR select "OperationType"."commission" from "OperationType" where "OperationType"."type_id" = 0 into :commision     DO BEGIN END

    success=:amount/:changeratio*:commision;
    suspend;
end^

SET TERM ; ^




SET TERM ^ ;

create or alter procedure GETOPERATIONS (
    BILL_ID integer)
returns (
    AMOUNT integer,
    CUR_ABBR varchar(3),
    OPDATE timestamp,
    INFO varchar(100),
    TYPE integer,
    ID_FOREIGN_BANK integer,
    ID_FOREIGN_SIDE integer)
as
declare variable OPTYPE integer;
begin
  for SELECT "Operations"."op_type",
     "Operations"."amount",
     "Operations"."date",
     "Operations"."id_foreign_side",
     "Operations"."id_foreign_bank",
     "Operations"."info"
  from "Operations" where "Operations"."id_local_side"=:bill_id
  INTO
    :optype, :amount, :opdate, :id_foreign_side, :id_foreign_bank, :info
  DO  begin
     for select "OperationType"."type_id" from "OperationType" where "OperationType"."type_id"=:optype into :type do begin end
     for select "Currencies"."abbr" from "Currencies","Bill" where "Currencies"."cur_id"="Bill"."currency_id" and "Bill"."id"=:bill_id into :cur_abbr do begin end
     suspend;
  end

  
end^

SET TERM ; ^




SET TERM ^ ;

create or alter procedure LOCALMONEYTRANSFER (
    INFO varchar(100),
    AMOUNT float,
    DST_BILL integer,
    SRC_BILL integer)
returns (
    COMMISION float)
as
declare variable DST_EQUTY integer;
declare variable SRC_EQUITY float;
declare variable ISCREDIT integer;
declare variable ID integer;
declare variable CHANGERAIO float;
declare variable SRC_CURRENCY integer;
declare variable DST_AMOUNT integer;
declare variable DST_CURRENCY integer;
begin
  commision = -1;
  for select "Bill"."currency_id" from "Bill" where "Bill"."id"=:src_bill into :src_currency do begin  end
  for select "Bill"."currency_id" from "Bill" where "Bill"."id"=:dst_bill into :dst_currency do begin  end
  for select "Excange"."ratio" from "Excange" where "Excange"."currency_sell"=:src_currency and "Excange"."currency_buy"=:dst_currency
        into :changeraio do begin end

  for select "Bill"."balance" from "Bill" where "Bill"."id"=:src_bill into :src_equity do begin  end
  for select "Bill"."balance" from "Bill" where "Bill"."id"=:dst_bill into :dst_equty do begin  end

    iscredit = -1;
    for select "Credits"."bill_id" from "Credits"
        where "Credits"."bill_id"=:src_bill
        into :iscredit do begin end
    IF (:amount<0 or (:src_equity<:amount and :iscredit<0)) THEN
    BEGIN
        SUSPEND;
        EXIT;
    END

  FOR select count (*) from "Operations" into :id  DO BEGIN END

  FOR select "OperationType"."commission" from "OperationType" where "OperationType"."type_id"=2 into :commision do begin end
  dst_amount=:amount*:changeraio;
  commision=:dst_amount*:commision;
  UPDATE "Bill" SET "Bill"."balance" = (:src_equity-:amount) WHERE "Bill"."id"=:src_bill;
  UPDATE "Bill" SET "Bill"."balance" = (:dst_equty+:dst_amount-:commision) WHERE "Bill"."id"=:dst_bill;

  INSERT INTO "Operations" ("for_id","op_type","id_local_side","id_foreign_side","id_foreign_bank","amount","foreign_currency","date","info")
    VALUES (:id+10,2, :src_bill, :dst_bill, null,:amount,null, current_timestamp,:info);
  INSERT INTO "Operations" ("for_id","op_type","id_local_side","id_foreign_side","id_foreign_bank","amount","foreign_currency","date","info")
    VALUES (:id+11,3, :dst_bill, :src_bill, null,:dst_amount, null, current_timestamp,:info);

  commision=:commision/:changeraio;
  suspend;

end^

SET TERM ; ^



SET TERM ^ ;

create or alter procedure ADD_PERCENTS_CREDIT (
    BILL_ID integer)
returns (
    AMOUNT float)
as
declare variable BALANCE float;
declare variable DAYS integer;
declare variable LAST_UPDATE date;
declare variable RATE float;
begin
  amount = 0;
  for select "Credits"."rate", "Credits"."last_update", "Bill"."balance" from "Credits", "Bill"
        where "Credits"."bill_id"=:bill_id  and "Bill"."id"=:bill_id
        into :rate,:last_update, :balance  do
    begin
        if (balance>0) then
        begin
          suspend;
          exit;
        end
        days = (current_date-:last_update);
        rate = :rate/365;
        amount = :days*:rate*:balance;
        UPDATE "Bill" SET "Bill"."balance" = :balance+:amount
            WHERE "Bill"."id"=:bill_id;
        update "Credits" SET "Credits"."last_update" = current_date
            where "Credits"."bill_id"=:bill_id;
        suspend;
    end
end^

SET TERM ; ^




SET TERM ^ ;

create or alter procedure ADD_PERCENT_DEPOSIT (
    BILL_ID integer)
returns (
    AMOUNT float)
as
declare variable DAYS integer;
declare variable BALANCE float;
declare variable LAST_UPDATE date;
declare variable RATE float;
begin
  amount = 0;
  for select "Deposits"."rate", "Deposits"."last_update", "Bill"."balance" from "Deposits", "Bill"
        where "Deposits"."bill_id"=:bill_id  and "Bill"."id"=:bill_id
        into :rate,:last_update, :balance  do
    begin
        if (balance<0) then
        begin
          suspend;
          exit;
        end
        days = (current_date-:last_update);
        rate = :rate/365;
        amount = :days*:rate*:balance;
        UPDATE "Bill" SET "Bill"."balance" = :balance+:amount
            WHERE "Bill"."id"=:bill_id;
        update "Deposits" SET "Deposits"."last_update" = current_date
            where "Deposits"."bill_id"=:bill_id;
        suspend;
    end
end^

SET TERM ; ^








SET TERM ^ ;

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

SET TERM ; ^

