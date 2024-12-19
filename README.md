

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

Codul incepe un joc cu intializarea sa in GameInitializer:
1. Se initializeaza pentru mai multe jocuri:
- scoreKeeper

2. Se initializeaza jocul curent pe baza datelor din inputData:
- playerOneHero + playerTwoHero
- playerOneDeck + playerTwoDeck
- PlayerOne + PlayerTwo + referinta catre Player-ul curent
- gameBoard, contine o lista de liste (4x5 slot-uri)
- gameService + este initializata runda 1 a jocului

Pe baza cartilor de tip CardInput se creeaza printr-un MinionFactory
cartile care vor intra intr-un deck, acestea urmand sa fie amestecate.
Acelasi proces este efectuat si pentru erou printr-un HeroFactory.

De asemenea, MinionCard este o clasa abstracta care:
- implementeaza interfata MinionCardActions().
- este extinsa de cele 8 tipuri de minioni, care fac override
  doar la metoda useAbility(...).

Procesul este identic pentru cei 4 eroi, doar ca acestia au clasa o
abstracta proprie si o interfata proprie.

Comenzile date la actionInput sunt "prelucrate" in ActionManager.
Acesta detine un Map (cheile corespund cu numele comenzilor),
si executa comenzile din Map pe baza design pattern-ului command:
- Comenzile au clasele lor specifice si implementeaza interfata GameCommand
  cu metoda execute(...)
- Aceste clase fac override la metoda execute(...),
  implementand functionalitatea dorita de la fiecare comanda.

Output-ul dorit este serializat in cadrul metodelor execute() din fiecare comanda.
Exceptie de la aceasta conventie fac:
- UseAttackHeroCommand (output-ul este dat din cadrul cartii care ucide eroul).
- Toate clasele utilitare care tin de verificarea conditiilor pentru efectuarea
  unei comenzi (cazul in care se intampina o eroare).

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

<div align="center"><img src="https://media1.tenor.com/m/aNAxmoSej-MAAAAd/dead-yukari.gif" width="300px"></div>