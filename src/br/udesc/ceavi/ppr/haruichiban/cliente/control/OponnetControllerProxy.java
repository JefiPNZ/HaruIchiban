package br.udesc.ceavi.ppr.haruichiban.cliente.control;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.channel.ModelGet;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IFluxoController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.observers.PlayerPanelObserver;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class OponnetControllerProxy implements IPlayerController {

    private Color cor;
    private List<Object> hand;
    private int pileSize;
    private List<PlayerPanelObserver> observers;
    private int pontos;

    public OponnetControllerProxy(Color cor) {
        this.observers = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.pileSize = 0;
        this.cor = cor;
        this.pontos = 0;
    }

    @Override
    public synchronized List<Object> getHand() {
        return hand;
    }

    @Override
    public synchronized int getPileSize() {
        return pileSize;
    }

    @Override
    public int getPlayerScore() {
        return pontos;
    }

    @Override
    public void addObserver(PlayerPanelObserver obs) {
        this.observers.add(obs);
    }

    @Override
    public void setFluxoController(IFluxoController aThis) {
    }

    @Override
    public void notifySimples(String messagem) {
    }

    @Override
    public void notifySemTitulo() {
    }

    @Override
    public void notifyYouAJunior() {
    }

    @Override
    public void notifyYouASenior() {
    }

    @Override
    public Color getColor() {
        return cor;
    }

    @Override
    public void setColor(Color cor) {
        this.cor = cor;
    }

    @Override
    public void atualizarHand() {
        GameClienteController.getInstance().getPackageOutput().newGet(ModelGet.OPONNET_HAND, "");
    }

    @Override
    public void atualizarCor() {
        GameClienteController.getInstance().getPackageOutput().newGet(ModelGet.OPONNET_COLOR, "");
    }

    @Override
    public void atualizarPileSize() {
        GameClienteController.getInstance().getPackageOutput().newGet(ModelGet.OPONNET_PILESIZE, "");
    }

    @Override
    public void atualizarPontos() {
        GameClienteController.getInstance().getPackageOutput().newGet(ModelGet.OPONNET_POSITION, "");
    }

    @Override
    public void notifySimplesComTempo(String primeiraMessagem, String messagemFim, int tempo) {

    }

    @Override
    public void setPosition(String parametroes) {
    }

    @Override
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    @Override
    public void setHand(String parametroes) {
        String carta = parametroes.substring(1, parametroes.length() - 1);
        hand.clear();
        if (carta.isEmpty()) {
            return;
        }
        for (String string : carta.split(",")) {
            hand.add(-1);
        }
        SwingUtilities.invokeLater(() -> this.observers.forEach(obs -> obs.repitarTela()));
    }

    @Override
    public void setPileSize(int pileSize) {
        this.pileSize = pileSize;
        SwingUtilities.invokeLater(() -> this.observers.forEach(obs -> obs.repitarTela()));
    }

    @Override
    public void notifyLoserScream() {
    }

    @Override
    public void notifyWinerScream() {
    }

    @Override
    public void notifyUtilizaCoachar() {

    }

    @Override
    public boolean isAmTop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void choseFlowerDeck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void chooseFlowerDeckEnd(int x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
