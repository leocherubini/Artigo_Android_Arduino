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

package com.artigo.tela01;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import com.artigo.rede.Ligar;
import com.artigo.rede.Temperatura;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TelaPrincipal extends Activity {

	// IP do servidor
	private String ipServer;
	// Porta do servidor
	private int tcpServer;
	// Váriavel responsável por armazenar o endereço do servidor
	private String servidor;
	private TextView txServidor;
	private EditText edIp;
	private EditText edTcp;
	private Button btServidor;
	private Button btMenos;
	private Button btMais;
	private Button btLigar;
	private TextView txTemperatura;
	// Socket da conexão
	private Socket socket;
	// Fluxo de saída da conexão
	private DataOutputStream output;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_principal);

		// Widget responsável por apresentar o endereço do servidor
		txServidor = (TextView) findViewById(R.id.txServidor);
		// Campo de texto responsável por receber o IP do servidor
		edIp = (EditText) findViewById(R.id.edIP);
		// Campo de texto responsável por receber a porta do servidor
		edTcp = (EditText) findViewById(R.id.edTCP);
		// Botão responsável pela conexão com o servidor
		btServidor = (Button) findViewById(R.id.btServidor);
		// Botão responsável por diminuir a temperatura
		btMenos = (Button) findViewById(R.id.btMenos);
		// Botão responsável por aumentar a temperatura
		btMais = (Button) findViewById(R.id.btMais);
		// Botão responsável por ligar e desligar o ar
		btLigar = (Button) findViewById(R.id.btLigar);
		// Widget responsável pela apresentação da temperatura na tela
		txTemperatura = (TextView) findViewById(R.id.txNumero);

		// O botão vai receber os dados do servidor para um conexão por socket
		btServidor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// recebe o ip do servidor pelo campo de texto 
				ipServer = edIp.getText().toString();
				// recebe o tcp do servidor pelo campo de texto e o converte para inteiro 
				tcpServer = Integer.parseInt(edTcp.getText().toString());
				// recebe os dados do servidor em uma String
				servidor = ipServer + ":" + tcpServer;
				// exibe os dados do servidor na tela
				txServidor.setText(servidor);
				try {
					// configura o socket da aplicação com o ip e a porta
					socket = new Socket(ipServer, tcpServer);
					// configura o fluxo de saído de dados
					output = new DataOutputStream(socket.getOutputStream());
					// exibe uma mensagem na aplicação com o servidor escolhido
					alerta("Conectado no servidor: " + getIp() + ":" + getTcp());
				} catch (IOException e) {
					// nenhum tratamento para o caso de erro
				}

			}
		});

		// Botão que diminuirá e enviará a temperatura do ar condicionado
		btMenos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// recebe a temperatura atual da widget e converte esta para inteiro
				int tem = Integer.parseInt(txTemperatura.getText().toString());
				if (tem >= 18) {
					tem = tem - 1;
					// configura o valor da temperatura na tela
					txTemperatura.setText("" + tem);
					try {
						// envia a temperatura para o ar condicionado
						temperatura(output, tem);
						// envia uma mensagem na tela
						alerta("Temperatura " + tem + "ºC enviada");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// configura o valor mínimo aceito pelo controle do ar
					txTemperatura.setText("17");
				}

			}
		});

		// Botão que aumentará e enviará a temperatura do ar condicionado
		btMais.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// recebe a temperatura atual da widget e converte esta para inteiro
				int tem = Integer.parseInt(txTemperatura.getText().toString());
				if (tem <= 29) {
					tem = tem + 1;
					// configura o valor da temperatura na tela
					txTemperatura.setText("" + tem);
					try {
						// envia a temperatura para o ar condicionado
						temperatura(output, tem);
						// envia uma mensagem na tela
						alerta("Temperatura " + tem + "ºC enviada");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// configura o valor mínimo aceito pelo controle do ar
					txTemperatura.setText("30");
				}

			}
		});

		// Botão que ligará e desligará o ar condicionado
		btLigar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					// enviará o valor inteiro do tipo byte 99 para o ar condicionado
					ligar(output);
					// envia uma mensagem na tela do aplicativo
					alerta("Ligar/Desligar");
				} catch (IOException e) {
					
					e.printStackTrace();
				}

			}
		});

	}

	/** Método que retorna o ip do servidor **/
	public String getIp() {
		return this.ipServer;
	}

	/** Método que retorna o tcp do servidor **/
	public int getTcp() {
		return this.tcpServer;
	}

	/** Método que ligará e deligará o ar condicionado **/
	public void ligar(DataOutputStream out) throws IOException {
		Ligar l = new Ligar(out);
		l.start();
	}

	/** Método de configuração da temperatura **/
	public void temperatura(DataOutputStream out, int n) throws IOException {
		Temperatura t = new Temperatura(out, n);
		t.start();
	}

	/** Método nativo inutilizado nesta aplicação **/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_principal, menu);
		return true;
	}

	/** Métodos responsáveis pelo envio de mensagens de texto na tela da aplicação **/
	private final Handler h = new Handler() {
		public void handleMessage(Message msg) {
			String content = (String) msg.obj;
			Toast.makeText(TelaPrincipal.this, content, Toast.LENGTH_SHORT)
					.show();
		}
	};

	public void alerta(String message) {
		Message m = h.obtainMessage();
		m.obj = message;
		h.sendMessage(m);
	}

}
