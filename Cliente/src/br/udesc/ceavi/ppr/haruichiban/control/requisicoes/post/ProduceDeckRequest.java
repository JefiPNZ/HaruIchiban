package br.udesc.ceavi.ppr.haruichiban.control.requisicoes.post;

import java.io.PrintWriter;
import java.util.Scanner;

import br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get.RequestSocket;

public class ProduceDeckRequest extends RequestSocket{

	public ProduceDeckRequest(Scanner respostaServidor, PrintWriter servidor) {
		super(respostaServidor, servidor, true);
	}

}
