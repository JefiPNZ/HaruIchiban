package br.udesc.ceavi.ppr.haruichiban.control;

/**
 *
 * @author Gustavo C Santos
 * @since 11/05/2019
 * 
 */
public interface PlayerPanelObserver {

    public void repintarPlayerHand();

    public void notifyJogadorEscolhaUmaFlor();

    public void notifyYouAreJunior();

    public void notifyYouAreSenior();

    public void notifyYouAreSemTitulo();

    public void notifyEscolhaUmaPosicaoNoTabuleiro();

}
