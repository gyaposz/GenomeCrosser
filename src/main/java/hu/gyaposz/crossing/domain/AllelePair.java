package hu.gyaposz.crossing.domain;

import lombok.Value;

import java.io.Serial;
import java.io.Serializable;

/**
 * AllelePair representing two alleles responsible for a gene.
 *
 * @author gyaposz
 */
@Value
public class AllelePair implements Comparable<AllelePair>, Serializable {

    /**
     * The one and only version UID
     */
    @Serial
    private static final long serialVersionUID = 3856464854229549232L;

    /**
     * First allele of the pair
     */
    Allele leftAllele = null;

    /**
     * Second allele of the pair
     */
    Allele rightAllele = null;

    @Override
    public int compareTo(AllelePair arg0) {
        return getLeftAllele().compareTo(arg0.getLeftAllele()) == 0 ?
                getRightAllele().compareTo(arg0.getRightAllele()) :
                getLeftAllele().compareTo(arg0.getLeftAllele());
    }
}
