package towa;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Votre IA pour le jeu Towa, au niveau 6.
 */
public class IATowa {

    /**
     * Hôte du grand ordonnateur.
     */
    String hote = null;

    /**
     * Port du grand ordonnateur.
     */
    int port = -1;

    /**
     * Ordre de jeu.
     */
    final int ordre;

    /**
     * Interface pour le protocole du grand ordonnateur.
     */
    TcpGrandOrdonnateur grandOrdo = null;

    /**
     * Taille du plateau.
     */
    static final int TAILLE = 16;

    /**
     * Nombre maximal de tours de jeu.
     */
    static final int NB_TOURS_JEU_MAX = 40;

    /**
     * Constructeur.
     *
     * @param hote Hôte.
     * @param port Port.
     * @param ordre Ordre de jeu.
     */
    public IATowa(String hote, int port, int ordre) {
        this.hote = hote;
        this.port = port;
        this.grandOrdo = new TcpGrandOrdonnateur();
        this.ordre = ordre;
    }

    /**
     * Connexion au Grand Ordonnateur.
     *
     * @throws IOException exception sur les entrées/sorties
     */
    void connexion() throws IOException {
        System.out.println(
                "Connexion au Grand Ordonnateur : " + hote + " " + port);
        System.out.flush();
        grandOrdo.connexion(hote, port);
    }

    /**
     * Boucle de jeu : envoi des actions que vous souhaitez jouer, et réception des actions de l'adversaire.
     *
     * @throws IOException exception sur les entrées/sorties
     */
    void toursDeJeu() throws IOException {
        // le plateau, vide au départ
        Case[][] plateau = new Case[TAILLE][TAILLE];
        //Initialisation des cases
        for (int i = 0; i < TAILLE; i++) {

            for (int j = 0; j < TAILLE; j++) {

                plateau[i][j] = new Case(false, true, 0, 0, 0);

            }

        }
        // compteur de tours de jeu (entre 1 et 40)
        int nbToursJeu = 1;
        // l'ordre du joueur courant : 1=noir, 2=blanc 
        int ordreCourant = 1;
        // booléen pour détecter la fin du jeu
        boolean fin = false;

        while (!fin) {

            boolean disqualification = false;

            if (ordreCourant == ordre) {
                // à nous de jouer !
                jouer(plateau, nbToursJeu);
            } else {
                // à l'adversaire de jouer : on récupère son action
                char action = 'x'; // valeur écrasée ensuite
                char ligne = 'x'; // valeur écrasée ensuite
                char colonne = 'x'; // valeur écrasée ensuite
                try {
                    action = grandOrdo.receptionCaractere();
                    ligne = grandOrdo.receptionCaractere();
                    colonne = grandOrdo.receptionCaractere();
                } catch (IOException e) {
                    // si ça se passe mal, fin de partie
                    System.out.println("Erreur à la réception du coup de "
                            + "l'adversaire : fin de partie.");
                    fin = true;
                }
                if (action == 'Z') {
                    System.out.println("L'adversaire est disqualifié.");
                    disqualification = true;
                } else if (!fin) {
                    System.out.println("L'adversaire joue : "
                            + action + ligne + colonne + ".");
                    if (action == 'P') {
                        ajoutPose(plateau, Utils.carLigneVersNum(ligne), Utils.carColonneVersNum(colonne), ordreCourant);
                    } else {
                        activationEnnemie(plateau, Utils.carLigneVersNum(ligne), Utils.carColonneVersNum(colonne), ordreCourant);
                    }
                }
            }

            if (nbToursJeu == NB_TOURS_JEU_MAX || disqualification) {
                // fini
                fin = true;
            } else {
                // au suivant
                nbToursJeu++;
                ordreCourant = (ordreCourant % 2) + 1;
            }
        }
    }

    /**
     * Pose d'un pion dans le plateau.
     *
     * @param plateau le plateau à mettre à jour
     * @param ligne la ligne de la case de pose ennemie
     * @param colonne la colonne de la case de pose ennemie
     * @param ordreCourant l'ordre de jeu pour connaître la couleur du joueur ennemi.
     */
    void ajoutPose(Case[][] plateau, int ligne, int colonne, int ordreCourant) {
        plateau[ligne][colonne].tourPresente = true;
        boolean estNoir = ordreCourant == 1;
        if (doublePose(plateau, ligne, colonne, estNoir)) {
            plateau[ligne][colonne].estNoire = estNoir;
            plateau[ligne][colonne].hauteur += 2;
        } else {
            plateau[ligne][colonne].estNoire = estNoir;
            plateau[ligne][colonne].hauteur += 1;
        }
    }

