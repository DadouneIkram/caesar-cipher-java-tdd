package io.github.dadouneikram.caesarcipher;

/**
 * Point d'entrée en ligne de commande.
 *
 * <pre>
 * java -jar caesar-cipher-java-tdd.jar chiffrer 3 "HELLO WORLD"
 * java -jar caesar-cipher-java-tdd.jar dechiffrer 3 "KHOOR ZRUOG"
 * </pre>
 */
public final class Main {

  private static final String USAGE =
      "Usage : java -jar caesar-cipher-java-tdd.jar (chiffrer|dechiffrer) <decalage> <texte>";

  private Main() {
  }

  /**
   * Lance le programme.
   *
   * @param args action, décalage et texte
   */
  public static void main(String[] args) {
    try {
      System.out.println(executer(args));
    } catch (IllegalArgumentException e) {
      System.err.println("Erreur : " + e.getMessage());
      System.err.println(USAGE);
      System.exit(1);
    }
  }

  /**
   * Exécute l'action demandée et retourne le texte produit.
   *
   * @param args action, décalage et texte
   * @return le texte chiffré ou déchiffré
   * @throws IllegalArgumentException si les arguments sont invalides
   */
  static String executer(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("trois arguments sont attendus");
    }
    int decalage;
    try {
      decalage = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("le décalage doit être un entier : " + args[1]);
    }
    switch (args[0]) {
      case "chiffrer":
        return ChaineCryptee.deEnClair(args[2], decalage).crypte();
      case "dechiffrer":
        return ChaineCryptee.deCryptee(args[2], decalage).decrypte();
      default:
        throw new IllegalArgumentException("action inconnue : " + args[0]);
    }
  }
}
