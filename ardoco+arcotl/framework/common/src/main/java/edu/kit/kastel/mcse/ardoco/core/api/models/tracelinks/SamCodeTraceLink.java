/* Licensed under MIT 2023. */
package edu.kit.kastel.mcse.ardoco.core.api.models.tracelinks;

import java.util.Objects;

import edu.kit.kastel.mcse.ardoco.core.api.models.arcotl.architecture.ArchitectureItem;
import edu.kit.kastel.mcse.ardoco.core.api.models.arcotl.code.CodeCompilationUnit;

/**
 * A trace link between exactly one architecture endpoint and exactly one code
 * endpoint. Trace links are created to connect corresponding elements of an
 * architecture and a code model.
 */
public class SamCodeTraceLink extends TraceLink {

    /**
     * Creates a new trace link between an architecture endpoint and a code endpoint
     * as given in the specified endpoint tuple.
     *
     * @param architectureEndpoint the architecture endpoint
     * @param codeEndpoint         the code endpoint
     */
    public SamCodeTraceLink(ArchitectureItem architectureEndpoint, CodeCompilationUnit codeEndpoint) {
        super(new EndpointTuple(architectureEndpoint, codeEndpoint));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEndpointTuple());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SamCodeTraceLink other)) {
            return false;
        }
        return Objects.equals(getEndpointTuple(), other.getEndpointTuple());
    }

    @Override
    public String toString() {
        return getEndpointTuple().toString();
    }
}
