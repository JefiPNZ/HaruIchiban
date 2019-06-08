package br.udesc.ceavi.ppr.haruichiban.view;

public interface IPlayerPanelObserver {

	void notifyYouAreJunior();

	void notifyYouAreSenior();

	void notifyYouAreSemTitulo();

	void notifyJogadorEscolhaUmaFlor();

	void notifyJogadorEscolhaUmaFlorEnd();

	void notifySimpleMessager(String mensagem);

}
