/*
 * Esta aplicação é referente ao trabalho SISTEMA DE CONTROLE REMOTO DE 
 * TEMPERATURA ATRAVÉS DE DISPOSITIVOS MÓVEIS BASEADOS EM ANDROID
 * e será submetido no 17º CONGRESSO INTERNACIONAL E EXPOSIÇÃO DE
 * AUTOMAÇÃO, SISTEMAS E INSTRUMENTAÇÃO.
 * 
 * O objetivo deste trabalho é apresentar uma alternativo barata e acessível
 * para controle de refrigeração residensial.
 * 
 * Autores do trabalho:
 * 
 * Elisângela Ferreira
 * Felipe Cesar Costa
 * Jackson Antônio Tavares Peixoto
 * Leonardo Fernandes Cherubini
 * Ronan Marcelo Martins
 * Silvino Soares Corrêa
 * Yasmine Lino Franco
 */

package com.artigo.rede;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Temperatura extends Thread {

	// Fluxo de saída de dados
	private DataOutputStream output;
	// Váriavel responsável por receber o valor da temperatura
	private int temperatura;

	/** Construtor da classe **/
	public Temperatura(DataOutputStream output, int n)
			throws IOException {
		this.temperatura = n;
		this.output = output;
	}

	/** Método nativo da Thread **/
	public void run() {
		try {
			// enviando o valor 99 para o ar condicionado
			output.writeByte(temperatura);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
