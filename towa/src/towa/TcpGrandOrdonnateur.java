package towa;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * Remarques destinées aux compétiteurs : utilisez cette classe pour
 * communiquer avec le grand ordonnateur ; ne changez rien au code de cette
 * classe.
 *
 * Interface pour le protocole du grand ordonnateur.
 */
public class TcpGrandOrdonnateur {

    /**
     * Valeurs autorisées par le protocole du grand ordonnateur.
     */
    public static final Character[] VALEURS_AUTORISEES = 
    {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
        'O', 'P', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
        'm', 'n', 'o', 'p'};

    /**
     * Socket.
     */
    private Socket socket;

    /**
     * Création d'une interface pour le protocole du grand ordonnateur.
     */
    public TcpGrandOrdonnateur() {
        socket = null;
    }

    /**
     * Connexion au serveur.
     *
     * @param hote Hôte.
     * @param port Port.
     * @throws IOException
     */
    public void connexion(String hote, int port) throws IOException {
        socket = new Socket(hote, port);
        socket.getInputStream();
        socket.getOutputStream();
    }

    /**
     * Envoi d'un entier au serveur.
     *
     * @param entier Entier à envoyer.
     * @throws IOException
     */
    public void envoiEntier(final int entier) throws IOException {
        testerSocket();
        final OutputStream fluxSortie = socket.getOutputStream();
        if (fluxSortie == null) {
            throw new IllegalArgumentException("Le flux en sortie est null.");
        } else if (estValidePourEnvoi(entier)) {
            fluxSortie.write(entier);
            fluxSortie.flush();
        } else {
            throw new IllegalArgumentException(
                    "L'entier " + entier + " n'est pas valide pour être envoyé au serveur.");
        }
    }

    /**
     * Envoi d'un caractère au serveur.
     *
     * @param caractere Caractère à envoyer.
     * @throws IOException
     */
    public void envoiCaractere(final char caractere) throws IOException {
        envoiEntier((int) caractere);
    }

    /**
     * Teste si un entier peut être envoyé au serveur, c'est-à-dire s'il fait
     * partie des valeurs autorisées.
     *
     * @param entier Entier à tester pour être envoyé.
     * @return L'entier est-il valide pour être envoyé ?
     */
    private boolean estValidePourEnvoi(final int entier) {
        return Arrays.stream(VALEURS_AUTORISEES).anyMatch(c -> c == entier);
    }

    /**
     * Réception d'un entier depuis le serveur.
     *
     * @return Entier reçu.
     * @throws IOException
     */
    public int receptionEntier() throws IOException {
        testerSocket();
        final InputStream fluxEntree = socket.getInputStream();
        if (fluxEntree == null) {
            throw new IllegalArgumentException("Le flux en entrée est null.");
        } else {
            int entierRecu = -1;
            entierRecu = fluxEntree.read();
            return entierRecu;
        }
    }

    /**
     * Réception d'un caractère depuis le serveur.
     *
     * @return Caractère reçu.
     * @throws IOException
     */
    public char receptionCaractere() throws IOException {
        return (char) receptionEntier();
    }

    /**
     * Déconnexion du serveur.
     *
     * @throws IOException
     */
    public void deconnexion() throws IOException {
        testerSocket();
        InputStream fluxEntree = null;
        fluxEntree = socket.getInputStream();
        if (fluxEntree != null) {
            fluxEntree.close();
        }
        OutputStream fluxSortie = null;
        fluxSortie = socket.getOutputStream();
        if (fluxSortie != null) {
            fluxSortie.close();
        }
        socket.close();
    }

    /**
     * Teste si le socket est initialisé ; sinon, arrêt du programme.
     */
    private void testerSocket() {
        if (socket == null) {
            System.out.println("Le socket n'est pas initialisé.");
            System.out.flush();
            System.exit(1);
        }
    }
}
