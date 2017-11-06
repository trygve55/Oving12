package com.trygve.oving3;

/**
 * Created by Trygve on 28.08.2017.
 */
public class Sort {
    public static int[] bubleSort(int[] array) {
        return bubleSort(array, array.length-1, 0);
    }

    public static int[] bubleSort(int[] array, int high, int low) {
        if (array == null || array.length <= 1) return array;

        int pos = low;
        boolean changed = true;
        while (changed) {
            pos = 0;
            changed = false;
            while (pos < high) {
                if (array[pos] > array[pos + 1]) {
                    int help = array[pos];
                    array[pos] = array[pos + 1];
                    array[pos + 1] = help;
                    changed = true;
                }
                pos++;
            }
        }

        return array;
    }

    public static int[] selectionSort(int[] array) {
        if (array == null || array.length <= 1) return array;
        int[] returnArray = new int[array.length];
        for (int i = 0; i < array.length;i++) returnArray[i] = array[i];

        for (int i = 0; i < returnArray.length - 2;i++) {
            int lowestNumPos = i;
            for (int j = i + 1; j < returnArray.length;j++) {
                if (returnArray[j] < returnArray[lowestNumPos]) {
                    lowestNumPos = j;
                }
            }

            int help = returnArray[i];
            returnArray[i] = returnArray[lowestNumPos];
            returnArray[lowestNumPos] = help;
        }

        return returnArray;
    }

    public static int[] insertionSort(int[] array) {
        return insertionSort(array, array.length-1, 0);
    }

    public static int[] insertionSort(int[] array, int high, int low) {
        if (array == null || array.length <= 1) return array;

        int i = low + 1;
        while (i < high + 1) {
            int j = i;
            while (j > low && array[j-1] > array[j]) {
                int help = array[j];
                array[j] = array[j-1];
                array[j-1] = help;
                j--;
            }
            i++;
        }

        return array;
    }

    public static void countSort(int[] array) {
        countSort(array, array.length-1, 0);
    }

    public static void countSort(int[] array, int high, int low) {
        int min = array[low];
        int max = array[low];
        for (int i = low + 1; i <= high; i++) {
            if (array[i] > max) max = array[i];
            else if (array[i] < min) min = array[i];
        }

        int[] count = new int[max - min + 1];

        for (int i = low;i <= high; i++) {
            count[array[i] - min]++;
        }

        count[0]--;
        for (int i = 1; i < count.length; i++) {
            count[i] = count[i] + count[i-1];
        }

        printArray(count);

        int[] aux = new int[high-low + 1];

        for (int i = high;i >= 1;i--) {
            aux[count[array[i] - min]--] = array[i];
        }

        for (int i = 0;i < aux.length;i++) {
            array[low + i ] = aux[i];
        }
        printArray(aux);
    }

    public static void quickSort1(int[] array, int threshhold) {
        quickSort1(array, array.length-1, 0, threshhold);
    }

    public static void quickSort1(int[] array, int high, int low, int threshhold) {
        if (array == null || array.length <= 1) return;

        //if (low > 0 && high < array.length - 1&& array[low-1] == array[high + 1]) return;

        if (high - low < threshhold) {
            insertionSort(array, high, low);
            return;
        }

        int j = high;
        int i = low;
        int middle = array[low + (high - low)/2];

        while (i <= j) {
            while (array[i] < middle) i++;
            while (array[j] > middle) j--;
            if (i <= j) {
                int help = array[i];
                array[i] = array[j];
                array[j] = help;
                i++;
                j--;
            }
        }
        if (low < j) quickSort1(array, j, low, threshhold);
        if (i < high) quickSort1(array, high, i, threshhold);

        return;
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i != array.length -1) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    public static boolean validateSort(int[] array) {
        for (int i = 0; i < array.length - 1;i++) if (array[i] > array[i+1]) return false;
        return true;
    }
}
