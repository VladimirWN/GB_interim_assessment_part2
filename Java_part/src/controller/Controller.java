package controller;

import config.Config;
import model.data.Toy;
import model.service.GiftQueue;
import model.service.ToyService;
import util.WriteReaderCSVImpl;
import view.ConsoleViewImpl;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    View view;

    public Controller() {
        this.view = new ConsoleViewImpl();
    }

    public void start(){
        ToyService toyService = new ToyService(Config.stockPath);
        GiftQueue giftQueue = new GiftQueue();
        List<String> prizeList = new ArrayList<>();
        WriteReaderCSVImpl wr = new WriteReaderCSVImpl();

        Toy tempToy = new Toy();
        while (true){
            view.set("1 - список игрушек в стоке\n" +
                    "2 - добавить игрушку в сток\n" +
                    "3 - розыгрышь (добавить игрушку в очередь на выдачу)\n" +
                    "4 - выдать приз из очереди\n" +
                    "0 - завершить работу\n");
            String key = view.get();
            switch (key) {
                case "1":
                    view.set("Всего позиций в стоке: " + toyService.getActualToys().size());
                    printAllList(toyService.getActualToys());
                    break;
                case "2":
                    String[] newParam = newToy();
                    if (newParam != null){
                        tempToy = toyService.newToy(newParam);
                        view.set("Добавлена игрушка: " + tempToy);
                    }
                    break;
                case "3":
                    if (toyService.getActualToys().size() == 0){
                        view.set("В стоке нет игрушек.");
                        break;
                    }
                    tempToy = giftQueue.fillingQueue(toyService.getActualToys());
                    toyService.decreaseQuantityToy(tempToy.getToyID());
                    view.set("В список призов добавлено: " + tempToy);
                    break;
                case "4":
                    if (giftQueue.getCurrentQueue().isEmpty()){
                        view.set("Очередь подарков пуста.");
                        break;
                    }
                    tempToy = giftQueue.getPrize();
                    view.set("Выдана игрушка: " + tempToy);
                    wr.writeCSV(Config.prizePath, String.format("id:%d Name: %s\n",
                            tempToy.getToyID(), tempToy.getName()));
                    break;
                case "0":
                    view.set("Работа приложения завершена!");
                    System.exit(0);
                    break;
                default:
                    view.set("Введено неверное значение!");
                    break;
            }
        }
    }

    public void printAllList(List<Toy> toys){
        List<String> toPrint = new ArrayList<>();
        for (Toy toy :
                toys) {
            toPrint.add(toy.toString());
            toPrint.add("\n");
        }
        view.set(toPrint.toString());
    }


    public String[] newToy(){
        view.set("Введите наименование новой игрушки: ");
        String name = view.get();
        if (name == null || name.trim().isEmpty()){
            view.set("Наименование не может быть пустым, отмена создания.");
            return null;
        }

        view.set("Введите количество, которое будет добавлено в сток: ");
        String quantity = view.get();
        int quantityInt = 0;
        try {
            quantityInt = Integer.parseInt(quantity);
        }catch (Exception e){
            view.set("Должно быть введено только целое число!");
            return null;
        }
        if (quantityInt <= 0){
            view.set("Количество может быть только больше 0!");
            return null;
        }

        view.set("Введите шанс выпадения в формате 0.%% ");
        String chance = view.get();
        float chanceFloat = 0.0f;
        try {
            chanceFloat = Float.parseFloat(chance);
        } catch (Exception e){
            view.set("Неверный формат числа!");
            return null;
        }
        if ((int)chanceFloat > 0 || chanceFloat == 0){
            view.set("Шанс выпадения должен быть меньше 1 и больше 0!");
            return null;
        }

        String result = String.valueOf(Toy.getNumberOfID() + 1) + ";"
                + name + ";" + quantity + ";" + chance;
        return result.split(";");
    }
}
