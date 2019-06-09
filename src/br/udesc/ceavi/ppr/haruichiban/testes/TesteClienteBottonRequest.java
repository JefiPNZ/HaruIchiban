package br.udesc.ceavi.ppr.haruichiban.testes;

import com.google.gson.Gson;

import br.udesc.ceavi.ppr.haruichiban.control.RequestSocket;
import br.udesc.ceavi.ppr.haruichiban.model.Product;
import br.udesc.ceavi.ppr.haruichiban.model.Request;

import java.awt.Color;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Gustavo C
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteClienteBottonRequest {

	private static Socket cliente;
	private static Scanner respostaServidor;
	private static PrintWriter servidor;
	private static RequestSocket request;

	@BeforeClass
	public static void iniciarSocket() throws Exception {
		cliente = new Socket("127.0.0.1", 60000);
		respostaServidor = new Scanner(cliente.getInputStream());
		servidor = new PrintWriter(cliente.getOutputStream(), true);
		request = new RequestSocket(respostaServidor, servidor);
	}

	@AfterClass
	public static void fechandoSocket() throws Exception {
		respostaServidor.close();
		servidor.close();
		cliente.close();
	}

	@Test
	public void T1requisicaoColorRequest() {
		request.newRequest(Request.MY_COLOR);

		request.enviar();

//		Assert.assertEquals("I,ColorRequest", request.getRequest());
		Assert.assertEquals("java.awt.Color[r=255,g=15,b=35]",
				((Color) new Gson().fromJson(request.getResposta(), Color.class)).toString());
	}

	@Test
	public void T2requisicaoPositionRequest() {
		request.newRequest(Request.MY_POSITION);
		
		request.enviar();

//		Assert.assertEquals("I,PositionRequest", request.getRequest());

		Assert.assertEquals("BOTTON", request.getResposta());
	}

	// Requisição feita antes do caregamento dos dados
	@Test
	public void T3HandRequestInicial() {
		request.newRequest(Request.MY_HAND);
		
		request.enviar();

//		Assert.assertEquals("I,HandRequest", request.getRequest());

		Assert.assertEquals("", request.getResposta());
	}

	// Requisição feita antes do caregamento dos dados
	@Test
	public void T4PileSizeRequest() {
		request.newRequest(Request.MY_PILESIZE);
		
		request.enviar();

//		Assert.assertEquals("I,PileSizeRequest", request.getRequest());

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
		request.newRequest(Request.GAME_HAVEOPONENT);
		request.enviar();
//		Assert.assertEquals("E,HaveOponentRequest", request.getRequest());
		Assert.assertEquals("true", request.getResposta());
	}

	@Test
	public void T6ProduceDeckRequest() {
		request.newProduct(Product.MY_PRODUCEMYDECK);
		request.enviar();
//		Assert.assertEquals("I,ProduceDeckRequest", request.getRequest());
		Assert.assertEquals("DeckProduzido", request.getResposta());

		request.newRequest(Request.MY_PILESIZE);
		request.enviar();
//		Assert.assertEquals("I,PileSizeRequest", request.getRequest());
		Assert.assertEquals("" + 5, request.getResposta());

		request.newRequest(Request.MY_HAND);
		request.enviar();
//		Assert.assertEquals("I,HandRequest", request.getRequest());
		Assert.assertNotEquals("", request.getResposta());

	}

}
