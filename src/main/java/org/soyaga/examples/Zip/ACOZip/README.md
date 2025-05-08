# ACOZip
For this LinkedIn Zip problem, we use the Ant Colony Optimization (ACO) Algorithms to solve the puzzle.


### [ZipACO](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/ACO/ZipACO.java)
This class extends `StatsAntColonyAlgorithm`, making it an instance of `Optimizer`. In other words, this class can be optimized, and its results can be retrieved.

````java
public void optimize(){...}
public Object getResult(Object ... resultArgs){...}
````

The `optimize` method is inherited from the abstract class `StatsAntColonyAlgorithm`, which defines a basic optimization procedure. The `getResults` function computes the output as a string.

````mermaid
flowchart LR
  subgraph  ide1 [Solution]
    direction LR
        subgraph ide3[Node1]
            direction LR
            Row1
            Col1
        end
        subgraph ide4[NodeX]
            direction LR
            RowX
            ColX
        end
        subgraph ide5[NodeN]
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

The Ants build solutions by moving through the Graph edges. A solution is an Array of Nodes, that is a valid solution when:
1. Start and end Nodes are the first and last priority nodes.
2. The priority Nodes are selected in order.
3. All Nodes of the Graph have been visited only once.

## In this folder:
This folder contains two different classes and two packages that define the structures required for solving the problem.
These classes are used as main and controller.
1. [Controller](#controller): Controller that communicates the UI with the ACO.
2. [RunLinkedInZip](#runlinkedinZip): The main class for instantiation of the frontend.


The folders contain the problem information.
1. [ACO](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/ACOZip/ACO/):
    Package with the ACO model built using the OptimizationLib.aco module.
2. [UI](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/ACOZip/UI/):
   Package with the UI for the ACO LinkedIn Zip problem.

### [Controller](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/ACOZip/Controller.java):
This Class controls the communication between the UI and the ACO model running in the back.

### [RunLinkedInZip](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/ACOZip/RunLinkedInZip.java):
This is the main class. Is where the run starts. As simple as instantiate the UI.


## Results
We set the stopping condition to a max number of iterations (10000).
Usually, the solution is found much sooner.
The optimization output looks like:
`````
-----------------------------------------------------------------------------------------------------------------------------------------------------------------
| Iteration | CurrentMin | CurrentMax | HistoricalMin | MeanFitness | StandardDevFitness | MeanPheromone | StandardDevPheromone | FitnessUpdate | SolutionFound |
-----------------------------------------------------------------------------------------------------------------------------------------------------------------
| 1         | 410.00     | 16030.00   | 410.00        | 4736.80     | 4000.15            | 0.98          | 0.02                 | updated       | False         |
| 2         | 410.00     | 17710.00   | 410.00        | 5223.60     | 4244.54            | 0.97          | 0.03                 | updated       | False         |
| 3         | 110.00     | 16710.00   | 110.00        | 5030.80     | 3690.02            | 0.97          | 0.04                 | updated       | False         |
| 4         | 710.00     | 11410.00   | 110.00        | 3839.00     | 2710.47            | 0.95          | 0.06                 | updated       | False         |
| 5         | 10.00      | 13610.00   | 10.00         | 4481.00     | 3693.17            | 0.96          | 0.05                 | updated       | False         |
| 6         | 110.00     | 19350.00   | 10.00         | 4539.60     | 4117.81            | 0.95          | 0.07                 | updated       | False         |
| 7         | 10.00      | 14310.00   | 10.00         | 3466.60     | 3154.95            | 0.96          | 0.07                 | updated       | False         |
| 8         | 110.00     | 12740.00   | 10.00         | 2707.80     | 2408.36            | 0.94          | 0.08                 | updated       | False         |
| 9         | 10.00      | 12210.00   | 10.00         | 3856.40     | 2862.16            | 0.94          | 0.08                 | updated       | False         |
| 10        | 10.00      | 16550.00   | 10.00         | 3412.00     | 3470.06            | 0.96          | 0.08                 | updated       | False         |
| 11        | 10.00      | 15930.00   | 10.00         | 3625.20     | 2897.84            | 0.95          | 0.09                 | updated       | False         |
| 12        | 10.00      | 10610.00   | 10.00         | 2287.40     | 2311.21            | 0.93          | 0.09                 | updated       | False         |
| 13        | 10.00      | 6810.00    | 10.00         | 2582.40     | 1828.88            | 0.94          | 0.10                 | updated       | False         |
| 14        | 10.00      | 13320.00   | 10.00         | 2467.40     | 2248.59            | 0.93          | 0.11                 | updated       | False         |
| 15        | 10.00      | 6610.00    | 10.00         | 2041.60     | 1707.41            | 0.94          | 0.12                 | updated       | False         |
| 16        | 110.00     | 6510.00    | 10.00         | 2312.20     | 1572.09            | 0.93          | 0.13                 | updated       | False         |
| 17        | 10.00      | 5010.00    | 10.00         | 1710.60     | 1218.70            | 0.92          | 0.13                 | updated       | False         |
| 18        | 0.00       | 8610.00    | 0.00          | 1889.60     | 1666.34            | 0.90          | 0.14                 | updated       | True          |
| 19        | 10.00      | 10010.00   | 0.00          | 1990.60     | 1929.21            | 0.88          | 0.14                 | updated       | True          |
`````

## Comment:
This problem is solved using the power of ACO Algorithms. It is just a guide that the user can follow 
to build its own problems.

In this example, we didn't conduct an extensive optimization of the ant properties, behaviors nor pheromone properties.
Nevertheless, we were able to get in a pretty sort time the solutions for the scenario. :smirk_cat:
