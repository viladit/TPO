package com.testinglab.lab1.btree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class BTreeTraceCollector {

    private final List<TraceStep> steps = new ArrayList<>();

    public void clear() {
        steps.clear();
    }

    public void log(BTreeTracePoint point) {
        steps.add(TraceStep.of(point));
    }

    public void log(BTreeTracePoint point, Object details) {
        steps.add(TraceStep.of(point, details));
    }

    public List<TraceStep> snapshot() {
        return List.copyOf(steps);
    }

    public List<String> snapshotAsText() {
        return steps.stream()
                .map(TraceStep::asText)
                .collect(Collectors.toUnmodifiableList());
    }
}
