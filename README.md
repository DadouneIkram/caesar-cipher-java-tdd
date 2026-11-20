# Chiffrement par décalage en Java (TDD)

> **English summary** — A small Java 17 library and command-line tool implementing the Caesar cipher
> (shift cipher) on uppercase text. Built with Maven, developed test-first with JUnit 5, and checked
> against the Google Java Style with Checkstyle on every build.

Implémentation du chiffre de César : chaque lettre est remplacée par la lettre située un nombre fixe
de positions plus loin dans l'alphabet. Le projet a été réalisé dans le cadre d'un master en
informatique, en suivant une démarche de développement piloté par les tests (TDD) : chaque
comportement est d'abord décrit par un test JUnit, puis implémenté.

## Fonctionnalités

- Chiffrement et déchiffrement d'un texte composé de lettres majuscules et d'espaces.
- Les espaces sont conservés, les lettres reviennent au début de l'alphabet après `Z`.
- Décalage quelconque (positif ou nul) : un décalage de 29 équivaut à un décalage de 3.
- Validation des entrées : texte `null`, caractères non autorisés ou décalage négatif
  provoquent une `IllegalArgumentException` avec un message explicite.
- Objet immuable, construit uniquement par deux méthodes de fabrique.

## Exemple d'utilisation

### En ligne de commande

```console
$ java -jar target/caesar-cipher-java-tdd.jar chiffrer 3 "HELLO WORLD"
KHOOR ZRUOG

$ java -jar target/caesar-cipher-java-tdd.jar dechiffrer 3 "KHOOR ZRUOG"
HELLO WORLD

$ java -jar target/caesar-cipher-java-tdd.jar chiffrer 3 "hello"
Erreur : La chaîne doit contenir uniquement des majuscules et des espaces
Usage : java -jar caesar-cipher-java-tdd.jar (chiffrer|dechiffrer) <decalage> <texte>
```

### Depuis du code Java

```java
ChaineCryptee message = ChaineCryptee.deEnClair("HELLO WORLD", 3);
message.crypte();    // "KHOOR ZRUOG"
message.decrypte();  // "HELLO WORLD"

ChaineCryptee recu = ChaineCryptee.deCryptee("KHOOR ZRUOG", 3);
recu.decrypte();     // "HELLO WORLD"
```

## Compilation et lancement

Prérequis : Java 17 ou plus récent, Maven 3.6 ou plus récent.

```bash
mvn clean verify                                   # vérification du style, compilation, tests, création du jar
java -jar target/caesar-cipher-java-tdd.jar chiffrer 5 "BONJOUR"
```

## Architecture

```
src/main/java/io/github/dadouneikram/caesarcipher/
├── ChaineCryptee.java   # logique de chiffrement, validation, objet immuable
└── Main.java            # interface en ligne de commande
```

- **`ChaineCryptee`** stocke le texte chiffré et le décalage. Le constructeur est privé : on passe par
  `deEnClair(texte, decalage)` ou `deCryptee(texte, decalage)`, qui valident l'entrée avant toute
  transformation. Chiffrement et déchiffrement partagent une seule méthode `decaler`, appelée avec
  un décalage positif ou négatif ; `Math.floorMod` garantit un résultat toujours compris entre 0 et 25.
- **`Main`** lit les arguments, appelle `ChaineCryptee` et affiche le résultat. La logique est isolée
  dans une méthode `executer` qui retourne une chaîne, ce qui permet de la tester sans capturer la
  sortie standard.

## Tests

```bash
mvn test
```

20 tests JUnit 5 couvrent :

- les cas nominaux (texte simple, espaces, retour en début d'alphabet, décalage nul ou supérieur à 26) ;
- l'aller-retour chiffrement puis déchiffrement ;
- les erreurs (texte `null`, caractères invalides, décalage négatif) pour les deux méthodes de fabrique ;
- l'interface en ligne de commande (actions valides, action inconnue, décalage non numérique,
  nombre d'arguments incorrect).

Le style de code est vérifié par Checkstyle (règles Google) pendant la phase `validate` de Maven :
toute violation fait échouer le build.
