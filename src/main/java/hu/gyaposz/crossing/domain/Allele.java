package hu.gyaposz.crossing.domain;

import lombok.NonNull;
import lombok.Value;
import java.io.Serializable;

/**
 * Class representing a specific allele.
 *
 * @author gyaposz
 * @since 14
 */
@Value
public class Allele implements Comparable<Allele>, Serializable {

    @NonNull
    String geneDescriptor;

    private static final long serialVersionUID = 3517850137249318803L;

    @Override
    public int compareTo(Allele arg0) {
        return this.geneDescriptor.compareTo(arg0.geneDescriptor);
    }
}
