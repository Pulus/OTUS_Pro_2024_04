package ru.otus.dataprocessor;

import java.io.FileNotFoundException;
import java.util.List;
import ru.otus.model.Measurement;

public interface Loader {

    List<Measurement> load() throws FileNotFoundException;
}
