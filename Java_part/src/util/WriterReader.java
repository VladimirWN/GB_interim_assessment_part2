package util;

import model.data.Toy;

import java.util.List;

public interface WriterReader {
    public void updateDB(String path, List<Toy> data);

    public String read(String path);
}
