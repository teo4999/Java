Tema mea contine Main-ul si 6 clase:
-MaxHeap: clasa de implementare a heap-ului prin vector;
-Pasageri: o clasa abstracta mostenita de alte 3 clase (am folosit conceptul de
abstractizare si mostenire);
-Persoana: clasa ce contine detaliile unei persoane;
-Singur: clasa ce defineste persoanele singure (mosteneste clasa Pasageri);
-Familie: clasa ce defineste familiile (mosteneste clasa Pasageri);
-Grup: clasa ce defineste grupurile (mosteneste clasa Pasageri).

In Main sunt declarati vectorii sng, fam si grp care retin persoanele, familiile
si grupurile dupa citirea lor, iar mai incolo sunt implementati in heap. Mai intai
se citesc persoanele din fisier, sunt adaugate in vectorii corespunzatori, iar apoi
se citesc comenzile care trebuie executate in heap.

In MaxHeap, am folosit conceptul de polimorfism la functia delete. Sunt 2 functii separate,
prima avand ca parametru o persoana singura, o familie sau un grup pe care-l sterge complet,
iar a doua are ca parametru in plus numele unei persoane din acea familie/grup care trebuie
stearsa, iar restul persoanelor din acea familie/grup sa ramana. Aceste 2 functii sunt
utilizate in functie de al 2 parametru citit din Main. Daca e null, este apelata prima,
daca nu e null, este apelata a doua.

Restul detaliilor temei mele se pot vedea in comment-urile din cod.