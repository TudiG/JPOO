

# Etapa 1 - JPOO
Gavriliu Tudor Paul - 322CD
#### Assignment Link: [https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/2024/proiect-e1](https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/2024/proiect-e1)

## Structura Temei

* main/java/org.poo/
    * associated/
        * actionManager/
            * commandInterface/
                * GameCommand - Interfata GameCommand defineste o actiune specifica ce poate fi executata in cadrul unui joc.
            * commands/* - Fisierul contine toate clasele specifice comenzilor date in timpul unui meci.
            * commandUtilities/* - Fisierul contine clase utilitare folosite pentru verificare conditiilor efectuarii anumitor comenzi.
            * ActionManager - Clasa ActionManager gestioneaza si executa comenzile date in timpul unui meci.
        * cards/
            * cardTemplates/* - Fisierul contine interfetele pentru actiunile cartilor si clasele abstracte extinse de toate cartile din joc.
            * cardTypes/* - Fisierul contine clasele asociate celor 8 tipuri de minioni.
            * heroTypes/* - Fisierul contine clasele asociate celor 4 tipuri de eroi.
            * HeroFactory - Clasa HeroFactory este un factory care creeaza obiecte de tip HeroCard pe baza unui obiect de tip CardInput.
            * MinionFactory - Clasa MinionFactory este un factory care creeaza obiecte de tip MinionCard pe baza unui obiect de tip CardInput.
        * customPrettyPrinter/
            * CustomPrettyPrinter - Aceasta clasa doar asigura o indentare asemanatoare cu cea din fisierele ref.
        * gameInitializer/
            * GameInitializer - Clasa GameInitializer initializeaza jocul si proceseaza datele de intrare pentru a crea obiectele necesare unui joc.
        * gameRelated/
            * GameBoard - Clasa GameBoard contine tabla de joc pe care se plaseaza cartile minion.
            * GameService - Clasa GameService retine parametrii necesari pentru logica jocului.
            * ScoreKeeper - Clasa ScoreKeeper contorizeaza scorul jucatorilor si numarul total de jocuri jucate.
        * playerRelated/
            * Player - Clasa Player retine toti parametrii specifici unui jucator.
            * PlayerDeck - Clasa PlayerDeck reprezinta deck-ul unui jucator.
            * PlayerHand - Clasa PlayerHand reprezinta mana unui jucator.
        * GlobalVariable - contine constantele specifice temei.

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
#### Singleton Eager Design Pattern:
1. GameInitializer
2. ActionManager

#### Factory Design Pattern:
1. HeroFactory
2. MinionFactory

#### Command Design Pattern:
1. src/associated/actionManager/commands/*

<div align="center"><img src="https://media1.tenor.com/m/aNAxmoSej-MAAAAd/dead-yukari.gif" width="300px"></div>