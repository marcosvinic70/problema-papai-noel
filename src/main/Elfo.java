package main;

import java.util.Random;

public class Elfo extends Thread {

	private static final int tempoMaxTrabalhando = 5000;
	private static final int tempoMaxReuniao = 500;
	private static int contadorElfos = 0;
	private int id;
	private PapaiNoel papaiNoel;

	public Elfo(PapaiNoel papaiNoel) {

		this.papaiNoel = papaiNoel;
		id = contadorElfos;
		contadorElfos++;

		start();

	}

	public synchronized void reuniaoPapaiNoel() throws InterruptedException {

		Thread.sleep(new Random().nextInt(tempoMaxReuniao));

		notify();
	}

	public void run() {

		try {

			while(true) {

				trabalhar();

				reunirComPapaiNoel();

			}

		} catch (InterruptedException e) {

			System.exit(1);

		}

	}

	private void trabalhar() throws InterruptedException {

		Log.elfoTrabalhando(id);

		Thread.sleep(new Random().nextInt(tempoMaxTrabalhando));

		Log.elfoParou(id);

	}

	private synchronized void reunirComPapaiNoel() throws InterruptedException {

		Log.elfoPedindoAjuda(id);

		papaiNoel.elfoChegou(this);

		wait();

		Log.elfoVoltouAoTrabalho(id);

	}

}
