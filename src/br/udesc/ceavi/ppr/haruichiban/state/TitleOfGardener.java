package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;

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

    public void putFlowerTable(PlayerController aThis);

    public void firstWind(PlayerController aThis);

    public void newDarkLeaf(PlayerController aThis);
}
