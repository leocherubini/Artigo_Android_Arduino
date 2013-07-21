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

public class Ligar extends Thread{

	// Fluxo de saída de dados
	private DataOutputStream output;
	
	/** Construtor da classe **/
	public Ligar(DataOutputStream output) throws IOException {
		this.output = output;
	}
	
	/** Método nativo da Thread **/
	public void run() {
		try {
			// váriavel que representa o valor padrão para ligar e desligar o ar condicionado
			byte i = 99;
			// enviando o valor 99 para o ar condicionado
			output.writeByte(i);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
