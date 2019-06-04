package br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get;

import java.io.PrintWriter;
import java.util.Scanner;

public class ColorRequest extends RequestSocket {

	public ColorRequest(Scanner respostaServidor, PrintWriter servidor) {
		super(respostaServidor, servidor,true);
	}

}
