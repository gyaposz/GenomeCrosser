package hu.gyaposz.crossing.domain;

import lombok.Value;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class representing a specific allele.
 *
 * @author gyaposz
 * @since 14
 */
@Value
public class Allele implements Comparable<Allele>, Serializable {

    /**
     * The one and only version UID
     */
    @Serial
    private static final long serialVersionUID = 3517850137249318803L;

    /**
     * Private field representing genes of allele.
     */
    String geneDescriptor = null;

    @Override
    public int compareTo(Allele arg0) {
        return this.getGeneDescriptor().compareTo(arg0.getGeneDescriptor());
    }
}
