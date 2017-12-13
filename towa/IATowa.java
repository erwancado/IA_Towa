package towa;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
     * Boucle de jeu : envoi des actions que vous souhaitez jouer, et réception
     * des actions de l'adversaire.
     *
     * @throws IOException exception sur les entrées/sorties
     */
    void toursDeJeu() throws IOException {
        // le plateau, vide au départ
        Case[][] plateau = new Case[TAILLE][TAILLE];
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
                    // TODO : mettre à jour le plateau
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
     * Fonction exécutée lorsque c'est à notre tour de jouer. Cette fonction
     * envoie donc l'action choisie au serveur.
     *
     * @param plateau le plateau de jeu
     * @param nbToursJeu numéro du tour de jeu
     * @throws IOException exception sur les entrées / sorties
     */
    void jouer(Case[][] plateau, int nbToursJeu) throws IOException {
        // on pose toujours en gO : TODO faire mieux...
        System.out.println("On joue : PgO.");
        grandOrdo.envoiCaractere('P');
        grandOrdo.envoiCaractere('g');
        grandOrdo.envoiCaractere('O');
        // TODO : mettre à jour le plateau
    }

    /**
     * Programme principal. Il sera lancé automatiquement, ce n'est pas à vous
     * de le lancer.
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
                + "\tUsage : java " + IATowa.class.getName()
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
