import java.util.ArrayList;
import org.jenetics.Genotype;
import org.jenetics.Optimize;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.EvolutionStatistics;
import org.jenetics.util.Factory;
import org.jenetics.util.ISeq;

import base.DeptTeamChromosome;
import base.DeptTeamGene;
import chromosome.GeneralsurgeryChromosome;
import chromosome.GynecologyChromosome;
import chromosome.OpthalmologyChromosome;
import chromosome.OralChromosome;
import chromosome.OtoralyngologyChromosome;

public class ORScheduling {
	//static int[][] surgicalTeams = {{2,2,2,2,2},{3,3,3,3,3},{0,1,0,1,0},{1,1,1,1,1},{6,6,6,6,6}};
	static int[] minORPerTeamDay = {0,0,0,0,0};
	static int[] maxORPerTeamDay = {2,3,1,1,6};
	static int[] minWeekly = {3,12,2,2,18};
	static int[] maxWeekly = {6,18,3,4,25};
	static double[] weeklyTargets = {39.4,117,19.9,26.3,189};
	/*static double[] targetPct = 
			{(8*(surgicalTeams[0][0]+surgicalTeams[0][1]+surgicalTeams[0][2]+surgicalTeams[0][3]+surgicalTeams[0][4]))/weeklyTargets[0],
			 (8*(surgicalTeams[1][0]+surgicalTeams[1][1]+surgicalTeams[1][2]+surgicalTeams[1][3]+surgicalTeams[1][4]))/weeklyTargets[1],
			 (8*(surgicalTeams[2][0]+surgicalTeams[2][1]+surgicalTeams[2][2]+surgicalTeams[2][3]+surgicalTeams[2][4]))/weeklyTargets[2],
			 (8*(surgicalTeams[3][0]+surgicalTeams[3][1]+surgicalTeams[3][2]+surgicalTeams[3][3]+surgicalTeams[3][4]))/weeklyTargets[3],
			 (8*(surgicalTeams[4][0]+surgicalTeams[4][1]+surgicalTeams[4][2]+surgicalTeams[4][3]+surgicalTeams[4][4]))/weeklyTargets[4]
			};*/
	static int MAX_OR_PER_DAY = 10;
	
	private static double eval(final Genotype<DeptTeamGene> gt) {
		double totalWeek = 0.0;
		double totalWeekHours = 0.0;
		double targetPct = 0.0;
		double sumOfTargetPct = 0.0;
		
		int totalORPerDay = 0;
		// check Max OR per day
		for(int i = 0 ; i < gt.getChromosome().length() ; i++){
			totalORPerDay = gt.getChromosome(0).getGene(i).intValue() +
							gt.getChromosome(1).getGene(i).intValue() +
							gt.getChromosome(2).getGene(i).intValue() +
							gt.getChromosome(3).getGene(i).intValue() +
							gt.getChromosome(4).getGene(i).intValue();
			if(totalORPerDay > MAX_OR_PER_DAY){
				return 0; // return 0 so this generation will not be used
			}
		}
		
		for(int j = 0 ; j < gt.length() ; j++){
			for(int i = 0 ; i < gt.getChromosome().length() ; i++){
				totalWeek += gt.getChromosome(j).getGene(i).intValue();
				totalWeekHours = totalWeek * 8;
			}
			
			targetPct = totalWeekHours / weeklyTargets[j];
			sumOfTargetPct += targetPct;
			
			totalWeek = 0;
			totalWeekHours = 0;
			targetPct = 0.0;
		}
				
		//System.out.println(gt);
		//System.out.println("sumOfTargetPct : " + sumOfTargetPct);

        return sumOfTargetPct; 
    }
	
