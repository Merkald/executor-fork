import sums.impl.ExecutorServiceSum;
import sums.impl.ForkJoinSum;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {
    private static final int AMMOUNT_ELEMENTS = 1_000_000;
    private static final int NUM_OF_THREADS = 100;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < AMMOUNT_ELEMENTS; i++) {
            list.add(i);
        }
        ExecutorServiceSum serviceSum = new ExecutorServiceSum(list);
        ForkJoinSum forkJoinSum = new ForkJoinSum(list, NUM_OF_THREADS);
        Integer forkSum = forkJoinSum.invoke();
        int sum = serviceSum.sum();
        System.out.println(sum);
        System.out.println(forkSum);
    }


}
