package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.control.IPlayerController;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class JuniorGardener implements TitleOfGardener {

    @Override
    public void becomeUntitledGardener(IPlayerController aThis) {
        aThis.setTitle(new UntitledGardener());
        aThis.notifySemTitulo();
    }

    @Override
    public void becomeJuniorGardener(IPlayerController aThis) throws PlayNaoPodeSeTornarJuniorException {
        throw new PlayNaoPodeSeTornarJuniorException("Este usuario já é um Junior");
    }

    @Override
    public void becomeSeniorGardener(IPlayerController aThis) throws PlayNaoPodeSeTornarSeniorException {
        throw new PlayNaoPodeSeTornarSeniorException("Este usuario é um Junior, e não pode se tornar um senior");
    }

    @Override
    public void putFlowerTable(IPlayerController aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void firstWind(IPlayerController aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void newDarkLeaf(IPlayerController aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
