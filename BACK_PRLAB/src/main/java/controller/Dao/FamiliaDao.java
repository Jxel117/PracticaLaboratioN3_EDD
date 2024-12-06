package controller.Dao;

import models.Familia;

import java.util.Arrays;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;
import controller.tda.list.Node;
import models.Familia;

public class FamiliaDao extends AdapterDao<Familia> {
    private Familia familia = new Familia();
    private LinkedList listAll;

    public FamiliaDao() {
        super(Familia.class);
    }

    public Familia getFamilia() { // Obtiene la familia
        if (familia == null) {
            familia = new Familia(); // En caso de que la familia sea nula, crea una nueva instancia de Familia
        }
        return this.familia; // Devuelve la familia
    }

    public void setFamilia(Familia familia) { // Establece la familia con un objeto Familia
        this.familia = familia; // Asigna el objeto Familia a la variable familia
    }

    public LinkedList getListAll() { // Obtiene la lista de objetos
        if (listAll == null) { // Si la lista es nula
            this.listAll = listAll(); // Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; // Devuelve la lista de objetos de la variable listAll
    }

    public Boolean save() throws Exception { // Guarda la variable familia en la lista de objetos
        Integer id = getListAll().getSize() + 1; // Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
        familia.setId(id); // Asigna el id a familia
        this.persist(this.familia); // Guarda la familia en la lista de objetos LinkedList y en el archivo JSON
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se guardó correctamente
    }

    public Boolean update() throws Exception { // Actualiza el nodo Familia en la lista de objetos
        this.merge(getFamilia(), getFamilia().getId() - 1); // Envia la familia a actualizar con su index
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true;
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto Familia por su índice
        this.supreme(index);
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se eliminó correctamente
    }

    public int contarFamiliasConGenerador() {
        int contador = 0;
        LinkedList<Familia> familias = listAll(); // lista todas las familias
        Familia[] familiasArray = familias.toArray(); // Convierte la lista enlazada en un arreglo

        for (Familia familia : familiasArray) { // Usa el bucle for-each en el arreglo
            if (familia.getTieneGenerador()) { // Verifica si la familia tiene generador
                contador++;
            }
        }
        return contador;
    }

    public LinkedList order(Integer type_order, String atributo) {
        LinkedList listita = listAll();
        if (!listAll.isEmpty()) {
            Familia[] lista = (Familia[]) listita.toArray();
            listita.reset();
            for (int i = 1; i < lista.length; i++) {
                Familia aux = lista[i];
                int j = i - 1;
                while (j >= 0 && (verify(lista[j], aux, type_order, atributo))) {
                    lista[j + 1] = lista[j--];
                }
                lista[j + 1] = aux;
            }
            listita.toList(lista);
        }
        return listita;
    }

    // Verifica si el atributo de un objeto es mayor o menor que otro objeto
    private Boolean verify(Familia a, Familia b, Integer type_order, String atributo) {
        if (a == null || b == null) {
            System.out.println("Escribe un objeto valido.");
            return false;
        }

        System.out.println("Verificando atributo... " + atributo);
        System.out.println("Familia A: " + a);
        System.out.println("Familia B: " + b);

        String atributoA = "";
        String atributoB = "";

        switch (atributo.toLowerCase()) {
            case "apellidopaterno":
                atributoA = a.getApellidoPaterno() != null ? a.getApellidoPaterno().trim() : null;
                atributoB = b.getApellidoPaterno() != null ? b.getApellidoPaterno().trim() : null;
                break;
            case "apellidomaterno":
                atributoA = a.getApellidoMaterno() != null ? a.getApellidoMaterno().trim() : null;
                atributoB = b.getApellidoMaterno() != null ? b.getApellidoMaterno().trim() : null;
                break;
            case "canton":
                atributoA = a.getCanton() != null ? a.getCanton().trim() : null;
                atributoB = b.getCanton() != null ? b.getCanton().trim() : null;
                break;
            default:
                System.out.println("Not found...");
                return false;
        }

        if (atributoA == null || atributoB == null) {
            System.out.println("Uno de los atributos es nulo: AtributoA = " + atributoA + ", AtributoB = " + atributoB);
            return false;
        }

        int result = atributoA.compareToIgnoreCase(atributoB);

        System.out.println("Resultado de la Comparacion: " + result);

        if (type_order == 1) { // A - Z
            return result > 0;
        } else { // Z - A
            return result < 0;
        }
    }

    // qs
    public LinkedList<Familia> orderQuick(Integer type_order, String atributo) {
        LinkedList<Familia> listita = new LinkedList<>();

        // Crear una nueva lista con los elementos actuales
        for (int i = 0; i < listAll().getSize(); i++) {
            try {
                listita.add(listAll().get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!listita.isEmpty()) {
            Familia[] lista = (Familia[]) listita.toArray(); // Convertir la lista enlazada a un arreglo
            qs(lista, 0, lista.length - 1, type_order, atributo); // Aplicar qs
            listita = new LinkedList<>(); // Reiniciar la lista enlazada
            listita.toList(lista); // Convertir el arreglo ordenado de vuelta a la lista enlazada
        }

        return listita;
    }

    // Método qs para un arreglo de Familias
    private void qs(Familia[] lista, int low, int high, Integer type_order, String atributo) {
        if (low < high) {
            int pivotIndex = partition(lista, low, high, type_order, atributo); // Particionar el arreglo
            qs(lista, low, pivotIndex - 1, type_order, atributo); // Ordenar la parte izquierda
            qs(lista, pivotIndex + 1, high, type_order, atributo); // Ordenar la parte derecha
        }
    }

    // Método de partición para el qs
    private int partition(Familia[] lista, int low, int high, Integer type_order, String atributo) {
        Familia pivot = lista[high]; // Elegir el último elemento como pivote
        int i = low - 1; // Índice del menor elemento

        for (int j = low; j < high; j++) {
            if (verify(lista[j], pivot, type_order, atributo)) {
                i++;
                Familia temp = lista[i];
                lista[i] = lista[j];
                lista[j] = temp;
            }
        }

        // Colocar el pivote en su lugar correcto
        Familia temp = lista[i + 1];
        lista[i + 1] = lista[high];
        lista[high] = temp;

        return i + 1;
    }

    public LinkedList<Familia> orderMerge(Integer type_order, String atributo) {
        LinkedList<Familia> listita = new LinkedList<>();

        // Crear una nueva lista con los elementos actuales
        for (int i = 0; i < listAll().getSize(); i++) {
            try {
                listita.add(listAll().get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!listita.isEmpty()) {
            Familia[] lista = (Familia[]) listita.toArray(); // Convertir a arreglo
            lista = mergesort(lista, atributo, type_order); // Aplicar MergeSort
            listita = new LinkedList<>(); // Reiniciar la lista enlazada
            listita.toList(lista); // Convertir el arreglo ordenado de vuelta a la lista enlazada
        }

        return listita;
    }

    // Método MergeSort para ordenar un arreglo de Familias
    private Familia[] mergesort(Familia[] lista, String atributo, Integer type_order) {
        if (lista.length <= 1) {
            return lista;
        }

        int mitad = lista.length / 2;
        Familia[] izq = Arrays.copyOfRange(lista, 0, mitad); // Dividir en parte izquierda
        Familia[] dere = Arrays.copyOfRange(lista, mitad, lista.length); // Dividir en parte derecha

        izq = mergesort(izq, atributo, type_order); // Ordenar la parte izquierda
        dere = mergesort(dere, atributo, type_order); // Ordenar la parte derecha

        return merge(izq, dere, atributo, type_order); // Combinar las partes ordenadas
    }

    // Método para combinar dos subarreglos ordenados
    private Familia[] merge(Familia[] izq, Familia[] dere, String atributo, Integer type_order) {
        Familia[] result = new Familia[izq.length + dere.length];
        int i = 0, j = 0, k = 0;

        while (i < izq.length && j < dere.length) {
            if (verify(izq[i], dere[j], type_order, atributo)) {
                result[k++] = izq[i++];
            } else {
                result[k++] = dere[j++];
            }
        }

        while (i < izq.length) {
            result[k++] = izq[i++];
        }

        while (j < dere.length) {
            result[k++] = dere[j++];
        }

        return result;
    }

    public LinkedList<Familia> orderShell(Integer type_order, String atributo) {
        LinkedList<Familia> listita = new LinkedList<>();

        // Crear una nueva lista con los elementos actuales
        for (int i = 0; i < listAll().getSize(); i++) {
            try {
                listita.add(listAll().get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!listita.isEmpty()) {
            Familia[] lista = (Familia[]) listita.toArray(); 
            lista = shellSort(lista, atributo, type_order); 
            listita = new LinkedList<>();
            listita.toList(lista); 
        }

        return listita;
    }

    private Familia[] shellSort(Familia[] lista, String atributo, Integer type_order) {
        int n = lista.length;

        // Inicialización del intervalo de Shell Sort
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Familia temp = lista[i];
                int j;

                // Comparar elementos en el intervalo
                for (j = i; j >= gap && verify(lista[j - gap], temp, type_order, atributo); j -= gap) {
                    lista[j] = lista[j - gap];
                }
                lista[j] = temp;
            }
        }
        return lista;
    }

    // Metodos de busqueda
    // Buscar por apellido paterno
    public LinkedList<Familia> buscarxapellidoPa(String texto) {
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();
        if (!listita.isEmpty()) {
            Familia[] aux = listita.toArray();

            for (Familia familia : aux) {
                if (familia != null && familia.getApellidoPaterno() != null &&
                        familia.getApellidoPaterno().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(familia);
                }

            }
        }
        return lista;
    }

    // buscar por apellido materno
    public LinkedList<Familia> buscarxapellidoMa(String texto) {
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();
        if (!listita.isEmpty()) {
            Familia[] aux = listita.toArray();

            for (Familia familia : aux) {
                if (familia != null && familia.getApellidoMaterno() != null &&
                        familia.getApellidoMaterno().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(familia);
                }
            }
        }
        return lista;
    }

    // buscar por canton
    public LinkedList<Familia> buscarxcanton(String texto) {
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();
        if (!listita.isEmpty()) {
            Familia[] aux = listita.toArray();
            for (Familia familia : aux) {
                if (familia != null && familia.getCanton() != null &&
                        familia.getCanton().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(familia);
                }
            }
        }
        return lista;
    }

    // Busqueda Lineal
    public LinkedList<Familia> busApPaLin(String texto) {
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();

        if (!listita.isEmpty()) {
            for (Familia familia : listita.toArray()) {
                if (familia != null && familia.getApellidoPaterno() != null &&
                        familia.getApellidoPaterno().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(familia);
                }
            }
        }
        return lista;
    }

    // Busqueda binaria
    public LinkedList<Familia> busApellidoPaBin(String texto) {
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();
    
        try {
            // Ordenar la lista usando shellsort por apellidoPaterno en orden ascendente
            listita.shellsort("apellidoPaterno", 1);
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
            return lista; // Devuelve una lista vacía si hay un error
        }
    
        if (!listita.isEmpty()) {
            // Convertir LinkedList a arreglo
            Familia[] aux = new Familia[listita.getSize()];
    
            int izq = 0, dere = aux.length - 1;
            while (izq <= dere) {
                int mitad = izq + (dere - izq) / 2;
                Familia familiaMid = aux[mitad];
    
                if (familiaMid != null && familiaMid.getApellidoPaterno() != null) {
                    String nombreMid = familiaMid.getApellidoPaterno().toLowerCase();
    
                    if (nombreMid.contains(texto.toLowerCase())) {
                        lista.add(familiaMid);
    
                        // Buscar elementos adyacentes que también coincidan
                        int i = mitad - 1;
                        while (i >= 0 && aux[i].getApellidoPaterno().toLowerCase().contains(texto.toLowerCase())) {
                            lista.add(aux[i]);
                            i--;
                        }
                        int j = mitad + 1;
                        while (j < aux.length && aux[j].getApellidoPaterno().toLowerCase().contains(texto.toLowerCase())) {
                            lista.add(aux[j]);
                            j++;
                        }
                        break;
                    } else if (nombreMid.compareTo(texto.toLowerCase()) < 0) {
                        izq = mitad + 1;
                    } else {
                        dere = mitad - 1;
                    }
                } else {
                    break;
                }
            }
        }
        return lista;
    }
    
    // Busqueda por canton lineal
    public LinkedList<Familia> busCantonLin(String texto) {
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();

        if (!listita.isEmpty()) {
            for (Familia familia : listita.toArray()) {
                if (familia != null && familia.getCanton() != null &&
                        familia.getCanton().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(familia);
                }
            }
        }
        return lista;
    }

    // Busqueda por canton binaria
    public LinkedList<Familia> busCantonBin(String texto) {
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();

        if (!listita.isEmpty()) {
            Familia[] aux = new Familia[listita.getSize()];
            int index = 0;
            for (Familia familia : listita.toArray()) {
                aux[index++] = familia;
            }
            int izq = 0;
            int dere = aux.length - 1;

            while (izq <= dere) {
                int mitad = izq + (dere - izq) / 2;
                Familia familiaMid = aux[mitad];

                if (familiaMid != null && familiaMid.getCanton() != null) {
                    String nombreMid = familiaMid.getCanton().toLowerCase();

                    if (nombreMid.contains(texto.toLowerCase())) {
                        lista.add(familiaMid);
                        int i = mitad - 1;
                        while (i >= 0 && aux[i].getCanton().toLowerCase().contains(texto.toLowerCase())) {
                            lista.add(aux[i]);
                            i--;
                        }
                        int j = mitad + 1;
                        while (j < aux.length && aux[j].getCanton().toLowerCase().contains(texto.toLowerCase())) {
                            lista.add(aux[j]);
                            j++;
                        }
                        break;
                    } else if (nombreMid.compareTo(texto.toLowerCase()) < 0) {
                        izq = mitad + 1;
                    } else {
                        dere = mitad - 1;
                    }
                } else {
                    break;
                }
            }
        }
        return lista;
    }

    // Busqueda por apellido materno lineal
    public LinkedList<Familia> busApMaLin(String texto) {
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();

        if (!listita.isEmpty()) {
            for (Familia familia : listita.toArray()) {
                if (familia != null && familia.getApellidoMaterno() != null &&
                        familia.getApellidoMaterno().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(familia);
                }
            }
        }
        return lista;
    }

    // Busqueda por apellido materno binaria
    public LinkedList<Familia> busApMaBin(String texto) {
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();

        if (!listita.isEmpty()) {
            Familia[] aux = new Familia[listita.getSize()];
            int index = 0;
            for (Familia familia : listita.toArray()) {
                aux[index++] = familia;
            }
            int izq = 0;
            int dere = aux.length - 1;

            while (izq <= dere) {
                int mitad = izq + (dere - izq) / 2;
                Familia familiaMid = aux[mitad];

                if (familiaMid != null && familiaMid.getApellidoMaterno() != null) {
                    String nombreMid = familiaMid.getApellidoMaterno().toLowerCase();

                    if (nombreMid.contains(texto.toLowerCase())) {
                        lista.add(familiaMid);
                        int i = mitad - 1;
                        while (i >= 0 && aux[i].getApellidoMaterno().toLowerCase().contains(texto.toLowerCase())) {
                            lista.add(aux[i]);
                            i--;
                        }
                        int j = mitad + 1;
                        while (j < aux.length
                                && aux[j].getApellidoMaterno().toLowerCase().contains(texto.toLowerCase())) {
                            lista.add(aux[j]);
                            j++;
                        }
                        break;
                    } else if (nombreMid.compareTo(texto.toLowerCase()) < 0) {
                        izq = mitad + 1;
                    } else {
                        dere = mitad - 1;
                    }
                } else {
                    break;
                }
            }
        }
        return lista;
    }
}
