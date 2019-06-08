import br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get.ColorRequest;
import br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get.HandRequest;
import br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get.HaveOponentRequest;
import br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get.PileSizeRequest;
import br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get.PositionRequest;
import br.udesc.ceavi.ppr.haruichiban.control.requisicoes.post.ProduceDeckRequest;
import com.google.gson.Gson;
import java.awt.Color;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Gustavo C
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(value={
    ColorRequest.class,
    HandRequest.class,
    HaveOponentRequest.class,
    PileSizeRequest.class,
    PositionRequest.class,
    ProduceDeckRequest.class,
    Gson.class,
    Color.class,
    PrintWriter.class,
    Socket.class,
    Scanner.class
})
public class NewTestSuite {

    private static Socket cliente;
    private static Scanner respostaServidor;
    private static PrintWriter servidor;
    @BeforeClass
    public static void iniciarSocket() throws Exception {
        cliente = new Socket("127.0.0.1", 60000);
        respostaServidor = new Scanner(cliente.getInputStream());
        servidor = new PrintWriter(cliente.getOutputStream(), true);
    }

    @AfterClass
    public static void fechandoSocket() throws Exception {
        respostaServidor.close();
        servidor.close();
        cliente.close();
    }

    @Test
    public void T1requisicaoColorRequest() {
        ColorRequest request = new ColorRequest(respostaServidor, servidor);

        request.enviar();

        Assert.assertEquals("I,ColorRequest", request.getRequest());
        Assert.assertEquals("java.awt.Color[r=255,g=210,b=65]",
                ((Color) new Gson().fromJson(request.getResposta(), Color.class)).toString());
    }

    @Test
    public void T2requisicaoPositionRequest() {
        PositionRequest request = new PositionRequest(respostaServidor, servidor);

        request.enviar();

        Assert.assertEquals("I,PositionRequest", request.getRequest());

        Assert.assertEquals("TOP", request.getResposta());
    }

    // Requisição feita antes do caregamento dos dados
    @Test
    public void T3HandRequestInicial() {
        HandRequest request = new HandRequest(respostaServidor, servidor);

        request.enviar();

        Assert.assertEquals("I,HandRequest", request.getRequest());

        Assert.assertEquals("", request.getResposta());
    }

    // Requisição feita antes do caregamento dos dados
    @Test
    public void T4PileSizeRequest() {
        PileSizeRequest request = new PileSizeRequest(respostaServidor, servidor);

        request.enviar();

        Assert.assertEquals("I,PileSizeRequest", request.getRequest());

        Assert.assertEquals("" + 0, request.getResposta());
    }

//	@Test
//	public void testandoComBoardParametroRequest() {
//		BoardRequest request = new BoardRequest(respostaServidor, servidor);
//		request.addParametro("y=1");
//		request.addParametro("x=1");
//		request.enviar();
//
//		assertEquals("E,BoardRequest{y=1,x=1}", request.getRequest());
//		assertEquals("NADA", request.getResposta());
//	}
    @Test
    public void T5HaveOponentRequest() {
        HaveOponentRequest request = new HaveOponentRequest(respostaServidor, servidor);
        request.enviar();
        Assert.assertEquals("E,HaveOponentRequest", request.getRequest());
        Assert.assertEquals("false", request.getResposta());
    }

    @Test
    public void T6ProduceDeckRequest() {
        ProduceDeckRequest request1 = new ProduceDeckRequest(respostaServidor, servidor);
        request1.enviar();
        Assert.assertEquals("I,ProduceDeckRequest", request1.getRequest());
        Assert.assertEquals("DeckProduzido", request1.getResposta());

        PileSizeRequest request2 = new PileSizeRequest(respostaServidor, servidor);
        request2.enviar();
        Assert.assertEquals("I,PileSizeRequest", request2.getRequest());
        Assert.assertEquals("" + 5, request2.getResposta());

        HandRequest request3 = new HandRequest(respostaServidor, servidor);
        request3.enviar();
        Assert.assertEquals("I,HandRequest", request3.getRequest());
        Assert.assertNotEquals("", request3.getResposta());

    }

}
