package model.service;

import model.data.Toy;
import util.WriteReaderCSVImpl;

import java.util.ArrayList;
import java.util.List;

public class ToyService {

    private List<Toy> actualToys;
    private String config;

    public List<Toy> getActualToys() {
        return actualToys;
    }

    public void addToyToList(Toy toy) {
        this.actualToys.add(toy);
        WriteReaderCSVImpl wr = new WriteReaderCSVImpl();
        wr.updateDB(this.config, actualToys);
    }

    public ToyService(String config) {
        this.actualToys = new ArrayList<>();
        this.config = config;
        WriteReaderCSVImpl wr = new WriteReaderCSVImpl();
        String[] temp = wr.read(this.config).split("\n");

        if (temp.length > 1) {
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

    public Toy createToyFromDB(String[] param) {
        return new Toy(Integer.parseInt(param[0]),
                param[1],
                Integer.parseInt(param[2]),
                Float.parseFloat(param[3]));
    }

    public Toy newToy(String[] param) {
        Toy toy = createToyFromDB(param);
        this.actualToys.add(toy);
        if (Toy.getNumberOfID() < toy.getToyID()) {
            Toy.setNumberOfID(toy.getToyID());
        }
        WriteReaderCSVImpl wr = new WriteReaderCSVImpl();
        wr.updateDB(this.config, actualToys);
        return toy;
    }

    public boolean removeToy(int id) {
        for (Toy item :
                this.actualToys) {
            if (item.getToyID() == id) {
                this.actualToys.remove(item);
                WriteReaderCSVImpl wr = new WriteReaderCSVImpl();
                wr.updateDB(this.config, this.actualToys);
                return true;
            }
        }
        return false;
    }

    public void changeChance(int id, String param) {
        for (Toy toy :
                this.actualToys) {
            if (id == toy.getToyID()) {
                toy.setDropChance(Float.parseFloat(param));
                WriteReaderCSVImpl wr = new WriteReaderCSVImpl();
                wr.updateDB(this.config, this.actualToys);
                return;
            }
        }
    }

    public void decreaseQuantityToy(int id) {
        for (Toy item :
                this.actualToys) {
            if (item.getToyID() == id) {
                item.setQuantity(item.getQuantity() - 1);
                if (item.getQuantity() == 0) {
                    removeToy(item.getToyID());
                    return;
                }
                WriteReaderCSVImpl wr = new WriteReaderCSVImpl();
                wr.updateDB(this.config, this.actualToys);
                return;
            }
        }
    }

}
