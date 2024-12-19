# Etapa 1 - JPOO
Gavriliu Tudor Paul - 322CD
#### Assignment Link: [https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/2024/proiect-e1](https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/2024/proiect-e1)

## Structura Temei

* main/java/org.poo/
    * associated/
        * bankRelated/
          * Alias - Reprezinta alias-ul custom al unui utilizator. 
          * Bank - Instanta unica a bancii.
          * BankInitializer - Clasa care initializeaza si sterge datele bancii, fiind si startpoint-ul executarii comenzilor.
        * commandService/
          * bankingCommands/* - Contine toate comenzile date in timpul rularii.
          * commandUtilities/
            * BankingCommand - Reprezinta interfata comuna tuturor comenzilor.
            * StaticOutputs - Aceasta clasa exista pentru a muta procesul de serializare din comenzile in alta parte.
          * CommandService - Clasa care executa comenzile date la intrare.
        * transactionRelated/
          * commerciantReport/CommerciantReport - Aceasta clasa permite executarea usoara a comenzii SpendingReport.
                                                  Detine informatii salvate in urma unei comenzi PayOnline.
          * transactions/* - Contine toate tranzactiile efectuate/nereusite dorite la output, incapsulate in clase proprii.
          * transactionUtilities/
            * Transaction - Clasa abstracta extinsa de toate tranzactiile.
            * TransactionData - Clasa care incapsuleaza diverse informatii pentru a fi preluate cu usurinta in TransactionFactory.
            * TransactionFactory - Factory-ul care instantiaza toate tranzactiile cerute.
        * userRelated/
          * accounts/
            * AccountUtilities/
              * Account - Clasa abstracta extinsa de cele 2 tipuri de conturi.
              * AccountFactory - Factory-ul care instantiaza conturile utilizatorilor.
            * ClassicAccount - Clasa unui cont de tip clasic.
            * SavingsAccount - Clasa unui cont de economii.
          * card/Card - Clasa care reprezinta cardul unui utilizator.
                        Pentru simplitate, oneTimeCard foloseste acieasi clasa.
          * user/
            * userUtilities/UserConverter - Convertorul de utilizatori dati la intrare in program.
            * User - Clasa care reprezinta "contul" principal al unui utilizator al bancii.

## Descrierea Modului de functionare

Codul incepe rularea unui test in BankInitializer:
- Sunt adaugati toti utilizatorii in instanta unica a bancii prin intermediul unei clase utilitare. 
- Sunt calculate toate ratele folosite in cadrul unui test in clasa SimpleRateMapConverter
- Incepe executarea comenzilor prin CommandService.
- Dupa executarea tuturor comenzilor date la intrare, sunt curatate toate datele utilizate in cadrul testului curent.

Clasa CommandService are un camp numit bankingCommands care este reprezentat sub forma unui Map,
astfel se retin toate comenzile care vor fi date in timpul rularii. Prin metoda execute() sunt executate comenzile
propriu-zise.

Atunci cand se efectueaza orice fel de tranzactie, are loc acest proces:
- Sunt salvate datele specifice tranzactiei printr-o instanta a clasei TransactionData (construita cu Builder).
- Instanta TransactionData este trimisa catre TransactionFactory.
- Este instantiat, cu datele din instanta TransactionData, si returnat obiectul corespunzator tranzactiei efectuate.

Aceste tranzactii sunt apoi salvate in:
1. Map-ul din banca, fiind adaugate in lista corespunzatoare email-ului utilizatorului.
2. Lista de tranzactii din contul care a fost folosit pentru efectuarea tranzactiei.
3. Lista de obiecte de tipul CommerciantReport, prin intermediul unui obiect de tipul CommerciantReport, 
dar doar in cazul in care tranzactia este rezultatul unei plati cu cadrul

Am sa descriu modul in care am implementat comenziile de afisare:
1. Pentru comanda PrintTranzactions:
   - Am ales sa retin toate tranzactiile unui utilizator intr-un Map (cheie = email, value = Lista de tranzactii),
   stocat in instanta unica a banci, urmand ca toate acestea sa fie printate la apelarea comenzii.
2. Pentru comanda Report:
    - Am ales sa retin toate tranzactiile unui utilizator facute cu un anumit cont in acelasi cont,
   urmand sa filtrez si sa printez la output tranzactiile din intervalul dorit.
3. Pentru comanda SpedingReport:
    - Am ales sa retin intr-o lista de tipul CommerciantReport doar tranzactiile ale unui utilizator,
   facute cu un card, in contul asociat cardului care a efectuat plata, urmand sa filtrez si sa printez la
   output comerciantii si tranzactiile cu cardul din intervalul dorit. 

Output-ul dorit este serializat in clasa StaticOutputs pentru a imbunatatii pe cat de mult posibil lizibilitatea
codului din clasele de tip BankingCommand.

## Pattern-uri folosite
#### Singleton Design Pattern:
1. BankInitializer
2. Bank
3. CommandService

#### Factory Design Pattern:
1. AccountFactory
2. TransactionFactory

#### Builder Design Pattern (Lombok annotation):
1. TransactionData (strans legat de TransactionFactory)

#### (Aproape) Command Design Pattern:
1. java/org.poo/associated/bankingCommands/*

<div align="center"><img src="https://media1.tenor.com/m/aNAxmoSej-MAAAAd/dead-yukari.gif" width="600px"></div>