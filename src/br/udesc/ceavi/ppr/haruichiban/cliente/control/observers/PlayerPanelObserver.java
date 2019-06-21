package br.udesc.ceavi.ppr.haruichiban.cliente.control.observers;

/**
 *
 * @author Gustavo C Santos
 * @since 11/05/2019
 *
 */
public interface PlayerPanelObserver {

    public void notifyJogadorEscolhaUmaFlor();

    public void notifyJogadorEscolhaUmaFlorEnd();

    public void notifyYouAreJunior();

    public void notifyYouAreSenior();

    public void notifyYouAreSemTitulo();

    public void notifySimpleMessager(String messagem);

    public void repitarTela();

    public void notifyUtilizaBotaoCoaxar();

    public void notifyLoserScream();

    public void notifyWinerScream();

}
