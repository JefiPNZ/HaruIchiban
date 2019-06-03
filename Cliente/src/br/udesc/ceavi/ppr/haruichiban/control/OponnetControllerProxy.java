package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.Color;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import br.udesc.ceavi.ppr.haruichiban.view.IPlayerPanelObserver;
import br.udesc.ceavi.ppr.haruichiban.view.Jogador;

public class OponnetControllerProxy implements Jogador {

    private PrintWriter out;
    private Scanner in;
    private Color cor;

    public OponnetControllerProxy(Socket playerSocket) throws Exception {
        this.out = new PrintWriter(playerSocket.getOutputStream());
        this.in = new Scanner(playerSocket.getInputStream());
    }

    @Override
    public Color getColor() {
        return cor;
    }

    @Override
    public void setCor(Color cor) {
        this.cor = cor;
    }

    @Override
    public List<Integer> getHand() {
        return Arrays.asList(-1, -1, -1);
    }

    @Override
    public int getPileSize() {
        sendRequest("E,OpennetPileSize");
        return 3;
    }

    @Override
    public void sendRequest(String request) {
        out.println(request.toUpperCase());
        out.flush();
    }

    @Override
    public void addObserver(IPlayerPanelObserver playerPanel) {
    }

    @Override
    public void chooseFlowerDeckEnd(int selectedColumn) {
    }

}
