package br.udesc.ceavi.ppr.haruichiban.cliente.control.channel;

import java.io.PrintWriter;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Saida
 *
 * @author Gustavo C
 */
public class PackageClientOutput implements Runnable {

    private BlockingDeque<String> saidas;
    private final PrintWriter saida;
    private final CommunicationPackage cPackage;

    public PackageClientOutput(PrintWriter entrada) {
        this.saida = entrada;
        saidas = new LinkedBlockingDeque<>();
        cPackage = new CommunicationPackage();
    }

    public synchronized void newGet(ModelGet get, String parametro) {
        cPackage.addGet(get, parametro);
        String saidaGet = cPackage.toJson();
        saidas.add(saidaGet);
    }

    public synchronized void newPost(ModelPost post, String parametro) {
        cPackage.addPost(post, parametro);
        String saidaPost = cPackage.toJson();
        saidas.add(saidaPost);
    }

    @Override
    public void run() {
        try {
            String cPacket = null;
            //enquanto existe um novo comando, lembrando take() bloqueia
            while ((cPacket = saidas.take()) != null) {
                saida.println(cPacket);
                saida.flush();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        saida.close();
        saidas = null;
        cPackage.close();
        try {
            finalize();
        } catch (Throwable ex) {
            Logger.getLogger(PackageClientOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
