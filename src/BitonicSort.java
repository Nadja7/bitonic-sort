
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;


public class BitonicSort {

    private int[] data = new int[1048576];
    //private int[] data2 = {3, 5, 1, 7, 9, 5, 2, 6, 3, 5, 4, 2, 9, 6, 4, 7,3, 5, 1, 7, 9, 5, 2, 6, 3, 5, 4, 2, 9, 6, 4, 7};

    public int[] getData(String data) {
        long start = System.currentTimeMillis();
        try {
            DataInputStream input = new DataInputStream(new FileInputStream(
                    data));

            for (int i = 0; i < this.data.length; i++) {
                this.data[i] = input.readInt();
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("");
            System.out.println("End of file reached");
        }
        return this.data;
    }


    public int[] merge(boolean direction, int[] sequenz) {
        if (sequenz.length == 1) {
            return sequenz;
        } else {
            swapAndCompare(direction, sequenz);
            int[] firstPart = merge(direction, Arrays.copyOfRange(sequenz, 0, sequenz.length / 2));
            int[] secondPart = merge(direction, Arrays.copyOfRange(sequenz, sequenz.length / 2, sequenz.length));
            int[] result = IntStream.concat(Arrays.stream(firstPart), Arrays.stream(secondPart)).toArray();
            return result;
        }


    }

    public void swapAndCompare(boolean direction, int[] sequenz) {
        int comparisionDistance = sequenz.length / 2;
        for (int i = 0; i < comparisionDistance; i++) {
            int temp = sequenz[i];
            if (sequenz[i] > sequenz[i + comparisionDistance] == direction) {
                sequenz[i] = sequenz[i + comparisionDistance];
                sequenz[i + comparisionDistance] = temp;
            }
        }

    }


    public int[] bitonicSort(boolean direction, int[] sequenz) {
        int[] firstPart = copyRequiredRange(sequenz, 0, sequenz.length / 2);
        int[] secondPart = copyRequiredRange(sequenz, sequenz.length / 2, sequenz.length);
        if (sequenz.length <= 1) {
            return sequenz;
        } else {
            int[] firstPair = bitonicSort(true, firstPart);
            int[] secondPair = bitonicSort(false, secondPart);
            int[] result = IntStream.concat(Arrays.stream(firstPair), Arrays.stream(secondPair)).toArray();

            return merge(direction, result);
        }


    }

    public int[] copyRequiredRange(int[] sequenz, int from, int to) {

        int requiredRange = to - from;
        int[] requiredSequenz = new int[requiredRange];
        if (requiredRange == 0) {
            return sequenz;
        }
        if (to > sequenz.length) {
            System.out.println("Second Parameter is out of max Range");
        } else if (from < 0) {
            System.out.println("First Parameter is out of min Range");
        } else {

            for (int i = from; i <= to - 1; i++) {
                requiredSequenz[i - from] = sequenz[i];
            }
        }
        return requiredSequenz;
    }

    public int getCores() {

        int cores = Runtime.getRuntime().availableProcessors();
        return cores;

    }

    public int[] startSort(int[] sequenz) {

        int[] result = bitonicSort(true, sequenz);
        return result;
    }

    public int[][] devideSubArrays(int cores, int[] data) {

        int unit = data.length / cores;
        int[] piece = new int[unit];
        int[][] result = new int[cores][unit];
        for (int i = 0; i < cores; i++) {
            for (int j = 0; j <= unit - 1; j++) {
                piece[j] = data[j + (unit * i)];
                result[i][j] = data[j + (unit * i)];
            }
        }

        return result;
    }

/*    public void saveData() {
        File out = new File("28.dat");
        FileWriter fw = null;
        int n = 268435456;
        try {
            fw = new FileWriter(out);

            BufferedWriter writer = new BufferedWriter(fw);
            int line;
            Random random = new Random();
            while (n > 0) {
                // Randomize an integer and write it to the output file
                line = random.nextInt(1000000);
                writer.write(line + System.getProperty("line.separator"));
                n--;
            }
            // Close the stream
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }*/

}

