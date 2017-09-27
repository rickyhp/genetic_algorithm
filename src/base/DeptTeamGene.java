package base;

import java.util.Random;

import org.jenetics.Gene;
import org.jenetics.internal.math.random;
import org.jenetics.util.ISeq;
import org.jenetics.util.MSeq;
import org.jenetics.util.RandomRegistry;

public class DeptTeamGene implements Gene<Integer, DeptTeamGene> {

    private Integer value;
   
    //private Integer total;
    
    private DeptTeamGene(Integer value) {
        this.value = value;
    }

    public static DeptTeamGene of(Integer value) {
        return new DeptTeamGene(value);
    }

    public static ISeq<DeptTeamGene> seq(Integer min, Integer max, int length) {
        Random r = RandomRegistry.getRandom();
        return MSeq.<DeptTeamGene>ofLength(length).fill(() ->
                new DeptTeamGene(random.nextInt(r, min, max))
        ).toISeq();
    }

    @Override
    public Integer getAllele() {
        return value;
    }

    @Override
    public DeptTeamGene newInstance() {
        final Random random = RandomRegistry.getRandom();
        return new DeptTeamGene(Math.abs(random.nextInt(2)));
    }

    @Override
    public DeptTeamGene newInstance(Integer integer) {
        return new DeptTeamGene(integer);
    }

    @Override
    public boolean isValid() {
    	if(value >= 0 && value <= 2){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public int intValue(){
    	return value;
    }
    
    public static DeptTeamGene of(final int min, final int max) {
    	Random r = RandomRegistry.getRandom();
		return new DeptTeamGene(random.nextInt(r, 0, 2));
	}

}