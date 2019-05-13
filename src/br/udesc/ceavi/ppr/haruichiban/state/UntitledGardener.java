package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import java.awt.Point;

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
        aThis.notifyYouAJunior();
    }

    @Override
    public void becomeSeniorGardener(PlayerController aThis) {
        aThis.setTitle(new SeniorGardener());
        aThis.notifyYouASenior();
    }

    @Override
    public void getFolhaNoTabuleiroParaFlor(PlayerController aThis) throws Exception {
        throw new Exception("O Jogador é um jardineiro Sem Titulo");
    }

    @Override
    public void chamarPrimeiroVentoDaPrimaveiraGetPosicoes(PlayerController aThis) throws Exception {
        throw new Exception("O Jogador é um jardineiro Sem Titulo");
    }

    @Override
    public void escolhaANovaFolhaEscuraGetPosicoes(PlayerController aThis) throws Exception {
        throw new Exception("O Jogador é um jardineiro Sem Titulo");
    }

    @Override
    public void colocarFlorNoTabuleiro(PlayerController aThis, ModelBoardTile posicaoTabuleiro) throws Exception {
        throw new Exception("O Jogador é um jardineiro Sem Titulo");
    }

    @Override
    public void getFolha(PlayerController playOuvindo, Point newSelection) throws Exception {
        throw new Exception("O Jogador é um jardineiro Sem Titulo");
    }

    @Override
    public void chamarPrimeiroVentoDaPrimaveiraSetFolha(PlayerController aThis, Point origem, Point destino) throws Exception {
        throw new Exception("O Jogador é um jardineiro Sem Titulo");
    }

}
