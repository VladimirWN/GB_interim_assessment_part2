package util;

import model.data.Toy;

import java.io.*;
import java.util.List;

public class WriteReaderCSVImpl implements WriterReader {
    @Override
    public void updateDB(String path, List<Toy> data) {
        File file = new File(path);

        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (Toy item :
                    data) {
                StringBuilder sb = new StringBuilder();
                sb.append(item.getToyID()).append(";")
                        .append(item.getName()).append(";")
                        .append(item.getQuantity()).append(";")
                        .append(item.getDropChance()).append("\n");
                printWriter.write(sb.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String read(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            while (bufferedReader.ready()) {
                sb.append(bufferedReader.readLine()).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeCSV(String path, String data) {
        File file = new File(path);

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(file, true))) {
            printWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
