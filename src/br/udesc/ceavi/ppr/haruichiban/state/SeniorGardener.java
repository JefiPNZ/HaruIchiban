package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.boardmovement.SeniorFlowerBoard;
import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.control.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.control.IFluxoController;
import br.udesc.ceavi.ppr.haruichiban.control.IPlayerController;
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
    public void becomeUntitledGardener(IPlayerController aThis) {
        aThis.setTitle(new UntitledGardener());
        aThis.notifySemTitulo();
    }

    @Override
    public void becomeJuniorGardener(IPlayerController aThis) throws PlayNaoPodeSeTornarJuniorException {
        throw new PlayNaoPodeSeTornarJuniorException("Este usuario é um Senior, e não pode se tornar um Junior");
    }

    @Override
    public void becomeSeniorGardener(IPlayerController aThis) throws PlayNaoPodeSeTornarSeniorException {
        throw new PlayNaoPodeSeTornarSeniorException("Este usuario já é um Senior");
    }

    @Override
    public void putFlowerTable(IPlayerController aThis) {
        IBoardController boardController = GameController.getInstance().getBoardController();
        IFluxoController fluxoController = GameController.getInstance().getFluxoController();

        SeniorFlowerBoard juniorFlowerBoard = new SeniorFlowerBoard(aThis, boardController, fluxoController);
        boardController.initBoardMovement(juniorFlowerBoard);
    }

    @Override
    public void firstWind(IPlayerController aThis) {
    }

    @Override
    public void newDarkLeaf(IPlayerController aThis) {
    }

}
