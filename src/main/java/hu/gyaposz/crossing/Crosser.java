package hu.gyaposz.crossing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import hu.gyaposz.crossing.domain.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
public class Crosser implements RequestHandler<Map<String, Object>, Map<Genome, AtomicInteger>> {
    @Override
    public Map<Genome, AtomicInteger> handleRequest(Map<String, Object> input, Context context) {
        LambdaLogger logger = context.getLogger();
        String body = (String) input.get("body");
        if (body != null)
        {
            return handleRequest(body.split("-")[0], body.split("-")[1]);
        } else {
            logger.log("father: " + input.get("father").toString());
            logger.log("mother: " + input.get("mother").toString());
            return handleRequest((String) input.get("father"), (String) input.get("mother"));
        }
    }
    public Map<Genome, AtomicInteger> handleRequest(String fatherGenome, String motherGenome) {
        return generateCrossing(convertToGenome(fatherGenome), convertToGenome(motherGenome));
    }

    public Genome convertToGenome(String genome) {
        List<Chromosome> chromosomes = new ArrayList<>();
        for (String chromosome : genome.split("\\|")) {
            List<Gene> genes = new ArrayList<>();
            for (String gene : chromosome.split(";")) {
                List<Allele> alleles = new ArrayList<>();
                for (String allele : gene.split(",")) {
                    alleles.add(new Allele(allele));
                }
                if (alleles.size() != 2)
                {
                    throw new IllegalArgumentException("Gene does not contain exactly 2 alleles.");
                }
                genes.add(new Gene(alleles));
            }
            chromosomes.add(new Chromosome(genes));
        }
        if (chromosomes.size() != 4)
        {
            throw new IllegalArgumentException("Genome does not contain exactly 4 chromosomes.");
        }
        return new Genome(chromosomes, Sex.MALE);
    }
    public Map<Genome, AtomicInteger> generateCrossing(Genome mother, Genome father) {
        List<Genome> generatedGenome = new ArrayList<>();

        List<List<Chromosome>> possibleChromosomes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<Chromosome> childChromosomes = new ArrayList<>();
            Chromosome motherChromosome = mother.getChromosomes().get(i);
            Chromosome fatherChromosome = father.getChromosomes().get(i);
            List<List<Gene>> childChromosomeGenes = new ArrayList<>();
            for (int j = 0; j < motherChromosome.getGeneList().size(); j++) {
                Gene motherGene = motherChromosome.getGeneList().get(j);
                Gene fatherGene = fatherChromosome.getGeneList().get(j);
                List<Gene> childGenes = new ArrayList<>();
                for (int m = 0; m < 2; m++) {
                    for (int l = 0; l < 2; l++) {
                        List<Allele> alleles = new ArrayList<>();
                        alleles.add(motherGene.getAllelePair().get(m));
                        alleles.add(fatherGene.getAllelePair().get(l));
                        childGenes.add(new Gene(alleles));
                    }
                }
                childChromosomeGenes.add(childGenes);
            }

            List<Integer> counter = new ArrayList<>();
            IntStream.range(0, childChromosomeGenes.size()).forEach((intValue) -> counter.add(0));

            boolean moreValue = true;

            while (moreValue) {
                List<Gene> genesOfOneChromosome = new ArrayList<>();
                for (int k = 0; k < childChromosomeGenes.size(); k++) {
                    genesOfOneChromosome.add(childChromosomeGenes.get(k).get(counter.get(k)));
                }
                childChromosomes.add(new Chromosome(genesOfOneChromosome));

                moreValue = false;
                for (int k = counter.size() - 1; k >= 0; k--) {
                    if (counter.get(k) < childChromosomeGenes.get(k).size() - 1) {
                        counter.set(k, counter.get(k) + 1);
                        moreValue = true;
                        break;
                    } else {
                        counter.set(k, 0);
                    }
                }
            }
            possibleChromosomes.add(childChromosomes);
        }

        List<Integer> counterOfChromosomes = new ArrayList<>();
        IntStream.range(0, possibleChromosomes.size()).forEach((intValue) -> counterOfChromosomes.add(0));

        boolean moreValue = true;

        while (moreValue) {
            List<Chromosome> chromosomeOfOneEntity = new ArrayList<>();
            for (int k = 0; k < possibleChromosomes.size(); k++) {
                chromosomeOfOneEntity.add(possibleChromosomes.get(k).get(counterOfChromosomes.get(k)));
            }
            generatedGenome.add(new Genome(chromosomeOfOneEntity, Sex.MALE));

            moreValue = false;
            for (int k = counterOfChromosomes.size() - 1; k >= 0; k--) {
                if (counterOfChromosomes.get(k) < possibleChromosomes.get(k).size() - 1) {
                    counterOfChromosomes.set(k, counterOfChromosomes.get(k) + 1);
                    moreValue = true;
                    break;
                } else {
                    counterOfChromosomes.set(k, 0);
                }
            }
        }
        Map<Genome, AtomicInteger> generatedGenomeMap = new HashMap<>();
        generatedGenome.forEach(genome -> {
            generatedGenomeMap.putIfAbsent(genome, new AtomicInteger(0));
            generatedGenomeMap.get(genome).incrementAndGet();
        });
        return generatedGenomeMap;
    }
}
