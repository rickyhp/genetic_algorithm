package chromosome;
import java.util.Random;

import org.jenetics.Chromosome;
import org.jenetics.util.ISeq;
import org.jenetics.util.RandomRegistry;

import base.DeptTeamChromosome;
import base.DeptTeamGene;

import org.jenetics.internal.math.random;

public class OtoralyngologyChromosome extends DeptTeamChromosome {
	final int MIN_OR_PER_DAY = 0;
	final int MAX_OR_PER_DAY = 1;
	final int MIN_WEEKLY = 2;
	final int MAX_WEEKLY = 4;
	
	public OtoralyngologyChromosome(ISeq<DeptTeamGene> genes) {
		super(genes);
	}
	
	public static OtoralyngologyChromosome of(ISeq<DeptTeamGene> genes) {
        return new OtoralyngologyChromosome(genes);
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
        
        return new OtoralyngologyChromosome(genes);
    }

}
