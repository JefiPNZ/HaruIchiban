package br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get;

import java.io.PrintWriter;
import java.util.Scanner;

public class HandRequest extends RequestSocket {

	public HandRequest(Scanner respostaServidor, PrintWriter servidor) {
		super(respostaServidor, servidor,true);
	}

}
