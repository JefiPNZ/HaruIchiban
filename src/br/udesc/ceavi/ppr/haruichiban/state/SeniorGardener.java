package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class SeniorGardener implements TitleOfGardener {

    @Override
    public void throwFlower() {
        //Codigo que vai colocar a flor no tabuleiro permitindo que esse escolha o local
    }

    @Override
    public void moveNenufares() throws Exception {
        throw new Exception("The Play is a GardenerSenior, he can not move Nenufares");
    }

    @Override
    public void chooseNewDarkNenufares() {
        //Codigo que permitir que o usuario escolha o novo nenufar escuro
    }

    @Override
    public void becomeUntitledGardener(PlayerController aThis) {
        aThis.setTitle(new UntitledGardener());
    }

    @Override
    public void becomeJuniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarJuniorException {
        throw new PlayNaoPodeSeTornarJuniorException("Este usuario é um Senior, e não pode se tornar um Junior");
    }

    @Override
    public void becomeSeniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarSeniorException{
        throw new PlayNaoPodeSeTornarSeniorException("Este usuario já é um Senior");
    }

}
