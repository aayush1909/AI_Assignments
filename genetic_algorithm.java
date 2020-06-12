import java.util.*;

class knapsack {

    private int population[] = new int[5];
    private int weight;
    private int profit;

    public knapsack() {
    }

    public knapsack(int[] population) {

        this.population = population;
    }

    public knapsack(int[] population1, int[] population2) {
        for (int i = 0; i < 5; i++) {

            if (i < 2)
                population[i] = population1[i];
            else
                population[i] = population2[i];
        }
    }

    public void updateWeight(int[] knapsackWeight) {

        weight = 0;

        for (int i = 0; i < 5; i++)
            weight = weight + (knapsackWeight[i] * population[i]);
    }

    public void updateProfit(int[] knapsackProfit) {

        profit = 0;

        for (int i = 0; i < 5; i++)
            profit = profit + (knapsackProfit[i] * population[i]);

    }

    public int[] getPopulation() {

        return population;

    }

    public int getWeight() {

        return weight;

    }

    public int getProfit() {

        return profit;

    }

    public void mutate() {

        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(5);

        if (population[random] == 1)
            population[random] = 0;
        else
            population[random] = 1;

    }

    public void displayKnapsack() {

        System.out.println(population[0] + " " + population[1] + " " + population[2] + " " + population[3] + " " + population[4] + " " + weight + " " + profit);

    }

}

class Genetic {

    public static void main (String[] args) {

        int N = 5;
        int weight[] = {10, 2, 5, 7, 15};
        int profit[] = {12, 8, 4, 6, 20};
        int maxWeight = 25;
        int minProfit = 25;

        System.out.println("=========== Random + Selection ===========");

        knapsack P1 = new knapsack(generateRandom(weight, profit, maxWeight, minProfit));
        P1.updateProfit(profit);
        P1.updateWeight(weight);
        P1.displayKnapsack();
        knapsack P2 = new knapsack(generateRandom(weight, profit, maxWeight, minProfit));
        P2.updateProfit(profit);
        P2.updateWeight(weight);
        P2.displayKnapsack();
        knapsack P3 = new knapsack(generateRandom(weight, profit, maxWeight, minProfit));
        P3.updateProfit(profit);
        P3.updateWeight(weight);
        P3.displayKnapsack();
        knapsack P4 = new knapsack(generateRandom(weight, profit, maxWeight, minProfit));
        P4.updateProfit(profit);
        P4.updateWeight(weight);
        P4.displayKnapsack();
        knapsack P5 = new knapsack(generateRandom(weight, profit, maxWeight, minProfit));
        P5.updateProfit(profit);
        P5.updateWeight(weight);
        P5.displayKnapsack();
        knapsack P6 = new knapsack(generateRandom(weight, profit, maxWeight, minProfit));
        P6.updateProfit(profit);
        P6.updateWeight(weight);
        P6.displayKnapsack();

        System.out.println("\n=========== Crossover ===========");

        knapsack P12 = new knapsack(P1.getPopulation(), P2.getPopulation());
        P12.updateProfit(profit);
        P12.updateWeight(weight);
        P12.displayKnapsack();
        knapsack P21 = new knapsack(P2.getPopulation(), P1.getPopulation());
        P21.updateProfit(profit);
        P21.updateWeight(weight);
        P21.displayKnapsack();
        knapsack P34 = new knapsack(P3.getPopulation(), P4.getPopulation());
        P34.updateProfit(profit);
        P34.updateWeight(weight);
        P34.displayKnapsack();
        knapsack P43 = new knapsack(P4.getPopulation(), P3.getPopulation());
        P43.updateProfit(profit);
        P43.updateWeight(weight);
        P43.displayKnapsack();
        knapsack P56 = new knapsack(P5.getPopulation(), P6.getPopulation());
        P56.updateProfit(profit);
        P56.updateWeight(weight);
        P56.displayKnapsack();
        knapsack P65 = new knapsack(P6.getPopulation(), P5.getPopulation());
        P65.updateProfit(profit);
        P65.updateWeight(weight);
        P65.displayKnapsack();

        System.out.println("\n=========== Mutation ===========");

        knapsack PM12 = P12;
        PM12.mutate();
        PM12.updateProfit(profit);
        PM12.updateWeight(weight);
        PM12.displayKnapsack();
        knapsack PM21 = P21;
        PM21.mutate();
        PM21.updateProfit(profit);
        PM21.updateWeight(weight);
        PM21.displayKnapsack();
        knapsack PM34 = P34;
        PM34.mutate();
        PM34.updateProfit(profit);
        PM34.updateWeight(weight);
        PM34.displayKnapsack();
        knapsack PM43 = P43;
        PM43.mutate();
        PM43.updateProfit(profit);
        PM43.updateWeight(weight);
        PM43.displayKnapsack();
        knapsack PM56 = P56;
        PM56.mutate();
        PM56.updateProfit(profit);
        PM56.updateWeight(weight);
        PM56.displayKnapsack();
        knapsack PM65 = P65;
        PM65.mutate();
        PM65.updateProfit(profit);
        PM65.updateWeight(weight);
        PM65.displayKnapsack();

        System.out.print("\nProfit: " + PM65.getProfit());

    }

    private static int[] generateRandom(int[] weight, int[] profit, int maxWeight, int minProfit) {

        int random[] = new int[5];
        int knapsackProfit = 0;
        int knapsackWeight = 0;
        Random randomGenerator = new Random();

        while ((knapsackProfit < minProfit) || (knapsackWeight > maxWeight)) {

            knapsackProfit = 0;
            knapsackWeight = 0;

            for (int i = 0; i < 5; i++) {

                random[i] = randomGenerator.nextInt(2);
                knapsackProfit += random[i] * profit[i];
                knapsackWeight += random[i] * weight[i];
            }
        }
        return random;
    }
}
