Pentru maparea temperaturilor in bucket-uri de cate o ora, am folosit HashMap,
deoarece mi s-a parut cel mai usor de utilizat din Collections, fiind usoara
parcurgerea si adaugarea de elemente in acesta. Obiectul utilizat ca index este
ora fiecarui bucket, iar obiectul asociat fiecarei ore este un ArrayList de tip
Bucket care contine temperatura observata in acea ora si timestamp-ul precis
al temperaturii.
In Main este citit fisierul de input, dupa care sunt adaugate camerele in
locuinta, iar atunci cand se citesc comenzile, le trimite la clasa
Locuinta pentru a fi executate. Clasa Locuinta la randul ei mai trimite unele
instructiuni clasei Camera pentru a executa in intregime comenzile.
Fisierul de output este trimis din Main in clasele Locuinta si Camera pentru
a putea scrie in acesta direct din acele clase.