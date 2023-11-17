import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class file {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        int start = 1;
        int end = 100000000;

        Future<Integer>[] futures = new Future[4];

        for (int i = 0; i < 4; i++) {
            int partStart = start + i * (end - start) / 4;
            int partEnd = start + (i + 1) * (end - start) / 4;

            Callable<Integer> callable = new NumberCounterCallable(partStart, partEnd);

            futures[i] = executorService.submit(callable);
        }

        int totalCount = 0;
        for (Future<Integer> future : futures) {
            totalCount += future.get();
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
