package br.udesc.ceavi.ppr.haruichiban.model;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class UntitledGardener implements TitleOfGardener {

    @Override
    public void throwFlower(ModelPlayer play) throws Exception {
        throw new Exception("The UntitledGardener can not throw Flower");
    }

    @Override
    public void moveNenufares(ModelPlayer play) throws Exception {
        throw new Exception("The UntitledGardener can not move Nenufares");
    }

    @Override
    public void chooseNewDarkNenufares(ModelPlayer play) throws Exception {
        throw new Exception("The UntitledGardener can choose New Dark Nenufares");
    }

    @Override
    public void becomeUntitledGardener(ModelPlayer play) throws Exception {
        throw new Exception("The Play is already a UntitledGardener");
    }

    @Override
    public void becomeJuniorGardener(ModelPlayer play) {
        play.title = new JuniorGardener();
    }

    @Override
    public void becomeSeniorGardener(ModelPlayer play) {
        play.title = new SeniorGardener();
    }

}
