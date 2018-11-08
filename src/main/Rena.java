package main;

import java.util.Random;

public class Rena extends Thread {

	private static final int tempoFerias = 3000;
	private static int count = 0;
	private int id;
	private PapaiNoel papaiNoel;
	private Treno treno;

	/* Cada rena tem um Papai Noel e um trenó como recurso de memória,
	 * e uma id determinada pelo contador de renas do controlador
	 * A cada thread de Rena adicionada, incrementa o número de threads da classe Rena
	 */
	public Rena(PapaiNoel papaiNoel, Treno treno) {

		this.papaiNoel = papaiNoel;
		this.treno = treno;
		this.id = count;

		count++;

		start();

	}

	/* Execução da thread:
	 * Cada rena é inicializada num estado de espera (férias)
	 * Enquanto esta rena está de férias, ela solicita acesso à memória (Papai Noel)
	 * Caso a memória esteja livre, a rena é adicionada numa lista de espera
	 * e aguarda o Papai Noel acessar a memória compartilhada do Trenó
	 */
	public void run() {

		try {

			while(true) {

				emFerias();

				papaiNoel.renaChegou(this);
				treno.prepararTreno();

			}

		} catch (InterruptedException e) {

			System.exit(1);

		}

	}

	private void emFerias() throws InterruptedException {

		Log.renaEntrouDeFerias(id);

		Thread.sleep(new Random().nextInt(tempoFerias));

	}

}
