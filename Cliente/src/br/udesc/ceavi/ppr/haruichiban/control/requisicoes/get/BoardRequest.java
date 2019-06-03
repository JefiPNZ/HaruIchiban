package br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get;

import java.io.PrintWriter;
import java.util.Scanner;

public class BoardRequest extends RequestSocket {

	
	public BoardRequest(Scanner respostaServidor, PrintWriter servidor) {
		super(respostaServidor,servidor,false);
	}
	
	public void addParametro(String paramentro) {
		super.addParametro(paramentro);
	}

}
