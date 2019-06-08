package br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get;

import java.io.PrintWriter;
import java.util.Scanner;

public class RequestSocket {

	private Scanner respostaServidor;
	private PrintWriter servidor;
	private boolean comandoInterno;
	private String resposta;
	private String request;
	private StringBuilder parametros;

	public RequestSocket(Scanner respostaServidor, PrintWriter servidor, boolean comandoInterno) {
		this.respostaServidor = respostaServidor;
		this.servidor = servidor;
		this.comandoInterno = comandoInterno;
		this.request = null;
		resposta = null;
		parametros = null;
	}

	protected void addParametro(String paramentro) {
		if (this.parametros == null) {
			this.parametros = new StringBuilder();
			parametros.append("{");
		}
		parametros.append(paramentro);
		parametros.append(",");
	}

	public void enviar() {
		request = (comandoInterno ? "I," : "E,") + this.getClass().getSimpleName();
		
		if (parametros != null) {
			parametros.setCharAt(parametros.length() - 1, '}');
			request += parametros;
		}
		
		servidor.println(request);
	}

	public String getResposta() {
		if (resposta == null) {
			resposta = respostaServidor.nextLine();
		}
		return resposta;
	}

	public String getRequest() {
		return request;
	}
}
