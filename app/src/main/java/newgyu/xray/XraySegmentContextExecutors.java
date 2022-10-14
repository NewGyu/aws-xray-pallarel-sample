package newgyu.xray;

import java.util.concurrent.Executor;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorder;
import com.amazonaws.xray.entities.Segment;

public final class XraySegmentContextExecutors {

    public static XraySegmentContextExecutor newExecutor(Executor executor) {
        return newExecutor(AWSXRay.getGlobalRecorder(), AWSXRay.getCurrentSegmentOptional().orElseThrow(), executor);
    }

    public static XraySegmentContextExecutor newExecutor(AWSXRayRecorder recorder, Segment segment, Executor executor) {
        return new XraySegmentContextExecutor(recorder, segment, executor);
    }

    static class XraySegmentContextExecutor implements Executor {
        private final AWSXRayRecorder recorder;
        private final Segment segment;
        private final Executor executor;

        XraySegmentContextExecutor(AWSXRayRecorder recorder, Segment segment, Executor executor) {
            this.recorder = recorder;
            this.segment = segment;
            this.executor = executor;
        }

        @Override
        public void execute(Runnable runable) {
            executor.execute(() -> segment.run(runable, recorder));
        }
    }
}
