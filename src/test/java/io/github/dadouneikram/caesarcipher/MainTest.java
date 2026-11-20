package io.github.dadouneikram.caesarcipher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MainTest {

  @Test
  void chiffrer() {
    assertEquals("KHOOR ZRUOG", Main.executer(new String[] {"chiffrer", "3", "HELLO WORLD"}));
  }

  @Test
  void dechiffrer() {
    assertEquals("HELLO WORLD", Main.executer(new String[] {"dechiffrer", "3", "KHOOR ZRUOG"}));
  }

  @Test
  void refuseActionInconnue() {
    assertThrows(IllegalArgumentException.class,
        () -> Main.executer(new String[] {"inverser", "3", "ABC"}));
  }

  @Test
  void refuseDecalageNonNumerique() {
    assertThrows(IllegalArgumentException.class,
        () -> Main.executer(new String[] {"chiffrer", "trois", "ABC"}));
  }

  @Test
  void refuseNombreArgumentsIncorrect() {
    assertThrows(IllegalArgumentException.class,
        () -> Main.executer(new String[] {"chiffrer", "3"}));
  }
}
