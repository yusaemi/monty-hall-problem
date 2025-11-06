package idv.module;

import idv.module.ui.ProblemFlow;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * App. 2020/8/17 下午 04:06
 *
 * @author sero
 * @version 1.0.0
 **/
public class App {

    void main() throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ProblemFlow.start(br);

    }

}
