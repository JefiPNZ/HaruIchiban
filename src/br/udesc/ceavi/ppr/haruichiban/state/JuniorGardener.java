package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.control.EtapaGame;
import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.FolhaJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import java.awt.Point;
import javax.swing.JOptionPane;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class JuniorGardener implements TitleOfGardener {

    public void chooseNewDarkNenufares() throws Exception {
        throw new Exception("The Play is already a GardenerJunior, he can not choose New Dark Nenufares");
    }

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

    /**
     * Metodo vai requerir a possicao da folha escura para o tabuleiro e chamar
     * metodo de colocandoFlorNaFolha(PlayerController aThis, Folha flor)
     *
     * @param aThis ControllerPlay a qual este state pertence
     */
    @Override
    public void getFolhaNoTabuleiroParaFlor(PlayerController aThis) {
        //Buscando Flor Escura
        ModelBoardTile folhaEscura = GameController.getInstance().getBoardeController().getFolhaEscura();
        colocarFlorNoTabuleiro(aThis, folhaEscura);
    }

    private Point origem;

    /**
     * Metodo Chamadao no JuniorGradener, este passa a flor escura que o
     * tabuleiro possui
     *
     * @param aThis ControllerPlay a qual este state pertence
     * @param point localizacao
     */
    @Override
    public void getFolha(PlayerController aThis, Point point) {
        JOptionPane.showMessageDialog(null, "getFolha");
        if (GameController.getInstance().getControlDeFluxo().getEtapa() == EtapaGame.CHAMAR_VENTO_DA_PRIMAVERA) {
            if (origem == null) {
                JOptionPane.showMessageDialog(null, "Origem sem");
                origem = point;
            } else {
                chamarPrimeiroVentoDaPrimaveiraSetFolha(aThis, origem, point);
                JOptionPane.showMessageDialog(null, "chamarPrimeiroVentoDaPrimaveiraSetFolha");
            }
        }
    }

    /**
     * Coloca controller como ouvinte BoardeController
     *
     * @param aThis ControllerPlay a qual este state pertence
     */
    @Override
    public void chamarPrimeiroVentoDaPrimaveiraGetPosicoes(PlayerController aThis) {
        GameController.getInstance().getBoardeController().setControlPlayOuvinte(aThis);
        JOptionPane.showMessageDialog(null, "Junir Chamando Ventos Da Manha");
    }

    @Override
    public void escolhaANovaFolhaEscuraGetPosicoes(PlayerController aThis) throws Exception {
        //Não Faz Nada
    }

    @Override
    public void chamarPrimeiroVentoDaPrimaveiraSetFolha(PlayerController aThis, Point origem, Point destino) {
        JOptionPane.showMessageDialog(null, "Move To State");
        GameController.getInstance().getBoardeController().moveTo(origem, destino);
        GameController.getInstance().getBoardeController().setControlPlayOuvinte(null);
        GameController.getInstance().getControlDeFluxo().escolherNovaFolhaEscura();
    }

    @Override
    public void colocarFlorNoTabuleiro(PlayerController aThis, ModelBoardTile posicaoTabuleiro) {
        try {
            posicaoTabuleiro.getFolha().colocarPecaNaFolha(aThis.removerFlorEmJogo());
            GameController.getInstance().getBoardeController().renderBoard();
            GameController.getInstance().getControlDeFluxo().florColocadaNoTabuleiro();
        } catch (FolhaJaPossuiUmaPecaEmCimaException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
