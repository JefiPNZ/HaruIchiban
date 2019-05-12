package br.udesc.ceavi.ppr.haruichiban.model.folha;

import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;
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
public class FolhaSeca extends Folha {

    public FolhaSeca(float rotacao) {
        super(rotacao);
    }

    private static BufferedImage imagem;
    @Override
    public BufferedImage getImagem() throws IOException{
        if(imagem == null){
            imagem = ImageIO.read(new File(Images.FOLHA_SECA));
        }
        return imagem;
    }

    @Override
    public Color getCor() {
        return this.isEscura() ? new Color(75, 128, 195) : new Color(176, 206, 255);
    }

}
