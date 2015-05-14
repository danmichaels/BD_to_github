----cd 'C:\Users\Danya\Documents\BD';

connect 'localhost:C:\Users\Danya\Documents\BD\BANK_TERM.FDB' user 'SYSDBA' password 'masterkey';
drop database; 
commit;

create database 'localhost:C:\Users\Danya\Documents\BD\BANK_TERM.FDB' user 'SYSDBA' password 'masterkey';
connect 'localhost:C:\Users\Danya\Documents\BD\BANK_TERM.FDB' user 'SYSDBA' password 'masterkey';
commit;



