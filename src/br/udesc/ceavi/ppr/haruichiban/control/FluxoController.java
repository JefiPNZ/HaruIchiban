package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.state.SeniorGardener;
import java.awt.Point;

/**
 *
 * @author Gustavo C Santos
 * @since 12/05/2019
 *
 */
public class FluxoController implements IFluxoController {

    private GameController controlGame;
    private IPlayerController bottomPlayer;
    private IPlayerController topPlayer;
    private IBoardController controllerBoard;


    public FluxoController(GameController controlGame) {
        this.controlGame = controlGame;
        this.controllerBoard = controlGame.getBoardeController();
        this.bottomPlayer = controlGame.getBottomPlayer();
        this.topPlayer = controlGame.getTopPlayer();
        this.bottomPlayer.setControllerFluxo(this);
        this.topPlayer.setControllerFluxo(this);
    }


    /**
     * Escolhe Aleatoriamete o Jogador Para Dar Inicio Ao Turno
     */
    @Override
    public void startGame() {
        int vez = controlGame.getRandomizer().nextInt();
        if (vez % 2 == 0) {
            this.notificaMudancaEstado("Jogador inferior escolha uma flor.");
            inicioDeTurno(bottomPlayer);
        } else {
            this.notificaMudancaEstado("Jogador superior escolha uma flor.");
            inicioDeTurno(topPlayer);
        }
    }

    /**
     * Da Inicio a um novo Turno Não Alterando Nenhuma Propriedade de Mao ou
     * Tabuleiro
     *
     * @param primeiro jogador que efetura a escolha da flor em primeiro lugar
     */
    private void inicioDeTurno(IPlayerController primeiro) {
        primeiro.choseFlowerDeck();
    }

    /**
     * Metodo usado para controlar a selecao da flor
     */
    public void selecaoDeFlorFinalizada() {
        //Verifica se um dos jogadores ainda tem que selecionar a flor do turno
        if (bottomPlayer.getFlorEmJogo() == null) {
            this.notificaMudancaEstado("Jogador inferior escolha uma flor.");
            bottomPlayer.choseFlowerDeck();
        } else if (topPlayer.getFlorEmJogo() == null) {
            this.notificaMudancaEstado("Jogador superior escolha uma flor.");
            topPlayer.choseFlowerDeck();
        } else if (topPlayer.getFlorEmJogo() != null && bottomPlayer.getFlorEmJogo() != null) {
            this.notificaMudancaEstado("Etapa de seleção de flores finalizada.");
            definirTitulos();
        }
    }


    /**
     * Apos as escolha de carta deve-se verificar qual jogador vai ter qual
     * titulo
     *
     * Evento Tratado Pelo State
     */
    private void definirTitulos() {
        this.notificaMudancaEstado("Definição de títulos:");
        if (bottomPlayer.getFlorEmJogo().getValor() > topPlayer.getFlorEmJogo().getValor()) {
            try {
                bottomPlayer.becomeSeniorGardener();
                topPlayer.becomeJuniorGardener();
                this.notificaMudancaEstado("Jogador Inferior é o jardineiro senior.");
                this.notificaMudancaEstado("Jogador Superior é o jardineiro junior.");
            } catch (PlayNaoPodeSeTornarSeniorException | PlayNaoPodeSeTornarJuniorException ex) {
                ex.printStackTrace();
                System.exit(0);
            }

        } else if (bottomPlayer.getFlorEmJogo().getValor() < topPlayer.getFlorEmJogo().getValor()) {
            try {
                bottomPlayer.becomeJuniorGardener();
                topPlayer.becomeSeniorGardener();
                this.notificaMudancaEstado("Jogador Inferior é o jardineiro junior.");
                this.notificaMudancaEstado("Jogador Superior é o jardineiro senior.");
            } catch (PlayNaoPodeSeTornarSeniorException | PlayNaoPodeSeTornarJuniorException ex) {
                ex.printStackTrace();
                System.exit(0);
            }

        } else if (bottomPlayer.getFlorEmJogo().getValor() == topPlayer.getFlorEmJogo().getValor()) {
            bottomPlayer.devolverFlorAoDeck();
            topPlayer.devolverFlorAoDeck();
            this.notificaMudancaEstado("Flores Com Mesmos Valores");
            this.notificaMudancaEstado("Voltando Flores Para Seus Respetivos Deck");
            novoTurno();
            return;
        }
        colocarFlorNoTabuleiro();
    }

    /**
     * Requer aos controlles de Jogador que este tratem o evendo de colocar a
     * flor no tabuleiro, Isso é requerido ao state do jogador em questao
     *
     * Evento Tratado Pelo State
     */
    private void colocarFlorNoTabuleiro() {
        this.notificaMudancaEstado("Jogador senior, coloque sua flor no tabuleiro.");
        bottomPlayer.requerirQueOJogadorColoqueAFlorNoTabuleiro();
        topPlayer.requerirQueOJogadorColoqueAFlorNoTabuleiro();
    }

    /**
     * Controle Jogador informa ao ControlFluxo que este ja colocou a flor no
     * tabuleiro Se ambos estirevem feito tal acao chama-se o primeiro vento da
     * primaveira
     *
     * Evento Tratado Pelo State
     */
    public void florColocadaNoTabuleiro() {
        this.controllerBoard.renderBoard();
        if (bottomPlayer.getFlorEmJogo() == null && topPlayer.getFlorEmJogo() == null) {
            this.notificaMudancaEstado("Flor colocada em tabuleiro.");
            this.notificaMudancaEstado("Jardineiro junior, chame o Primeiro Vento da Primaveira.");
            bottomPlayer.chamarOPrimeiroVentoDaPrimaveira();
            topPlayer.chamarOPrimeiroVentoDaPrimaveira();
        }
    }

    public void escolherNovaFolhaEscura() {
        bottomPlayer.escolhaANovaFolhaEscura();
        topPlayer.escolhaANovaFolhaEscura();
    }

    public void folhaVirada(Point newFolhaEscura) {
        verificarPontuacao();
    }

    private void verificarPontuacao() {
        novoTurno();
    }

    private void novoTurno() {
        if (topPlayer.getTitle().getClass() == SeniorGardener.class) {
            inicioDeTurno(topPlayer);
        } else if (bottomPlayer.getTitle().getClass() == SeniorGardener.class) {
            inicioDeTurno(bottomPlayer);
        } else {
            startGame();
        }
    }

    public void notificaMudancaEstado(String mensagem) {
        GameController.getInstance().notificaMudancaEstado(mensagem);
    }

    //Metodo de Chamada >>>>
    private void chooseFlower() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void defineTitles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void putFlowerTable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void firstWind() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void newDarkLeaf() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void getPlayerPoints() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //Metodo de Chamada <<<

    //Metodo de Finalizacao >>>
    @Override
    public void chooseFlowerEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void defineTitlesEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void putFlowerTableEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void firstWindEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void newDarkLeafEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void getPlayerPointsEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //Metodo de Finalizacao <<<

}
