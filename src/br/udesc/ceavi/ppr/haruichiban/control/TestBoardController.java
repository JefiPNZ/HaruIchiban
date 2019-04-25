package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.utils.Images;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Jeferson Penz
 */
public class TestBoardController implements IBoardController {

    @Override
    public BufferedImage getImage(int row, int col) {
        try {
            return ImageIO.read(new File(Images.PECA_TABULEIRO));
        } catch (IOException ex) {
            return null;
        }
    }
    
}
