
DROP DATABASE IF EXISTS bank_term;

CREATE DATABASE bank_term
            DEFAULT CHARACTER SET 'utf8'
            DEFAULT COLLATE 'utf8_unicode_ci';

USE bank_term;

\. Bank_term.sql
commit;

\. currency_gen.sql

\. cli_gen.sql
\. bill_gen.sql
\. card_gen.sql
\. cred_gen.sql
\. dep_gen.sql
\. exca_gen.sql
\. op_type_gen.sql
\. banks_gen.sql
/*.\bank_proc.sql*/
\. op_gen.sql
commit;