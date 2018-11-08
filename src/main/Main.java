/*
 *	Os elfos não são sincronizados com as renas, e nem vice versa.
 *	O Papai Noel só executa alguma ação quando um conjunto de threads, das renas OU dos elfos sincroniza com a thread do Papai Noel, exclusivamente
 */
package main;

public class Main {

	public static void main(String[] args) {

		// inicializa o Trenó (recurso de memória das threads Renas)
		Treno treno = new Treno();

		// inicializa a thread do Papai Noel
		PapaiNoel papaiNoel = new PapaiNoel(treno);

		// inicializa as threads das renas
		for(int i = 0; i < 9; i++) {

			new Rena(papaiNoel, treno);

		}

		// inicializa as threads dos elfos
		for(int i = 0; i < 10; i++) {

			new Elfo(papaiNoel);

		}

	}

}
