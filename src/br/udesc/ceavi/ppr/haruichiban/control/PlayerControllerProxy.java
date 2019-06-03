package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.Color;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import br.udesc.ceavi.ppr.haruichiban.view.IPlayerPanelObserver;
import br.udesc.ceavi.ppr.haruichiban.view.Jogador;

public class PlayerControllerProxy implements Jogador {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    private boolean top;
    private Color cor;

    public PlayerControllerProxy(Socket socket) throws Exception {
        this.socket = socket;
        this.in = new Scanner(socket.getInputStream());
        this.out = new PrintWriter(socket.getOutputStream());
        definirPosicao();
        definirCor();
    }

    private void definirCor() {
        System.out.println("\nRequerindo Ao Servidor Minha Cor " + myPosicao());
        sendRequest("I,MYCOLOR");
        String ret = in.nextLine();
        this.cor = (Color) new Gson().fromJson(ret, Color.class);
        System.out.println("Minha Cor " + myPosicao() + " :" + cor);
    }

    private void definirPosicao() {
        System.out.println("Requerindo Ao Servidor Minha Posicao");
        String confi = in.nextLine();
        top = confi.contains("TOP");
        System.out.println("Posicao Definida " + myPosicao());
    }

    private String myPosicao() {
        return isTop() ? "TOP" : "BOTTON";
    }

    public boolean isTop() {
        return top;
    }

    @Override
    public Color getColor() {
        return cor;
    }

    @Override
    public List<Integer> getHand() {
        System.out.println("\nRequerindo valores da flores que tenho em mao");
        sendRequest("I,HAND");
        String resposta = in.nextLine();
        System.out.println("Resposta: " + resposta);
        List<String> asList = Arrays.asList(resposta.split(",")[0], resposta.split(",")[1], resposta.split(",")[2]);
        return asList.stream().map(valor -> Integer.parseInt(valor)).collect(Collectors.toList());
    }

    @Override
    public int getPileSize() {
        System.out.println("\nRequerindo o numero de flores que tenho no montes");
        sendRequest("I,PILESIZE");
        String resposta = in.nextLine();
        System.out.println("Resposta: " + resposta);
        return Integer.parseInt(resposta);
    }

    public void chooseFlowerDeckEnd(int selectedColumn) {
        sendRequest("I,CHOSEFLOR(" + selectedColumn + ")");
    }

    @Override
    public void sendRequest(String request) {
        out.println(request);
        out.flush();
    }

    public void addObserver(IPlayerPanelObserver obs) {

    }

    public Scanner getIn() {
        return in;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void setCor(Color cor) {
        this.cor = cor;
    }

}
