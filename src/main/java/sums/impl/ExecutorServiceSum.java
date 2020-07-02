package sums.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceSum{
    private final List<Callable<Integer>> callableTasks;
    private final List<Integer> list;

    public ExecutorServiceSum(List<Integer> list) {
        this.list = list;
        this.callableTasks = listOfThreads();
    }

    private List<Callable<Integer>> listOfThreads() {
        if (list.size() > 1000) {
            List<Callable<Integer>> listCallabels = new ArrayList<>();
            int nunmOfElem = list.size() / numOfThreads();
            for (int i = 0; i < numOfThreads(); i++) {
                listCallabels.add(getCallable(i * nunmOfElem, i * nunmOfElem + nunmOfElem));
            }
            return listCallabels;
        } else {
            return List.of(getCallable(0, list.size()));
        }
    }

    public int sum() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(list.size()/1000);
        List<Future<Integer>> result = executor.invokeAll(callableTasks);
        int sum = result.stream().map(x -> {
            try {
                return x.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return 0;
        }).reduce(Integer::sum).orElse(0);
        executor.shutdown();
        return sum;
    }

    private Callable<Integer> getCallable(int begin, int end) {
        return () -> this.list.subList(begin, end)
                .stream().reduce(Integer::sum).orElse(0);
    }

    private int numOfThreads() {
        return list.size() / 1000;
    }
}
