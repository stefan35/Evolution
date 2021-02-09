package Evolution;

import java.util.ArrayList;
import java.util.Random;

import Population.Individual;

public class CrossGenes {

	public ArrayList<Individual> evolute(ArrayList<Individual> population, ArrayList<Individual> base_population) 
	{
		int[] cross_array = null;
		int parent1 = 0, parent2 = 0;
		ArrayList<Individual> new_population = new ArrayList<Individual>();
		
		//cyklus na vyber rodicov a nasledne krizenie, pridanie do populacie novych krizenych jedincov
		for(int i = 0; i < population.size(); i++) 
		{
			Individual individual = new Individual();
			parent1 = ruleta(population);
			parent2 = ruleta(population);
			cross_array = cross_genes(parent1, parent2, base_population);
			individual.setMachineGenes(cross_array);
			new_population.add(individual);		
		}
		new_population = mutate(new_population);
		return new_population;
	}
	
	//Z rodicov sa vyberu geny a daju sa do noveho jedinca
	private int[] cross_genes(int parent1, int parent2, ArrayList<Individual> population) 
	{
		Random random = new Random();
		int[] parent1_genes = population.get(parent1).getMachineGenes();
		int[] parent2_genes = population.get(parent2).getMachineGenes();
		int[] crossed = new int[64];
		int random_value = 0;
		for(int i = 0; i < 64; i++)
		{
			random_value = random.nextInt(2);
			if(random_value == 0)
				crossed[i] = parent1_genes[i];
			else if(random_value == 1)
				crossed[i] = parent2_genes[i];
		}
		return crossed;
	}
	
	//Vyberie sa rodic pre krizenie
	private int ruleta(ArrayList<Individual> population) 
	{
		Random random = new Random();
		int fitness_sum = 0;
		int random_value = 0;
		for(int i = 0; i < population.size(); i++) 
			fitness_sum += population.get(i).getFitness();
		
		if(fitness_sum < 0)
			fitness_sum = 200;
		
		random_value = random.nextInt(fitness_sum + 1);
		if(random_value > 0)
		{
			for(int i = 0; i < population.size(); i++)
			{
				random_value = random_value - population.get(i).getFitness();
				if(random_value < 0)
					return i;
			}
		}
		return population.size() - 1;
	}
	
	//Ak je nova populacia vytvorena tak sa nasledne zmutuju nahodne dvaja jedinci
	private ArrayList<Individual> mutate(ArrayList<Individual> population) 
	{
		Random random = new Random();
		int[] mutate_genes1 = new int[64];
		int[] mutate_genes2 = new int[64];
		int random1 = random.nextInt(59);
		int random2 = random.nextInt(59);
		System.out.println(random1);
		mutate_genes1 = population.get(random1).getMachineGenes();
		mutate_genes1 = population.get(random2).getMachineGenes();
		
		for(int j = 0; j < 64; j++)
		{
			mutate_genes1[j] = random.nextInt(255);
			mutate_genes2[j] = random.nextInt(255);
			if(mutate_genes1[j] > 255)
				mutate_genes1[j] = 0;
			if(mutate_genes2[j] > 255)
				mutate_genes2[j] = 0;
		}
		population.get(random1).setMachineGenes(mutate_genes1);
		population.get(random2).setMachineGenes(mutate_genes2);
		return population;
	}
}
