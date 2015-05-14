----cd 'C:\Users\Danya\Documents\BD\mysql';

connect 'localhost:C:\Users\Danya\Documents\BD\mysql\BANK_TERM.FDB' user 'SYSDBA' password 'masterkey';
drop database; 
commit;

create database 'localhost:C:\Users\Danya\Documents\BD\mysql\BANK_TERM.FDB' user 'SYSDBA' password 'masterkey';
connect 'localhost:C:\Users\Danya\Documents\BD\mysql\BANK_TERM.FDB' user 'SYSDBA' password 'masterkey';
commit;



