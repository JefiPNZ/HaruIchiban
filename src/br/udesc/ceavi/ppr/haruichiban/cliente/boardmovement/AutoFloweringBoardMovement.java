package br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.cliente.model.ModelBoardTileProxy;
import br.udesc.ceavi.ppr.haruichiban.cliente.model.PecaProxy;
import br.udesc.ceavi.ppr.haruichiban.cliente.state.State;
import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Diretion;
import java.awt.Point;

/**
 *
 * @author Gustavo C
 */
public class AutoFloweringBoardMovement implements BoardMovement {

    private Point newAnimalTOPPositon;
    private Point newAnimalBOTTOMPositon;
    private PecaProxy topAnimal;
    private PecaProxy bottomAnimal;

    private final IBoardController boardController;
    private final IPlayerController player;
    private String mensagem;

    public AutoFloweringBoardMovement() {
        this.boardController = GameClienteController.getInstance().getBoardController();
        this.player = GameClienteController.getInstance().getPlayer();
        setSapoPosition();
        mensagem = "Coloque o animal do Jogador Superior";
        player.notifySimplesComTempo("Escolha as novas posi\u00E7\u00F5es dos Animais", mensagem, 5000);
    }

    @Override
    public boolean addPoint(Point positionBoard) {
        ModelBoardTileProxy boardTile = boardController.getBoardTile(positionBoard);
        
        if (newAnimalTOPPositon == null) {
            if (!validar(positionBoard)) {
                newAnimalTOPPositon = positionBoard;
                boardTile.setPeca(topAnimal);
                boardController.renderBoard();
                mensagem = "Coloque o animal do Jogador Inferior";
                player.notifySimplesComTempo("Escolha as novas posi\u00E7\u00F5es dos Animais", mensagem, 2000);
            }
        } else {
            if (!validar(positionBoard)) {
                newAnimalBOTTOMPositon = positionBoard;
                boardTile.setPeca(bottomAnimal);
                boardController.renderBoard();
            }
        }
        if (isReady()) {
            execute();
        }
        return false;
    }

    private boolean validar(Point positionBoard) {
        ModelBoardTileProxy boardTile = boardController.getBoardTile(positionBoard);
        if (!boardTile.hasFolha()) {
            player.notifySimplesComTempo("Animal apenas pode ser colocado em Folhas", mensagem, 1500);
            return true;
        }

        if (boardTile.hasPeca()) {
            player.notifySimplesComTempo("Animal apenas pode ser colocado em Folhas Vazias", mensagem, 1500);
            return true;
        }

        if (boardTile.isEscura()) {
            player.notifySimplesComTempo("Animal apenas pode ser colocado em Folhas Claras", mensagem, 1500);
            return true;
        }

        return false;
    }

    @Override
    public boolean addDiretion(Diretion deretion) {
        return false;
    }

    public boolean isReady() {
        return newAnimalBOTTOMPositon != null && newAnimalTOPPositon != null;
    }

    @Override
    public void execute() {
        boardController.removeBoardMovement();
        State state = GameClienteController.getInstance().getFluxoController().getState();

        state.addParametroToFase(newAnimalTOPPositon);
        state.addParametroToFase(newAnimalBOTTOMPositon);
        state.setEndFase();
        boardController.removeBoardMovement();
        player.notifySimples("");
    }

    @Override
    public boolean tableInteraction() {
        return true;
    }

    /**
     * Tem como objetivo definir a cor do sapo do player de cima e de baixo
     */
    private void setSapoPosition() {
        topAnimal = new PecaProxy();
        topAnimal.setPecaCor(GameClienteController.getInstance().getTopPlayer().getColor());
        topAnimal.setPecaName(GameClienteController.getInstance().getEstacao().equals("Primavera") ? "Sapo" : "Pinguim");
        topAnimal.setPecaRotacao(GameClienteController.getInstance().getRandomizer().nextFloat());

        bottomAnimal = new PecaProxy();
        bottomAnimal.setPecaCor(GameClienteController.getInstance().getBottomPlayer().getColor());
        bottomAnimal.setPecaName(GameClienteController.getInstance().getEstacao().equals("Primavera") ? "Sapo" : "Pinguim");
        bottomAnimal.setPecaRotacao(GameClienteController.getInstance().getRandomizer().nextFloat());
    }

}
