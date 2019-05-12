package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;

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

    public void colocandoFlorNaFolha(PlayerController aThis, Folha flor) throws Exception;

    public void chamarPrimeiroVentoDaPrimaveira(PlayerController aThis) throws Exception;;

    public void escolhaANovaFolhaEscura(PlayerController aThis) throws Exception;
}
