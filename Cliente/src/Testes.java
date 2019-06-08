import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.awt.Color;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.gson.Gson;

import br.udesc.ceavi.ppr.haruichiban.control.requisicoes.get.*;
import br.udesc.ceavi.ppr.haruichiban.control.requisicoes.post.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Testes {

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

		assertEquals("I,ColorRequest", request.getRequest());
		assertEquals("java.awt.Color[r=255,g=210,b=65]",
				((Color) new Gson().fromJson(request.getResposta(), Color.class)).toString());
	}

	@Test
	public void T2requisicaoPositionRequest() {
		PositionRequest request = new PositionRequest(respostaServidor, servidor);

		request.enviar();

		assertEquals("I,PositionRequest", request.getRequest());

		assertEquals("TOP", request.getResposta());
	}

	// Requisição feita antes do caregamento dos dados
	@Test
	public void T3HandRequestInicial() {
		HandRequest request = new HandRequest(respostaServidor, servidor);

		request.enviar();

		assertEquals("I,HandRequest", request.getRequest());

		assertEquals("", request.getResposta());
	}

	// Requisição feita antes do caregamento dos dados
	@Test
	public void T4PileSizeRequest() {
		PileSizeRequest request = new PileSizeRequest(respostaServidor, servidor);

		request.enviar();

		assertEquals("I,PileSizeRequest", request.getRequest());

		assertEquals("" + 0, request.getResposta());
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
		assertEquals("E,HaveOponentRequest", request.getRequest());
		assertEquals("false", request.getResposta());
	}

	@Test
	public void T6ProduceDeckRequest() {
		ProduceDeckRequest request1 = new ProduceDeckRequest(respostaServidor, servidor);
		request1.enviar();
		assertEquals("I,ProduceDeckRequest", request1.getRequest());
		assertEquals("DeckProduzido", request1.getResposta());

		PileSizeRequest request2 = new PileSizeRequest(respostaServidor, servidor);
		request2.enviar();
		assertEquals("I,PileSizeRequest", request2.getRequest());
		assertEquals("" + 5, request2.getResposta());

		HandRequest request3 = new HandRequest(respostaServidor, servidor);
		request3.enviar();
		assertEquals("I,HandRequest", request3.getRequest());
		assertNotEquals("", request3.getResposta());
		
	}

	
}