    void activationEnnemie(Case[][] plateau, int ligne, int colonne, int ordreCourant) {
        boolean estNoir = ordreCourant == 1;
        int hauteur = plateau[ligne][colonne].hauteur;
        parcoursLigneEnnemi(plateau, ligne, estNoir, hauteur);
        parcoursColonneEnnemi(plateau, colonne, estNoir, hauteur);
        carreActivationEnnemi(plateau, ligne, colonne, estNoir, hauteur);
    }

    String activationPerso(Case[][] plateau, int ligne, int colonne, boolean estNoir,int hauteur) {
        int nbPionsDetruits = parcoursColonnePerso(plateau, colonne, estNoir, hauteur) + parcoursLignePerso(plateau, ligne, estNoir, hauteur) + carreActivationPerso(plateau, ligne, colonne, estNoir, hauteur);
        String action = "A" // action = Activation
                + Utils.numVersCarLigne(ligne) // convertit la ligne en lettre
                + Utils.numVersCarColonne(colonne)
                + nbPionsDetruits;
        return action;
    }

    /**
     * Fonction exécutée lorsque c'est à notre tour de jouer. Cette fonction envoie donc l'action choisie au serveur.
     *
     * @param plateau le plateau de jeu
     * @param nbToursJeu numéro du tour de jeu
     * @throws IOException exception sur les entrées / sorties
     */
    void jouer(Case[][] plateau, int nbToursJeu) throws IOException {
        boolean estNoir = ordre == 1;
        String[] actionsPossibles = actionsPossibles(plateau, estNoir);
        int nbActions = actionsPossibles.length;
        int iAction = 0;
        int pionsDetruits=0;
        for(int i = 0; i<nbActions;i++){
            if(actionsPossibles[i].charAt(0)=='A' && actionsPossibles[i].charAt(3)>pionsDetruits){
                iAction=i;
                pionsDetruits=actionsPossibles[i].charAt(3);
            }
            else{
                if(actionsPossibles[i].charAt(3) >= actionsPossibles[i+1].charAt(3)){
                    iAction = i;
                    i = nbActions-1;
                }
            }
        }
        String action = actionsPossibles[iAction];
        grandOrdo.envoiCaractere(action.charAt(0));
        grandOrdo.envoiCaractere(action.charAt(1));
        grandOrdo.envoiCaractere(action.charAt(2));

        if (action.charAt(0) == 'P') {
            ajoutPose(plateau, Utils.carLigneVersNum(action.charAt(1)), Utils.carColonneVersNum(action.charAt(2)), ordre);
        } else {
            activationEnnemie(plateau, Utils.carLigneVersNum(action.charAt(1)), Utils.carColonneVersNum(action.charAt(2)), ordre);
        }

    }

