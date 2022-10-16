package hu.gyaposz.crossing;

import hu.gyaposz.crossing.domain.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CrosserTest {

    @Test
    public void testGenerateCrossing() {
        Crosser bean = new Crosser();
        List<Chromosome> chromosomes1 = new ArrayList<>();
        List<Allele> sexAlleles1 = new ArrayList<>();
        sexAlleles1.add(new Allele("X1"));
        sexAlleles1.add(new Allele("X1"));
        chromosomes1.add(new Chromosome(Collections.singletonList(new Gene(sexAlleles1))));

        List<Allele> autoAlleles11 = new ArrayList<>();
        autoAlleles11.add(new Allele("A1"));
        autoAlleles11.add(new Allele("A2"));
        chromosomes1.add(new Chromosome(Collections.singletonList(new Gene(autoAlleles11))));

        List<Allele> autoAlleles12 = new ArrayList<>();
        autoAlleles12.add(new Allele("B1"));
        autoAlleles12.add(new Allele("B1"));
        chromosomes1.add(new Chromosome(Collections.singletonList(new Gene(autoAlleles12))));

        List<Allele> autoAlleles13 = new ArrayList<>();
        autoAlleles13.add(new Allele("z"));
        autoAlleles13.add(new Allele("z"));
        chromosomes1.add(new Chromosome(Collections.singletonList(new Gene(autoAlleles13))));
        Genome genome1 = new Genome(chromosomes1, Sex.MALE);

        List<Chromosome> chromosomes2 = new ArrayList<>();
        List<Allele> sexAlleles2 = new ArrayList<>();
        sexAlleles2.add(new Allele("X1"));
        sexAlleles2.add(new Allele("X1"));
        chromosomes2.add(new Chromosome(Collections.singletonList(new Gene(sexAlleles2))));

        List<Allele> autoAlleles21 = new ArrayList<>();
        autoAlleles21.add(new Allele("C1"));
        autoAlleles21.add(new Allele("C2"));
        chromosomes2.add(new Chromosome(Collections.singletonList(new Gene(autoAlleles21))));

        List<Allele> autoAlleles22 = new ArrayList<>();
        autoAlleles22.add(new Allele("D1"));
        autoAlleles22.add(new Allele("D1"));
        chromosomes2.add(new Chromosome(Collections.singletonList(new Gene(autoAlleles22))));

        List<Allele> autoAlleles23 = new ArrayList<>();
        autoAlleles23.add(new Allele("z"));
        autoAlleles23.add(new Allele("z"));
        chromosomes2.add(new Chromosome(Collections.singletonList(new Gene(autoAlleles23))));

        Genome genome2 = new Genome(chromosomes2, Sex.FEMALE);

        Map<Genome, AtomicInteger> genomeList = bean.generateCrossing(genome1, genome2);
        assertNotNull(genomeList);
        assertEquals(genomeList.size(), 4);
    }

    @Test
    public void testHandleRequest() {
        Crosser bean = new Crosser();
        String fatherGenome = "X1,X1|A1,A2;d,d|B1,B1|z,z";
        String motherGenome = "X1,X1|A2,A1;d,d|B1,B1|z,z";
        Map<Genome, AtomicInteger> genomeList = bean.handleRequest(fatherGenome, motherGenome);
        assertNotNull(genomeList);
        assertEquals(genomeList.size(), 3);
    }
}