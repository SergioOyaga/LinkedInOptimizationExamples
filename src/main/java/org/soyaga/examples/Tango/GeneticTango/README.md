# GeneticTango
For this LinkedIn Tango problem, we use Genetic Algorithms to solve the puzzle.


### [TangoGAInitializer](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/GA/TangoGAInitializer.java)
This class initializes a new individual from scratch.
It uses `ArrayListGenome`, `ArrayListChromosomes` and booleans to store the information of a randomly (but in an "intelligent" way) initialized individual.
````java
public Individual initializeIndividual();
````
In this case, the row position is implicitly stored based on the position in the `Genome`(`ArrayList<ArrayListChromosomes>`). Each column is implicitly stores on the position of the `Chromosome` (`ArrayListChromosomes<Boolean>`). Each Boolean represents a sun (true) or a moon (false).

````mermaid
flowchart LR
  subgraph  ide1 [Genome]
    direction LR
    subgraph ide2 [Chromosomes]
        direction LR
        subgraph ide3[Chromosome1]
            direction LR
            Bool1
        end
        subgraph ide4[ChromosomeX]
            direction LR
            BoolX
        end
        subgraph ide5[ChromosomeN]
            direction LR
            BoolN
        end
        Bool1-->BoolX-->BoolN
    end
    style ide1 fill:#0405
    style ide2 fill:#0045
    style ide3 fill:#4005
    style ide4 fill:#4005
    style ide5 fill:#4005
  end
````

As an initialization strategy, we first assign fixed cells and the forced cells due to cell relations. Then we decide randomly the remaining cells, and checking if those cells force other cells every time. In a way, we force that the cell relations must be followed and the fixed cells are also selected accordingly.

## In this folder:
This folder contains two different classes and two packages that define the structures required for solving the problem.
These classes are used as main and controller.
1. [Controller](#controller): Controller that communicates the UI with the GA.
2. [RunLinkedInTango](#runlinkedinTango): The main class for instantiation of the frontend.


The folders contain the problem information.
1. [GA](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/GeneticTango/GA/):
    Package with the GA model built using the OptimizationLib.mm module.
2. [UI](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/GeneticTango/UI/):
   Package with the UI for the GA LinkedIn Tango problem.

### [Controller](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/GeneticTango/Controller.java):
This Class controls the communication between the UI and the GA running in the back.

### [RunLinkedInTango](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/GeneticTango/RunLinkedInTango.java):
This is the main class. Is where the run starts. As simple as instantiate the UI.


## Results
We set the stopping condition to a max number of iterations (1000). The problem gets solved generally much sooner.
The optimization output looks like:
`````
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| Iteration | CurrentMin | CurrentMax | HistoricalMin | HistoricalMax | MeanFitness | StandardDev | P0    | P25   | P50   | P75   | P100  | StepGradient(u/iter) | TimeGradient(u/s) | ElapsedTime(s) | FitnessUpdate | SolutionFound |
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| 0         | 6.0000     | 35.0000    | 6.0000        | 35.0000       | 19.5380     | 4.6096      | 6.000 | 16.00 | 19.00 | 23.00 | 35.00 |                      |                   | 35.4754        | updated       | False         |
| 10        | 2.0000     | 46.0000    | 2.0000        | 46.0000       | 19.5790     | 8.6886      | 2.000 | 14.00 | 20.00 | 26.00 | 46.00 | -0.0041              | -118.8261         | 0.3450         | updated       | False         |
/
|Constraints= 0.0000
|ObjectiveFunction= 0.0000
|Fitness= 0.0000
\
 O  )  O  )  )  O 
 )  )  O  )  O  O 
 O  O  )  O  )  ) 
 )  O  )  O  )  O 
 O  )  O  )  O  ) 
 )  O  )  O  O  ) 

| 20        | 0.0000     | 45.0000    | 0.0000        | 46.0000       | 17.8990     | 9.5049      | 0.000 | 12.00 | 18.00 | 24.00 | 45.00 | 0.1680               | 8193.7710         | 0.2050         | updated       | True          |

`````

## Comment:
This problem is solved using the power of Genetic Algorithms. It is just a guide that the user can follow 
to build its own problems.

In this example, we didn't conduct an extensive optimization of the initialization, crossover nor mutation.
Nevertheless, we were able to get in a pretty sort time the solutions for the scenario. :smirk_cat:
