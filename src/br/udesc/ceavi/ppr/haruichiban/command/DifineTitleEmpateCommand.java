package br.udesc.ceavi.ppr.haruichiban.command;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.control.IFluxoController;
import br.udesc.ceavi.ppr.haruichiban.control.IPlayerController;

/**
 *
 * @author Gustavo C Santos
 * @since 14/05/2019
 *
 */
public class DifineTitleEmpateCommand implements Command {

    private IPlayerController topPlayer;
    private IPlayerController bottomPlayer;
    private GameController gameController;
    private final IFluxoController fluxo;

    public DifineTitleEmpateCommand(IPlayerController topPlayer, IPlayerController bottomPlayer,IFluxoController fluxo) {
        this.topPlayer = topPlayer;
        this.bottomPlayer = bottomPlayer;
        this.gameController = GameController.getInstance();
        this.fluxo = fluxo;
    }

    @Override
    public void execute() {
        bottomPlayer.devolverFlorAoDeck();
        topPlayer.devolverFlorAoDeck();
        this.gameController.notificaMudancaEstado("Flores Com Mesmos Valores");
        this.gameController.notificaMudancaEstado("Voltando Flores Para Seus Respetivos Deck");
        fluxo.startGame();
    }

}
