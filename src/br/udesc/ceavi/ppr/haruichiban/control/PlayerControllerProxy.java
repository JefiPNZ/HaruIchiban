package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.Color;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import br.udesc.ceavi.ppr.haruichiban.model.Product;
import br.udesc.ceavi.ppr.haruichiban.model.Request;
import br.udesc.ceavi.ppr.haruichiban.view.IPlayerPanelObserver;
import br.udesc.ceavi.ppr.haruichiban.view.Jogador;
import java.util.ArrayList;

public class PlayerControllerProxy implements Jogador {

    private Socket socket;

    private boolean top;
    private Color cor;

    public PlayerControllerProxy(Socket socket) throws Exception {
        this.socket = socket;
        ClientController.getInstance().initRequestSocket(new Scanner(socket.getInputStream()),
                new PrintWriter(socket.getOutputStream(), true));
        definirCor();
        definirPosicao();
    }

    private void definirCor() {
        getCanal().newRequest(Request.MY_COLOR).enviar();
        this.cor = (Color) new Gson().fromJson(getCanal().getResposta(), Color.class);
    }

    private void definirPosicao() {
        getCanal().newRequest(Request.MY_POSITION).enviar();
        top = getCanal().getResposta().contains("TOP");
    }

    public boolean isTop() {
        return top;
    }

    @Override
    public Color getColor() {
        return cor;
    }

    @Override
    public synchronized List<Integer> getHand() {
        getCanal().newRequest(Request.MY_HAND).enviar();
        String resposta = getCanal().getResposta();

        List<Integer> lista = new ArrayList<>();

        if (resposta.isEmpty()) {
            return lista;
        }

        for (String numero : resposta.split(",")) {
            lista.add(Integer.getInteger(numero));
        }

        return lista;
    }

    @Override
    public synchronized int getPileSize() {
        getCanal().newRequest(Request.MY_PILESIZE).enviar();
        String resposta = getCanal().getResposta();
        return Integer.parseInt(resposta);
    }

    @Override
    public void chooseFlowerDeckEnd(int selectedColumn) {
        getCanal().newProduct(Product.MY_CHOOSEFLOWER).addParametro("x=" + selectedColumn).enviar();
    }

    @Override
    public void addObserver(IPlayerPanelObserver obs) {

    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void setCor(Color cor) {
        this.cor = cor;
    }

    private RequestSocket getCanal() {
        return ClientController.getInstance().getCanal();
    }
}
