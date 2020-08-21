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

    private final static int pickOn = 2, skunk = 1;
    private List<String> pool = new ArrayList<>();

    public ProblemData() {
        IntStream.range(0, pickOn + skunk).forEach(v -> pool.add(v < pickOn ? "pickOn" : "skunk"));
        Collections.shuffle(pool);
    }

    public List<String> getPool() {
        return pool;
    }

}
