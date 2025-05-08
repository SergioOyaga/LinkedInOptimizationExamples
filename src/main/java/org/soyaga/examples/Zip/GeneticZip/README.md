# GeneticZip
For this LinkedIn Zip problem, we use Genetic Algorithms to solve the puzzle.


### [ZipGAInitializer](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/GA/ZipGAInitializer.java)
This class initializes a new individual from scratch.
It uses `ArrayListGenome` and SquareButtons to store the information of a randomly (but in an "intelligent" way) initialized individual.
````java
public Individual initializeIndividual();
````
In this case, the Array position in the genome implicitly indicates the orders of the SquareButtons `Genome`(`ArrayList<SquareButtons>`).

````mermaid
flowchart LR
  subgraph  ide1 [Genome]
    direction LR
        subgraph ide3[SquareButton1]
            direction LR
            Row1
            Col1
        end
        subgraph ide4[SquareButtonX]
            direction LR
            RowX
            ColX
        end
        subgraph ide5[SquareButtonN]
            direction LR
            RowN
            ColN
        end
      ide3-->ide4-->ide5
    style ide1 fill:#0405
    style ide3 fill:#4005
    style ide4 fill:#4005
    style ide5 fill:#4005
  end
````

As an initialization strategy, we first assign Start and end SquareButtons. Then, starting from the first, we decide randomly the next SquareButtons, by checking available connected SquareButtons. In no available SquareButton remains, then a new random SquareButtons is selected from the remaining. In a way, we force that the cell relations must be followed.

## In this folder:
This folder contains two different classes and two packages that define the structures required for solving the problem.
These classes are used as main and controller.
1. [Controller](#controller): Controller that communicates the UI with the GA.
2. [RunLinkedInZip](#runlinkedinZip): The main class for instantiation of the frontend.


The folders contain the problem information.
1. [GA](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/GeneticZip/GA/):
    Package with the GA model built using the OptimizationLib.ga module.
2. [UI](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/GeneticZip/UI/):
   Package with the UI for the GA LinkedIn Zip problem.

### [Controller](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/GeneticZip/Controller.java):
This Class controls the communication between the UI and the GA model running in the back.

### [RunLinkedInZip](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/GeneticZip/RunLinkedInZip.java):
This is the main class. Is where the run starts. As simple as instantiate the UI.


## Results
We set the stopping condition to a max number of iterations (1000).
The optimization output looks like:
`````
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| Iteration | CurrentMin | CurrentMax | HistoricalMin | HistoricalMax | MeanFitness | StandardDev | P0    | P25   | P50   | P75   | P100  | StepGradient(u/iter) | TimeGradient(u/s) | ElapsedTime(s) | FitnessUpdate | SolutionFound |
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| 0         | 2.0000     | 16.0000    | 2.0000        | 16.0000       | 9.3060      | 2.3320      | 2.000 | 8.000 | 9.000 | 11.00 | 16.00 |                      |                   | 34.3911        | updated       | False         |
| 10        | 2.0000     | 51.0000    | 2.0000        | 51.0000       | 20.0710     | 10.4141     | 2.000 | 11.00 | 21.00 | 28.00 | 51.00 | -1.0765              | -38345.0060       | 0.2807         | updated       | False         |
| 20        | 2.0000     | 50.0000    | 2.0000        | 51.0000       | 19.4520     | 10.8090     | 2.000 | 10.00 | 20.00 | 27.00 | 50.00 | 0.0619               | 3223.8559         | 0.1920         | updated       | False         |
| 30        | 2.0000     | 56.0000    | 2.0000        | 56.0000       | 18.6950     | 10.6719     | 2.000 | 10.00 | 19.00 | 26.00 | 56.00 | 0.0757               | 4894.4493         | 0.1547         | updated       | False         |
| 40        | 2.0000     | 55.0000    | 2.0000        | 56.0000       | 18.6710     | 10.4160     | 2.000 | 10.00 | 20.00 | 26.25 | 55.00 | 0.0024               | 174.3563          | 0.1376         | updated       | False         |
| 50        | 2.0000     | 45.0000    | 2.0000        | 56.0000       | 17.2410     | 9.8710      | 2.000 | 9.000 | 18.00 | 24.00 | 45.00 | 0.1430               | 10799.4378        | 0.1324         | updated       | False         |
| 60        | 2.0000     | 48.0000    | 2.0000        | 56.0000       | 17.8330     | 10.3127     | 2.000 | 9.000 | 18.00 | 26.00 | 48.00 | -0.0592              | -4662.9961        | 0.1270         | updated       | False         |
| 70        | 2.0000     | 44.0000    | 2.0000        | 56.0000       | 18.2000     | 10.4369     | 2.000 | 10.00 | 19.00 | 26.00 | 44.00 | -0.0367              | -2550.1465        | 0.1439         | updated       | False         |
| 80        | 2.0000     | 53.0000    | 2.0000        | 56.0000       | 17.6520     | 10.2207     | 2.000 | 9.000 | 19.00 | 25.00 | 53.00 | 0.0548               | 4413.0023         | 0.1242         | updated       | False         |
| 90        | 2.0000     | 50.0000    | 2.0000        | 56.0000       | 18.0710     | 10.3934     | 2.000 | 10.00 | 19.00 | 26.00 | 50.00 | -0.0419              | -3348.2821        | 0.1251         | updated       | False         |
| 100       | 2.0000     | 45.0000    | 2.0000        | 56.0000       | 17.9800     | 10.4868     | 2.000 | 9.000 | 19.00 | 26.00 | 45.00 | 0.0091               | 713.7955          | 0.1275         | updated       | False         |
/
|Constraints= 0.0000
|ObjectiveFunction= 0.0000
|Fitness= 0.0000
\
 34  33  32  31  0  3  4 
 35  36  37  30  1  2  5 
 40  39  38  29  8  7  6 
 41  26  27  28  9  10  11 
 42  25  24  23  22  21  12 
 43  48  47  18  19  20  13 
 44  45  46  17  16  15  14 

| 110       | 0.0000     | 48.0000    | 0.0000        | 56.0000       | 18.0620     | 10.5990     | 0.000 | 9.000 | 19.00 | 26.00 | 48.00 | -0.0082              | -575.9568         | 0.1424         | updated       | True          |


`````

## Comment:
This problem is solved using the power of Genetic Algorithms. It is just a guide that the user can follow 
to build its own problems.

In this example, we didn't conduct an extensive optimization of the initialization, crossover nor mutation.
Nevertheless, we were able to get in a pretty sort time the solutions for the scenario. :smirk_cat:
