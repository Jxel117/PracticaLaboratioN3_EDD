package models;

import java.util.Arrays;
import java.util.Random;
import controller.tda.list.LinkedList;

public class Pruebas {

    public static void main(String[] args) throws Exception {
        // Pruebas de algoritmos de ordenamiento
        probarAlgoritmosOrdenamiento();

        // Pruebas de algoritmos de búsqueda
        probarAlgoritmosBusqueda();
    }

    private static void probarAlgoritmosOrdenamiento() throws Exception {
        System.out.println("Pruebas de Algoritmos de Ordenamiento:");
        int[] tamanos = { 10000, 20000, 25000 };

        for (int tamano : tamanos) {
            LinkedList<Integer> listaOriginal = generarListaAleatoria(tamano);

            long tiempoqs = medirTiempo(listaOriginal, "qs", "ascendente");
            long tiempoMergeSort = medirTiempo(listaOriginal, "mergesort", "ascendente");
            long tiempoShellSort = medirTiempo(listaOriginal, "shellsort", "ascendente");

            System.out.println("Arreglo: " + tamano);
            System.out.println("Metdo QuickSort: " + tiempoqs + " ns");
            System.out.println("Mtodo MergeSort: " + tiempoMergeSort + " ns");
            System.out.println("Mtodo ShellSort: " + tiempoShellSort + " ns");
            System.out.println();
        }
    }

    private static void probarAlgoritmosBusqueda() {
        System.out.println("Pruebas de Algoritmos de Búsqueda:");
        int[] tamanos = { 10000, 20000, 25000 };

        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-10s%-20s%-20s%-20s%-20s%n", "Tamaño", "Secuencial (ns)", "Binaria (ns)",
                "Binaria (Ordenada) (ns)", "Secuencial Ordenado (ns)");
        System.out.println("---------------------------------------------------------------");

        for (int tamano : tamanos) {
            int[] arreglo = generarArregloAleatorio(tamano);

            long inicio = System.nanoTime();
            busquedaSecuencial(arreglo, arreglo[arreglo.length / 2]);
            long tiempoSecuencial = System.nanoTime() - inicio;

            inicio = System.nanoTime();
            busquedaBinaria(arreglo, arreglo[arreglo.length / 2]);
            long tiempoBinaria = System.nanoTime() - inicio;

            Arrays.sort(arreglo);

            inicio = System.nanoTime();
            busquedaBinaria(arreglo, arreglo[arreglo.length / 2]);
            long tiempoBinariaOrdenada = System.nanoTime() - inicio;

            inicio = System.nanoTime();
            busquedaSecuencial(arreglo, arreglo[arreglo.length / 2]);
            long tiempoSecuencialOrdenado = System.nanoTime() - inicio;

            System.out.printf("%-10d%-20d%-20d%-20d%-20d%n", tamano, tiempoSecuencial, tiempoBinaria,
                    tiempoBinariaOrdenada, tiempoSecuencialOrdenado);
        }

        System.out.println("---------------------------------------------------------------");
    }

    private static long medirTiempo(LinkedList<Integer> listaOriginal, String algoritmo, String orden) throws Exception {
        LinkedList<Integer> listaCopia = listaOriginal.clonar();
        long tiempoInicio = System.nanoTime();

        switch (algoritmo) {
            case "qs":
                listaCopia.qs(orden.equals("ascendente") ? 0 : 1);
                break;
            case "ms":
                listaCopia.ms(orden.equals("ascendente") ? 0 : 1);
                break;
            case "shs":
                listaCopia.shellsort("ordertype", orden.equals("ascendente") ? 1 : -1);
                break;
        }

        return System.nanoTime() - tiempoInicio;
    }

    private static LinkedList<Integer> generarListaAleatoria(int tamano) {
        LinkedList<Integer> lista = new LinkedList<>();
        Random aleatorio = new Random();

        for (int i = 0; i < tamano; i++) {
            lista.add(aleatorio.nextInt());
        }

        return lista;
    }

    private static int[] generarArregloAleatorio(int tamano) {
        int[] arreglo = new int[tamano];
        Random aleatorio = new Random();

        for (int i = 0; i < tamano; i++) {
            arreglo[i] = aleatorio.nextInt();
        }

        return arreglo;
    }

    private static int busquedaSecuencial(int[] arreglo, int objetivo) {
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] == objetivo) {
                return i;
            }
        }
        return -1;
    }

    private static int busquedaBinaria(int[] arreglo, int objetivo) {
        int izquierda = 0, derecha = arreglo.length - 1;

        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;

            if (arreglo[medio] == objetivo) {
                return medio;
            }

            if (arreglo[medio] < objetivo) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }

        return -1;
    }
}