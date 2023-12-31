/* Licensed under MIT 2023. */
package edu.kit.kastel.mcse.ardoco.core.api.models.tracelinks;

import java.util.Objects;

public class TraceLink {
    private final EndpointTuple endpointTuple;

    public TraceLink(EndpointTuple endpointTuple) {
        this.endpointTuple = endpointTuple;
    }

    /**
     * Returns the endpoint tuple of this trace link.
     *
     * @return the endpoint tuple of this trace link
     */
    public EndpointTuple getEndpointTuple() {
        return endpointTuple;
    }

    @Override
    public int hashCode() {
        return Objects.hash(endpointTuple);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TraceLink other)) {
            return false;
        }
        return Objects.equals(endpointTuple, other.endpointTuple);
    }

    @Override
    public String toString() {
        return endpointTuple.toString();
    }
}
