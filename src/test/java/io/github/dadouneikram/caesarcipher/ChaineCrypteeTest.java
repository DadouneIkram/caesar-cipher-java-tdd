package io.github.dadouneikram.caesarcipher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ChaineCrypteeTest {

  @Test
  void crypteChaineVide() {
    assertEquals("", ChaineCryptee.deEnClair("", 3).crypte());
  }

  @Test
  void crypteMajuscules() {
    assertEquals("DEF", ChaineCryptee.deEnClair("ABC", 3).crypte());
  }

  @Test
  void conserveLesEspaces() {
    assertEquals("B C", ChaineCryptee.deEnClair("A B", 1).crypte());
  }

  @Test
  void revientAuDebutDeAlphabet() {
    assertEquals("ABC", ChaineCryptee.deEnClair("XYZ", 3).crypte());
  }

  @Test
  void decalageNulNeModifiePasLeTexte() {
    assertEquals("HELLO", ChaineCryptee.deEnClair("HELLO", 0).crypte());
  }

  @Test
  void decalageSuperieurA26() {
    assertEquals("DEF", ChaineCryptee.deEnClair("ABC", 29).crypte());
  }

  @Test
  void decrypteTexteChiffre() {
    assertEquals("ABC", ChaineCryptee.deCryptee("DEF", 3).decrypte());
  }

  @Test
  void decrypteAvecRetourEnFinAlphabet() {
    assertEquals("XYZ", ChaineCryptee.deCryptee("ABC", 29).decrypte());
  }

  @Test
  void deCrypteeConserveLeTexteChiffre() {
    assertEquals("DEF", ChaineCryptee.deCryptee("DEF", 3).crypte());
  }

  @Test
  void chiffrerPuisDechiffrerRedonneLeTexteInitial() {
    ChaineCryptee c = ChaineCryptee.deEnClair("HELLO WORLD", 5);
    assertEquals("HELLO WORLD", ChaineCryptee.deCryptee(c.crypte(), 5).decrypte());
  }

  @Test
  void refuseTexteEnClairNull() {
    assertThrows(IllegalArgumentException.class, () -> ChaineCryptee.deEnClair(null, 3));
  }

  @Test
  void refuseTexteChiffreNull() {
    assertThrows(IllegalArgumentException.class, () -> ChaineCryptee.deCryptee(null, 3));
  }

  @Test
  void refuseDecalageNegatif() {
    assertThrows(IllegalArgumentException.class, () -> ChaineCryptee.deEnClair("ABC", -2));
  }

  @Test
  void refuseCaracteresInvalides() {
    assertThrows(IllegalArgumentException.class, () -> ChaineCryptee.deEnClair("abc123", 3));
  }

  @Test
  void refuseTexteChiffreInvalide() {
    assertThrows(IllegalArgumentException.class, () -> ChaineCryptee.deCryptee("abc", 3));
  }
}
