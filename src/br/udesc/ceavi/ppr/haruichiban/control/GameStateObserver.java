package br.udesc.ceavi.ppr.haruichiban.control;

/**
 *
 * @author Jeferson Penz
 */
public interface GameStateObserver {
    
    public void notificaMudancaEstado(String mensagem);
    
    public void notificaFimJogo(String mensagem);
    
}
