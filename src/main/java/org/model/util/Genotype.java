package org.model.util;

import org.model.WorldSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genotype {

    private final int genLength = 8;
    Random rand = new Random();
    private List<Integer> genom;

    public List<Integer> getGenes() {
        return genom;
    }
    WorldSettings worldSettings;



    public List<Integer> randomGenom(){
        Random mutNum = new Random();
        List<Integer> randGenomList = new ArrayList<>();
        for(int i = 0; i<genLength; i++){
            int genomDigit = mutNum.nextInt(genLength);
            randGenomList.add(genomDigit);
        }
        return randGenomList;
    }

    public List<Integer> reproduceGeno(Animal a1, Animal a2, int e1, int e2) {

        double randomNum = rand.nextDouble();
        List<Integer> g1 = a1.getGenotype();
        List<Integer> g2 = a2.getGenotype();

        double parent1ratio = (double)e1 / (e1 + e2);
        double parent2ratio = (double)e2 / (e1 + e2);

        int g1length = (int) ((parent1ratio) * genLength);
        int g2length = (int) ((parent2ratio) * genLength);
//        System.out.println("Tu bedzie energia:");
//        System.out.println(e1);
//        System.out.println(e2);
//        System.out.println("Tutaj mam dlugosci g1,g2");
//        System.out.println(g1length);
//        System.out.println(g2length);
//        System.out.println(g1length+g2length);

        System.out.println(a1.getGenotype() + " " + a2.getGenotype());
        if (g1length+g2length< worldSettings.GENOTYPE_LENGTH){
            g1length+=1;
        }
        System.out.println(g1length + " " + g2length);
        List<Integer> GenoChild = new ArrayList<>();
        int j = 0;

        if (randomNum > 0.5) {
            //prawa strona silniejszego
            if(e1>e2){
                for(int i=0; i<g2length; i++){
                    GenoChild.add(g2.get(i));
                }
                for(int i=g2length; i<worldSettings.GENOTYPE_LENGTH; i++){
                    GenoChild.add(g1.get(i));
                }
            } else{
                for(int i=0; i<g1length; i++){
                    GenoChild.add(g1.get(i));
                }
                for(int i=g1length; i<worldSettings.GENOTYPE_LENGTH; i++){
                    GenoChild.add(g2.get(i));
                }
            }
        } else{
            if(e1>e2) {
                //teraz pierwszy jest silniejszy i bierzemy jego lewą strone
                for (int i = 0; i <g1length; i++) {
                    GenoChild.add(g1.get(i));

                }

                for(int i = g1length; i<worldSettings.GENOTYPE_LENGTH; i++){
                    GenoChild.add(g2.get(i));
                }
            } else {
                for(int i = 0; i<g2length; i++){
                    GenoChild.add(g2.get(i));
                }
                for(int i = g2length; i<worldSettings.GENOTYPE_LENGTH; i++){
                    GenoChild.add(g1.get(i));
                }

            }


        }


        Random rand1 = new Random();
        int mutCnt = rand1.nextInt(genLength);


        Random mutInd = new Random();
        Random mutNumber = new Random();

        for(int i = 0; i<=mutCnt; i++){
            int mutateIndex = mutInd.nextInt(genLength);
            int mutNumberSwap = mutNumber.nextInt(genLength);
            GenoChild.set(mutateIndex, mutNumberSwap);
        }

        ////wariant 1
        Random random_index = new Random();
        int gensToMutate = random_index.nextInt(worldSettings.MAXIMAL_GENS_TO_MUTATE) + worldSettings.MINIMAL_GENS_TO_MUTATE;


        Random rand2 = new Random();
        for(int i = 0; i<gensToMutate; i++){
            int randomMutIndex = mutNumber.nextInt(worldSettings.GENOTYPE_LENGTH - 1);
            double plusminus = rand2.nextDouble();
            int numToChange = GenoChild.get(randomMutIndex);


            if(GenoChild.get(randomMutIndex) == 0){
                GenoChild.set(randomMutIndex, 1);
            } else if (GenoChild.get(randomMutIndex)==8) {
                GenoChild.set(randomMutIndex,7);
            }else {
                if (plusminus > 0.5) {
                    GenoChild.set(randomMutIndex, numToChange + 1);
                } else {
                    GenoChild.set(randomMutIndex, numToChange - 1);
                }
            }

        }
        return GenoChild;
    }


    public Genotype(Animal a1, Animal a2, int n, WorldSettings worldSettings){
        this.worldSettings = worldSettings;
        this.genom = reproduceGeno(a1, a2, a1.getEnergy(),a2.getEnergy());
    }

    public Genotype(WorldSettings worldSettings){
        this.worldSettings = worldSettings;
        this.genom = randomGenom();
    }



    public List<Integer> getGenom() {
        return genom;
    }
}

