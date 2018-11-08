package main;

public class Treno {

	// Atraso de entrega dos brinquedos
	private static final int atrasoDaEntrega = 1000;

	// Contador para controle de quantas renas estão disponíveis para inicializar a entrega
	private int renasEsperando = 0;

	// Variável de controle para identificar se a entrega de brinquedos terminou, para desamarrar as renas do trenó
	private boolean terminouEntrega = false;

	/* Para cada rena que é adicionada na lista de renas em espera pelo Papai Noel, a thread atual avisa as outras e
	 * imprime quantas renas estão à espera do Papai Noel, pelo número da rena que acabou de entrar na lista de espera.
	 * Assim que a última rena for adicionada, a thread do Papai Noel recebe a notificação da mesma executa a entrega.
	 * Ao fim da entrega, o Papai noel avisa ao controlador do trenó que as renas foram removidas do mesmo, e as renas
	 * recebem um aviso síncrono para serem liberadas.
	 * Assim que ele termina a entrega, as renas voltam ao seu estado de produção até que o Papai Noel possa voltar a
	 * consumir novamente.
	 */
	public synchronized void prepararTreno() throws InterruptedException {

		renasEsperando++;

		Log.renaRetornou(renasEsperando);

		notifyAll();

		while(!terminouEntrega) {

			wait();

		}

		renasEsperando--;

		if(renasEsperando == 0) {

			terminouEntrega = false;

		}

	}

	public synchronized void amarrarRenas(int numRenas) throws InterruptedException {

		Log.amarrandoRenas();

		while(renasEsperando < numRenas) {

			wait();

		}

		Log.fimAmarrarRenas();

	}

	public synchronized void viajar() throws InterruptedException {

		Log.papaiNoelViajando();

		Thread.sleep(atrasoDaEntrega);

		Log.papaiNoelVoltou();

		terminouEntrega = true;

	}

	public synchronized void desamarrarRenas() {

		Log.desamarrandoRenas();

		notifyAll();

	}

}
