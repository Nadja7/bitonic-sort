public class Main {
    public static void main(String[] args) {
        BitonicSort bitonicSort = new BitonicSort();
        //bitonicSort.saveData();
        int[] data = bitonicSort.getData(args[0]);
        int cores = bitonicSort.getCores();
        int[][] result = bitonicSort.devideSubArrays(cores, data);
        myThread myThreads[] = new myThread[cores];
        for (int i = 0; i < cores; i++) {
            String id = "current thread is " + i;
            myThread thread = new myThread(result[i]);
            thread.setName(id);
            myThreads[i] = thread;
        }


        int[] endResult = new int[data.length];
        for (int i = 0; i < myThreads.length; i++) {
            myThreads[i].start();
        }
        for (int j = 0; j < myThreads.length; j++) {
            try {
                myThreads[j].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int a = 0; a < myThreads.length; a++) {
            for (int b = 0; b < myThreads[a].getData().length; b++) {
                endResult[b + (a * myThreads[a].getData().length)] = myThreads[a].getData()[b];
            }
        }

        int[] end = bitonicSort.startSort(endResult);

        for (int q = 0; q < end.length; q++) {
            if (q == Integer.parseInt(args[1]))
                System.out.println("Element am Index " + q + ":" + end[q]);
        }
    }
}


