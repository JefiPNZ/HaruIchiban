package br.udesc.ceavi.ppr.haruichiban.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jeferson Penz
 */
public class TestPlayerController implements IPlayerController{

    @Override
    public int getPileSize() {
        return 5;
    }

    @Override
    public List<Object> getHand() {
        return Arrays.asList(1, 2, 3);
    }
    
}
