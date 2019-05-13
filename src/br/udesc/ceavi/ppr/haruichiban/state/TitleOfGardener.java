package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import java.awt.Point;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public interface TitleOfGardener {

    public void becomeUntitledGardener(PlayerController aThis) throws PlayNaoPodeSeTornarUntitledGardenerException;

    public void becomeJuniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarJuniorException;

    public void becomeSeniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarSeniorException;

    public void getFolhaNoTabuleiroParaFlor(PlayerController aThis) throws Exception;

    public void colocarFlorNoTabuleiro(PlayerController aThis, ModelBoardTile posicaoTabuleiro) throws Exception;

    public void chamarPrimeiroVentoDaPrimaveiraGetPosicoes(PlayerController aThis) throws Exception;

    public void chamarPrimeiroVentoDaPrimaveiraSetFolha(PlayerController aThis, Point origem, Point destino) throws Exception;

    public void escolhaANovaFolhaEscuraGetPosicoes(PlayerController aThis) throws Exception;

    public void getFolha(PlayerController playOuvindo, Point newSelection) throws Exception;
}
