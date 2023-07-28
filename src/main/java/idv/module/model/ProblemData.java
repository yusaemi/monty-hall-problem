package idv.module.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * ProblemData. 2020/8/17 下午 04:09
 *
 * @author sero
 * @version 1.0.0
 **/
public class ProblemData {

    private static final int SKUNK = 1;
    private static final int PICK_ON = 2;
    private final List<String> pool = new ArrayList<>();

    public ProblemData() {
        IntStream.range(0, PICK_ON + SKUNK).forEach(v -> pool.add(v < PICK_ON ? "pickOn" : "skunk"));
        Collections.shuffle(pool);
    }

    public List<String> getPool() {
        return pool;
    }

}
