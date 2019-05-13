package br.udesc.ceavi.ppr.haruichiban.control;

/**
 *
 * @author Gustavo C Santos
 * @since 12/05/2019
 * 
 */
public interface IControleDeFluxo {

    public void startGame();

    public void selecaoDeFlorFinalizada();

    public void florColocadaNoTabuleiro();

    public ETAPAGAME getEtapa();

    public void escolherNovaFolhaEscura();

}
