package hu.gyaposz.crossing.domain;

import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Class representing chromosome as a list of genes.
 *
 * @author gyaposz
 */
@Value
public class Chromosome implements Comparable<Chromosome>, Serializable {

    @NonNull
    List<Gene> geneList;

    private static final long serialVersionUID = 4730131835130304103L;

    @Override
    public int compareTo(Chromosome arg0) {
        return IntStream.range(0, geneList.size() - 1)
                .map(index -> geneList.get(index).compareTo(arg0.geneList.get(index)))
                .filter(intValue -> intValue != 0)
                .findFirst()
                .orElse(0);
    }
}
