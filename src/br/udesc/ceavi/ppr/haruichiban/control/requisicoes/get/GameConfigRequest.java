package br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get;

import java.io.PrintWriter;
import java.util.Scanner;

public class GameConfigRequest extends RequestSocket {

	public GameConfigRequest(Scanner respostaServidor, PrintWriter servidor) {
		super(respostaServidor, servidor,true);
	}

}
