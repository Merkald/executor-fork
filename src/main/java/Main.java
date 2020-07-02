import sums.impl.ExecutorServiceSum;
import sums.impl.ForkJoinSum;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Integer> list = new ArrayList<>();
        for (int i =0; i<1_000_000; i++) {
            list.add(1);
            list.add(2);
            list.add(3);
        }
        ExecutorServiceSum serviceSum = new ExecutorServiceSum(list);
        ForkJoinSum forkJoinSum = new ForkJoinSum(list,100);
        Integer forkSum = forkJoinSum.invoke();
        int sum = serviceSum.sum();
        System.out.println(sum);
        System.out.println(forkSum);
    }


}
