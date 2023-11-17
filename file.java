import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class file {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        int start = 1;
        int end = 100000000;

        Future<Integer>[] futures = new Future[5];
        long[] timers = new long[5];

        for (int i = 0; i < 5; i++) {
            int partStart = start + i * (end - start) / 5;
            int partEnd = start + (i + 1) * (end - start) / 5;

            long startTime = System.currentTimeMillis();

            Callable<Integer> callable = new NumberCounterCallable(partStart, partEnd);
            futures[i] = executorService.submit(callable);

            long endTime = System.currentTimeMillis();
            timers[i] = endTime - startTime;
        }

        int totalCount = 0;
        for (int i = 0; i < 5; i++) {
            totalCount += futures[i].get();
            System.out.println("Task " + (i + 1) + " took " + timers[i] + " milliseconds");
        }

        executorService.shutdown();
        System.out.println("Total count of numbers between 1 and 100,000,000: " + totalCount);
    }
}

class NumberCounterCallable implements Callable<Integer> {
    private final int start;
    private final int end;

    public NumberCounterCallable(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() {
        int count = 0;
        for (int i = start; i <= end; i++) {
            count++;
        }
        return count;
    }
}
