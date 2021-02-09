package Population;

import java.util.ArrayList;

import Map.Map;

public class VirtualMachine {
	
	public void runMachine(Individual individual)
	{	
		StringBuilder path = new StringBuilder();
		Map map = new Map();
		int[] machine_genes; 
        int start, map_size, map_side, found_treasure = 0;
        int machine_adress = 0, fitness = 0, instructions_counter = 0, machine_position = 0, gene, instruction; 
        String binary_instruction, incrementNumber = "00", decrementNumber = "01", jump = "10", writeNumber = "11";
        ArrayList<Integer> treasures = new ArrayList<>();
        
        start = map.start;
        map_size = map.size;
        machine_genes = individual.getMachineGenes();
          	
        //Vytvorenie pola s poziciami pokladov
        for(int i = 0; i < map.count_treaserues; i++)
        	treasures.add(map.positions_treasures[i]);
        
        while (instructions_counter < 500)
        {
        	//Zistenie instrukcie a adresy
        	gene = machine_genes[machine_position];
            instruction = gene>>6;
        	binary_instruction = Integer.toBinaryString(instruction);
        	machine_adress = gene & 63;
        
            if (binary_instruction.equals(incrementNumber) == true)
            {	
            	increment(machine_genes, machine_adress);   
            }		
            else if (binary_instruction.equals(decrementNumber) == true)
            {		
            	decrement(machine_genes, machine_adress);
            }
            else if (binary_instruction.equals(jump) == true)
            {		
                machine_position = machine_adress;           
            }
            else if (binary_instruction.equals(writeNumber) == true)
            {		
            	//Pomcou bitovej operacie zistim smer
                map_side = gene & 3;
                if (map_side == 0)
                {
                	start = start - map_size;
                    if (start < 0)
                    {	
                        fitness = fitness - 1;
                        break;
                    }
                    else 
                    {
                    	path.append("H");
                    	if (treasures.contains(start) == true)
                    	{	
                    		found_treasure++;
                    		fitness = fitness + 10;
                    		treasures = removeTreasure(start, treasures);
                    	}
                    }
                }
                if (map_side == 1)
                {
                    start = start + map_size;
                    if (start > ((map_size * map_size)-1))
                    {
                    	fitness = fitness - 1;
                        break;
                    }
                    else
                    {
                    	path.append("D");
                    	if (treasures.contains(start) == true)
                        {
                        	found_treasure++;
                        	fitness = fitness + 10;
                        	treasures = removeTreasure(start, treasures);
                        }
                    }
                }
                if (map_side == 2)
                {     
                    if (start == 6 || start == 13 || start == 20 || start == 27 || start == 34 || start== 41 || start == 48)
                    {
                    	fitness = fitness - 1;
                        break;
                    }
                    else
                    {
                    	start++;
                    	path.append("P");
                    	if (treasures.contains(start) == true)
                        {
                    		found_treasure++;
                    		fitness = fitness + 10;
                    		treasures = removeTreasure(start, treasures);
                        }
                    }  
                }
                if (map_side == 3) 
                {
                    if(start == 0 || start == 7 || start == 14 || start == 21 || start == 28 || start== 35 || start == 42)
                    {
                    	fitness = fitness - 1;
                        break;
                    }
                    else 
                    {
                    	start--;
                    	path.append("L");
                    	if (treasures.contains(start) == true)
                        {
                        	found_treasure++;
                        	fitness = fitness + 10;
                        	treasures = removeTreasure(start, treasures);		
                        }
                    }  
                }
            }
            machine_position++;
            instructions_counter++;
            if(machine_position > 63)
            	machine_position = 0;      
            if(found_treasure == map.count_treaserues)
            	break;
        }
        setVariable(found_treasure, path, fitness, individual);      		
    }

	private ArrayList<Integer> removeTreasure(int start, ArrayList<Integer> treasures) 
	{
		for(int i = 0; i < treasures.size(); i++)
    		if(treasures.get(i) == start)
    			treasures.remove(i);
		return treasures;
	}

	private void decrement(int[] machine_genes, int machine_adress) 
	{
		machine_genes[machine_adress]--;
        if (machine_genes[machine_adress] < 0) 
        	machine_genes[machine_adress] = 255;	
	}

	private void increment(int[] machine_genes, int machine_adress) 
	{
		machine_genes[machine_adress]++;
        if(machine_genes[machine_adress] > 255) 
        	machine_genes[machine_adress] = 0;  	
	}

	private Individual setVariable(int found_treasure, StringBuilder path, int fitness, Individual individual) 
	{
		individual.setTreasuresFound(found_treasure);
		individual.setPath(path);
		individual.setFitness(fitness);
        return individual;
	}
}
