package br.udesc.ceavi.ppr.haruichiban.control.observers;

public interface GameStateObserverProxy {

	void notificaMudancaEstado(String mensagem);

	void notificaFimJogo(String mensagem);

}
