package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class JuniorGardener implements TitleOfGardener {

    @Override
    public void becomeUntitledGardener(PlayerController aThis) {
        aThis.setTitle(new UntitledGardener());
        aThis.notifySemTitulo();
    }

    @Override
    public void becomeJuniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarJuniorException {
        throw new PlayNaoPodeSeTornarJuniorException("Este usuario já é um Junior");
    }

    @Override
    public void becomeSeniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarSeniorException {
        throw new PlayNaoPodeSeTornarSeniorException("Este usuario é um Junior, e não pode se tornar um senior");
    }

    @Override
    public void putFlowerTable(PlayerController aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void firstWind(PlayerController aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void newDarkLeaf(PlayerController aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
