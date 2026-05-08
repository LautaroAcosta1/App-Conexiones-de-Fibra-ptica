package servicio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import modelo.Localidad;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RepositorioLocalidades {

    private static final String ARCHIVO =
            "localidades.json";

    private Gson gson;

    public RepositorioLocalidades() {
        gson = new Gson();
    }

    public void guardar(
            List<Localidad> localidades)
            throws IOException {

        FileWriter writer =
                new FileWriter(ARCHIVO);

        gson.toJson(localidades, writer);

        writer.close();
    }

    public List<Localidad> cargar()
            throws IOException {

        FileReader reader =
                new FileReader(ARCHIVO);

        Type tipoLista =
                new TypeToken<
                        ArrayList<Localidad>>() {}.getType();

        List<Localidad> localidades =
                gson.fromJson(reader, tipoLista);

        reader.close();

        return localidades;
    }
}