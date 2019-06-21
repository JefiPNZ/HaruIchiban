package br.udesc.ceavi.ppr.haruichiban.cliente.control.observers;

/**
 *
 * @author Jeferson Penz
 */
public interface GameStateObserver {
    
    public void notificaMudancaEstado(String mensagem);
    
    public void notificaFimJogo(String mensagem);

    public void notifyVencedor(int pontosDoVencedor, int pontosDoPerdedor);

    public void notifyPerdedor(int pontosDoVencedor, int pontosDoPerdedor);
    
}
