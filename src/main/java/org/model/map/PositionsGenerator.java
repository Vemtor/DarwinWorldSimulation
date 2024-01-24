package org.model.map;

import org.model.util.Vector2d;

import java.util.ArrayList;
import java.util.Random;

public class PositionsGenerator {
    private ArrayList<Vector2d> positions;
    private final Random rand;
    private final int maxHeightGen;
    private final int maxWidthGen;
    private final int numberOfObjectsToPut;
    private final int minHeightGen;
    private final int minWidthGen;

    public PositionsGenerator(int maxHeightGen, int maxWidthGen, int maxObjects, Random rand, int minHeightGen, int minWidthGen){
        this.maxHeightGen = maxHeightGen;
        this.maxWidthGen = maxWidthGen;
        this.numberOfObjectsToPut = maxObjects;
        this.minHeightGen = minHeightGen;
        this.minWidthGen = minWidthGen;
        this.rand = rand;
    }

    public PositionsGenerator(int maxHeightGen, int maxWidthGen, int maxObjects){
        this(maxHeightGen, maxWidthGen, maxObjects, new Random(), 0, 0);
    }

    public ArrayList<Vector2d> randPositionAnimal(){
        int cnt = 0;
        while(cnt<=this.numberOfObjectsToPut){
            int generatedX = rand.nextInt((this.maxWidthGen - this.minWidthGen) + 1) + this.minWidthGen;
            int generatedY = rand.nextInt((this.maxHeightGen - this.minHeightGen) + 1) + this.minHeightGen;
            positions.add(new Vector2d(generatedX, generatedY));
            cnt++;
        }
        return positions;
    }


    public ArrayList<Vector2d> randPositionPlant(){
        int cnt = this.numberOfObjectsToPut;
        while (cnt > 0){
            int y= (int) (minHeightGen + Math.random() * this.maxHeightGen);
            int x = (int) (minWidthGen + Math.random() * this.maxWidthGen);
            Vector2d position = new Vector2d(x, y);
            if(!positions.contains(position)){
                positions.add(position);
                cnt--;
            }
        }
        return positions;
    }

}
