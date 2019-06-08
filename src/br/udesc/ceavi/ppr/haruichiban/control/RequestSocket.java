package br.udesc.ceavi.ppr.haruichiban.control;

import java.io.PrintWriter;
import java.util.Scanner;

public class RequestSocket {

    private final Scanner respostaServidor;
    private final PrintWriter servidor;
    private String resposta;
    private String request;
    private StringBuilder parametros;

    public RequestSocket(Scanner respostaServidor, PrintWriter servidor) {
        this.respostaServidor = respostaServidor;
        this.servidor = servidor;
        this.request = null;
        resposta = null;
        parametros = null;
    }

    public RequestSocket newRequest(Request newRequest) {
        this.request = newRequest.getRequest();
        resposta = null;
        return this;
    }
    public RequestSocket newProduct(Product newRequest) {
    	this.request = newRequest.getProduct();
    	resposta = null;
    	return this;
    }

    protected RequestSocket addParametro(String paramentro) {
        if (this.parametros == null) {
            this.parametros = new StringBuilder();
            parametros.append("{");
        }
        parametros.append(paramentro);
        parametros.append(",");
        return this;
    }

    public void enviar() {
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

    public enum Request {
        MYCOLOR("I,ColorRequest"),
        MYPILESIZE("I,PileSizeRequest"),
        MYPOSITION("I,PositionRequest"),
        MYHAND("I,HandRequest"),
        
        OPONNETCOLOR("E,OponnetColorRequest"),
        OPONNETPILESIZE("E,OpennetPileSize"),

    	POINTS("I,PointsRequest"),
    	WINEW("E,WinerRequest"),
    	GAMECONFIG("E,GameConfigRequest"),
    	BOARDTILE("E,BoardRequest"), 
    	JOGOCONTINUA("E,JogoContinua"), 
    	HAVEOPONENT("E,HaveOponentRequest");

        private String request;

        Request(String request) {
            this.request = request;
        }

        public String getRequest() {
            return request;
        }
    }
    
    public enum Product {
    	PRODUCEMYDECK("I,ProduceDeckRequest"),
    	CHOOSEFLOWER("I,ChooseFlower"),
    	ENDGAME("END");
    	
    	private String request;
    	
    	Product(String request) {
    		this.request = request;
    	}
    	
    	public String getProduct() {
    		return request;
    	}
    }
	public boolean isAtivo() {
		newRequest(Request.JOGOCONTINUA).enviar();
		return getResposta().equals("true");
	}
}
