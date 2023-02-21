package view;

import java.util.Scanner;
import java.util.logging.Logger;

public class ConsoleViewImpl implements View {

    Scanner sc = new Scanner(System.in);
    Logger logger = Logger.getAnonymousLogger();

    @Override
    public void set(String string) {
        logger.info(string);
    }

    @Override
    public String get() {
        return sc.next();
    }
}
