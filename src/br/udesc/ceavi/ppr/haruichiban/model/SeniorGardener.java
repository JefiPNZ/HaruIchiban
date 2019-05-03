package br.udesc.ceavi.ppr.haruichiban.model;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class SeniorGardener implements TitleOfGardener {

    @Override
    public void throwFlower(ModelPlayer play) {
        //Codigo que vai colocar a flor no tabuleiro permitindo que esse escolha o local
    }

    @Override
    public void moveNenufares(ModelPlayer play) throws Exception {
        throw new Exception("The Play is a GardenerSenior, he can not move Nenufares");
    }

    @Override
    public void chooseNewDarkNenufares(ModelPlayer play) {
        //Codigo que permitir que o usuario escolha o novo nenufar escuro
    }

    @Override
    public void becomeUntitledGardener(ModelPlayer play) throws Exception {
        play.title = new UntitledGardener();
    }

    @Override
    public void becomeJuniorGardener(ModelPlayer play) throws Exception {
        throw new Exception("The Play is a GardenerSenior, he can not became a GradenerJunior");
    }

    @Override
    public void becomeSeniorGardener(ModelPlayer play) throws Exception {
        throw new Exception("The Play is already a GardenerSenior");
    }

}
