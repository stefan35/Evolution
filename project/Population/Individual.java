package Population;

public class Individual {
	private int[] machine_genes;
	private int found_treasures;
	private int fitness;
	private StringBuilder path;
	
	public int[] getMachineGenes() {
        return machine_genes;
    }

    public void setMachineGenes(int[] machine_genes) {
        this.machine_genes = machine_genes;
    }
    
    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
	
    public int getTreasuresFound() {
        return found_treasures;
    }

    public void setTreasuresFound(int found_treasures) {
        this.found_treasures = found_treasures;
    }
    
    public StringBuilder getPath() {
        return path;
    }

    public void setPath(StringBuilder path) {
        this.path = path;
    }

}
