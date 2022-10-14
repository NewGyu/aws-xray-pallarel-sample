package newgyu.xray;

import java.util.NoSuchElementException;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.SegmentImpl;

public class SomeProcesses {

    public static String callExternalApi1() {
        try {
            Thread.sleep(300);
            log("api1 finished");
            return "res1";
        } catch (InterruptedException e) {
            throw new RuntimeException("API1 Error");
        }
    }

    public static String callExternalApi2() {
        try {
            Thread.sleep(200);
            log("api2 finished");
            return "res2";
        } catch (InterruptedException e) {
            throw new RuntimeException("API2 Error");
        }
    }

    public static void proceedSomething(String a, String b) {
        log(String.format("proceeded (%s, %s)", a, b));
    }

    private static void log(String msg) {
        var segment = AWSXRay.getCurrentSegmentOptional()
                .orElse(new SegmentImpl(AWSXRay.getGlobalRecorder(), "no segment"));
        var threadName = Thread.currentThread().getName();
        System.out.printf("%s <- [thread=%s][segment=%s]%n", msg, threadName, segment.getName());
    }

}
