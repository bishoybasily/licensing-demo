package concurrency;

import com.gmail.bishoybasily.licensing.commons.concurrency.OverloadHandler;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static com.gmail.bishoybasily.licensing.commons.concurrency.OverloadHandler.Strategy.FAIL;
import static com.gmail.bishoybasily.licensing.commons.concurrency.OverloadHandler.Strategy.THROTTLE;
import static org.assertj.core.api.Assertions.assertThat;
import static reactor.core.publisher.Flux.range;

class OverloadHandlerTest {

    private final static Long TASK_LENGTH_MILLIS = 10L;
    private final static Scheduler SCHEDULER = Schedulers.fromExecutor(Executors.newVirtualThreadPerTaskExecutor());

    @Test
    void test_fail() {

        var handler = new OverloadHandler(FAIL, new Semaphore(1, true));

        final int numberOfTasks = 3;

        var tasks = range(1, numberOfTasks)
                .flatMap(it -> {
                    return Mono.create(sink -> {
                        try {
                            sink.success(handler.execute(this::createLongBlockingTask));
                        } catch (Exception e) {
                            sink.error(e);
                        }
                    }).subscribeOn(SCHEDULER);
                });

        StepVerifier.create(tasks)
                .expectErrorSatisfies(err -> {
                    assertThat(err).hasMessage("Resource is too busy, no permits available");
                })
                .verify();

    }

    @Test
    void test_throttle() {

        var handler = new OverloadHandler(THROTTLE, new Semaphore(1, true));

        final int numberOfTasks = 3;

        var tasks = range(1, numberOfTasks)
                .flatMap(it -> {
                    return Mono.create(sink -> {
                        try {
                            sink.success(handler.execute(this::createLongBlockingTask));
                        } catch (Exception e) {
                            sink.error(e);
                        }
                    }).subscribeOn(SCHEDULER);
                });

        var start = System.currentTimeMillis();

        StepVerifier.create(tasks)
                .expectNextMatches(r -> r.equals("dummy res"))
                .expectNextMatches(r -> r.equals("dummy res"))
                .expectNextMatches(r -> r.equals("dummy res"))
                .verifyComplete();

        var elapsed = System.currentTimeMillis() - start;

        assertThat(elapsed).isGreaterThanOrEqualTo(numberOfTasks * TASK_LENGTH_MILLIS);
    }

    private String createLongBlockingTask() throws InterruptedException {
        Thread.sleep(TASK_LENGTH_MILLIS);
        return "dummy res";
    }


}