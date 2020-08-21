package idv.module.utils;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * InputUtils. 2020/8/17 下午 04:07
 *
 * @author sero
 * @version 1.0.0
 **/
public class InputUtils {

    public static String getInput(BufferedReader br) throws Exception {
        String text = br.readLine().toLowerCase();
        if ("exit".equals(text)) {
            stop(br);
            System.out.println("\n【Good Bye.】\n");
            Thread.sleep(1000);
            System.exit(0);
        }
        return text;
    }

    public static void stop(BufferedReader br) throws IOException {
        br.close();
    }

}
