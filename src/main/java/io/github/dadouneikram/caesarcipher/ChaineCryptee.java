package io.github.dadouneikram.caesarcipher;

/**
 * Chaîne chiffrée par décalage (chiffre de César).
 *
 * <p>Seules les lettres majuscules et les espaces sont acceptés. Les espaces sont
 * conservés tels quels ; chaque lettre est décalée de {@code decalage} positions
 * dans l'alphabet, avec retour au début après 'Z'.
 *
 * <p>La classe est immuable : on la construit uniquement par les fabriques
 * {@link #deEnClair(String, int)} et {@link #deCryptee(String, int)}.
 */
public final class ChaineCryptee {

  private static final int TAILLE_ALPHABET = 26;
  private static final String FORMAT_VALIDE = "[A-Z ]*";

  private final String texteCrypte;
  private final int decalage;

  private ChaineCryptee(String texteEnClair, int decalage) {
    this.decalage = decalage % TAILLE_ALPHABET;
    this.texteCrypte = decaler(texteEnClair, this.decalage);
  }

  /**
   * Construit une chaîne chiffrée à partir d'un texte en clair.
   *
   * @param clair texte en clair (majuscules et espaces uniquement)
   * @param decalage décalage positif ou nul
   * @return la chaîne chiffrée
   * @throws IllegalArgumentException si le texte ou le décalage est invalide
   */
  public static ChaineCryptee deEnClair(String clair, int decalage) {
    valider(clair, decalage);
    return new ChaineCryptee(clair, decalage);
  }

  /**
   * Construit une chaîne chiffrée à partir d'un texte déjà chiffré.
   *
   * @param cryptee texte chiffré (majuscules et espaces uniquement)
   * @param decalage décalage utilisé lors du chiffrement
   * @return la chaîne chiffrée
   * @throws IllegalArgumentException si le texte ou le décalage est invalide
   */
  public static ChaineCryptee deCryptee(String cryptee, int decalage) {
    valider(cryptee, decalage);
    return new ChaineCryptee(decaler(cryptee, -(decalage % TAILLE_ALPHABET)), decalage);
  }

  /** Retourne le texte chiffré. */
  public String crypte() {
    return texteCrypte;
  }

  /** Retourne le texte en clair. */
  public String decrypte() {
    return decaler(texteCrypte, -decalage);
  }

  private static void valider(String texte, int decalage) {
    if (texte == null) {
      throw new IllegalArgumentException("La chaîne ne peut pas être null");
    }
    if (!texte.matches(FORMAT_VALIDE)) {
      throw new IllegalArgumentException(
          "La chaîne doit contenir uniquement des majuscules et des espaces");
    }
    if (decalage < 0) {
      throw new IllegalArgumentException("Le décalage ne peut pas être négatif");
    }
  }

  /** Décale chaque lettre de {@code decalage} positions (négatif pour reculer). */
  private static String decaler(String texte, int decalage) {
    StringBuilder resultat = new StringBuilder(texte.length());
    for (char c : texte.toCharArray()) {
      if (c == ' ') {
        resultat.append(' ');
      } else {
        int position = Math.floorMod(c - 'A' + decalage, TAILLE_ALPHABET);
        resultat.append((char) ('A' + position));
      }
    }
    return resultat.toString();
  }
}
