connect 'localhost:C:\Users\Danya\Documents\BD\Bank_term.FDB' user 'SYSDBA' password 'masterkey';
commit;

SET TERM ^ ;
create or alter procedure checkCardCvc (
    C_NUMBER integer,
	C_CVC integer)
returns (
    EXISTING integer)
as
begin
  existing = 0;
  for select "number" FROM "Card"
  WHERE "Card"."number"=:c_number and "Card"."cvc"=:c_cvc
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
create or alter procedure getbalancerurcvc (
    C_NUMBER integer,
	C_CVC integer)
returns (
    amount float)
as
DECLARE VARIABLE login INTEGER = 0;
DECLARE VARIABLE currency INTEGER = 0;
DECLARE VARIABLE changeRur FLOAT = 0;
begin
	login = 0;
	amount = -1;
    EXECUTE PROCEDURE checkCardCvc(C_NUMBER, C_CVC) RETURNING_VALUES :login;
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



