# GeneticQueens
For this LinkedIn Queens problem, we use Genetic Algorithms to solve the puzzle.
For a problem as simple as this one, it is not necessary to encapsulate the information in deeper levels than the genome itself. This example
uses an `ArrayList<Integer>` as a genetic information container.

### [ChessGAInitializer](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Queens/GA/QueensGAInitializer.java)
This class initializes a new individual from scratch.
It uses `ArrayListGenome` and integers to store the information of a randomly (but in an "intelligent" way) initialized individual.
````java
public Individual initializeIndividual();
````
In this case, the row position is implicitly stored based on the position of the integer in the `Genome`
(`ArrayList<Integer>`). Each integer represents the column position of the queen in that row.

````mermaid
flowchart LR
    subgraph  ide0 [Genome]
        subgraph ide1 [ArrayList &ltInteger&gt]
            Integer1 o--o Integer2 o--o Integer3 o--o IntegerX o--o IntegerN
        end
    end
````

As an initialization strategy, we first assign the row and column from the colors with lesser options. The concrete row and col
are selected randomly from the available options. The available options are defined as: The color cells, which its row 
and col have not been picked yet by other colors. I case that there is no option (because we chose wrongly for the 
random previous cells) we select one random combination of the remaining row, col combinations that have not been picked jet. 
In that sense, we make sure that the initialized individual contains all rows and all columns, but we are not sure that 
all colors have been used.

### [QueensFeasibilityFunction](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Queens/GA/Evaluable/QueensFeasibilityFunction.java)
This function evaluates that all rows, columns and colors have been used. It also checks how many collisions in diagonal exist for the neighbor column.


## In this folder:
This folder contains two different classes and two packages that define the structures required for solving the problem.
These classes are used as main and controller.
1. [Controller](#controller): Controller that communicates the UI with the GA.
2. [RunLinkedInQueens](#runlinkedinQueens): The main class for instantiation of the frontend.

The folders contain the problem information.
1. [GA](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Queens/GeneticQueens/GA/):
    Package with the GA model built using the OptimizationLib.mm module.
2. [UI](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Queens/GeneticQueens/UI/):
   Package with the UI for the GA LinkedIn Queens problem.

### [Controller](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Queens/GeneticQueens/Controller.java):
This Class controls the communication between the UI and the GA running in the back.

### [RunLinkedInQueens](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Queens/GeneticQueens/RunLinkedInQueens.java):
This is the main class. Is where the run starts. As simple as instantiate the UI.


## Results
We set the stopping condition to a max number of iterations (1000). The problem gets solved generally much sooner.
The optimization output looks like:
`````
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| Iteration | CurrentMin | CurrentMax | HistoricalMin | HistoricalMax | MeanFitness | StandardDev | P0    | P25   | P50   | P75   | P100  | StepGradient(u/iter) | TimeGradient(u/s) | ElapsedTime(s) | FitnessUpdate | SolutionFound |
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| 0         | 1.0000     | 7.0000     | 1.0000        | 7.0000        | 3.7800      | 1.2376      | 1.000 | 3.000 | 4.000 | 4.000 | 7.000 |                      |                   | 77.0098        | updated       | False         |
| 10        | 1.0000     | 10.0000    | 1.0000        | 10.0000       | 5.1300      | 2.3944      | 1.000 | 3.000 | 6.000 | 7.000 | 10.00 | -0.1350              | -4079.6468        | 0.0331         | updated       | False         |
| 20        | 1.0000     | 10.0000    | 1.0000        | 10.0000       | 4.9500      | 2.4223      | 1.000 | 3.000 | 5.000 | 7.000 | 10.00 | 0.0180               | 1225.5486         | 0.0147         | updated       | False         |
| 30        | 1.0000     | 11.0000    | 1.0000        | 11.0000       | 4.7600      | 2.3838      | 1.000 | 3.000 | 5.000 | 6.000 | 11.00 | 0.0190               | 1244.9628         | 0.0153         | updated       | False         |
| 40        | 1.0000     | 10.0000    | 1.0000        | 11.0000       | 4.7900      | 2.1693      | 1.000 | 3.000 | 5.000 | 6.000 | 10.00 | -0.0030              | -207.7318         | 0.0144         | updated       | False         |
| 50        | 1.0000     | 10.0000    | 1.0000        | 11.0000       | 4.9000      | 2.4269      | 1.000 | 3.000 | 5.000 | 7.000 | 10.00 | -0.0110              | -1345.7139        | 0.0082         | updated       | False         |
| 60        | 1.0000     | 11.0000    | 1.0000        | 11.0000       | 5.4400      | 2.3551      | 1.000 | 4.000 | 6.000 | 7.000 | 11.00 | -0.0540              | -7479.5352        | 0.0072         | updated       | False         |
| 70        | 1.0000     | 10.0000    | 1.0000        | 11.0000       | 4.8300      | 2.2586      | 1.000 | 3.750 | 5.000 | 6.000 | 10.00 | 0.0610               | 7404.7998         | 0.0082         | updated       | False         |
| 80        | 1.0000     | 10.0000    | 1.0000        | 11.0000       | 4.9300      | 2.5427      | 1.000 | 3.000 | 5.000 | 7.000 | 10.00 | -0.0100              | -1308.8491        | 0.0076         | updated       | False         |
| 90        | 1.0000     | 10.0000    | 1.0000        | 11.0000       | 5.1400      | 2.5061      | 1.000 | 3.750 | 5.000 | 7.000 | 10.00 | -0.0210              | -2219.9670        | 0.0095         | updated       | False         |
| 100       | 1.0000     | 11.0000    | 1.0000        | 11.0000       | 5.2700      | 2.5014      | 1.000 | 3.000 | 6.000 | 7.000 | 11.00 | -0.0130              | -1398.9927        | 0.0093         | updated       | False         |
| 110       | 1.0000     | 10.0000    | 1.0000        | 11.0000       | 4.9300      | 2.2283      | 1.000 | 3.000 | 5.000 | 7.000 | 10.00 | 0.0340               | 4072.3928         | 0.0083         | updated       | False         |
| 120       | 1.0000     | 13.0000    | 1.0000        | 13.0000       | 5.0500      | 2.4387      | 1.000 | 3.000 | 5.000 | 7.000 | 13.00 | -0.0120              | -1454.7218        | 0.0082         | updated       | False         |
/
|Constraints= 0.0000
|ObjectiveFunction= 0.0000
|Fitness= 0.0000
\
 _  _  _  _  _  _  Q  _  _ 
 _  _  Q  _  _  _  _  _  _ 
 _  _  _  _  _  _  _  Q  _ 
 _  _  _  _  Q  _  _  _  _ 
 Q  _  _  _  _  _  _  _  _ 
 _  _  _  Q  _  _  _  _  _ 
 _  _  _  _  _  _  _  _  Q 
 _  Q  _  _  _  _  _  _  _ 
 _  _  _  _  _  Q  _  _  _ 
`````

## Comment:
This problem is solved using the power of Genetic Algorithms. It is just a guide that the user can follow 
to build its own problems.

In this example, we didn't conduct an extensive optimization of the initialization, crossover nor mutation. 
Nevertheless, we were able to get in a pretty sort time the solutions for the scenario. :smirk_cat:
