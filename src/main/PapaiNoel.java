package main;

import java.util.ArrayList;

public class PapaiNoel extends Thread {

	// Objeto controlador utilizado pelo Papai Noel
	private Treno treno;

	// Lista de renas para consumo do Papai Noel
	private ArrayList<Rena> listaRenas = new ArrayList<>();

	// Lista de elfos para consumo do Papai Noel
	private ArrayList<Elfo> listaElfos = new ArrayList<>();

	// Constante de renas necessárias para se realizar uma entrega (mínimo de consumo do Papai Noel)
	private static int renasParaEntrega = 9;

	// Constante de elfos necessários para uma reunião (mínimo de consumo do Papai Noel)
	private static int elfosParaReuniao = 3;

	// Marcador constante definido para indicar que as renas estão prontas para consumo
	private static final int renasProntas = 0;

	// Marcador constante definido para indicar que os elfos estão prontos para consumo
	private static final int elfosProntos = 1;

	// Construtor da thread do Papai Noel
	public PapaiNoel(Treno treno) {

		this.treno = treno;

		start();

	}

	/* Adiciona a thread da rena pronta na lista do Papai Noel (consumidor) e indica à thread do Papai Noel que uma rena
	 * foi inserida na sua lista de renas.
	 */
	public synchronized void renaChegou(Rena rena) {

		this.listaRenas.add(rena);

		notify();

	}

	/* Adiciona a thread do elfo pronto na lista do Papai Noel (consumidor) e indica à thread do Papai Noel que um elfo
	 * foi inserido na sua lista de elfos.
	 */
	public synchronized void elfoChegou(Elfo elfo) {

		listaElfos.add(elfo);

		notify();

	}

	public void run() {

		try {

			while(true) {

				Log.dormindo();

				if(sleep() == renasProntas) {

					entregarBrinquedos();

				} else {

					reunirComElfos();

				}

			}

		} catch(InterruptedException e) {

			System.exit(1);

		}

	}

	private synchronized int sleep() throws InterruptedException {

		while((listaRenas.size() < renasParaEntrega) && (listaElfos.size() < elfosParaReuniao)) {

			wait();

		}

		Log.acordou();

		if(listaRenas.size() >= renasParaEntrega) {

			return renasProntas;

		} else {

			return elfosProntos;

		}

	}

	private void entregarBrinquedos() throws InterruptedException {

		Log.inicioEntrega();

		treno.amarrarRenas(renasParaEntrega);
		treno.viajar();
		treno.desamarrarRenas();
		listaRenas.clear();

		Log.fimEntrega();

	}

	private void reunirComElfos() throws InterruptedException {

		Log.inicioReuniaoComElfos();

		for(int i = 0; i < elfosParaReuniao; i++) {

			Elfo elfo = removeUmElfo();
			elfo.reuniaoPapaiNoel();

		}

		Log.fimReuniaoComElfos();

	}

	// remove um elfo da lista de elfos que estão consultando com o Papai Noel
	private synchronized Elfo removeUmElfo() {

		return listaElfos.remove(0);

	}

}
