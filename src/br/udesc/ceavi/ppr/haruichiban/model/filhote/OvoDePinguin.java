package br.udesc.ceavi.ppr.haruichiban.model.filhote;

import br.udesc.ceavi.ppr.haruichiban.model.filhote.Filhote;
import br.udesc.ceavi.ppr.haruichiban.utils.Images;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Gustavo C Santos
 * @since 10/05/2019
 * 
 */
public class OvoDePinguin extends Filhote {

    public OvoDePinguin(float rotacao, Color cor) {
        super(rotacao, cor);
    }

    private static BufferedImage imagem;
    @Override
    public BufferedImage getImagem() throws IOException{
        if(imagem == null){
            imagem = ImageIO.read(new File(Images.OVO_PINGUIM));
        }
        return imagem;
    }

}