    /**
     * Vérifie si une double pose est possible.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case
     * @param colonne colonne de la case
     * @param estNoir couleur du joueur
     * @return true si la case est vide et qu'il y a une tour ennemie adjacente.
     */
    boolean doublePose(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        if ((!plateau[ligne][colonne].tourPresente && adjacent(plateau, ligne, colonne, estNoir))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Parcours une ligne ou une colonne en fonction de ses paramètres et renvoie la ligne et la colonne de la première tour rencontrée ainsi que sa couleur.
     *
     * @param plateau le plateau
     * @param ligDepart indice de la ligne de départ
     * @param colDepart indice de la colonne de départ
     * @param incremLig incrément pour les lignes
     * @param incremCol incrément pour les colonnes
     * @return un tableau contenant la ligne, la colonne et la couleur (1 pour noir, 2 pour blanc)
     */
    int parcoursLignePerso(Case[][] plateau, int ligne, boolean estNoir, int hauteur) {
        int nbPionsDetruits = 0;
        for (int i = 0; i < TAILLE; i++) {
            if (plateau[ligne][i].tourPresente && plateau[ligne][i].hauteur < hauteur && plateau[ligne][i].estNoire != estNoir) {
                nbPionsDetruits += plateau[ligne][i].hauteur;
            }
        }
        return nbPionsDetruits;
    }

    int parcoursColonnePerso(Case[][] plateau, int colonne, boolean estNoir, int hauteur) {
        int nbPionsDetruits = 0;
        for (int i = 0; i < TAILLE; i++) {
            if (plateau[i][colonne].tourPresente && plateau[i][colonne].hauteur < hauteur && plateau[i][colonne].estNoire != estNoir) {
                nbPionsDetruits += plateau[i][colonne].hauteur;
            }
        }
        return nbPionsDetruits;
    }

    /**
     * Parcours une ligne ou une colonne en fonction de ses paramètres et renvoie la ligne et la colonne de la première tour rencontrée ainsi que sa couleur.
     *
     * @param plateau le plateau
     * @param ligDepart indice de la ligne de départ
     * @param colDepart indice de la colonne de départ
     * @param incremLig incrément pour les lignes
     * @param incremCol incrément pour les colonnes
     * @return un tableau contenant la ligne, la colonne et la couleur (1 pour noir, 2 pour blanc)
     */
    void parcoursLigneEnnemi(Case[][] plateau, int ligne, boolean estNoir, int hauteur) {
        for (int i = 0; i < TAILLE; i++) {
            if (plateau[ligne][i].tourPresente && plateau[ligne][i].hauteur < hauteur && plateau[ligne][i].estNoire != estNoir) {
                detruireTour(plateau, ligne, i);
            }
        }
    }

    void parcoursColonneEnnemi(Case[][] plateau, int colonne, boolean estNoir, int hauteur) {
        for (int i = 0; i < TAILLE; i++) {
            if (plateau[i][colonne].tourPresente && plateau[i][colonne].hauteur < hauteur && plateau[i][colonne].estNoire != estNoir) {
                detruireTour(plateau, i, colonne);
            }
        }
    }

    /**
     * Compte le nombre de pions à éliminer autour de la tour activée.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case sur laquelle la tour est activée
     * @param colonne colonne de la case sur laquelle la tour est activée
     * @param estNoir couleur du joueur
     * @return le nombre de pions à éliminer
     */
    int carreActivationPerso(Case[][] plateau, int ligne, int colonne, boolean estNoir, int hauteur) {
        int nbPionsDetruits=0;
        for (int i = ligne - 1; i <= ligne + 1; i += 2) {
            for (int j = colonne - 1; j <= colonne + 1; j += 2) {
                if (caseExiste(i, j)) {
                    if ((plateau[i][j].tourPresente) && (plateau[i][j].estNoire != estNoir)) {
                        if (plateau[i][j].hauteur < hauteur) {
                            nbPionsDetruits+=plateau[i][j].hauteur;
                        }
                    }
                }
            }
        }
        return nbPionsDetruits;
    }

    /**
     * Compte le nombre de pions à éliminer autour de la tour activée.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case sur laquelle la tour est activée
     * @param colonne colonne de la case sur laquelle la tour est activée
     * @param estNoir couleur du joueur
     * @return le nombre de pions à éliminer
     */
    void carreActivationEnnemi(Case[][] plateau, int ligne, int colonne, boolean estNoir, int hauteur) {

        for (int i = ligne - 1; i <= ligne + 1; i += 2) {
            for (int j = colonne - 1; j <= colonne + 1; j += 2) {
                if (caseExiste(i, j)) {
                    if ((plateau[i][j].tourPresente) && (plateau[i][j].estNoire != estNoir) && (plateau[i][j].hauteur) < hauteur) {
                        detruireTour(plateau, i, j);
                    }
                }
            }
        }
    }

    void detruireTour(Case[][] plateau, int ligne, int colonne) {
        plateau[ligne][colonne].tourPresente = false;
        plateau[ligne][colonne].hauteur = 0;
    }

    /**
     * Indique si la case existe, c'est à dire, si elle est dans la grille.
     *
     * @param ligne ligne de la case à considérer
     * @param colonne colonne de la case à considérer
     * @return true si elle existe, false sinon
     */
    boolean caseExiste(int ligne, int colonne) {
        if (ligne >= 0 && ligne < TAILLE && colonne >= 0 && colonne < TAILLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Vérifie si il y a une tour ennemie adjacente à la case.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case
     * @param colonne colonne de la case
     * @param estNoir couleur du joueur
     * @return true si il y en a une, false sinon.
     */
    boolean adjacent(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        boolean adjacent = false;
        for (int i = ligne - 1; i <= ligne + 1; i++) {
            for (int j = colonne - 1; j <= colonne + 1; j++) {
                if (caseExiste(i, j)) {
                    if ((plateau[i][j].tourPresente) && (plateau[i][j].estNoire != estNoir)) {
                        adjacent = true;
                    }
                }
            }
        }
        return adjacent;
    }

    /**
     * Indique s'il est possible de poser un pion sur une case pour ce plateau, ce joueur, dans ce niveau.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case à considérer
     * @param colonne colonne de la case à considérer
     * @param estNoir vrai ssi il s'agit du tour du joueur noir
     * @return vrai ssi la pose d'un pion sur cette case est autorisée dans ce niveau
     */
    boolean posePossible(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        if (plateau[ligne][colonne].hauteur < 4) {
            if (!plateau[ligne][colonne].tourPresente) {
                return true;
            }
            if (plateau[ligne][colonne].tourPresente && plateau[ligne][colonne].estNoire == estNoir) {
                return true;
            }
        }
        return false;

    }

    /**
     * Indique s'il est possible d'active une tour sur une case pour ce plateau, ce joueur, dans ce niveau.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case à considérer
     * @param colonne colonne de la case à considérer
     * @param estNoir vrai ssi il s'agit du tour du joueur noir
     * @return vrai ssi la pose d'un pion sur cette case est autorisée dans ce niveau
     */
    boolean activationPossible(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        if (plateau[ligne][colonne].tourPresente && plateau[ligne][colonne].estNoire == estNoir) {
            return true;
        }
        return false;
    }

    /**
     * Action de pose d'un pion.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case
     * @param colonne colonne de la case
     * @param estNoir couleur du joueur
     * @param nbPionsNoirs le nombre de pions noirs sur le plateau
     * @param nbPionsBlancs le nombre de pions blancs sur le plateau
     * @return l'action de pose à ajouter dans les actions possibles.
     */
    String actionPose(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        int nbPionsPose = 0;

        if (doublePose(plateau, ligne, colonne, estNoir)) {
            nbPionsPose += 2;
        } else {
            nbPionsPose++;
        }
        // on ajoute l'action dans les actions possibles
        String action = "P" // action = Pose
                + Utils.numVersCarLigne(ligne) // convertit la ligne en lettre
                + Utils.numVersCarColonne(colonne) // convertit la colonne en lettre
                + nbPionsPose;// nombre de pions noirs
        return action;
    }

    /**
     * Cette méthode renvoie, pour un plateau donné et un joueur donné, toutes les actions possibles pour ce joueur.
     *
     * @param plateau le plateau considéré
     * @param joueurNoir vrai si le joueur noir joue, faux si c'est le blanc
     * @param niveau le niveau de la partie à jouer
     * @return l'ensemble des actions possibles
     */
    String[] actionsPossibles(Case[][] plateau, boolean joueurNoir) {
        //initialisation de notre nombre d'action
        int nbActions = 0;
        //comptage des pions sur notre plateau

        // permet d'agrandir la taille de la grille en fonction des pions déjà présent et des actions
        String actions[];

        actions = new String[TAILLE * TAILLE + 2];

        for (int lig = 0; lig < TAILLE; lig++) { //pour chaque ligne

            for (int col = 0; col < TAILLE; col++) { // pour chaque colonne

                if (activationPossible(plateau, lig, col, joueurNoir)) {// si l'activation d'une tour de cette couleur est possible sur cette case
                    int hauteur = plateau[lig][col].hauteur;
                    actions[nbActions] = activationPerso(plateau, lig, col,joueurNoir,hauteur);
                    nbActions++;
                }
                if (posePossible(plateau, lig, col, joueurNoir)) { // si la pose d'un pion de cette couleur est possible sur cette case
                    actions[nbActions] = actionPose(plateau, lig, col, joueurNoir);
                    nbActions++;
                }
            }
        }
        return Utils.nettoyerTableau(actions);
    }

    /**
     * Programme principal. Il sera lancé automatiquement, ce n'est pas à vous de le lancer.
     *
     * @param args Arguments.
     */
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        System.out.println("Démarrage le " + format.format(new Date()));
        System.out.flush();
        // « create » du protocole du grand ordonnateur.
        final String USAGE
                = System.lineSeparator()
                + "\tUsage : java " + IATowa.class
                        .getName()
                + " <hôte> <port> <ordre>";
        if (args.length != 3) {
            System.out.println("Nombre de paramètres incorrect." + USAGE);
            System.out.flush();
            System.exit(1);
        }
        String hote = args[0];
        int port = -1;
        try {
            port = Integer.valueOf(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Le port doit être un entier." + USAGE);
            System.out.flush();
            System.exit(1);
        }
        int ordre = -1;
        try {
            ordre = Integer.valueOf(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("L'ordre doit être un entier." + USAGE);
            System.out.flush();
            System.exit(1);
        }
        try {
            IATowa iaTowa = new IATowa(hote, port, ordre);
            iaTowa.connexion();
            iaTowa.toursDeJeu();
        } catch (IOException e) {
            System.out.println("Erreur à l'exécution du programme : \n" + e);
            System.out.flush();
            System.exit(1);
        }
    }
}
