CREATE TABLE "Currencies"(
  "cur_id" INTEGER NOT NULL,
  "abbr" VARCHAR(3),
  "name" VARCHAR(20));

ALTER TABLE "Currencies" ADD CONSTRAINT "PK_Currencies" PRIMARY KEY ("cur_id");

CREATE TABLE "Excange"(
  "currency_sell" INTEGER,
  "currency_buy" INTEGER,
  "ratio" FLOAT);

ALTER TABLE "Excange" ADD CONSTRAINT "FK_ex_sell_cur" FOREIGN KEY ("currency_sell") REFERENCES
"Currencies" ("cur_id");
ALTER TABLE "Excange" ADD CONSTRAINT "FK_ex_buy_cur" FOREIGN KEY ("currency_buy") REFERENCES
"Currencies" ("cur_id");
ALTER TABLE "Excange" ADD CONSTRAINT "UQ_ratio" UNIQUE ("currency_sell", "currency_buy");


CREATE TABLE "Banks" (
    "id" INTEGER NOT NULL,
    "name" VARCHAR(20));



CREATE TABLE "Card" (
    "number" INTEGER NOT NULL,
    "client_id" INTEGER,
    "bill_id" INTEGER,
    "start_date" DATE,
    "end_date" DATE,
    "cvc" INTEGER,
    "pin" INTEGER);

CREATE TABLE "Clients" (
    "id" INTEGER NOT NULL,
    "name" VARCHAR(50) NOT NULL,
    "address" VARCHAR(50),
    "phone" VARCHAR(10),
    "passport_No" VARCHAR(20) NOT NULL) ;

ALTER TABLE "Clients" ADD CONSTRAINT "PK_id" PRIMARY KEY ("id");
ALTER TABLE "Clients" ADD CONSTRAINT "UQ_pass_no" UNIQUE ("passport_No");



CREATE TABLE "Bill"(
  "id" INTEGER NOT NULL,
  "client_id" INTEGER NOT NULL,
  "balance" FLOAT,
  "is_active" SMALLINT,
  "currency_id" INTEGER NOT NULL);

ALTER TABLE "Bill" ADD CONSTRAINT "PK_bill_id" PRIMARY KEY ("id");
ALTER TABLE "Bill" ADD CONSTRAINT "FK_Acc_Cur_id" FOREIGN KEY ("currency_id") REFERENCES
"Currencies" ("cur_id");
ALTER TABLE "Bill" ADD CONSTRAINT "FK_bill_cl_id" FOREIGN KEY ("client_id") REFERENCES
"Clients" ("id");



CREATE TABLE "OperationType" (
    "type_id" INTEGER NOT NULL,
    "name" VARCHAR(20),
    "commission" FLOAT);



ALTER TABLE "Banks" ADD CONSTRAINT "PK_Banks" PRIMARY KEY ("id");
ALTER TABLE "Card" ADD CONSTRAINT "PK_Card" PRIMARY KEY ("number");
ALTER TABLE "OperationType" ADD CONSTRAINT "PK_OperationType" PRIMARY KEY ("type_id");

ALTER TABLE "Card" 		ADD CONSTRAINT "FK_client_id" 				FOREIGN KEY ("client_id") REFERENCES "Clients" ("id");
ALTER TABLE "Card" 		ADD CONSTRAINT "FK_Card_bill_id" 			FOREIGN KEY ("bill_id") REFERENCES "Bill" ("id");


CREATE TABLE "Deposits"(
  "bill_id" INTEGER UNIQUE,
  "rate" FLOAT,
  "start_date" DATE,
  "period" INTEGER,
  "last_update" DATE);

ALTER TABLE "Deposits" ADD CONSTRAINT "FK_de_bill_id" FOREIGN KEY ("bill_id") REFERENCES
"Bill" ("id");

CREATE TABLE "Credits"(
  "bill_id" INTEGER UNIQUE,
  "rate" FLOAT,
  "start_date" DATE,
  "period" INTEGER,
  "last_update" DATE);

ALTER TABLE "Credits" ADD CONSTRAINT "FK_cr_bill_id" FOREIGN KEY ("bill_id") REFERENCES
"Bill" ("id");


CREATE TABLE "Operations"(
  "for_id" INTEGER NOT NULL,
  "op_type" INTEGER,
  "id_local_side" INTEGER,
  "id_foreign_side" INTEGER,
  "id_foreign_bank" INTEGER,
  "amount" FLOAT,
  "foreign_currency" INTEGER,
  "date" TIMESTAMP,
  "info" VARCHAR(100));

ALTER TABLE "Operations" ADD CONSTRAINT "PK_Foreign_Operations" PRIMARY KEY ("for_id");
ALTER TABLE "Operations" ADD CONSTRAINT "FK_foreign_op_bank_id" FOREIGN KEY ("id_foreign_bank") REFERENCES
"Banks" ("id");
ALTER TABLE "Operations" ADD CONSTRAINT "FK_fo_op_lo_si" FOREIGN KEY ("id_local_side") REFERENCES
"Bill" ("id");
ALTER TABLE "Operations" ADD CONSTRAINT "FK_fo_op_type" FOREIGN KEY ("op_type") REFERENCES
"OperationType" ("type_id");

ALTER TABLE "Operations" ADD CONSTRAINT "foreign_currency_low_check" CHECK ("foreign_currency" >= 0);
ALTER TABLE "Operations" ADD CONSTRAINT "foreign_currency_high_check" CHECK ("foreign_currency" <= 2);

COMMIT;
SHOW TABLES;

