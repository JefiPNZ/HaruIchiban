package br.udesc.ceavi.ppr.haruichibanl.command;

import br.udesc.ceavi.ppr.haruichiban.control.BoardController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PosicaoEmTabuleiroOcupadaException;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class MoveNenufar implements Command {

    private final int deX, deY, paraX, paraY;
    private final ModelBoardTile nenufar;
    private final BoardController control;

    public MoveNenufar(int deX, int deY, int paraX, int paraY, ModelBoardTile nenufar, BoardController control) {
        this.deX = deX;
        this.deY = deY;
        this.paraX = paraX;
        this.paraY = paraY;
        this.nenufar = nenufar;
        this.control = control;
    }

    @Override
    public void execute() {
        try {
            control.changeNenufarTo(nenufar, deX, deY, paraX, paraY);
        } catch (PosicaoEmTabuleiroOcupadaException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
