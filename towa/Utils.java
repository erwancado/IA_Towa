package towa;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Quelques fonctions utiles au projet. Vous devez comprendre ce que font ces
 * méthodes (voir leur documentation), mais pas comment elles le font (leur
 * code).
 */
public class Utils {

    /**
     * Indice de la première ligne (et colonne).
     */
    final static int NUM_LIGNE_MIN = 0;

    /**
     * Indice de la dernière ligne (et colonne).
     */
    final static int NUM_LIGNE_MAX = 15;

    /**
     * Caractère de la première ligne.
     */
    final static char CAR_PREMIERE_LIGNE = 'a';

    /**
     * Caractère de la première colonne.
     */
    final static char CAR_PREMIERE_COLONNE = 'A';

    /**
     * Caractère pour indiquer une tour noire.
     */
    final static char CAR_NOIR = 'N';

    /**
     * Caractère pour indiquer une tour blanche.
     */
    final static char CAR_BLANC = 'B';

    /**
     * Caractère pour indiquer une case vide.
     */
    final static char CAR_VIDE = ' ';

    /**
     * Convertit un numéro de ligne (par exemple 2) en nom de ligne (ici 'c').
     *
     * @param numLigne le numéro de ligne à convertir
     * @return le caractère pour cette ligne
     */
    public static char numVersCarLigne(final int numLigne) {
        if ((numLigne < NUM_LIGNE_MIN) || (numLigne > NUM_LIGNE_MAX)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à numVersCarLigne, avec numLigne = "
                    + numLigne
                    + ". Les valeurs autorisées sont les entiers entre "
                    + NUM_LIGNE_MIN + " et " + NUM_LIGNE_MAX);
        }
        return (char) (CAR_PREMIERE_LIGNE + numLigne);
    }

    /**
     * Convertit un numéro de colonne (par exemple 2) en nom de colonne (ici
     * 'C').
     *
     * @param numColonne le numéro de colonne à convertir
     * @return le caractère pour cette ligne
     */
    public static char numVersCarColonne(final int numColonne) {
        if ((numColonne < NUM_LIGNE_MIN) || (numColonne > NUM_LIGNE_MAX)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à numVersCarColonne, avec numLigne = "
                    + numColonne
                    + ". Les valeurs autorisées sont les entiers entre "
                    + NUM_LIGNE_MIN + " et " + NUM_LIGNE_MAX);
        }
        return (char) (CAR_PREMIERE_COLONNE + numColonne);
    }

    /**
     * Convertit un nom de ligne (par exemple 'c') en numéro de ligne (ici 2).
     *
     * @param nomLigne le nom de ligne à convertir
     * @return le numéro de cette ligne
     */
    public static int carLigneVersNum(final char nomLigne) {
        final char carMin = CAR_PREMIERE_LIGNE + NUM_LIGNE_MIN;
        final char carMax = CAR_PREMIERE_LIGNE + NUM_LIGNE_MAX;
        if ((nomLigne < carMin) || (nomLigne > carMax)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à carVersNum, avec car = " + nomLigne
                    + ". Les valeurs autorisées sont les caractères entre "
                    + carMin + " et " + carMax + ".");
        }
        return nomLigne - CAR_PREMIERE_LIGNE;
    }

    /**
     * Convertit un nom de colonnes (par exemple 'C') en numéro de colonne (ici
     * 2).
     *
     * @param nomColonne le nom de colonne à convertir
     * @return le numéro de cette colonne
     */
    public static int carColonneVersNum(final char nomColonne) {
        final char carMin = CAR_PREMIERE_COLONNE + NUM_LIGNE_MIN;
        final char carMax = CAR_PREMIERE_COLONNE + NUM_LIGNE_MAX;
        if ((nomColonne < carMin) || (nomColonne > carMax)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à carVersNum, avec car = " + nomColonne
                    + ". Les valeurs autorisées sont les caractères entre "
                    + carMin + " et " + carMax + ".");
        }
        return nomColonne - CAR_PREMIERE_COLONNE;
    }

    /**
     * Fonction qui renvoie une copie du tableau sans les cases non utilisées,
     * c'est-à-dire contenant null ou la chaîne vide. Par exemple {"Coucou", "",
     * null, "Hello", null} renvoie {"Coucou", "Hello"}.
     *
     * @param actions le tableau à nettoyer
     * @return le tableau nettoyé
     */
    public static String[] nettoyerTableau(final String[] actions) {
        return Arrays.stream(actions)
                .filter(a -> ((a != null) && (!"".equals(a))))
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    /**
     * Construit un plateau à partir de sa représentation sour forme texte,
     * comme renvoyé par formatTexte(), avec coordonnées et séparateurs.
     *
     * @param texteOriginal le texte du plateau
     * @return le plateau
     */
    public static Case[][] plateauDepuisTexte(final String texteOriginal) {
        final Case[][] plateau = new Case[JoueurTowa.TAILLE][JoueurTowa.TAILLE];
        final String[] lignes = texteOriginal.split("\n");
        for (int lig = 0; lig < JoueurTowa.TAILLE; lig++) {
            final String ligne = lignes[2 * (lig + 1)];
            for (int col = 0; col < JoueurTowa.TAILLE; col++) {
                String codage = ligne.substring(2 + 4 * col, 2 + 4 * col + 3);
                plateau[lig][col] = caseDepuisCodage(codage);
            }
        }
        return plateau;
    }

    /**
     * Construit une case depuis son codage (affichage dans PlateauTerminal).
     *
     * @param codage codage de la case
     * @return case correspondante
     */
    public static Case caseDepuisCodage(final String codage) {
        final Case laCase = new Case(false, false, 0, 0, 0);
        // 1er caractère : couleur tour / nature
        final char codageNature = codage.charAt(0);
        switch (codageNature) {
            case CAR_NOIR:
                laCase.tourPresente = true;
                laCase.estNoire = true;
                break;
            case CAR_BLANC:
                laCase.tourPresente = true;
                laCase.estNoire = false;
                break;
            case CAR_VIDE:
                laCase.tourPresente = false;
                break;
            default:
                System.out.println("Nature inconnue");
                break;
        }
        // 2ème caractère : hauteur
        if (laCase.tourPresente) {
            final char carHauteur = codage.charAt(1);
            laCase.hauteur = new Integer("" + carHauteur);
        }
        // 3ème caractère : altitude
        final char carAltitude = codage.charAt(2);
        if (carAltitude != ' ') {
            laCase.altitude = new Integer("" + carAltitude);
        }
        return laCase;
    }

    /**
     * Afficher les action possibles déjà calculées.
     *
     * @param actionsPossibles les actions possibles calculées
     */
    static void afficherActionsPossibles(String[] actionsPossibles) {
        System.out.println(Arrays.deepToString(actionsPossibles));
    }

    /**
     * Indique si une action est présente parmi les actions possibles calculées.
     * 
     * @param actionsPossibles actions possibles calculées
     * @param action l'action à tester
     * @return vrai ssi l'action est présente parmi les actions possibles
     */
    static boolean actionsPossiblesContient(String[] actionsPossibles,
            String action) {
        return Arrays.asList(actionsPossibles).contains(action);
    }
}
