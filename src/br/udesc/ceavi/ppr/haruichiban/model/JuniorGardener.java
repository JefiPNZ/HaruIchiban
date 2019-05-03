package br.udesc.ceavi.ppr.haruichiban.model;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class JuniorGardener implements TitleOfGardener {

    @Override
    public void throwFlower(ModelPlayer play) {
        //Codigo para colocar a flor no local dda nenufare escura
    }

    @Override
    public void moveNenufares(ModelPlayer play) {
        //Codigo que permite ao jogador mover os nenufares no tabuleiro
    }

    @Override
    public void chooseNewDarkNenufares(ModelPlayer play) throws Exception {
        throw new Exception("The Play is already a GardenerJunior, he can not choose New Dark Nenufares");
    }

    @Override
    public void becomeUntitledGardener(ModelPlayer play) throws Exception {
        play.title = new UntitledGardener();
    }

    @Override
    public void becomeJuniorGardener(ModelPlayer play) throws Exception {
        throw new Exception("The Play is already a GardenerJunior");
    }

    @Override
    public void becomeSeniorGardener(ModelPlayer play) throws Exception {
        throw new Exception("The Play is a GardenerJunior, he can not became a SeniorGradener");
    }
}
