package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.model.Product;
import br.udesc.ceavi.ppr.haruichiban.model.Request;
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
        this.parametros = null;
        resposta = null;
        this.request = newRequest.getRequest();
        return this;
    }

    public RequestSocket newProduct(Product newRequest) {
        this.parametros = null;
        resposta = null;
        this.request = newRequest.getProduct();
        return this;
    }

    protected RequestSocket addParametro(String paramentro) {
        if (this.parametros == null) {
            this.parametros = new StringBuilder();
            parametros.append(",:");
        }
        parametros.append(paramentro);
        parametros.append(",");
        return this;
    }

    protected RequestSocket addParametro(int paramentro) {
        return addParametro("" + paramentro);
    }

    public void enviar() {
        if (parametros != null) {
            parametros.deleteCharAt(parametros.length() - 1);
            request += parametros;
        }
        servidor.println(request);

    }

    public String getResposta() {
        if (resposta == null) {
            resposta = respostaServidor.nextLine();
        }
        if (resposta.equals("Request-Perdida")) {
            System.out.println(resposta);
            newProduct(Product.GAME_ENDGAME);
            System.exit(0);
        }
        return resposta;
    }

//    public String getRequest() {
//        return request;
//    }
    public boolean isAtivo() {
        newRequest(Request.GAME_JOGOCONTINUA).enviar();
        return getResposta().equals("true");
    }
}
