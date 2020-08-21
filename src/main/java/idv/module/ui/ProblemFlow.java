package idv.module.ui;

import idv.module.model.ProblemData;
import idv.module.utils.InputUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * ProblemFlow. 2020/8/17 下午 04:10
 *
 * @author sero
 * @version 1.0.0
 **/
public class ProblemFlow {

    private static ProblemData data;

    public static void start(BufferedReader br) throws Exception {

        boolean isErrorPara = false, isRestart = true, isManuallyFirst = true, isAutoFirst = true;

        while (isRestart) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("【Enter 'exit' to exit this loop at anytime.】");
            System.out.println(isErrorPara ? "※Invalid parameter!※" : "");
            System.out.println("【automatic： 1 / manual： 2】");
            System.out.print("Please choose mode： ");
            try {
                isErrorPara = false;
                isManuallyFirst = true;
                isAutoFirst = true;
                String input = InputUtils.getInput(br);
                switch (input) {
                    case "1":
                        if (isAutoFirst) {
                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                            isAutoFirst = false;
                        }
                        System.out.println("----------------------------");
                        System.out.println("Play game on automatic mode");
                        System.out.println("----------------------------");
                        Thread.sleep(10);
                        System.out.println("【200 above and 10000000 below / Quit this loop： 0】\n");
                        while (true) {
                            int frequency = 0;
                            data = new ProblemData();
                            System.out.print("Please key in frequency： ");
                            input = InputUtils.getInput(br);
                            if (input.matches("[1-9][0-9]*") && input.length() < 9) {
                                frequency = Integer.parseInt(input);
                                if (frequency >= 200 && frequency <= 10000000) {
                                    autoStart(frequency);
                                } else {
                                    System.out.println("\n※Invalid parameter!※\n");
                                }
                            } else if (input.matches("[0]")) {
                                break;
                            } else {
                                System.out.println("\n※Invalid parameter!※\n");
                            }
                        }
                        break;
                    case "2":
                        if (isManuallyFirst) {
                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                            isManuallyFirst = false;
                        }
                        System.out.println("----------------------------");
                        System.out.println("Play game on manually mode");
                        System.out.println("----------------------------");
                        Thread.sleep(10);
                        System.out.println("【Left： 1 / middle： 2 / Right： 3 / Quit this loop： 0】\n");
                        AtomicInteger manuallySuccess = new AtomicInteger(), manuallyFailure = new AtomicInteger(), count = new AtomicInteger(1);
                        while (true) {
                            data = new ProblemData();
                            Thread.sleep(10);
                            System.out.print("Please select the answer： ");
                            input = InputUtils.getInput(br);
                            if (input.matches("[123]")) {
                                boolean isPickOn = manuallyStart(input);
                                manuallySuccess.set(isPickOn ? manuallySuccess.incrementAndGet() : manuallySuccess.get());
                                manuallyFailure.set(!isPickOn ? manuallyFailure.incrementAndGet() : manuallyFailure.get());
                                System.out.println(isPickOn ? "\nYou win!\n" : "\nYou lose!\n");
                                Thread.sleep(10);
                                String rate = new BigDecimal(Double.toString(manuallySuccess.get())).divide(new BigDecimal(Double.toString(count.get())), 10, RoundingMode.HALF_UP) + "";
                                NumberFormat nf = NumberFormat.getPercentInstance();
                                nf.setMinimumFractionDigits(2);
                                System.out.println("Sequence number of play： " + count.get());
                                System.out.println("Average hit rate： " + nf.format(Double.parseDouble(rate)) + "\n");
                                count.incrementAndGet();
                            } else if (input.matches("[0]")) {
                                break;
                            } else {
                                System.out.println("\n※Invalid parameter!※\n");
                            }
                        }
                        break;
                    default:
                        isErrorPara = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                InputUtils.stop(br);
            }
        }
    }

    private static boolean manuallyStart(String position) throws InterruptedException, IOException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("----------------------------");
        System.out.println("Play game on manually mode");
        System.out.println("----------------------------");
        Thread.sleep(10);
        System.out.println("【Left： 1 / middle： 2 / Right： 3 / Quit this loop： 0】\n");
        List<String> select = new ArrayList<String>(), result = new ArrayList<String>();
        IntStream.range(0, 3).forEach(v -> {
            result.add("pickOn".equals(data.getPool().get(v)) ? "★" : "☆");
            select.add(Integer.toString(v + 1).equals(position) ? "√" : " ");
        });
        System.out.println("\t" + select.get(0) + "\t\t" + select.get(1) + "\t\t" + select.get(2));
        // System.out.println("╒══╤══╤══╕");
        System.out.println("|\t" + result.get(0) + "\t|\t" + result.get(1) + "\t|\t" + result.get(2) + "\t|");
        // System.out.println("╘══╧══╧══╛");
        return "pickOn".equals(data.getPool().get(Integer.parseInt(position) - 1));
    }

    private static void autoStart(int frequency) throws InterruptedException, IOException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("----------------------------");
        System.out.println("Play game on automatic mode");
        System.out.println("----------------------------");
        Thread.sleep(10);
        System.out.println("【200 above and 10000000 below / Quit this loop： 0】\n");
        AtomicInteger autoSuccess = new AtomicInteger(), autoFailure = new AtomicInteger();
        IntStream.range(0, frequency).forEach(v -> {
            boolean isPickOn = "pickOn".equals(data.getPool().get((int) (Math.random() * data.getPool().size())));
            autoSuccess.set(isPickOn ? autoSuccess.incrementAndGet() : autoSuccess.get());
            autoFailure.set(!isPickOn ? autoFailure.incrementAndGet() : autoFailure.get());
        });
        String rate = new BigDecimal(Double.toString(autoSuccess.get())).divide(new BigDecimal(Double.toString(frequency)), 10, RoundingMode.HALF_UP).toPlainString();
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        System.out.println("Mode: automatic\nFrequency: " + frequency);
        System.out.println("Average hit rate: " + nf.format(Double.parseDouble(rate)) + "\n");
    }

}
