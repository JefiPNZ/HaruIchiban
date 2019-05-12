package br.udesc.ceavi.ppr.haruichiban.state;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;
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
    public void getFolhaNoTabuleiroParaFlor(PlayerController aThis) throws Exception {
        //Buscando Flor Escura
        Folha folhaEscura = GameController.getInstance().getBoardeController().getFolhaEscura();
        colocandoFlorNaFolha(aThis, folhaEscura);
    }

    @Override
    public void colocandoFlorNaFolha(PlayerController aThis, Folha flor) throws Exception {
        flor.colocarPecaNaFolha(aThis.removerFlorEmJogo());
        GameController.getInstance().florColocadaNoTabuleiro();
    }

    @Override
    public void chamarPrimeiroVentoDaPrimaveira(PlayerController aThis) {
        try {
            aThis.requerirAoJogadorQueEsteEscolhaUmFolhaParaSerMovida();
        } catch (Exception ex) {
            
        }
    }

    @Override
    public void escolhaANovaFolhaEscura(PlayerController aThis) throws Exception{
        //Não Faz Nada
    }
}
