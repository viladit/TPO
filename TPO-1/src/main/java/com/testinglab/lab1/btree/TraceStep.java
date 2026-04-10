package com.testinglab.lab1.btree;

import java.util.Objects;

public record TraceStep(BTreeTracePoint point, String details) {

    public TraceStep {
        Objects.requireNonNull(point, "Точка трассировки не должна быть null");
    }

    public static TraceStep of(BTreeTracePoint point) {
        return new TraceStep(point, null);
    }

    public static TraceStep of(BTreeTracePoint point, Object details) {
        return new TraceStep(point, String.valueOf(details));
    }

    public String asText() {
        return details == null ? point.name() : point.name() + "(" + details + ")";
    }
}
