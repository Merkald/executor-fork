package sums.impl;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSum extends RecursiveTask<Integer> {
    private static int threshold;
    private final List<Integer> list;

    public ForkJoinSum(List<Integer> list, int num) {
        this.list = list;
        threshold = list.size() / num;
    }

    private ForkJoinSum(List<Integer> list) {
        this.list = list;
    }

    @Override
    protected Integer compute() {
        if (list.size() > threshold) {
            return ForkJoinTask.invokeAll(createSubTasks())
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        }
        return list.stream().reduce(Integer::sum).orElse(0);
    }

    private List<ForkJoinSum> createSubTasks() {
        return List.of(
                new ForkJoinSum(
                        list.subList(0, list.size() / 2 + 1)),
                new ForkJoinSum(
                        list.subList(list.size() / 2 + 1, list.size())));
    }
}
