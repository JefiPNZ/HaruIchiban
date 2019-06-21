package br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.observers.PlayerPanelObserver;
import java.awt.Color;
import java.util.List;

/**
 * Interface para classes que realizam o controle da mão do jogador.
 *
 * @author Jeferson Penz
 */
public interface IPlayerController {

    /**
     * Retorna o tamanho da pilha do jogador.
     *
     * @return
     */
    public int getPileSize();

    /**
     * Retorna a mão do usuário.
     *
     * @return
     */
    public List<Object> getHand();

    public int getPlayerScore();

    public void choseFlowerDeck();

    public void chooseFlowerDeckEnd(int x);

    public void addObserver(PlayerPanelObserver obs);

    public void setFluxoController(IFluxoController aThis);

    public void notifySimples(String messagem);

    public void notifySemTitulo();

    public void notifyYouAJunior();

    public void notifyYouASenior();

    public Color getColor();

    public void setColor(Color corJogador);

    public void atualizarHand();

    public void atualizarCor();

    public void atualizarPileSize();

    public void atualizarPontos();

    /**
     * Mostra para o usuario uma messagems com espaco de tempo pre-definido
     * @param primeiraMessagem primeira messagem
     * @param messagemFim segunda messagem
     * @param tempo tempo entre mensagens
     */
    public void notifySimplesComTempo(String primeiraMessagem, String messagemFim, int tempo);

    public void setPosition(String parametroes);

    public void setPontos(int parseInt);

    public void setHand(String parametroes);

    public void setPileSize(int parseInt);

    public void notifyLoserScream();

    public void notifyWinerScream();

    void notifyUtilizaCoachar();

    public boolean isAmTop();

}
