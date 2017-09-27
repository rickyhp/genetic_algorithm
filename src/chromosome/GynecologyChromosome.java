package chromosome;
import java.util.Random;

import org.jenetics.Chromosome;
import org.jenetics.internal.math.random;
import org.jenetics.util.ISeq;
import org.jenetics.util.RandomRegistry;

import base.DeptTeamChromosome;
import base.DeptTeamGene;

public class GynecologyChromosome extends DeptTeamChromosome {
	final int MIN_OR_PER_DAY = 2;
	final int MAX_OR_PER_DAY = 3;
	final int MIN_WEEKLY = 12;
	final int MAX_WEEKLY = 18;
	
	public GynecologyChromosome(ISeq<DeptTeamGene> genes) {
		super(genes);
	}
	
	public static GynecologyChromosome of(ISeq<DeptTeamGene> genes) {
        return new GynecologyChromosome(genes);
    }
	
	@Override
    public Chromosome<DeptTeamGene> newInstance() {
		Random r = RandomRegistry.getRandom();
        ISeq<DeptTeamGene> genes = ISeq.empty();
        
        int totalWeekly = 0;
        for (int i = 0; i < super.length(); i++) {
            int potentialGene = Math.abs(random.nextInt(r, MIN_OR_PER_DAY, MAX_OR_PER_DAY));
        	if(totalWeekly + potentialGene <= MIN_WEEKLY || totalWeekly + potentialGene <= MAX_WEEKLY){
        		if(i == super.length()-1 && totalWeekly + potentialGene < MIN_WEEKLY){
        			potentialGene = potentialGene + (MIN_WEEKLY-totalWeekly);
        		}
        		genes = genes.append(DeptTeamGene.of(potentialGene));
        		totalWeekly = totalWeekly + potentialGene;
            }else{
            	genes = genes.append(DeptTeamGene.of(0));
            }
        }
        
        return new GynecologyChromosome(genes);
    }
}
