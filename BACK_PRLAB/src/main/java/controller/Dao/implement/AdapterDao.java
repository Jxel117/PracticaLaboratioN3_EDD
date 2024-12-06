package controller.Dao.implement;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import com.google.gson.Gson;
import controller.tda.list.LinkedList;

public class AdapterDao<T> implements InterfazDao<T> {
    private Class<T> clazz;
    protected Gson g;
    private LinkedList<T> dataList; // Lista de datos

    public static String filePath = "src/main/java/Data/";

    public AdapterDao(Class<T> clazz) {
        this.clazz = clazz;
        this.g = new Gson();
        this.dataList = new LinkedList<>(); // Inicializa la lista
    }

    public T get(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        if (!list.isEmpty()) {
            T[] matriz = list.toArray();
            return matriz[id - 1];
        }
        return null; 
    }

    public LinkedList<T> listAll() {
        try {
            String data = readFile();
            T[] matrix = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            dataList.toList(matrix); // Llena dataList con los datos leídos
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList; // Retorna la lista que ahora contiene los datos
    }

    public void merge(T object, Integer index) throws Exception {
        LinkedList<T> list = listAll();
        list.update(object, index);
        String info = g.toJson(list.toArray());
        saveFile(info);
    }

    public void persist(T object) throws Exception {
        if (object == null) {
            throw new IllegalArgumentException("El objeto a guardar no puede ser nulo");
        }
    
        // En lugar de imprimir directamente el objeto, imprime un mensaje más general.
        System.out.println("Guardando objeto...");
    
        LinkedList<T> list = listAll(); // Obtener la lista existente
        if (list == null) {
            list = new LinkedList<>(); // Asegúrate de inicializar la lista
        }
        list.add(object);
    
        // Aquí también se evita imprimir directamente la lista serializada
        String info = g.toJson(list.toArray());
        System.out.println("Escribiendo datos al archivo...");
        saveFile(info);
    }
    

    private String readFile() throws Exception {
        File file = new File(filePath + clazz.getSimpleName() + ".json");

        if (!file.exists()) {
            System.out.println("El archivo no existe, creando uno nuevo: " + file.getAbsolutePath());
            saveFile("[]");
        }

        StringBuilder sb = new StringBuilder();
        try (Scanner in = new Scanner(new FileReader(file))) {
            while (in.hasNextLine()) {
                sb.append(in.nextLine()).append("\n");
            }
        }
        return sb.toString().trim();
    }
    
    protected void saveFile(String data) throws Exception {
        File file = new File(filePath + clazz.getSimpleName() + ".json");
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            System.out.println("Creando el archivo JSON: " + file.getAbsolutePath());
            file.createNewFile();
        }

        try (FileWriter f = new FileWriter(file)) {
            f.write(data);
            f.flush();
        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public Boolean supreme(int index) throws Exception {
        LinkedList<T> list = listAll(); //Invoca el método listAll() para obtener la lista de objetos
        list.remove(index); //Elimina el objeto en la posición index
        String info = g.toJson(list.toArray()); //Convierte la lista en un String JSON
        saveFile(info); //Guarda el String JSON en un archivo
        return true; //Retorna verdadero si se eliminó correctamente
    }
}

