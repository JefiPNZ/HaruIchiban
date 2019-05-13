package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.control.ETAPAGAME;
import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.control.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.FolhaJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import java.awt.Point;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class SeniorGardener implements TitleOfGardener {

    @Override
    public void becomeUntitledGardener(PlayerController aThis) {
        aThis.setTitle(new UntitledGardener());
        aThis.notifySemTitulo();
    }

    @Override
    public void becomeJuniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarJuniorException {
        throw new PlayNaoPodeSeTornarJuniorException("Este usuario é um Senior, e não pode se tornar um Junior");
    }

    @Override
    public void becomeSeniorGardener(PlayerController aThis) throws PlayNaoPodeSeTornarSeniorException {
        throw new PlayNaoPodeSeTornarSeniorException("Este usuario já é um Senior");
    }

    @Override
    public void getFolhaNoTabuleiroParaFlor(PlayerController aThis) throws Exception {
        GameController.getInstance().getBoardeController().setControlPlayOuvinte(aThis);
    }

    @Override
    public void chamarPrimeiroVentoDaPrimaveiraGetPosicoes(PlayerController aThis) {
        //Não Faz Nada
    }

    /**
     * Metodo Chamadao no BoardeController, este passa a pocisao escolhida
     *
     * @param aThis controllerPlay a qual o state pertence
     * @param modelBoardTile flor encontrada no tabuleiro
     *
     */
    @Override
    public void getFolha(PlayerController aThis, Point modelBoardTile) throws Exception {
        if (GameController.getInstance().getControlDeFluxo().getEtapa() == ETAPAGAME.JOGAR_FLOR_NO_TABULEIRO) {
            ModelBoardTile tile = GameController.getInstance().getBoardeController().getModelBoardTile(modelBoardTile);
            colocarFlorNoTabuleiro(aThis, tile);
        } else if (GameController.getInstance().getControlDeFluxo().getEtapa() == ETAPAGAME.ESCOLHE_FOLHA_ESCURA) {
            setNewFolhaEscura(modelBoardTile);
        }
    }

    @Override
    public void escolhaANovaFolhaEscuraGetPosicoes(PlayerController aThis) throws Exception {
        GameController.getInstance().getBoardeController().setControlPlayOuvinte(aThis);
    }

    @Override
    public void chamarPrimeiroVentoDaPrimaveiraSetFolha(PlayerController aThis, Point origem, Point destino) throws Exception {
    }

    @Override
    public void colocarFlorNoTabuleiro(PlayerController aThis, ModelBoardTile modelBoardTile) {
        try {
            modelBoardTile.getFolha().colocarPecaNaFolha(aThis.removerFlorEmJogo());
        } catch (FolhaJaPossuiUmaPecaEmCimaException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
        GameController.getInstance().getBoardeController().renderBoard();
        GameController.getInstance().getBoardeController().setControlPlayOuvinte(null);

        GameController.getInstance().getControlDeFluxo().florColocadaNoTabuleiro();
    }

    IBoardController boardeController;

    private void setNewFolhaEscura(Point modelBoardTile) {
        boardeController = GameController.getInstance().getBoardeController();
        boardeController.setControlPlayOuvinte(null);
    }

}
