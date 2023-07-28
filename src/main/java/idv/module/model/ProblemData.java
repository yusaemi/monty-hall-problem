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

    private final List<String> pools = new ArrayList<>();

    public ProblemData() {
        int skunk = 1;
        int pickOn = 2;
        IntStream.range(0, pickOn + skunk).forEach(v -> pools.add(v < pickOn ? "pickOn" : "skunk"));
        Collections.shuffle(pools);
    }

    public List<String> getPools() {
        return pools;
    }

}
