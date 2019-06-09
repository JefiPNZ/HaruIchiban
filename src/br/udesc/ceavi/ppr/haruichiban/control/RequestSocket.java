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
        resposta = null;
        this.request = newRequest.getRequest();
        return this;
    }

    public RequestSocket newProduct(Product newRequest) {
        resposta = null;
        this.request = newRequest.getProduct();
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
        if (resposta.equals("Request-Perdida")) {
            System.out.println(resposta);
            newProduct(Product.GAME_ENDGAME);
            System.exit(0);
        }
        return resposta;
    }

    public String getRequest() {
        return request;
    }

    /**
     * Enum Request em uma String
     *
     * MY,Do Player OP,Do Oponnet GAME,Do game MY,ColorRequest MY -> Informa a
     *
     */
    public enum Request {
        MY_COLOR("MY,Color"),
        MY_PILESIZE("MY,PileSize"),
        MY_POSITION("MY,Position"),
        MY_HAND("MY,Hand"),
        
        OPONNET_COLOR("OP,Color"),
        OPONNET_PILESIZE("OP,PileSize"),
        OPONNET_POSITION("OP,Position"),
        OPONNET_HAND("OP,Hand"),
        
        GAME_WINEW("GAME,Winer"),
        GAME_GAMECONFIG("GAME,GameConfig"),
        GAME_JOGOCONTINUA("GAME,JogoContinua"),
        GAME_HAVEOPONENT("GAME,HaveOponent"),
        GAME_BOARDTILE("GEME,BoardPonit"),
        GAME_POINTS("GAME,Points");
        
        private String request;

        Request(String request) {
            this.request = request;
        }

        public String getRequest() {
            return request;
        }
    }

    public enum Product {
        MY_PRODUCEMYDECK("MY,ProduceDeck"),
        MY_CHOOSEFLOWER("MY,ChooseFlower"),
       
        GAME_ENDGAME("GAME,ENDGAME");

        private String request;

        Product(String request) {
            this.request = request;
        }

        public String getProduct() {
            return request;
        }
    }

    public boolean isAtivo() {
        newRequest(Request.GAME_JOGOCONTINUA).enviar();
        return getResposta().equals("true");
    }
}
