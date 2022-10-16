package hu.gyaposz.crossing.domain;

import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a gene set up as a pair of alleles responsible for a gene.
 *
 * @author gyaposz
 * @since 14
 */
@Value
public class Gene implements Comparable<Gene>, Serializable {

    private static final long serialVersionUID = 3856464854229549232L;
    @NonNull List<Allele> allelePair;

    public Gene(@NonNull List<Allele> allelePair) {
        this.allelePair = new ArrayList<>(allelePair);
        this.allelePair.sort(Allele::compareTo);
    }

    @Override
    public int compareTo(Gene arg0) {
        return allelePair.get(0).compareTo(arg0.allelePair.get(0)) == 0
                ? allelePair.get(1).compareTo(arg0.allelePair.get(1))
                : allelePair.get(0).compareTo(arg0.allelePair.get(0));
    }
}
