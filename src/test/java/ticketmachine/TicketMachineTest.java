package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	//S3
	void nImprimePasSiBalanceInsuffisante() {
		machine.insertMoney(PRICE - 1);
		assertFalse(machine.printTicket(), "Pas assez d'argent, on ne doit pas imprimer");
	}

	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	void ImprimeSiBalanceSuffisante() {
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "La balance est suffisante, le ticket doit être imprimé");
	}

	@Test
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void balanceDecrementeApresImpression() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(0, machine.getBalance(), "La balance n'a pas été correctement décrémentée après impression");
	}

	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void montantCollecteMisAJourApresImpression() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Le montant collecté n'a pas été mis à jour après impression du ticket");
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	void refundRendCorrectementLaMonnaie() {
		machine.insertMoney(30);
		assertEquals(30, machine.refund(), "Le montant remboursé n'est pas correct");
	}

	@Test
	// S8 : refund() remet la balance à zéro
	void refundRemetBalanceAZero() {
		machine.insertMoney(30);
		machine.refund();
		assertEquals(0, machine.getBalance(), "La balance n'a pas été remise à zéro après le remboursement");
	}

	@Test
	// S9 : on ne peut pas insérer un montant négatif
	void nePeutPasInsererMontantNegatif() {
		assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-10), "L'insertion d'un montant négatif devrait lancer une exception");
	}

	@Test
	// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void prixNegatifNonAutorise() {
		assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-50), "Le prix du ticket ne doit pas être négatif");
	}



}
