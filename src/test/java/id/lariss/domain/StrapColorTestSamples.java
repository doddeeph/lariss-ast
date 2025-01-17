package id.lariss.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StrapColorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StrapColor getStrapColorSample1() {
        return new StrapColor().id(1L).name("name1").value("value1");
    }

    public static StrapColor getStrapColorSample2() {
        return new StrapColor().id(2L).name("name2").value("value2");
    }

    public static StrapColor getStrapColorRandomSampleGenerator() {
        return new StrapColor().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).value(UUID.randomUUID().toString());
    }
}
