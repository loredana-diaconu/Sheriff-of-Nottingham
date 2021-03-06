Diaconu Maria-Loredana, 325CA

Am ales sa ma folosesc, pe langa scheletul dat, de urmatoarele clase:
I. PLAYER: Descrie un jucator oarecare, cu atributele si actiunile sale (luatul
   cartilor din pachet, adaugatul bunurilor in sac in rolul de comerciant,
   jucatul rolului de Sheriff, verificarea jucatorilor in acest rol si alte
   metode ajutatoare). Acestea sunt foloside de toate subclasele. 
   1. BASIC: Este un tip de jucator, ce completeaza cu o solutie concreta
      pentru adaugarea bunurilor in sac, unde are 2 variante (sa joace onest
      sau nu) si una pentru rolul de Sheriff. Actiunea de luare a mitei va fi
        folosita de GREEDY si BRIBED.
      a) GREEDY: il mosteneste pe BASIC pentru ca actioneaza in rolul de
         comerciant ca el, cu exceptia rundelor pare si are propria
         implementare a rolului de Sheriff.
      b) BRIBED: il mosteneste pe BASIC deoarece actioneaza uneori ca el in
         rolul de comerciant. In rolul de Sheriff trebuie sa isi afle vecinii
         pentru a-i controla.
II. LEADERBOARD si SCORE: Sunt utilizate pentru a calcula scorul jucatorilor, a
    realiza si a afisa clasamentul.
III. Clasele din pachetul "comparators": Sunt utilizate pentru sortarea
     bunurilor si a jucatorilor in functie de un anumit criteriu (de ex. id),
     sau mai multe simultan.
IV. SORTFUNCTIONS din "common": Sunt utilizate des de catre jucatori dar si de
    catre clasament pentru a sorta fie bunurile din mana sau de pe tarabele
    jucatorilor, fie jucatorii si utilizeaza clasele Comparator.
V. MAIN: Se ocupa cu desfasurarea rundelor si subrundelor, mai precis ordindea
   evenimentelor din joc:
   * Se stabilesc cartile de la masa si jucatorii.
   * La fiecare subrunda, se decide Sherifful, se impart cartile, se aleg
     bunurile si se face inspectia.
   * La finalul jocului, se face clasamentul.
   Aici se interactioneaza doar cu jucatorii, scorul si clasamentul.