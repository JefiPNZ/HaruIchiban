package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class SeniorGardener implements TitleOfGardener {

    @Override
    public void becomeUntitledGardener(PlayerController aThis) {
        aThis.setTitle(new UntitledGardener());
    }

    @Override
    public void becomeJuniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarJuniorException {
        throw new PlayNaoPodeSeTornarJuniorException("Este usuario é um Senior, e não pode se tornar um Junior");
    }

    @Override
    public void becomeSeniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarSeniorException {
        throw new PlayNaoPodeSeTornarSeniorException("Este usuario já é um Senior");
    }

    @Override
    public void getFolhaNoTabuleiroParaFlor(PlayerController aThis) throws Exception {
        //Requerindo Ao Jogador Que Escolha Um Local Apropriado
        aThis.requerirAoJogadorQueEsteEscolhaUmFolhaParaColocarAFlor();
    }

    @Override
    public void colocandoFlorNaFolha(PlayerController aThis, Folha flor) throws Exception {
        flor.colocarPecaNaFolha(aThis.removerFlorEmJogo());
        GameController.getInstance().florColocadaNoTabuleiro();
    }

    @Override
    public void chamarPrimeiroVentoDaPrimaveira(PlayerController aThis) {
        //Não Faz Nada
    }

    @Override
    public void escolhaANovaFolhaEscura(PlayerController aThis) throws Exception {
        aThis.requerirAoJogadorQueEsteEscolhaUmFolhaParaSerVirada();
    }

}
