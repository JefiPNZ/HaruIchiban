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
public class ControleDeFluxoDeJogo implements IControleDeFluxo {

    private GameController controlGame;
    private IPlayerController bottomPlayer;
    private IPlayerController topPlayer;
    private IBoardController controllerBoard;

    private EtapaGame etapa;

    public ControleDeFluxoDeJogo(GameController controlGame) {
        this.controlGame = controlGame;
        this.controllerBoard = controlGame.getBoardeController();
        this.bottomPlayer = controlGame.getBottomPlayer();
        this.topPlayer = controlGame.getTopPlayer();
        this.bottomPlayer.setControllerFluxo(this);
        this.topPlayer.setControllerFluxo(this);
    }

    @Override
    public EtapaGame getEtapa() {
        return etapa;
    }

    /**
     * Escolhe Aleatoriamete o Jogador Para Dar Inicio Ao Turno
     */
    @Override
    public void startGame() {
        etapa = EtapaGame.INICIAL;
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
        etapa = EtapaGame.ESCOLHE_FLOR;
        primeiro.requerirAoJogadorQueEsteEscolhaUmaFlor();
    }

    /**
     * Metodo usado para controlar a selecao da flor
     */
    @Override
    public void selecaoDeFlorFinalizada() {
        escoderOsValoresDasCartadasDosJogadores();
        //Verifica se um dos jogadores ainda tem que selecionar a flor do turno
        if (bottomPlayer.getFlorEmJogo() == null) {
            this.notificaMudancaEstado("Jogador inferior escolha uma flor.");
            bottomPlayer.requerirAoJogadorQueEsteEscolhaUmaFlor();
        } else if (topPlayer.getFlorEmJogo() == null) {
            this.notificaMudancaEstado("Jogador superior escolha uma flor.");
            topPlayer.requerirAoJogadorQueEsteEscolhaUmaFlor();
        } else if (topPlayer.getFlorEmJogo() != null && bottomPlayer.getFlorEmJogo() != null) {
            this.notificaMudancaEstado("Etapa de seleção de flores finalizada.");
            definirTitulos();
        }
    }

    private void escoderOsValoresDasCartadasDosJogadores() {
        topPlayer.hideHandValue();
        bottomPlayer.hideHandValue();
    }

    /**
     * Apos as escolha de carta deve-se verificar qual jogador vai ter qual
     * titulo
     *
     * Evento Tratado Pelo State
     */
    private void definirTitulos() {
        this.notificaMudancaEstado("Definição de títulos:");
        etapa = EtapaGame.DISTRIIBUICAO_DE_TITULOS;
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
        etapa = EtapaGame.JOGAR_FLOR_NO_TABULEIRO;
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
    @Override
    public void florColocadaNoTabuleiro() {
        this.controllerBoard.renderBoard();
        if (bottomPlayer.getFlorEmJogo() == null && topPlayer.getFlorEmJogo() == null) {
            this.notificaMudancaEstado("Flor colocada em tabuleiro.");
            etapa = EtapaGame.CHAMAR_VENTO_DA_PRIMAVERA;
            this.notificaMudancaEstado("Jardineiro junior, chame o Primeiro Vento da Primaveira.");
            bottomPlayer.chamarOPrimeiroVentoDaPrimaveira();
            topPlayer.chamarOPrimeiroVentoDaPrimaveira();
        }
    }

    /**
     * Apos o chamado da Primaveira deve escolher uma nova folha escura Evento
     *
     * Evento Tratado Pelo State
     */
    @Override
    public void escolherNovaFolhaEscura() {
        this.notificaMudancaEstado("Chamado o Primeiro Vento Da Primaveira.");
        etapa = EtapaGame.ESCOLHE_FOLHA_ESCURA;
        this.notificaMudancaEstado("Jardineiro Senior escolha a nova Folha Escura");
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

}
