package br.udesc.ceavi.ppr.haruichiban.cliente.control;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.channel.ModelGet;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IFluxoController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.observers.PlayerPanelObserver;
import java.awt.Color;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import br.udesc.ceavi.ppr.haruichiban.cliente.state.State;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 * Controlador de Player, servirar para controlar as acoes do player no jogo
 *
 * @author Gustavo C Santos
 * @since 08/05/2019
 *
 */
public class PlayerController implements IPlayerController {

    /**
     * Este guarda a flor do turno
     */
    private final List<PlayerPanelObserver> observers = new ArrayList<>();

    private IFluxoController fluxoController;
    private Color myColor;
    private List<Object> hand;
    private Socket mySocket;
    private int pileSize;
    private int pontos;
    private boolean amTop;

    public PlayerController(Socket socket) {
        this.hand = new ArrayList<>();
        this.pileSize = 0;
        this.mySocket = socket;
        this.pontos = 0;
    }

    @Override
    public int getPileSize() {
        return pileSize;
    }

    @Override
    public synchronized List<Object> getHand() {
        return hand;
    }

    @Override
    public void choseFlowerDeck() {
        observers.forEach(obs -> obs.notifyJogadorEscolhaUmaFlor());
    }

    @Override
    public void chooseFlowerDeckEnd(int x) {
        SwingUtilities.invokeLater(() -> notifySimples("Flor de Valor: " + (int) hand.get(x) + " Escolhoda"));
        State state = GameClienteController.getInstance().getFluxoController().getState();
        state.addParametroToFase(x);
        notifiyFlowerChoise();
        state.setEndFase();
    }

    @Override
    public void setFluxoController(IFluxoController aThis) {
        this.fluxoController = aThis;
    }

    public IFluxoController getFluxoController() {
        return fluxoController;
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
    public void notifySemTitulo() {
        observers.forEach(obs -> obs.notifyYouAreSemTitulo());
    }

    @Override
    public void notifyYouAJunior() {
        observers.forEach(obs -> obs.notifyYouAreJunior());
    }

    @Override
    public void notifyYouASenior() {
        observers.forEach(obs -> obs.notifyYouAreSenior());
    }

    @Override
    public void notifySimples(String messagem) {
        SwingUtilities.invokeLater(() -> observers.forEach(obs -> obs.notifySimpleMessager(messagem)));
    }

    private void notifiyFlowerChoise() {
        observers.forEach(obs -> obs.notifyJogadorEscolhaUmaFlorEnd());
    }

    @Override
    public Color getColor() {
        return myColor;
    }

    @Override
    public void setColor(Color cor) {
        this.myColor = cor;
    }

    @Override
    public void atualizarHand() {
        GameClienteController.getInstance().getPackageOutput().newGet(ModelGet.MY_HAND, "");
    }

    @Override
    public void atualizarCor() {
        GameClienteController.getInstance().getPackageOutput().newGet(ModelGet.MY_COLOR, "");
    }

    @Override
    public void atualizarPileSize() {
        GameClienteController.getInstance().getPackageOutput().newGet(ModelGet.MY_PILESIZE, "");
    }

    @Override
    public void atualizarPontos() {
        GameClienteController.getInstance().getPackageOutput().newGet(ModelGet.MY_POINTS, "");
    }

    @Override
    public void notifySimplesComTempo(String primeiraMessagem, String segundaMessagem, int tempo) {
        GameClienteController.getInstance().threadPoolExecute(() -> {
            notifySimples(primeiraMessagem);
            try {
                Thread.sleep(tempo);
            } catch (InterruptedException ex) {
                Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            notifySimples(segundaMessagem);
        });
    }

    @Override
    public void setPosition(String parametroes) {
        this.amTop = parametroes.contains("TOP");
    }

    public boolean isAmTop() {
        return amTop;
    }

    @Override
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    @Override
    public void setHand(String parametroes) {
        hand.clear();
        String carta = parametroes.substring(1, parametroes.length() - 1);
        if (carta.isEmpty()) {
            return;
        }
        for (String string : carta.split(",")) {
            hand.add(Integer.parseInt(string));
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
        SwingUtilities.invokeLater(() -> observers.forEach(obs -> obs.notifyLoserScream()));
    }

    @Override
    public void notifyWinerScream() {
        SwingUtilities.invokeLater(() -> observers.forEach(obs -> obs.notifyWinerScream()));
    }

    @Override
    public void notifyUtilizaCoachar() {
        this.observers.forEach((observador) -> {
            observador.notifyUtilizaBotaoCoaxar();
        });
    }
}
