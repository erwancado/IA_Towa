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
 * @author ecado
 */
public class JoueurTowaIT {
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
        final Case[][] PLATEAUTEST = Utils.plateauDepuisTexte(textePlateau);
    
    public JoueurTowaIT() {
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
     * Test of posePossible method, of class JoueurTowa.
     */
    @Test
    public void testPosePossible() {
        System.out.println("posePossible");
        Case[][] plateau = PLATEAUTEST;
        int ligne1 = 0;
        int colonne1 = 1;
        int ligne2 = 4;
        int colonne2 = 2;
        boolean estNoir = false;
        JoueurTowa instance = new JoueurTowa();
        boolean expResult1 = false;
        boolean result1 = instance.posePossible(plateau, ligne1, colonne1, estNoir);
        boolean expResult2 = true;
        boolean result2 = instance.posePossible(plateau, ligne2, colonne2, estNoir);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of activationPossible method, of class JoueurTowa.
     */
    @Test
    public void testActivationPossible() {
        System.out.println("activationPossible");
        Case[][] plateau = PLATEAUTEST;
        int ligne1 = 1;
        int colonne1 = 0;
        int ligne2 = 3;
        int colonne2 = 1;
        boolean estNoir = true;
        JoueurTowa instance = new JoueurTowa();
        boolean expResult1 = true;
        boolean result1 = instance.activationPossible(plateau, ligne1, colonne1, estNoir);
        boolean expResult2 = false;
        boolean result2 = instance.activationPossible(plateau, ligne2, colonne2, estNoir);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of caseExiste method, of class JoueurTowa.
     */
    @Test
    public void testCaseExiste() {
        System.out.println("caseExiste");
        int ligne = 18;
        int colonne = 3;
        JoueurTowa instance = new JoueurTowa();
        boolean expResult = false;
        boolean result = instance.caseExiste(ligne, colonne);
        assertEquals(expResult, result);
    }

    /**
     * Test of niveau method, of class JoueurTowa.
     */
    @Test
    public void testNiveau() {
        System.out.println("niveau");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 4;
        int colonne = 3;
        JoueurTowa instance = new JoueurTowa();
        int expResult = 1;
        int result = instance.niveau(plateau, ligne, colonne);
        assertEquals(expResult, result);
    }

    /**
     * Test of carreActivation method, of class JoueurTowa.
     */
    @Test
    public void testCarreActivation() {
        System.out.println("carreActivation");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 9;
        int colonne = 6;
        boolean estNoir = true;
        JoueurTowa instance = new JoueurTowa();
        int expResult = 0;
        int result = instance.carreActivation(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
    }

    /**
     * Test of carreFusion method, of class JoueurTowa.
     */
    @Test
    public void testCarreFusion() {
        System.out.println("carreFusion");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 3;
        int colonne = 11;
        boolean estNoir = true;
        JoueurTowa instance = new JoueurTowa();
        int expResult = 1;
        int result = instance.carreFusion(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
    }

    /**
     * Test of ligneActivation method, of class JoueurTowa.
     */
    @Test
    public void testLigneActivation() {
        System.out.println("ligneActivation");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 9;
        int colonne = 6;
        boolean estNoir = true;
        JoueurTowa instance = new JoueurTowa();
        int expResult = 0;
        int result = instance.ligneActivation(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
    }

    /**
     * Test of ligneFusion method, of class JoueurTowa.
     */
    @Test
    public void testLigneFusion() {
        System.out.println("ligneFusion");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 3;
        int colonne = 11;
        boolean estNoir = true;
        JoueurTowa instance = new JoueurTowa();
        int expResult = 0;
        int result = instance.ligneFusion(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
    }

    /**
     * Test of colonneActivation method, of class JoueurTowa.
     */
    @Test
    public void testColonneActivation() {
        System.out.println("colonneActivation");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 15;
        int colonne = 14;
        boolean estNoir = true;
        JoueurTowa instance = new JoueurTowa();
        int expResult = 1;
        int result = instance.colonneActivation(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
    }

    /**
     * Test of colonneFusion method, of class JoueurTowa.
     */
    @Test
    public void testColonneFusion() {
        System.out.println("colonneFusion");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 2;
        int colonne = 2;
        boolean estNoir = false;
        JoueurTowa instance = new JoueurTowa();
        int expResult = 2;
        int result = instance.colonneFusion(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
    }

//    /**
//     * Test of parcoursGrille method, of class JoueurTowa.
//     */
//    @Test
//    public void testParcoursGrille() {
//        System.out.println("parcoursGrille");
//        Case[][] plateau = PLATEAUTEST;
//        int ligDepart = 0;
//        int colDepart = 0;
//        int incremLig = 0;
//        int incremCol = 1;
//        JoueurTowa instance = new JoueurTowa();
//        int expResult = 4;
//        int result = instance.parcoursGrille(plateau, ligDepart, colDepart, incremLig, incremCol);
//        assertEquals(expResult, result);
//    }

    /**
     * Test of cptKamikaze method, of class JoueurTowa.
     */
    @Test
    public void testCptKamikaze() {
        System.out.println("cptKamikaze");
        Case[][] plateau = PLATEAUTEST;
        int ligDepart = 0;
        int colDepart = 0;
        int incremLig = 0;
        int incremCol = 1;
        JoueurTowa instance = new JoueurTowa();
        int[] expResult = {10,18};
        int[] result = instance.cptKamikaze(plateau, ligDepart, colDepart, incremLig, incremCol);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of symetrie method, of class JoueurTowa.
     */
    @Test
    public void testSymetrie() {
        System.out.println("symetrie");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 0;
        int colonne = 1;
        JoueurTowa instance = new JoueurTowa();
        int[] expResult = {15,14};
        int[] result = instance.symetrie(plateau, ligne, colonne);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of adjacent method, of class JoueurTowa.
     */
    @Test
    public void testAdjacent() {
        System.out.println("adjacent");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 9;
        int colonne = 6;
        boolean estNoir = true;
        JoueurTowa instance = new JoueurTowa();
        boolean expResult = true;
        boolean result = instance.adjacent(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
    }

    /**
     * Test of doublePose method, of class JoueurTowa.
     */
    @Test
    public void testDoublePose() {
        System.out.println("doublePose");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 3;
        int colonne = 3;
        boolean estNoir = true;
        JoueurTowa instance = new JoueurTowa();
        boolean expResult = true;
        boolean result = instance.doublePose(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
    }

    /**
     * Test of fusionPossible method, of class JoueurTowa.
     */
    @Test
    public void testFusionPossible() {
        System.out.println("fusionPossible");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 2;
        int colonne = 2;
        boolean estNoir = false;
        JoueurTowa instance = new JoueurTowa();
        boolean expResult = true;
        boolean result = instance.fusionPossible(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
    }

    /**
     * Test of magiePossible method, of class JoueurTowa.
     */
    @Test
    public void testMagiePossible() {
        System.out.println("magiePossible");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 0;
        int colonne = 1;
        boolean estNoir = false;
        JoueurTowa instance = new JoueurTowa();
        boolean expResult = false;
        boolean result = instance.magiePossible(plateau, ligne, colonne, estNoir);
        assertEquals(expResult, result);
    }

    /**
     * Test of couvrant method, of class JoueurTowa.
     */
    @Test
    public void testCouvrant() {
        System.out.println("couvrant");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 4;
        int colonne = 3;
        boolean apresPose = true;
        boolean joueurNoir = true;
        JoueurTowa joueur = new JoueurTowa();
        boolean expResult = true;
        boolean result = joueur.couvrant(plateau, ligne, colonne, joueurNoir, apresPose);
        assertEquals(expResult, result);
    }

    /**
     * Test of ligneCouvrante method, of class JoueurTowa.
     */
    @Test
    public void testLigneCouvrante() {
        System.out.println("ligneCouvrante");
        Case[][] plateau = PLATEAUTEST;
        int ligPose = 4;
        int colPose = 3;
        boolean apresPose = true;
        boolean joueurNoir = true;
        int ligne = 4;
        JoueurTowa instance = new JoueurTowa();
        boolean expResult = true;
        boolean result = instance.ligneCouvrante(plateau, ligne, ligPose, colPose, joueurNoir, apresPose);
        assertEquals(expResult, result);
    }

    /**
     * Test of colonneCouvrante method, of class JoueurTowa.
     */
    @Test
    public void testColonneCouvrante() {
        System.out.println("colonneCouvrante");
        Case[][] plateau = PLATEAUTEST;
        int ligPose = 4;
        int colPose = 3;
        boolean apresPose = true;
        boolean joueurNoir = true;
        int colonne = 3;
        JoueurTowa instance = new JoueurTowa();
        boolean expResult = true;
        boolean result = instance.colonneCouvrante(plateau, colonne, ligPose, colPose, joueurNoir, apresPose);
        assertEquals(expResult, result);
    }

    /**
     * Test of actionPose method, of class JoueurTowa.
     */
    @Test
    public void testActionPose() {
        System.out.println("actionPose");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        int nbPionsNoirs = 0;
        int nbPionsBlancs = 0;
        JoueurTowa instance = new JoueurTowa();
        String expResult = "";
        String result = instance.actionPose(plateau, ligne, colonne, estNoir, nbPionsNoirs, nbPionsBlancs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionActivation method, of class JoueurTowa.
     */
    @Test
    public void testActionActivation() {
        System.out.println("actionActivation");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        int nbPionsNoirs = 0;
        int nbPionsBlancs = 0;
        JoueurTowa instance = new JoueurTowa();
        String expResult = "";
        String result = instance.actionActivation(plateau, ligne, colonne, estNoir, nbPionsNoirs, nbPionsBlancs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionFusion method, of class JoueurTowa.
     */
    @Test
    public void testActionFusion() {
        System.out.println("actionFusion");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        boolean estNoir = false;
        int nbPionsNoirs = 0;
        int nbPionsBlancs = 0;
        JoueurTowa instance = new JoueurTowa();
        String expResult = "";
        String result = instance.actionFusion(plateau, ligne, colonne, estNoir, nbPionsNoirs, nbPionsBlancs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionKamikaze method, of class JoueurTowa.
     */
    @Test
    public void testActionKamikaze() {
        System.out.println("actionKamikaze");
        Case[][] plateau = null;
        int nbPionsNoirs = 0;
        int nbPionsBlancs = 0;
        char direction = ' ';
        JoueurTowa instance = new JoueurTowa();
        String expResult = "";
        String result = instance.actionKamikaze(plateau, nbPionsNoirs, nbPionsBlancs, direction);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionMagie method, of class JoueurTowa.
     */
    @Test
    public void testActionMagie() {
        System.out.println("actionMagie");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 0;
        int nbPionsNoirs = 0;
        int nbPionsBlancs = 0;
        JoueurTowa instance = new JoueurTowa();
        String expResult = "";
        String result = instance.actionMagie(plateau, ligne, colonne, nbPionsNoirs, nbPionsBlancs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class JoueurTowa.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        JoueurTowa.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of carreActivationPerso method, of class JoueurTowa.
     */
    @Test
    public void testCarreActivationPerso() {
        System.out.println("carreActivationPerso");
        Case[][] plateau = null;
        int ligne = 0;
        int colonne = 1;
        boolean estNoir = false;
        int hauteur = 4;
        JoueurTowa instance = new JoueurTowa();
        int expResult = 1;
        int result = instance.carreActivationPerso(plateau, ligne, colonne, estNoir, hauteur);
        assertEquals(expResult, result);
    }

    /**
     * Test of parcoursLignePerso method, of class JoueurTowa.
     */
    @Test
    public void testParcoursLignePerso() {
        System.out.println("parcoursLignePerso");
        Case[][] plateau = PLATEAUTEST;
        int ligne = 3;
        boolean estNoir = false;
        int hauteur = 4;
        JoueurTowa instance = new JoueurTowa();
        int expResult = 2;
        int result = instance.parcoursLignePerso(plateau, ligne, estNoir, hauteur);
        assertEquals(expResult, result);
    }

    /**
     * Test of parcoursColonnePerso method, of class JoueurTowa.
     */
    @Test
    public void testParcoursColonnePerso() {
        System.out.println("parcoursColonnePerso");
        Case[][] plateau = PLATEAUTEST;
        int colonne = 12;
        boolean estNoir = false;
        int hauteur = 4;
        JoueurTowa instance = new JoueurTowa();
        int expResult = 1;
        int result = instance.parcoursColonnePerso(plateau, colonne, estNoir, hauteur);
        assertEquals(expResult, result);
    }
    
}
