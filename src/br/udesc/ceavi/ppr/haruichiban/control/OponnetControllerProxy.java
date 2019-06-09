package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.Color;
import java.util.List;
import br.udesc.ceavi.ppr.haruichiban.model.Request;
import br.udesc.ceavi.ppr.haruichiban.view.IPlayerPanelObserver;
import br.udesc.ceavi.ppr.haruichiban.view.Jogador;
import java.util.ArrayList;

public class OponnetControllerProxy implements Jogador {

    private Color cor;

    public OponnetControllerProxy() {
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
    public synchronized List<Integer> getHand() {
        getCanal().newRequest(Request.OPONNET_HAND).enviar();
        String resposta = getCanal().getResposta();

        List<Integer> lista = new ArrayList<>();

        if (resposta.isEmpty()) {
            return lista;
        }

        for (String numero : resposta.split(",")) {
            lista.add(-1);
        }

        return lista;
    }

    @Override
    public synchronized int getPileSize() {
        getCanal().newRequest(Request.OPONNET_PILESIZE).enviar();
        String resposta = getCanal().getResposta();
        return Integer.parseInt(resposta);
    }

    public RequestSocket getCanal() {
        return ClientController.getInstance().getCanal();
    }

    @Override
    public void addObserver(IPlayerPanelObserver playerPanel) {
    }

    @Override
    public void chooseFlowerDeckEnd(int selectedColumn) {
    }

}
