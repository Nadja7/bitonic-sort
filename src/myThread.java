
public class myThread extends Thread {
    private int[] data;
    private BitonicSort bitonicSort = new BitonicSort();


    public myThread(int[] data) {
        this.data = data;
    }

    public int[] getData() {
        return this.data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public void run() {
        this.data = this.bitonicSort.startSort(this.data);
    }
}
