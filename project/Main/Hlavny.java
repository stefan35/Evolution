package Main;

import java.util.*;
import Evolution.CrossGenes;
import Map.Map;
import Population.Individual;
import Population.VirtualMachine;

public class Hlavny {

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		VirtualMachine machine = new VirtualMachine();	
		CrossGenes cros = new CrossGenes();
		ArrayList<Individual> population = new ArrayList<Individual>();
		ArrayList<Individual> base_population = new ArrayList<Individual>();
		String select = null;
		int loop = 0;
		
		//Vytvorenie prvej populacie
		population = createPopulation(population);
		
		//Cyklus ktory ide do 500 populacii, postupne posiela jedincov do stroja
		//Po prejdeni vsetkych jedincov z populacie nasleduje krizenie
		//Ak sa vytvori 500 generaci program sa spyta ci ma pokracovat alebo ukoncit
        for(int i = 1; i <= 501 + loop; i++)
        {
        	System.out.println("Generation: "+ i);	
        	base_population = population;
            for(int j = 0; j < 60; j++)
            {	
                machine.runMachine(population.get(j));
                if(checkTreasures(population, j) == true)
                {
                	 writeOutput(population);
                	return;
                }
            }
            writeOutput(population);
            if (i == 500 + loop)
        	{
        		System.out.println("Create new generations ? y-Yes/n-No: ");
        		select = scanner.nextLine();
        		if(select.equals("n") == true)
            		break;
        		else
        			loop = loop + 500;	
        	} 
            population = cros.evolute(population, base_population);      
        }   
	}
	
	public static ArrayList<Individual> createPopulation(ArrayList<Individual> population) 
	{
		Random rand = new Random();
		int[] machine_genes;
		Individual individual;
		
		//Vytvorenie 60 jedincov a ich geny, tito jedinci budu tvorit zaciatocnu populaciu
		for (int j = 0; j < 60; j++)
		{        
			machine_genes = new int[64];
            for (int i=0; i<64; i++)
            	machine_genes[i] = rand.nextInt(255);
            
            individual = new Individual();
            individual.setMachineGenes(machine_genes);
            population.add(individual);
        }
		return population;
	}
	
	//Vypisanie najlpesieho jedinca z kazdej generacie
	public static void writeOutput(ArrayList<Individual> individual) 
	{
		Individual best_individual = new Individual();
		best_individual.setFitness(0);
		for(int i = 0; i < individual.size(); i++)
		{
			if(best_individual.getFitness() <= individual.get(i).getFitness())
				best_individual = individual.get(i);
		}
    	System.out.println("Best path: " + best_individual.getPath());
    	System.out.println("Best fitness: " + best_individual.getFitness());
    	System.out.println("Found treasures: " + best_individual.getTreasuresFound());
	}
	
	//Skontrolovanie ci sa uz nenasli vsetky poklady
	public static boolean checkTreasures(ArrayList<Individual> population, int i) {
		Map map = new Map();
		
		if(population.get(i).getTreasuresFound() == map.count_treaserues)
		{
        	System.out.println("Found the best path with all treasures: " + population.get(i).getPath());
        	return true;
		}
		else
			return false;
	}
}
