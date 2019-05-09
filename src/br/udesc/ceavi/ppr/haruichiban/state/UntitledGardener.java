package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class UntitledGardener implements TitleOfGardener {

    @Override
    public void throwFlower() throws Exception {
        throw new Exception("The UntitledGardener can not throw Flower");
    }

    @Override
    public void moveNenufares() throws Exception {
        throw new Exception("The UntitledGardener can not move Nenufares");
    }

    @Override
    public void chooseNewDarkNenufares() throws Exception {
        throw new Exception("The UntitledGardener can choose New Dark Nenufares");
    }

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

}