	static DeptTeamChromosome constructChromosome(String deptName, int min, int max, int minWeekly, int maxWeekly){
		int total = 0;
		
		ArrayList<DeptTeamGene> arrGene = new ArrayList<DeptTeamGene>();
		ISeq<DeptTeamGene> tempIseq = ISeq.empty();
		
		for(int i = 0 ; i < 5 ; i ++){
			DeptTeamGene gene = DeptTeamGene.of(min, max);
			
			if(total + gene.intValue() <= minWeekly || total + gene.intValue() <= maxWeekly){
				total = total + gene.intValue();
				arrGene.add(gene);
			}else{
				DeptTeamGene gene0 = DeptTeamGene.of(0);
				arrGene.add(gene0);
			}
			
		}
		
		tempIseq = ISeq.of(arrGene);
		
		if(deptName.equals("OPTHALMOLOGY")){
			OpthalmologyChromosome oc = OpthalmologyChromosome.of(tempIseq);
			return oc;
		}else if(deptName.equals("GYNECOLOGY")){
			GynecologyChromosome gc = GynecologyChromosome.of(tempIseq);
			return gc;
		}else if(deptName.equals("ORAL")){
			OralChromosome oc = OralChromosome.of(tempIseq);
			return oc;
		}else if(deptName.equals("OTORALYNGOLOGY")){
			OtoralyngologyChromosome oc = OtoralyngologyChromosome.of(tempIseq);
			return oc;
		}else if(deptName.equals("GENERAL")){
			GeneralsurgeryChromosome gc = GeneralsurgeryChromosome.of(tempIseq);
			return gc;
		}else{
			DeptTeamChromosome ic = DeptTeamChromosome.of(
						tempIseq
					);
			return ic;
		}
		
	}
	
    public static void main(String args[]){	
    	// One chromosome shares the same constraint e.g. min, max
    	// Generating only valid Genotypes for a given problem, can be quite challenging. For implementing an more advanced Genotype generation mechanism, you currently have two possibilities:
    	//1.) Create the Engine instance with your own Genotype factory (Factory<Genotype<>>), which only produces valid Genotypes. Creating new Genotypes from a template is meant as a convenient short-cut.
    	//2.) Implement your own Gene/Chromosome pair and implement proper newInstance factory methods.
    	
    	/*final Factory<Genotype<IntegerGene>> gtf = Genotype.of(
    			IntegerChromosome.of(minORPerTeamDay[0], maxORPerTeamDay[0], 5),
    			IntegerChromosome.of(minORPerTeamDay[1], maxORPerTeamDay[1], 5),
    			IntegerChromosome.of(minORPerTeamDay[2], maxORPerTeamDay[2], 5),
		    	IntegerChromosome.of(minORPerTeamDay[3], maxORPerTeamDay[3], 5),
		    	IntegerChromosome.of(minORPerTeamDay[4], maxORPerTeamDay[4], 5));
    	*/
    	final Factory<Genotype<DeptTeamGene>> gtf = Genotype.of(
    				constructChromosome("OPTHALMOLOGY", minORPerTeamDay[0], maxORPerTeamDay[0], minWeekly[0], maxWeekly[0]),
    				constructChromosome("GYNECOLOGY", minORPerTeamDay[1], maxORPerTeamDay[1], minWeekly[1], maxWeekly[1]),
    				constructChromosome("ORAL", minORPerTeamDay[2], maxORPerTeamDay[2], minWeekly[2], maxWeekly[2]),
    				constructChromosome("OTORALYNGOLOGY", minORPerTeamDay[3], maxORPerTeamDay[3], minWeekly[3], maxWeekly[3]),
    				constructChromosome("GENERAL", minORPerTeamDay[4], maxORPerTeamDay[4], minWeekly[4], maxWeekly[4])
    			);
    	
    	System.out.println(gtf);
    	
		final Engine<DeptTeamGene,Double> engine =
				Engine.builder(ORScheduling::eval, gtf)
				.optimize(Optimize.MAXIMUM)
				.build();
		
		final EvolutionStatistics<Double,?> statistics = EvolutionStatistics.ofNumber();
		
		final Genotype<DeptTeamGene> best = engine.stream()
				.limit(100)
				.peek(statistics)
				.collect(EvolutionResult.toBestGenotype());
		
		System.out.println(statistics);
		System.out.println(best);
		System.out.println(ORScheduling.eval(best));
	}
}
