package hu.gyaposz.crossing.domain;

import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Class representing a schematic view of a Genome to support crossing.
 * It supports one sex geneChromosome and multiple Autosomes.
 *
 * @author gyaposz
 */
@Value
public class Genome implements Comparable<Genome>, Serializable {

    @NonNull
    List<Chromosome> chromosomes;

    @NonNull Sex sex;

    /**
     * The one and only version UID
     */
    private static final long serialVersionUID = -5231727895237988934L;

    @Override
    public int compareTo(Genome arg0) {
        return IntStream.range(0, chromosomes.size() - 1)
                .map(index -> chromosomes.get(index).compareTo(arg0.chromosomes.get(index)))
                .filter(intValue -> intValue != 0)
                .findFirst()
                .orElse(this.sex.compareTo(arg0.sex));
    }
}
