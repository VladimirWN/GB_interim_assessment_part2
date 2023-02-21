package model.service;

import config.Config;
import model.data.Toy;
import util.WriteReaderCSVImpl;

import java.util.ArrayList;
import java.util.List;

public class ToyService {

    private List<Toy> actualToys;

    public ToyService() {
        this.actualToys = new ArrayList<>();
        WriteReaderCSVImpl wr = new WriteReaderCSVImpl();
        String[] temp = wr.read(Config.path).split("\n");

        if (temp.length > 1){
            for (String item :
                    temp) {
                Toy toy = createToyFromDB(item.split(";"));
                actualToys.add(toy);
                if (Toy.getNumberOfID() < toy.getToyID()) {
                    Toy.setNumberOfID(toy.getToyID());
                }
            }
        }
    }

    public Toy createToyFromDB(String[] param){
        return new Toy(Integer.parseInt(param[0]),
                param[1],
                Integer.parseInt(param[2]),
                Float.parseFloat(param[3]));
    }

    public void newToy(String[] param){
        Toy toy = createToyFromDB(param);
        this.actualToys.add(toy);

        WriteReaderCSVImpl wr = new WriteReaderCSVImpl();
        wr.updateDB(Config.path, actualToys);
    }

}
