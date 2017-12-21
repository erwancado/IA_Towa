/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towa;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mapotin
 */
public class IATowaTest {
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
                + "e|B1 |   |   |   |   |   |   |   |   |   |   |   |   |B3 |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "f|   |   |   |   |   |N2 |   |B1 |   |   |   |   |   |N1 |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "g|   |   |B2 |   |   |   |   |   |   |N1 |   |   |   |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "h|   |   |   |   |   |B4 |   |   |   |   |N1 |   |   |   |   |   |\n"
                + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
                + "i|   |N1 |N1 |   |   |   |   |B2 |B1 |   |   |   |   |   |   |   |\n"
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
        final Case[][] PLATEAUTEST = Utils.plateauDepuisTexte(textePlateau);
    public IATowaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of connexion method, of class IATowa.
     */
    @Test
    public void testConnexion() throws Exception {
        System.out.println("connexion");
        IATowa instance = null;
        instance.connexion();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toursDeJeu method, of class IATowa.
     */
    @Test
    public void testToursDeJeu() throws Exception {
        System.out.println("toursDeJeu");
        IATowa instance = null;
        instance.toursDeJeu();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ajoutPose method, of class IATowa.
     */
    @Test
    public void testAjoutPose() {
        System.out.println("ajoutPose");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        IATowa instance = null;
        instance.ajoutPose(plateau, ligne, colonne, estNoir);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activationEnnemie method, of class IATowa.
     */
    @Test
    public void testActivationEnnemie() {
        System.out.println("activationEnnemie");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        IATowa instance = null;
        instance.activationEnnemie(plateau, ligne, colonne, estNoir);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activationPerso method, of class IATowa.
     */
    @Test
    public void testActivationPerso() {
        System.out.println("activationPerso");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        int hauteur = 0;
        IATowa instance = null;
        String expResult = "";
        String result = instance.activationPerso(plateau, ligne, colonne, estNoir, hauteur);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of jouer method, of class IATowa.
     */
    @Test
    public void testJouer() throws Exception {
        System.out.println("jouer");
        Case[][] plateau = null;
        int nbToursJeu = 0;
        IATowa instance = null;
        instance.jouer(plateau, nbToursJeu);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doublePose method, of class IATowa.
     */
    @Test
    public void testDoublePose() {
        System.out.println("doublePose");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        IATowa instance = null;
        boolean expResult = false;
        boolean result = instance.doublePose(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of poseTourAdjacent method, of class IATowa.
     */
    @Test
    public void testPoseTourAdjacent() {
        System.out.println("poseTourAdjacent");
        Case[][] plateau = null;
        boolean estNoir = false;
        IATowa instance = null;
        char[] expResult = null;
        char[] result = instance.poseTourAdjacent(plateau, estNoir);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of presenceTour method, of class IATowa.
     */
    @Test
    public void testPresenceTour() {
        System.out.println("presenceTour");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        IATowa instance = null;
        int[] expResult = null;
        int[] result = instance.presenceTour(plateau, ligne, colonne, estNoir);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parcoursLignePerso method, of class IATowa.
     */
    @Test
    public void testParcoursLignePerso() {
        System.out.println("parcoursLignePerso");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 3;
        boolean estNoir = false;
        int hauteur = 4;
        IATowa instance = null;
        int expResult = 2;
        int result = instance.parcoursLignePerso(plateau, ligne, estNoir, hauteur);
        assertEquals(expResult, result);
    }

    /**
     * Test of parcoursColonnePerso method, of class IATowa.
     */
    @Test
    public void testParcoursColonnePerso() {
        System.out.println("parcoursColonnePerso");
        Case[][] plateau = PLATEAUTEST;
        int colonne = 12;
        boolean estNoir = false;
        int hauteur = 4;
        IATowa instance = null;
        int expResult = 1;
        int result = instance.parcoursColonnePerso(plateau, colonne, estNoir, hauteur);
        assertEquals(expResult, result);
    }

    /**
     * Test of parcoursLigneEnnemi method, of class IATowa.
     */
    @Test
    public void testParcoursLigneEnnemi() {
        System.out.println("parcoursLigneEnnemi");
        Case[][] plateau = null;
        int ligne = 0;
        boolean estNoir = false;
        int hauteur = 0;
        IATowa instance = null;
        instance.parcoursLigneEnnemi(plateau, ligne, estNoir, hauteur);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parcoursColonneEnnemi method, of class IATowa.
     */
    @Test
    public void testParcoursColonneEnnemi() {
        System.out.println("parcoursColonneEnnemi");
        Case[][] plateau = null;
        int colonne = 0;
        boolean estNoir = false;
        int hauteur = 0;
        IATowa instance = null;
        instance.parcoursColonneEnnemi(plateau, colonne, estNoir, hauteur);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of carreActivationPerso method, of class IATowa.
     */
    @Test
    public void testCarreActivationPerso() {
        System.out.println("carreActivationPerso");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 1;
        boolean estNoir = false;
        int hauteur = 4;
        IATowa instance = null;
        int expResult = 1;
        int result = instance.carreActivationPerso(plateau, ligne, colonne, estNoir, hauteur);
        assertEquals(expResult, result);
    }

    /**
     * Test of carreActivationEnnemi method, of class IATowa.
     */
    @Test
    public void testCarreActivationEnnemi() {
        System.out.println("carreActivationEnnemi");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        int hauteur = 0;
        IATowa instance = null;
        instance.carreActivationEnnemi(plateau, ligne, colonne, estNoir, hauteur);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detruireTour method, of class IATowa.
     */
    @Test
    public void testDetruireTour() {
        System.out.println("detruireTour");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        IATowa instance = null;
        instance.detruireTour(plateau, ligne, colonne);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of caseExiste method, of class IATowa.
     */
    @Test
    public void testCaseExiste() {
        System.out.println("caseExiste");
        int ligne = 0;
        int colonne = 0;
        IATowa instance = null;
        boolean expResult = false;
        boolean result = instance.caseExiste(ligne, colonne);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adjacent method, of class IATowa.
     */
    @Test
    public void testAdjacent() {
        System.out.println("adjacent");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        IATowa instance = null;
        boolean expResult = false;
        boolean result = instance.adjacent(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of posePossible method, of class IATowa.
     */
    @Test
    public void testPosePossible() {
        System.out.println("posePossible");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        IATowa instance = null;
        boolean expResult = false;
        boolean result = instance.posePossible(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activationPossible method, of class IATowa.
     */
    @Test
    public void testActivationPossible() {
        System.out.println("activationPossible");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        IATowa instance = null;
        boolean expResult = false;
        boolean result = instance.activationPossible(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionPose method, of class IATowa.
     */
    @Test
    public void testActionPose() {
        System.out.println("actionPose");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        IATowa instance = null;
        String expResult = "";
        String result = instance.actionPose(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionsPossibles method, of class IATowa.
     */
    @Test
    public void testActionsPossibles() {
        System.out.println("actionsPossibles");
        Case[][] plateau = null;
        boolean joueurNoir = false;
        IATowa instance = null;
        String[] expResult = null;
        String[] result = instance.actionsPossibles(plateau, joueurNoir);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class IATowa.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        IATowa.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
