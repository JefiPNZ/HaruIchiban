package br.udesc.ceavi.ppr.haruichiban.cliente.control.channel;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IEmitirSomController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IFluxoController;
import com.google.gson.Gson;
import java.awt.Color;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Gustavo C
 */
public class PackageClientConsumer implements Runnable {

    private BlockingQueue<CommunicationPackage> entradas;
    private IBoardController boardController;
    private IEmitirSomController emitirSomController;
    private IFluxoController fluxoController;
    private Gson gson;

    public PackageClientConsumer(BlockingQueue<CommunicationPackage> entradas) {
        this.entradas = entradas;
        this.boardController = game().getBoardController();
        this.emitirSomController = game().getEmitirSomController();
        this.fluxoController = game().getFluxoController();
        this.gson = new Gson();
    }

    private static GameClienteController game() {
        return GameClienteController.getInstance();
    }

    @Override
    public void run() {
        try {
            CommunicationPackage cPacket = null;
            //enquanto existe um novo comando, lembrando take() bloqueia
            while ((cPacket = entradas.take()) != null) {
                if (cPacket.isPost()) {
                    submitPost(cPacket.getModelPost(), cPacket.getParametro());
                }
                if (cPacket.isGet()) {
                    submitGet(cPacket.getModelGet(), cPacket.getParametro());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void submitPost(ModelPost post, String parametroes) {
        switch (post) {
            case GAME_ENDGAME:
                game().getPlayer().notifySimples(parametroes);
                game().notificaMudancaEstado("END-GAME");
                game().notificaMudancaEstado(parametroes);
                break;
            case GAME_WINEW:
                //index 0 - cor do vencedor
                //index 1 - pontos do vencedor
                //index 2 - pontos do perdedor
                String[] campos = parametroes.split("/");
                Color corVencedor = gson.fromJson(campos[0], Color.class);
                if (game().getPlayer().getColor().equals(corVencedor)) {
                    game().getPlayer().notifySimples("Parabens Voc\u00EA \u00E9 o Vencedor");
                    game().notificaMudancaEstado("Parabens Voc\u00EA \u00E9");
                    game().notificaMudancaEstado("END-GAME");
                    game().vencedor(Integer.parseInt(campos[1]), Integer.parseInt(campos[2]));
                } else {
                    game().getPlayer().notifySimples("Voc\u00EA Perdeu");
                    game().notificaMudancaEstado("Voc\u00EA Perdeu");
                    game().notificaMudancaEstado("END-GAME");
                    game().perderdor(Integer.parseInt(campos[1]), Integer.parseInt(campos[2]));
                }
                break;
            case GAME_MUDANCA_ESTADO:
                game().notificaMudancaEstado(parametroes);
                break;
            case PLAYER_NOTIFICAO_SIMPLER:
                game().getPlayer().notifySimples(parametroes);
                break;
            case PLAYER_NOTIFICAO_TEMPO:
                String[] tratamentoInicio = parametroes.split("/");
                String comentarioInicio = tratamentoInicio[0];
                int tempo = Integer.parseInt(tratamentoInicio[1]);
                String comentarioFim = tratamentoInicio[2];
                game().getPlayer().notifySimplesComTempo(comentarioInicio, comentarioFim, tempo);
                break;
            case OPPONENT_DISCOUNTED:
                game().getPlayer().notifySimples(parametroes);
                game().notificaMudancaEstado("END-GAME");
                break;
            case BECAME_JUNIOR:
                game().getPlayer().notifyYouAJunior();
                game().getOponnet().notifyYouASenior();
                break;
            case BECAME_SENIOR:
                game().getPlayer().notifyYouASenior();
                game().getOponnet().notifyYouAJunior();
                break;
            case BECAME_NOT_TITLE:
                game().getPlayer().notifySemTitulo();
                game().getOponnet().notifySemTitulo();
                break;
            case SCREAM_LOSER:
                game().getPlayer().notifyLoserScream();
                game().getOponnet().notifyWinerScream();
                break;
            case SCREAM_WINNER:
                game().getPlayer().notifyWinerScream();
                game().getOponnet().notifyLoserScream();
                break;
            default:
                throw new RuntimeException(post + "=" + parametroes);
        }
    }

    private void submitGet(ModelGet modelGet, String parametroes) {
        switch (modelGet) {
            case GAME_BOARD:
                boardController.atualizar(parametroes);
                break;
            case GAME_FASE:
                fluxoController.setNewFase(parametroes);
                break;
            case MY_COLOR:
                game().getPlayer().setColor(gson.fromJson(parametroes, Color.class));
                break;
            case MY_POINTS:
                game().getPlayer().setPontos(Integer.parseInt(parametroes));
                break;
            case MY_PILESIZE:
                game().getPlayer().setPileSize(Integer.parseInt(parametroes));
                break;
            case MY_HAND:
                game().getPlayer().setHand(parametroes);

                break;
            case MY_POSITION:
                game().getPlayer().setTop(parametroes.contains("TOP"));
                break;

            case OPONNET_COLOR:
                game().getOponnet().setColor(gson.fromJson(parametroes, Color.class));
                break;
            case OPONNET_HAND:
                game().getOponnet().setHand(parametroes);
                break;
            case OPONNET_PILESIZE:
                game().getOponnet().setPileSize(Integer.parseInt(parametroes));
                break;
            case OPONNET_POINTS:
                game().getOponnet().setPontos(Integer.parseInt(parametroes));
                break;
            case OPONNET_POSITION:
                game().getOponnet().setTop(parametroes.contains("TOP"));
                break;
            case GAME_ESTACAO:
                game().setEstacao(parametroes);
                break;
            default:
                throw new RuntimeException(modelGet + "=" + parametroes);
        }
    }

}
