package br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get;

import java.io.PrintWriter;
import java.util.Scanner;

public class PointsRequest extends RequestSocket {

	public PointsRequest(Scanner respostaServidor, PrintWriter servidor) {
		super(respostaServidor, servidor,true);
	}
	

}
