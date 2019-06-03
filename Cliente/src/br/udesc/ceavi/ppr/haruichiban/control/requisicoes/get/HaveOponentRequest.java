package br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get;

import java.io.PrintWriter;
import java.util.Scanner;

public class HaveOponentRequest extends RequestSocket{

	public HaveOponentRequest(Scanner respostaServidor, PrintWriter servidor) {
		super(respostaServidor, servidor, false);
	}

}
