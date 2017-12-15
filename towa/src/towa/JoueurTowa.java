package towa;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Joueur implémentant les actions possibles à partir d'un plateau, pour un niveau donné.
 */
public class JoueurTowa implements IJoueurTowa {

    /**
     * Taille d'un côté du plateau (carré).
     */
    final static int TAILLE = 16;

    /**
     * Cette méthode renvoie, pour un plateau donné et un joueur donné, toutes les actions possibles pour ce joueur.
     *
     * @param plateau le plateau considéré
     * @param joueurNoir vrai si le joueur noir joue, faux si c'est le blanc
     * @param niveau le niveau de la partie à jouer
     * @return l'ensemble des actions possibles
     */
    @Override
    public String[] actionsPossibles(Case[][] plateau, boolean joueurNoir, int niveau) {
        // afficher l'heure de lancement
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        System.out.println("actionsPossibles : lancement le " + format.format(new Date()));
        //initialisation de notre nombre d'action
        int nbActions = 0;
        //comptage des pions sur notre plateau
        int nbPionsNoirs = 0;
        int nbPionsBlancs = 0;

        for (int lig = 0; lig < TAILLE; lig++) { //pour chaque ligne

            for (int col = 0; col < TAILLE; col++) { // pour chaque colonne
                if (plateau[lig][col].tourPresente) {
                    if (plateau[lig][col].estNoire) {
                        nbPionsNoirs += plateau[lig][col].hauteur;
                    } else {
                        nbPionsBlancs += plateau[lig][col].hauteur;
                    }
                }
            }
        }

        // permet d'agrandir la taille de la grille en fonction des pions déjà présent et des actions
        String actions[];
        if (joueurNoir) {
            actions = new String[TAILLE * TAILLE * TAILLE * TAILLE + nbPionsNoirs + 4];
        } else {
            actions = new String[TAILLE * TAILLE * TAILLE * TAILLE + nbPionsBlancs + 4];
        }
        //les quatres différentes actions pour nos chatons kamikazes
        actions[nbActions] = actionKamikaze(plateau, nbPionsNoirs, nbPionsBlancs, 'N'); //Depuis le nord
        nbActions++;
        actions[nbActions] = actionKamikaze(plateau, nbPionsNoirs, nbPionsBlancs, 'S'); //Depuis le sud
        nbActions++;
        actions[nbActions] = actionKamikaze(plateau, nbPionsNoirs, nbPionsBlancs, 'E'); //Depuis l'est
        nbActions++;
        actions[nbActions] = actionKamikaze(plateau, nbPionsNoirs, nbPionsBlancs, 'O'); //Depuis l'ouest
        nbActions++;
        for (int lig = 0; lig < TAILLE; lig++) { //pour chaque ligne

            for (int col = 0; col < TAILLE; col++) { // pour chaque colonne

                if (activationPossible(plateau, lig, col, joueurNoir)) { // si l'activation d'une tour de cette couleur est possible sur cette case
                    actions[nbActions] = actionActivation(plateau, lig, col, joueurNoir, nbPionsNoirs, nbPionsBlancs);
                    nbActions++;
                }

                if (fusionPossible(plateau, lig, col, joueurNoir)) { // si la fusion d'une tour de cette couleur est possible sur cette case
                    actions[nbActions] = actionFusion(plateau, lig, col, joueurNoir, nbPionsNoirs, nbPionsBlancs);
                    nbActions++;
                }
                if (magiePossible(plateau, lig, col, joueurNoir)) { // si la magie d'une tour de cette couleur est possible
                    actions[nbActions] = actionMagie(plateau, lig, col, nbPionsNoirs, nbPionsBlancs);
                    nbActions++;
                }

                if (posePossible(plateau, lig, col, joueurNoir)) { // si la pose d'un pion de cette couleur est possible sur cette case
                    actions[nbActions] = actionPose(plateau, lig, col, joueurNoir, nbPionsNoirs, nbPionsBlancs);
                    nbActions++;
                }
            }
        }

        System.out.println("actionsPossibles : fin");
        return Utils.nettoyerTableau(actions);
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
        if (niveau(plateau, ligne, colonne) < 4 && !(plateau[ligne][colonne].altitude == 3 && adjacent(plateau, ligne, colonne, estNoir)) && plateau[ligne][colonne].nature != 1) {
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

    int niveau(Case[][] plateau, int ligne, int colonne) {
        return (plateau[ligne][colonne].hauteur + plateau[ligne][colonne].altitude);
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
    int carreActivation(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        int nbPions = 0;
        for (int i = ligne - 1; i <= ligne + 1; i += 2) {
            for (int j = colonne - 1; j <= colonne + 1; j += 2) {
                if (caseExiste(i, j)) {
                    if ((plateau[i][j].tourPresente) && (plateau[i][j].estNoire != estNoir)) {
                        if (niveau(plateau, i, j) < niveau(plateau, ligne, colonne)) {
                            nbPions += plateau[i][j].hauteur;
                        }
                    }
                }
            }
        }
        return nbPions;
    }

    /**
     * Compte le nombre de pions à éliminer autour de la tour fusionnée.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case sur laquelle se trouve la tour à fusionner
     * @param colonne colonne de la case sur laquelle se trouve la tour à fusionner
     * @param estNoir couleur du joueur
     * @return le nombre de pions à éliminer
     */
    int carreFusion(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        int nbPions = 0;
        for (int i = ligne - 1; i <= ligne + 1; i += 2) {
            for (int j = colonne - 1; j <= colonne + 1; j += 2) {
                if (caseExiste(i, j)) {
                    if ((plateau[i][j].tourPresente) && (plateau[i][j].estNoire == estNoir)) {

                        nbPions += plateau[i][j].hauteur;

                    }
                }
            }
        }
        return nbPions;
    }

    /**
     * Compte le nombre de tours ennemies desctructibles sur la ligne.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case activée
     * @param colonne colonne de la case activée
     * @param estNoir couleur du joueur
     * @return le nombre de tours ennemies desctructibles sur la ligne
     */
    int ligneActivation(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        int nbPions = 0;
        int i = colonne - 1;
        boolean tourAtteinteGauche = false;
        boolean tourAtteinteDroite = false;

        while (i >= 0 && !tourAtteinteGauche) {
            if (plateau[ligne][i].tourPresente) {
                tourAtteinteGauche = true;
                if (niveau(plateau, ligne, i) < niveau(plateau, ligne, colonne) && (plateau[ligne][i].estNoire != estNoir)) {
                    nbPions += plateau[ligne][i].hauteur;
                }
            }
            i--;
        }

        i = colonne + 1;
        while (i < TAILLE && !tourAtteinteDroite) {
            if (plateau[ligne][i].tourPresente) {
                tourAtteinteDroite = true;
                if (niveau(plateau, ligne, i) < niveau(plateau, ligne, colonne) && (plateau[ligne][i].estNoire != estNoir)) {
                    nbPions += plateau[ligne][i].hauteur;
                }
            }
            i++;
        }

        return nbPions;
    }

    /**
     * Compte le nombre de tours amies desctructibles sur la ligne.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case fusionnée
     * @param colonne colonne de la case fusionnée
     * @param estNoir couleur du joueur
     * @return le nombre de tours amies desctructibles sur la ligne
     */
    int ligneFusion(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        int nbPions = 0;
        int i = colonne - 1;
        boolean tourAtteinteGauche = false;
        boolean tourAtteinteDroite = false;

        while (i >= 0 && !tourAtteinteGauche) {
            if (plateau[ligne][i].tourPresente) {
                tourAtteinteGauche = true;
                if (plateau[ligne][i].estNoire == estNoir) {
                    nbPions += plateau[ligne][i].hauteur;
                }
            }
            i--;
        }

        i = colonne + 1;
        while (i < TAILLE && !tourAtteinteDroite) {
            if (plateau[ligne][i].tourPresente) {
                tourAtteinteDroite = true;
                if (plateau[ligne][i].estNoire == estNoir) {
                    nbPions += plateau[ligne][i].hauteur;
                }
            }
            i++;
        }

        return nbPions;
    }

    /**
     * Compte le nombre de tours ennemies desctructibles sur la colonne.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case activée
     * @param colonne colonne de la case activée
     * @param estNoir couleur du joueur
     * @return le nombre de tours ennemies desctructibles sur la colonne
     */
    int colonneActivation(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        int nbPions = 0;
        int i = ligne - 1;
        boolean tourAtteinteHaut = false;
        boolean tourAtteinteBas = false;

        while (i >= 0 && !tourAtteinteHaut) {
            if (plateau[i][colonne].tourPresente) {
                tourAtteinteHaut = true;
                if (niveau(plateau, i, colonne) < niveau(plateau, ligne, colonne) && (plateau[i][colonne].estNoire != estNoir)) {
                    nbPions += plateau[i][colonne].hauteur;
                }
            }
            i--;
        }

        i = ligne + 1;
        while (i < TAILLE && !tourAtteinteBas) {
            if (plateau[i][colonne].tourPresente) {
                tourAtteinteBas = true;
                if (niveau(plateau, i, colonne) < niveau(plateau, ligne, colonne) && (plateau[i][colonne].estNoire != estNoir)) {
                    nbPions += plateau[i][colonne].hauteur;
                }
            }
            i++;
        }
        return nbPions;
    }

    /**
     * Compte le nombre de tours amies desctructibles sur la colonne.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case fusionnée
     * @param colonne colonne de la case fusionnée
     * @param estNoir couleur du joueur
     * @return le nombre de tours amies desctructibles sur la colonne
     */
    int colonneFusion(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        int nbPions = 0;
        int i = ligne - 1;
        boolean tourAtteinteHaut = false;
        boolean tourAtteinteBas = false;

        while (i >= 0 && !tourAtteinteHaut) {
            if (plateau[i][colonne].tourPresente) {
                tourAtteinteHaut = true;
                if (plateau[i][colonne].estNoire == estNoir) {
                    nbPions += plateau[i][colonne].hauteur;
                }
            }
            i--;
        }

        i = ligne + 1;
        while (i < TAILLE && !tourAtteinteBas) {
            if (plateau[i][colonne].tourPresente) {
                tourAtteinteBas = true;
                if (plateau[i][colonne].estNoire == estNoir) {
                    nbPions += plateau[i][colonne].hauteur;
                }
            }
            i++;
        }
        return nbPions;
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
    int[] parcoursGrille(Case[][] plateau, int ligDepart, int colDepart, int incremLig, int incremCol) {
        boolean tourAtteinte = false;
        int[] tourTrouvee = new int[3]; //Négatif si noir, positif si blanc
        while (caseExiste(ligDepart, colDepart) && !tourAtteinte) {
            if (plateau[ligDepart][colDepart].tourPresente) {
                tourAtteinte = true;
                tourTrouvee[0] = ligDepart;
                tourTrouvee[1] = colDepart;
                if (plateau[ligDepart][colDepart].estNoire) {
                    tourTrouvee[2] = 1;
                } else {

                    tourTrouvee[2] = 2;
                }

            }
            ligDepart += incremLig;
            colDepart += incremCol;
        }
        return tourTrouvee;
    }

    /**
     * Compte le nombre de pions détruits par l'action kamikaze.
     *
     * @param plateau le plateau
     * @param ligDepart indice de la ligne de départ
     * @param colDepart indice de la colonne de départ
     * @param incremLig incrément pour les lignes
     * @param incremCol incrément pour les colonnes
     * @return un tableau de 2 entiers égaux au nombre de pions noirs détruits et au nombre de pions blancs détruits.
     */
    int[] cptKamikaze(Case[][] plateau, int ligDepart, int colDepart, int incremLig, int incremCol) {
        int[] nbPions = new int[2];
        int nbPionsN = 0;
        int nbPionsB = 0;
        while (caseExiste(ligDepart, colDepart)) {
            if (parcoursGrille(plateau, ligDepart, colDepart, incremCol, incremLig) < 0) {
                nbPionsN -= parcoursGrille(plateau, ligDepart, colDepart, incremCol, incremLig);
            } else {
                nbPionsB += parcoursGrille(plateau, ligDepart, colDepart, incremCol, incremLig);
            }
            ligDepart += incremLig;
            colDepart += incremCol;
        }
        nbPions[0] = nbPionsN;
        nbPions[1] = nbPionsB;
        return nbPions;
    }

    int[] symetrie(Case[][] plateau, int ligne, int colonne) {
        int[] coord = new int[2];
        coord[0] = TAILLE - 1 - ligne; //ligne de la case symétrique
        coord[1] = TAILLE - 1 - colonne; //colonne de la case symétrique
        return coord;
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
     * Vérifie si une double pose est possible.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case
     * @param colonne colonne de la case
     * @param estNoir couleur du joueur
     * @return true si la case est vide et qu'il y a une tour ennemie adjacente.
     */
    public boolean doublePose(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        if ((!plateau[ligne][colonne].tourPresente && adjacent(plateau, ligne, colonne, estNoir)) && plateau[ligne][colonne].altitude <= 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Vérifie si une fusion est possible.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case
     * @param colonne colonne de la case
     * @param estNoir couleur du joueur
     * @return true si une fusion est possible, false sinon.
     */
    boolean fusionPossible(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        return (plateau[ligne][colonne].tourPresente && plateau[ligne][colonne].estNoire == estNoir);
    }

    /**
     * Vérifie si une action magie est possible.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case
     * @param colonne colonne de la case
     * @param estNoir couleur du joueur
     * @return true si une action de magie est possible, false sinon.
     */
    boolean magiePossible(Case[][] plateau, int ligne, int colonne, boolean estNoir) {
        int ligSym = symetrie(plateau, ligne, colonne)[0];
        int colSym = symetrie(plateau, ligne, colonne)[1];
        int niveauSym = (plateau[ligne][colonne].hauteur + plateau[ligSym][colSym].altitude);
        return (!(plateau[ligSym][colSym].tourPresente) && niveauSym <= 4 && plateau[ligne][colonne].tourPresente && plateau[ligne][colonne].estNoire == estNoir && plateau[ligSym][colSym].nature != 1);
    }

    /**
     * Vérifie si le plateau est couvrant après la pose de pionPose sur la case [ligne] [colonne].
     *
     * @param plateau le plateau
     * @param ligne ligne de la pose
     * @param colonne colonne de la pose
     * @param joueurNoir couleur du joueur
     * @param apresPose true si on fait une vérification après pose du pion, false sinon
     * @return true si il est couvrant, false sinon
     */
    boolean couvrant(Case[][] plateau, int ligne, int colonne, boolean joueurNoir, boolean apresPose) {
        boolean estCouvrant = true;
        int lig = 0;
        int col = 0;
        while (caseExiste(lig, col) && estCouvrant) {
            if (!ligneCouvrante(plateau, lig, ligne, colonne, joueurNoir, apresPose) || !colonneCouvrante(plateau, col, ligne, colonne, joueurNoir, apresPose)) {
                estCouvrant = false;
            } else {
                lig++;
                col++;
            }
        }
        return estCouvrant;
    }

    /**
     * Vérifie si la ligne est couvrante.
     *
     * @param plateau le plateau
     * @param ligne la ligne dont on veut vérifier si elle est couvrante
     * @param ligPose la ligne de la pose
     * @param colPose la colonne de la pose
     * @param joueurNoir la couleur du joueur
     * @param apresPose true si on fait une vérification après pose du pion, false sinon
     * @return true si elle est couvrante, false sinon.
     */
    boolean ligneCouvrante(Case[][] plateau, int ligne, int ligPose, int colPose, boolean joueurNoir, boolean apresPose) {
        boolean estCouvrant = false;
        boolean noir = false; // true si au moins une tour noire est présente sur la ligne
        boolean blanc = false; // true si au moins une tour blanche est présente sur la ligne
        for (int i = 0; i < TAILLE; i++) {
            if (ligne == ligPose && i == colPose && apresPose) { // Si on vérifie après une pose et qu'on est sur la case de la pose alors on indique sa couleur
                if (joueurNoir) {
                    noir = true;
                } else {
                    blanc = true;
                }
            } else {
                if (plateau[ligne][i].tourPresente) {
                    if (plateau[ligne][i].estNoire) {
                        noir = true;
                    } else {
                        blanc = true;
                    }
                }
            }
        }
        if (noir && blanc) {
            estCouvrant = true;
        }
        return estCouvrant;
    }

    /**
     * Vérifie si la colonne est couvrante.
     *
     * @param plateau le plateau
     * @param colonne la colonne dont on veut vérifier si elle est couvrante
     * @param ligPose la ligne de la pose
     * @param colPose la colonne de la pose
     * @param joueurNoir la couleur du joueur
     * @param apresPose true si on fait une vérification après pose du pion, false sinon
     * @return true si elle est couvrante, false sinon.
     */
    boolean colonneCouvrante(Case[][] plateau, int colonne, int ligPose, int colPose, boolean joueurNoir, boolean apresPose) {
        boolean estCouvrant = false;
        boolean noir = false; // true si au moins une tour noire est présente sur la colonne
        boolean blanc = false; // true si au moins une tour blanche est présente sur la colonne 
        for (int i = 0; i < TAILLE; i++) {
            if (i == ligPose && colonne == colPose && apresPose) { // Si on vérifie après une pose et qu'on est sur la case de la pose alors on indique la couleur de la case après pose
                if (joueurNoir) {
                    noir = true;
                } else {
                    blanc = true;
                }
            } else {
                if (plateau[i][colonne].tourPresente) {
                    if (plateau[i][colonne].estNoire) {
                        noir = true;
                    } else {
                        blanc = true;
                    }
                }
            }
        }
        if (noir && blanc) {
            estCouvrant = true;
        }
        return estCouvrant;
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
    String actionPose(Case[][] plateau, int ligne, int colonne, boolean estNoir, int nbPionsNoirs, int nbPionsBlancs) {

        int nbPionsPoseN = nbPionsNoirs;
        int nbPionsPoseB = nbPionsBlancs;

        if (estNoir) {
            if (doublePose(plateau, ligne, colonne, estNoir)) {
                if (!couvrant(plateau, ligne, colonne, estNoir, false) && couvrant(plateau, ligne, colonne, estNoir, true)) { //Vérifie si le plateau est couvrant avant la pose avec apresPose=false et après avec apresPose=true
                    nbPionsPoseN += 4 - niveau(plateau, ligne, colonne);
                } else {
                    nbPionsPoseN += 2;
                }

            } else {
                if (!couvrant(plateau, ligne, colonne, estNoir, false) && couvrant(plateau, ligne, colonne, estNoir, true)) {
                    nbPionsPoseN += 4 - niveau(plateau, ligne, colonne);
                } else {
                    nbPionsPoseN++;
                }

            }
        } else {
            if (doublePose(plateau, ligne, colonne, estNoir)) {
                if (!couvrant(plateau, ligne, colonne, estNoir, false) && couvrant(plateau, ligne, colonne, estNoir, true)) {
                    nbPionsPoseB += 4 - niveau(plateau, ligne, colonne);
                } else {
                    nbPionsPoseB += 2;
                }
            } else {
                if (!couvrant(plateau, ligne, colonne, estNoir, false) && couvrant(plateau, ligne, colonne, estNoir, true)) {
                    nbPionsPoseB += 4 - niveau(plateau, ligne, colonne);
                } else {
                    nbPionsPoseB++;
                }
            }
        }

        // on ajoute l'action dans les actions possibles
        String action = "P" // action = Pose
                + Utils.numVersCarLigne(ligne) // convertit la ligne en lettre
                + Utils.numVersCarColonne(colonne) // convertit la colonne en lettre
                + "," + nbPionsPoseN // nombre de pions noirs
                + "," + nbPionsPoseB; // nombre de pions blancs
        return action;
    }

    /**
     * Action d'activation d'une tour.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case
     * @param colonne colonne de la case
     * @param estNoir couleur du joueur
     * @param nbPionsNoirs le nombre de pions noirs sur le plateau
     * @param nbPionsBlancs le nombre de pions blancs sur le plateau
     * @return l'action d'activation à ajouter dans les actions possibles.
     */
    String actionActivation(Case[][] plateau, int ligne, int colonne, boolean estNoir, int nbPionsNoirs, int nbPionsBlancs
    ) {
        int nbPionsActiveN = nbPionsNoirs;
        int nbPionsActiveB = nbPionsBlancs;
        // si activation d'un pion : détection des pions de la couleur opposée et de niveau strictement inférieur à la tour activée, 
        // puis destruction sur la ligne/colonne du pion de couleur opposée le plus proche et tous les pions de couleur opposée sur les diagonales correspondantes à la case activée
        if (estNoir) {
            nbPionsActiveB -= (carreActivation(plateau, ligne, colonne, estNoir) + ligneActivation(plateau, ligne, colonne, estNoir) + colonneActivation(plateau, ligne, colonne, estNoir));
        } else {
            nbPionsActiveN -= (carreActivation(plateau, ligne, colonne, estNoir) + ligneActivation(plateau, ligne, colonne, estNoir) + colonneActivation(plateau, ligne, colonne, estNoir));
        }

        String action = "A" // action = Activation
                + Utils.numVersCarLigne(ligne) // convertit la ligne en lettre
                + Utils.numVersCarColonne(colonne) // convertit la colonne en lettre
                + "," + nbPionsActiveN // nombre de pions noirs
                + "," + nbPionsActiveB; // nombre de pions blancs
        return action;
    }

    /**
     * Action de fusion d'une tour.
     *
     * @param plateau le plateau
     * @param ligne ligne de la case
     * @param colonne colonne de la case
     * @param estNoir couleur du joueur
     * @param nbPionsNoirs le nombre de pions noirs sur le plateau
     * @param nbPionsBlancs le nombre de pions blancs sur le plateau
     * @return l'action de fusion à ajouter dans les actions possibles.
     */
    String actionFusion(Case[][] plateau, int ligne, int colonne, boolean estNoir, int nbPionsNoirs, int nbPionsBlancs
    ) {
        int nbPionsFusionN = nbPionsNoirs;
        int nbPionsFusionB = nbPionsBlancs;
        // si fusion d'un pion, détection des pions de la même couleur peu importe le niveau des tours, puis fusion (on enlève) sur la ligne/colonne du pion de même couleur le plus proche 
        // et tous les pions de même couleur sur les diagonales correspondantes à la case activée pour les ajouter à tour correspondante
        if (estNoir) {
            int nbPionsAutourN = (carreFusion(plateau, ligne, colonne, estNoir) + ligneFusion(plateau, ligne, colonne, estNoir) + colonneFusion(plateau, ligne, colonne, estNoir));
            nbPionsFusionN -= nbPionsAutourN;
            // si le niveau de la tour construite est sup. à 4 on enlève les pions en trop
            if (plateau[ligne][colonne].hauteur + nbPionsAutourN + plateau[ligne][colonne].altitude > 4) {
                nbPionsFusionN += 4 - niveau(plateau, ligne, colonne);
            } else {
                nbPionsFusionN += nbPionsAutourN;
            }
        } else {
            int nbPionsAutourB = (carreFusion(plateau, ligne, colonne, estNoir) + ligneFusion(plateau, ligne, colonne, estNoir) + colonneFusion(plateau, ligne, colonne, estNoir));
            nbPionsFusionB -= nbPionsAutourB;
            if (plateau[ligne][colonne].hauteur + nbPionsAutourB + plateau[ligne][colonne].altitude > 4) {
                nbPionsFusionB += 4 - niveau(plateau, ligne, colonne);
            } else {
                nbPionsFusionB += nbPionsAutourB;
            }
        }

        String action = "F" // action = Fusion
                + Utils.numVersCarLigne(ligne) // convertit la ligne en lettre
                + Utils.numVersCarColonne(colonne) // convertit la colonne en lettre
                + "," + nbPionsFusionN // nombre de pions noirs
                + "," + nbPionsFusionB; // nombre de pions blancs
        return action;
    }

    /**
     * Action des chatons kamikazes
     *
     * @param plateau le plateau
     * @param nbPionsNoirs
     * @param nbPionsBlancs
     * @param direction
     * @return
     */
    String actionKamikaze(Case[][] plateau, int nbPionsNoirs, int nbPionsBlancs, char direction
    ) {
        int[] nbPions = new int[2];
        int nbPionsN = nbPionsNoirs;
        int nbPionsB = nbPionsBlancs;
        switch (direction) {
            case 'N':
                nbPions = cptKamikaze(plateau, 0, 0, 0, 1); //on part de la case en haut à gauche et on incrémente les colonnes de 1 allant de 0 jusqu'à TAILLE-1
                break;
            case 'S':
                nbPions = cptKamikaze(plateau, TAILLE - 1, TAILLE - 1, 0, -1); //on part de la case en bas à droite et on incrémente les colonnes de -1 allant de TAILLE-1 jusqu'à 0
                break;
            case 'E':
                nbPions = cptKamikaze(plateau, TAILLE - 1, TAILLE - 1, -1, 0); //on part de la case en bas à droite et on incrémente les lignes de -1 allant de TAILLE-1 jusqu'à 0
                break;
            case 'O':
                nbPions = cptKamikaze(plateau, 0, 0, 1, 0); //on part de la case en haut à gauche et on incrémente les lignes de 1 allant de 0 jusqu'à TAILLE-1
                break;

            default:
                break;
        }
        nbPionsN -= nbPions[0];
        nbPionsB -= nbPions[1];
        String action = "C" // action = Chaton kamikaze
                + direction
                + "," + nbPionsN // nombre de pions noirs
                + "," + nbPionsB; // nombre de pions blancs
        return action;
    }

    /**
     * Cette méthode renvoie, pour un plateau donné et un joueur donné, l'ensemble des actions de magie possible pour ce joueur
     *
     * @param plateau le plateau considéré
     * @param ligne ligne de la case considérée
     * @param colonne colonne de la case considérée
     * @param estNoir vrai si le joueur noir joue, faux si c'est le blanc
     * @param nbPionsNoirs nombre de pions noirs sur le plateau
     * @param nbPionsBlancs nombre de pions blanc sur le plateau
     * @return l'ensemble des actions de magie possibles
     */
    String actionMagie(Case[][] plateau, int ligne, int colonne, int nbPionsNoirs, int nbPionsBlancs) {
        String action = "M" // action = Magie
                + Utils.numVersCarLigne(ligne) // convertit la ligne de la case symetrique en lettre
                + Utils.numVersCarColonne(colonne)// convertit la colonne de la case symetrique en lettre
                + "," + nbPionsNoirs // nombre de pions noirs
                + "," + nbPionsBlancs; // nombre de pions blancs        
        return action;
    }

    /**
     * Une fonction principale juste là pour que vous puissiez tester votre actionsPossibles() sur le plateau de votre choix. À modifier librement.
     *
     * @param args arguments de la ligne de commande, inutiles ici
     */
    public static void main(String[] args) {
        JoueurTowa joueur = new JoueurTowa();
        // un plateau sur lequel on veut tester actionsPossibles()
        String textePlateau
                = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P \n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "a|   |B4 |   |   |   |N4 |   |   |   |   |   |   |   |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "b|N1 |   |   |   |   |   |   |B1 |   |B3 |   |   |   |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "c|   |   |B1 |   |   |   |   |   |   |   |B1 |   |N1 |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "d|   |   |   |   |B1 |   |   |   |B1 |   |   |N2 |B4 |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "e|B1 |   |   |  1|   |   |   |   |   |   |   |   |   |B3 |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "f|   |   |   |   |   |N2 |   |B11|   |   |   |   |   |N1 |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "g|   |   |B2 |   |   |   |   |  2|   |N1 |   |   |   |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "h|   |   |   |   |   |B4 |   |  3|  3|   |N1 |   |   |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "i|   |N1 |N1 |   |   |   |   |B22|B1 |   |   |   |   |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "j|   |   |   |   |   |   |N1 |   |N2 |   |   |B1 |   |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "k|   |   |   |   |N1 |   |   |   |   |N2 |   |   |   |   |B1 |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "l|N1 |N4 |   |   |N3 |   |B1 |   |   |   |   |   |   |   |   |N1 |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "m|   |   |   |   |B1 |   |   |   |   |N1 |   |   |   |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "n|   |   |   |B2 |N1 |N1 |   |   |   |N1 |   |   |   |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "o|   |N1 |   |   |   |   |   |N1 |   |   |   |   |   |   |   |B4 |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "p|   |   |   |   |   |   |B1 |   |   |   |   |   |   |   |N2 |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
        Case[][] plateau = Utils.plateauDepuisTexte(textePlateau);
        // on choisit la couleur du joueur
        boolean noir = true;
        // on choisit le niveau
        int niveau = 13;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, noir, niveau);

        // on affiche toutes les actions possibles calculées :
        Utils.afficherActionsPossibles(actionsPossiblesDepuisPlateau);

        // on peut aussi tester si une action est dans les actions possibles :
        String action = "PeD,41,41";
        if (Utils.actionsPossiblesContient(actionsPossiblesDepuisPlateau, action)) {
            System.out.println(action + " est calculé comme possible");
        } else {
            System.out.println(action + " est calculé comme impossible");
        }
        // etc.
    }
}
