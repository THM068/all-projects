package streams;

import org.junit.jupiter.api.Test;

public class SelectionSort {

    @Test
    public void selectionSort() {
        int [] x = {3,2,4,5,1};
        for(int i: selectionSort(x) ) {
            System.out.println(i);
        }
    }

    public int[] selectionSort(int[] array) {
        int n = array.length;
        for(int x = 0; x < n - 1 ; x++) {
            int min_idx = x;
            for(int j= x + 1; j < n; j++) {
                if(array[j] < array[min_idx]) {
                    min_idx = j;
                }
                int temp = array[min_idx];
                array[min_idx] = array[x];
                array[x] = temp;
            }

        }

        return array;
    }
}
