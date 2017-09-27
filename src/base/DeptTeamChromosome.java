package base;
import org.jenetics.Chromosome;
import org.jenetics.util.ISeq;
import org.jenetics.util.RandomRegistry;

import java.util.Iterator;
import java.util.Random;

public class DeptTeamChromosome implements Chromosome<DeptTeamGene> {

    private ISeq<DeptTeamGene> iSeq;
    private final int length;
    
    public DeptTeamChromosome(ISeq<DeptTeamGene> genes) {
        this.iSeq = genes;
        this.length = iSeq.length();
    }

    public static DeptTeamChromosome of(ISeq<DeptTeamGene> genes) {
        return new DeptTeamChromosome(genes);
    }

    @Override
    public Chromosome<DeptTeamGene> newInstance(ISeq<DeptTeamGene> iSeq) {
        this.iSeq = iSeq;
        return this;
    }

    @Override
    public DeptTeamGene getGene(int i) {
        return iSeq.get(i);
    }

    @Override
    public int length() {
        return iSeq.length();
    }

    @Override
    public ISeq<DeptTeamGene> toSeq() {
        return iSeq;
    }

    @Override
    public Chromosome<DeptTeamGene> newInstance() {
        final Random random = RandomRegistry.getRandom();
        ISeq<DeptTeamGene> genes = ISeq.empty();
        for (int i = 0; i < length; i++) {
            genes = genes.append(DeptTeamGene.of(Math.abs(random.nextInt(2))));
        }
        return new DeptTeamChromosome(genes);
    }

    @Override
    public Iterator<DeptTeamGene> iterator() {
        return iSeq.iterator();
    }

    @Override
    public boolean isValid() {
        return iSeq.stream().allMatch(DeptTeamGene::isValid);
    	//return valid;
    }
    
    public String toString() {
    	String ret = "[";
    	for (int i = 0 ; i < length ; i++){
    		ret += iSeq.get(i).intValue();
    	}
    	ret += "]";
		return ret;
    }
    
}