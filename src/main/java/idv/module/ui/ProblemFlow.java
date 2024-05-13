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
public final class ProblemFlow {

    private ProblemFlow() {
    }

    private static ProblemData data;

    public static void start(BufferedReader br) throws Exception {

        boolean isErrorPara = false;
        boolean isRestart = true;

        while (isRestart) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println(isErrorPara ? "\n※Invalid parameter!※\n" : "");
            System.out.println("【Enter 'exit' to exit this loop at anytime.】");
            System.out.println("【automatic： 1 / manual： 2】");
            System.out.print("Please choose mode： ");
            try {
                isErrorPara = false;
                String input = InputUtils.getInput(br);
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                switch (input) {
                    case "1" -> {
                        while (true) {
                            playAutomaticMode();
                            data = new ProblemData();
                            System.out.print("Please key in frequency： ");
                            input = InputUtils.getInput(br);
                            if ("0".equals(input)) {
                                break;
                            }
                            autoStart(input);
                        }
                    }
                    case "2" -> {
                        AtomicInteger manuallySuccess = new AtomicInteger();
                        AtomicInteger manuallyFailure = new AtomicInteger();
                        AtomicInteger count = new AtomicInteger(1);
                        while (true) {
                            playManuallyMode();
                            data = new ProblemData();
                            Thread.sleep(1);
                            System.out.print("Please select the answer： ");
                            input = InputUtils.getInput(br);
                            if ("0".equals(input)) {
                                break;
                            }
                            manuallyStart(input, manuallySuccess, manuallyFailure, count);
                        }
                    }
                    default -> isErrorPara = true;
                }
            } catch (Exception e) {
                InputUtils.stop(br);
            }
        }
    }

    private static void manuallyStart(String position, AtomicInteger manuallySuccess, AtomicInteger manuallyFailure, AtomicInteger count) throws InterruptedException, IOException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        if (!position.matches("[0-3]")) {
            System.out.println("\n※Invalid parameter!※\n");
            return;
        }
        List<String> select = new ArrayList<>();
        List<String> result = new ArrayList<>();
        IntStream.range(0, 3).forEach(v -> {
            result.add("pickOn".equals(data.getPools().get(v)) ? "羊" : "車");
            select.add(Integer.toString(v + 1).equals(position) ? "√" : " ");
        });
        System.out.println("\t" + select.get(0) + "\t\t" + select.get(1) + "\t\t" + select.get(2));
        System.out.println("╒═══════════════╤═══════════════╤═══════════════╕");
        System.out.println("|\t" + result.get(0) + "\t|\t" + result.get(1) + "\t|\t" + result.get(2) + "\t|");
        System.out.println("╘═══════════════╧═══════════════╧═══════════════╛");
        boolean isPickOn = "pickOn".equals(data.getPools().get(Integer.parseInt(position) - 1));
        manuallySuccess.set(isPickOn ? manuallySuccess.incrementAndGet() : manuallySuccess.get());
        manuallyFailure.set(!isPickOn ? manuallyFailure.incrementAndGet() : manuallyFailure.get());
        System.out.println(isPickOn ? "\nYou win!\n" : "\nYou lose!\n");
        Thread.sleep(1);
        String rate = new BigDecimal(Double.toString(manuallySuccess.get())).divide(new BigDecimal(Double.toString(count.get())), 10, RoundingMode.HALF_UP) + "";
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        System.out.println("Sequence number of play： " + count.get());
        System.out.println("Average hit rate： " + nf.format(Double.parseDouble(rate)) + "\n");
        count.incrementAndGet();
    }

    private static void autoStart(String input) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        if (input.matches("\\D+")) {
            System.out.println("\n※Invalid parameter!※\n");
            return;
        }
        BigDecimal frequency = new BigDecimal(input);
        if (frequency.compareTo(new BigDecimal(200)) < 0 || frequency.compareTo(new BigDecimal(10000000)) > 0) {
            System.out.println("\n※Invalid parameter!※\n");
            return;
        }
        AtomicInteger autoSuccess = new AtomicInteger();
        AtomicInteger autoFailure = new AtomicInteger();
        IntStream.range(0, frequency.intValueExact()).forEach(v -> {
            boolean isPickOn = "pickOn".equals(data.getPools().get((int) (Math.random() * data.getPools().size())));
            autoSuccess.set(isPickOn ? autoSuccess.incrementAndGet() : autoSuccess.get());
            autoFailure.set(!isPickOn ? autoFailure.incrementAndGet() : autoFailure.get());
        });
        String rate = new BigDecimal(Double.toString(autoSuccess.get())).divide(frequency, 10, RoundingMode.HALF_UP).toPlainString();
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        System.out.println("Mode: automatic\nFrequency: " + frequency);
        System.out.println("Average hit rate: " + nf.format(Double.parseDouble(rate)) + "\n");
    }

    private static void playManuallyMode() throws InterruptedException, IOException {
        System.out.println("----------------------------");
        System.out.println("Play game on manually mode");
        System.out.println("----------------------------");
        Thread.sleep(1);
        System.out.println("【Left： 1 / middle： 2 / Right： 3 / Quit this loop： 0】\n");
    }

    private static void playAutomaticMode() throws InterruptedException, IOException {
        System.out.println("----------------------------");
        System.out.println("Play game on automatic mode");
        System.out.println("----------------------------");
        Thread.sleep(1);
        System.out.println("【200 above and 10000000 below / Quit this loop： 0】\n");
    }

}
