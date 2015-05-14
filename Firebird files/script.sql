connect 'localhost:C:\Users\Danya\Documents\BD\Bank_term.FDB' user 'SYSDBA' password 'masterkey';
drop database; 
commit;

create database 'localhost:C:\Users\Danya\Documents\BD\Bank_term.FDB' user 'SYSDBA' password 'masterkey';
connect 'localhost:C:\Users\Danya\Documents\BD\Bank_term.FDB' user 'SYSDBA' password 'masterkey';
commit;

input Bank_term.sql;
commit;

input currency_gen.sql;
input cli_gen.sql;
input bill_gen.sql;
input card_gen.sql;
input cred_gen.sql;
input dep_gen.sql;
input exca_gen.sql;
input op_type_gen.sql;
input banks_gen.sql;
--input procedures.sql;
input bank_proc.sql;
input op_gen.sql;
commit;