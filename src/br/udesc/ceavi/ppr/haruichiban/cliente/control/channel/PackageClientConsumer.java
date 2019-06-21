package br.udesc.ceavi.ppr.haruichiban.cliente.control.channel;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IEmitirSomController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IFluxoController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IPlayerController;
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
    private IPlayerController oponnet;
    private IPlayerController player;
    private Gson gson;

    public PackageClientConsumer(BlockingQueue<CommunicationPackage> entradas) {
        this.entradas = entradas;
        this.boardController = game().getBoardController();
        this.emitirSomController = game().getEmitirSomController();
        this.fluxoController = game().getFluxoController();
        this.oponnet = game().getOponnet();
        this.player = game().getPlayer();
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
                player.notifySimples(parametroes);
                game().notificaMudancaEstado("END-GAME");
                game().notificaMudancaEstado(parametroes);
                break;
            case GAME_WINEW:
                //index 0 - cor do vencedor
                //index 1 - pontos do vencedor
                //index 2 - pontos do perdedor
                String[] campos = parametroes.split("/");
                Color corVencedor = gson.fromJson(campos[0], Color.class);
                if (player.getColor().equals(corVencedor)) {
                    player.notifySimples("Parabens Voc\u00EA \u00E9 o Vencedor");
                    game().notificaMudancaEstado("Parabens Voc\u00EA \u00E9");
                    game().notificaMudancaEstado("END-GAME");
                    game().vencedor(Integer.parseInt(campos[1]), Integer.parseInt(campos[2]));
                } else {
                    player.notifySimples("Voc\u00EA Perdeu");
                    game().notificaMudancaEstado("Voc\u00EA Perdeu");
                    game().notificaMudancaEstado("END-GAME");
                    game().perderdor(Integer.parseInt(campos[1]), Integer.parseInt(campos[2]));
                }
                break;
            case GAME_MUDANCA_ESTADO:
                game().notificaMudancaEstado(parametroes);
                break;
            case PLAYER_NOTIFICAO_SIMPLER:
                player.notifySimples(parametroes);
                break;
            case PLAYER_NOTIFICAO_TEMPO:
                String[] tratamentoInicio = parametroes.split("/");
                String comentarioInicio = tratamentoInicio[0];
                int tempo = Integer.parseInt(tratamentoInicio[1]);
                String comentarioFim = tratamentoInicio[2];
                player.notifySimplesComTempo(comentarioInicio, comentarioFim, tempo);
                break;
            case OPPONENT_DISCOUNTED:
                player.notifySimples(parametroes);
                game().notificaMudancaEstado("END-GAME");
                break;
            case BECAME_JUNIOR:
                player.notifyYouAJunior();
                oponnet.notifyYouASenior();
                break;
            case BECAME_SENIOR:
                player.notifyYouASenior();
                oponnet.notifyYouAJunior();
                break;
            case BECAME_NOT_TITLE:
                player.notifySemTitulo();
                oponnet.notifySemTitulo();
                break;
            case SCREAM_LOSER:
                player.notifyLoserScream();
                oponnet.notifyWinerScream();
                break;
            case SCREAM_WINNER:
                player.notifyWinerScream();
                oponnet.notifyLoserScream();
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
                Color corPlayer = gson.fromJson(parametroes, Color.class);
                player.setColor(corPlayer);
                break;
            case MY_POINTS:
                player.setPontos(Integer.parseInt(parametroes));
                break;
            case MY_PILESIZE:
                player.setPileSize(Integer.parseInt(parametroes));
                break;
            case MY_HAND:
                player.setHand(parametroes);

                break;
            case MY_POSITION:
                player.setPosition(parametroes);
                break;

            case OPONNET_COLOR:
                oponnet.setColor(gson.fromJson(parametroes, Color.class));
                break;
            case OPONNET_HAND:
                oponnet.setHand(parametroes);
                break;
            case OPONNET_PILESIZE:
                oponnet.setPosition(parametroes);
                break;
            case OPONNET_POINTS:
                oponnet.setPontos(Integer.parseInt(parametroes));
                break;
            case OPONNET_POSITION:
                oponnet.setPosition(parametroes);
                break;
            default:
                throw new RuntimeException(modelGet + "=" + parametroes);
        }
    }

}
