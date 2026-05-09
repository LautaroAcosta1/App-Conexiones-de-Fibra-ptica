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

    public void guardar(List<Localidad> localidades) throws IOException {
        // carga lo que habia antes
        List<Localidad> existentes = new ArrayList<>();
        try {
            existentes = cargar();
            if (existentes == null) {
                existentes = new ArrayList<>();
            }
        } catch (IOException e) {
            // si no existia el archivo arranca vacio
        }
        // agrega las nuevas sin duplicar
        for (Localidad nueva : localidades) {
            if (!existentes.contains(nueva)) {
                existentes.add(nueva);
            }
        }
        // guarda todo junto
        FileWriter writer = new FileWriter(ARCHIVO);
        gson.toJson(existentes, writer);
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