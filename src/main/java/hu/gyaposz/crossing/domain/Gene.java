package hu.gyaposz.crossing.domain;

import lombok.Value;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Class representing a schematic view of a Gene to support crossing.
 * It supports one sexChromosome and multiple Autosomes.
 *
 * @author gyaposz
 */
@Value
public class Gene implements Comparable<Gene>, Serializable {

    /**
     * The one and only version UID
     */
    @Serial
    private static final long serialVersionUID = -5231727895237988934L;

    /**
     * Autosomes allele pairs.
     */
    List<AllelePair> autosomes = null;

    /**
     * Sex chromosome allele pair to keep track of predecessor's sex.
     */
    AllelePair sexChromosome = null;


    public Gene() {
        super();
    }

    @Override
    public int compareTo(Gene arg0) {
        int result = this.getSexChromosome().compareTo(arg0.getSexChromosome());
        if (result != 0) {
            return result;
        }
        for (int i = 0; i < this.getAutosomes().size(); i++) {
            result = this.getAutosomes().get(i).compareTo(arg0.getAutosomes().get(i));
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}
