package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.model.ModelPlayer;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public interface TitleOfGardener {

    public void throwFlower() throws Exception;

    public void moveNenufares() throws Exception;

    public void chooseNewDarkNenufares() throws Exception;

    public void becomeUntitledGardener(PlayerController aThis) throws PlayNaoPodeSeTornarUntitledGardenerException;

    public void becomeJuniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarJuniorException;

    public void becomeSeniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarSeniorException;
}
