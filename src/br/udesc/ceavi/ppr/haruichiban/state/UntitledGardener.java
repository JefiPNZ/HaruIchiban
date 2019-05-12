package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class UntitledGardener implements TitleOfGardener {

    @Override
    public void becomeUntitledGardener(PlayerController aThis) throws PlayNaoPodeSeTornarUntitledGardenerException {
        throw new PlayNaoPodeSeTornarUntitledGardenerException("Este usuario já é um UntitledGardener");
    }

    @Override
    public void becomeJuniorGardener(PlayerController aThis) {
        aThis.setTitle(new JuniorGardener());
    }

    @Override
    public void becomeSeniorGardener(PlayerController aThis) {
        aThis.setTitle(new SeniorGardener());
    }

    @Override
    public void getFolhaNoTabuleiroParaFlor(PlayerController aThis) throws Exception {
        throw new Exception("O Jogador é um jardineiro Sem Titulo");
    }

    @Override
    public void colocandoFlorNaFolha(PlayerController aThis, Folha flor) throws Exception {
        throw new Exception("O Jogador é um jardineiro Sem Titulo");
    }

    @Override
    public void chamarPrimeiroVentoDaPrimaveira(PlayerController aThis) throws Exception {
        throw new Exception("O Jogador é um jardineiro Sem Titulo");
    }

    @Override
    public void escolhaANovaFolhaEscura(PlayerController aThis) throws Exception {
        throw new Exception("O Jogador é um jardineiro Sem Titulo");
    }

}
