# ACOTango
For this LinkedIn Tango problem, we use the Ant Colony Optimization (ACO) Algorithms to solve the puzzle.


### [TangoACO](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/ACO/TangoACO.java)
This class extends `StatsAntColonyAlgorithm`, making it an instance of `Optimizer`. In other words, this class can be optimized, and its results can be retrieved.

````java
public void optimize(){...}
public Object getResult(Object ... resultArgs){...}
````

The `optimize` method is inherited from the abstract class `StatsAntColonyAlgorithm`, which defines a basic optimization procedure. The `getResults` function computes the output as a string.

````mermaid
flowchart LR
  subgraph  ide1 [Solution]
      direction TB
        Node1
        NodeX
        NodeN
        Sun
        Moon
        Node1<-->Sun
        Node1<-->Moon
        NodeX<-->Sun
        NodeX<-->Moon
        NodeN<-->Sun
        NodeN<-->Moon
  end
````

The Ants build solutions by moving through the Graph edges. A solution is an Array of Nodes, that is a valid solution when:
1. All nodes have been visited at least once.
2. The start and end node is the same.

## In this folder:
This folder contains two different classes and two packages that define the structures required for solving the problem.
These classes are used as main and controller.
1. [Controller](#controller): Controller that communicates the UI with the ACO.
2. [RunLinkedInTango](#runlinkedinTango): The main class for instantiation of the frontend.


The folders contain the problem information.
1. [ACO](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/ACOTango/ACO/):
    Package with the ACO model built using the OptimizationLib.aco module.
2. [UI](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/ACOTango/UI/):
   Package with the UI for the ACO LinkedIn Tango problem.

### [Controller](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/ACOTango/Controller.java):
This Class controls the communication between the UI and the ACO model running in the back.

### [RunLinkedInTango](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/ACOTango/RunLinkedInTango.java):
This is the main class. Is where the run starts. As simple as instantiate the UI.


## Results
We set the stopping condition to a max number of iterations (10000).
However, we did not perform any "intelligent" ant, so the solution is built without any rule (Eg.: if the ant decides the type of a node that has a relation with other, then force the assignation of the related cell).
The optimization output looks like:
`````
-----------------------------------------------------------------------------------------------------------------------------------------------------------------
| Iteration | CurrentMin | CurrentMax | HistoricalMin | MeanFitness | StandardDevFitness | MeanPheromone | StandardDevPheromone | FitnessUpdate | SolutionFound |
-----------------------------------------------------------------------------------------------------------------------------------------------------------------
| 1         | 2100.00    | 5600.00    | 2100.00       | 3677.00     | 755.36             | 1.00          | 0.00                 | updated       | False         |
| 2         | 1800.00    | 5200.00    | 1800.00       | 3496.00     | 730.06             | 1.00          | 0.00                 | updated       | False         |
| 3         | 2000.00    | 5800.00    | 1800.00       | 3372.00     | 754.73             | 1.00          | 0.00                 | updated       | False         |
| 4         | 1900.00    | 5400.00    | 1800.00       | 3480.00     | 720.56             | 1.00          | 0.00                 | updated       | False         |
| 5         | 1600.00    | 5500.00    | 1600.00       | 3398.00     | 645.13             | 1.00          | 0.00                 | updated       | False         |
| 6         | 1800.00    | 5800.00    | 1600.00       | 3529.00     | 722.26             | 1.00          | 0.00                 | updated       | False         |
| 7         | 2300.00    | 4800.00    | 1600.00       | 3483.00     | 611.89             | 1.00          | 0.00                 | updated       | False         |
| 8         | 1900.00    | 5800.00    | 1600.00       | 3548.00     | 767.27             | 1.00          | 0.00                 | updated       | False         |
| 9         | 1800.00    | 6800.00    | 1600.00       | 3688.00     | 859.33             | 1.00          | 0.00                 | updated       | False         |
| 10        | 1700.00    | 5900.00    | 1600.00       | 3497.00     | 819.57             | 1.00          | 0.00                 | updated       | False         |
| 11        | 1800.00    | 5700.00    | 1600.00       | 3524.00     | 784.36             | 1.00          | 0.00                 | updated       | False         |
| 12        | 2000.00    | 5200.00    | 1600.00       | 3546.00     | 732.18             | 1.00          | 0.00                 | updated       | False         |
| 13        | 1800.00    | 5500.00    | 1600.00       | 3449.00     | 765.96             | 1.00          | 0.01                 | updated       | False         |
| 14        | 1200.00    | 6000.00    | 1200.00       | 3487.00     | 818.98             | 1.00          | 0.00                 | updated       | False         |
| 15        | 1900.00    | 5400.00    | 1200.00       | 3355.00     | 699.62             | 1.00          | 0.01                 | updated       | False         |
| 16        | 2200.00    | 5900.00    | 1200.00       | 3485.00     | 818.70             | 1.00          | 0.01                 | updated       | False         |
| 17        | 1800.00    | 5800.00    | 1200.00       | 3557.00     | 794.14             | 1.00          | 0.01                 | updated       | False         |
| 18        | 1900.00    | 6000.00    | 1200.00       | 3551.00     | 821.64             | 1.00          | 0.01                 | updated       | False         |
| 19        | 2100.00    | 5000.00    | 1200.00       | 3520.00     | 727.74             | 0.99          | 0.01                 | updated       | False         |
| 20        | 2100.00    | 5000.00    | 1200.00       | 3386.00     | 692.10             | 0.99          | 0.01                 | updated       | False         |
| 21        | 1400.00    | 5100.00    | 1200.00       | 3345.00     | 684.74             | 0.99          | 0.01                 | updated       | False         |
| 22        | 1500.00    | 5600.00    | 1200.00       | 3337.00     | 813.22             | 0.99          | 0.01                 | updated       | False         |
| 23        | 2100.00    | 5800.00    | 1200.00       | 3598.00     | 751.53             | 0.99          | 0.01                 | updated       | False         |
| 24        | 1600.00    | 5100.00    | 1200.00       | 3421.00     | 706.30             | 0.99          | 0.01                 | updated       | False         |
| 25        | 1600.00    | 5800.00    | 1200.00       | 3463.00     | 796.20             | 0.99          | 0.01                 | updated       | False         |
| 26        | 1900.00    | 5400.00    | 1200.00       | 3577.00     | 715.38             | 0.99          | 0.01                 | updated       | False         |
| 27        | 1600.00    | 5000.00    | 1200.00       | 3492.00     | 662.67             | 0.99          | 0.01                 | updated       | False         |
| 28        | 1700.00    | 5700.00    | 1200.00       | 3485.00     | 787.70             | 0.99          | 0.01                 | updated       | False         |
| 29        | 2000.00    | 5400.00    | 1200.00       | 3443.00     | 695.02             | 0.99          | 0.01                 | updated       | False         |
| 30        | 1800.00    | 5100.00    | 1200.00       | 3553.00     | 776.98             | 0.99          | 0.01                 | updated       | False         |
| 31        | 1800.00    | 5200.00    | 1200.00       | 3505.00     | 634.57             | 0.99          | 0.01                 | updated       | False         |
| 32        | 1900.00    | 5000.00    | 1200.00       | 3553.00     | 665.35             | 0.99          | 0.01                 | updated       | False         |
| 33        | 2000.00    | 5900.00    | 1200.00       | 3478.00     | 736.96             | 0.99          | 0.01                 | updated       | False         |
| 34        | 2200.00    | 5300.00    | 1200.00       | 3608.00     | 696.80             | 0.99          | 0.01                 | updated       | False         |
| 35        | 2400.00    | 5700.00    | 1200.00       | 3550.00     | 723.26             | 0.99          | 0.01                 | updated       | False         |
| 36        | 1500.00    | 5100.00    | 1200.00       | 3455.00     | 751.45             | 0.99          | 0.01                 | updated       | False         |
| 37        | 1700.00    | 5400.00    | 1200.00       | 3540.00     | 742.70             | 0.99          | 0.01                 | updated       | False         |
| 38        | 1500.00    | 5600.00    | 1200.00       | 3490.00     | 694.05             | 0.99          | 0.02                 | updated       | False         |
| 39        | 1900.00    | 5400.00    | 1200.00       | 3512.00     | 701.89             | 0.99          | 0.02                 | updated       | False         |
| 40        | 1800.00    | 7000.00    | 1200.00       | 3522.00     | 890.12             | 0.98          | 0.02                 | updated       | False         |
| 41        | 2200.00    | 4800.00    | 1200.00       | 3424.00     | 618.24             | 0.98          | 0.02                 | updated       | False         |
| 42        | 1700.00    | 5200.00    | 1200.00       | 3472.00     | 754.73             | 0.98          | 0.02                 | updated       | False         |
| 43        | 1800.00    | 6300.00    | 1200.00       | 3536.00     | 856.21             | 0.98          | 0.02                 | updated       | False         |
| 44        | 1700.00    | 5200.00    | 1200.00       | 3414.00     | 680.59             | 0.98          | 0.02                 | updated       | False         |
| 45        | 1700.00    | 5300.00    | 1200.00       | 3476.00     | 810.32             | 0.98          | 0.02                 | updated       | False         |
| 46        | 1400.00    | 4800.00    | 1200.00       | 3380.00     | 667.23             | 0.98          | 0.02                 | updated       | False         |
| 47        | 2200.00    | 6100.00    | 1200.00       | 3509.00     | 769.82             | 0.98          | 0.02                 | updated       | False         |
| 48        | 2300.00    | 5100.00    | 1200.00       | 3570.00     | 575.24             | 0.98          | 0.02                 | updated       | False         |
| 49        | 1500.00    | 5300.00    | 1200.00       | 3432.00     | 727.72             | 0.98          | 0.02                 | updated       | False         |
| 50        | 1900.00    | 5300.00    | 1200.00       | 3555.00     | 726.83             | 0.98          | 0.02                 | updated       | False         |
| 51        | 1700.00    | 5500.00    | 1200.00       | 3463.00     | 738.06             | 0.98          | 0.02                 | updated       | False         |
| 52        | 1900.00    | 5200.00    | 1200.00       | 3434.00     | 753.29             | 0.98          | 0.02                 | updated       | False         |
| 53        | 1500.00    | 5400.00    | 1200.00       | 3363.00     | 831.10             | 0.98          | 0.02                 | updated       | False         |
| 54        | 1300.00    | 5500.00    | 1200.00       | 3455.00     | 705.89             | 0.98          | 0.02                 | updated       | False         |
| 55        | 2100.00    | 5700.00    | 1200.00       | 3522.00     | 711.84             | 0.98          | 0.02                 | updated       | False         |
| 56        | 2200.00    | 5300.00    | 1200.00       | 3484.00     | 682.75             | 0.98          | 0.02                 | updated       | False         |
| 57        | 1600.00    | 5400.00    | 1200.00       | 3544.00     | 714.61             | 0.98          | 0.02                 | updated       | False         |
| 58        | 1500.00    | 5400.00    | 1200.00       | 3435.00     | 834.07             | 0.98          | 0.02                 | updated       | False         |
| 59        | 1600.00    | 5300.00    | 1200.00       | 3373.00     | 712.86             | 0.98          | 0.03                 | updated       | False         |
| 60        | 2200.00    | 5900.00    | 1200.00       | 3615.00     | 688.10             | 0.98          | 0.03                 | updated       | False         |
| 61        | 1900.00    | 5400.00    | 1200.00       | 3487.00     | 674.93             | 0.98          | 0.03                 | updated       | False         |
| 62        | 2000.00    | 5300.00    | 1200.00       | 3567.00     | 673.21             | 0.97          | 0.03                 | updated       | False         |
| 63        | 1800.00    | 5600.00    | 1200.00       | 3597.00     | 810.49             | 0.97          | 0.03                 | updated       | False         |
| 64        | 2000.00    | 5900.00    | 1200.00       | 3656.00     | 838.49             | 0.97          | 0.03                 | updated       | False         |
| 65        | 1700.00    | 5900.00    | 1200.00       | 3508.00     | 792.30             | 0.97          | 0.03                 | updated       | False         |
| 66        | 1900.00    | 5400.00    | 1200.00       | 3537.00     | 717.45             | 0.97          | 0.03                 | updated       | False         |
| 67        | 1700.00    | 5100.00    | 1200.00       | 3429.00     | 741.26             | 0.97          | 0.03                 | updated       | False         |
| 68        | 1800.00    | 5200.00    | 1200.00       | 3481.00     | 773.52             | 0.97          | 0.03                 | updated       | False         |
| 69        | 1800.00    | 5400.00    | 1200.00       | 3454.00     | 816.26             | 0.97          | 0.03                 | updated       | False         |
| 70        | 1700.00    | 5400.00    | 1200.00       | 3442.00     | 728.86             | 0.97          | 0.03                 | updated       | False         |
| 71        | 2100.00    | 5300.00    | 1200.00       | 3607.00     | 710.53             | 0.97          | 0.03                 | updated       | False         |
| 72        | 1900.00    | 5600.00    | 1200.00       | 3448.00     | 808.14             | 0.97          | 0.03                 | updated       | False         |
| 73        | 1900.00    | 6100.00    | 1200.00       | 3522.00     | 756.25             | 0.97          | 0.03                 | updated       | False         |
| 74        | 1700.00    | 5800.00    | 1200.00       | 3499.00     | 764.79             | 0.97          | 0.03                 | updated       | False         |
| 75        | 1700.00    | 5300.00    | 1200.00       | 3367.00     | 778.60             | 0.97          | 0.03                 | updated       | False         |
| 76        | 2200.00    | 5400.00    | 1200.00       | 3620.00     | 670.97             | 0.97          | 0.03                 | updated       | False         |
| 77        | 1500.00    | 5600.00    | 1200.00       | 3581.00     | 762.06             | 0.97          | 0.03                 | updated       | False         |
| 78        | 2200.00    | 5600.00    | 1200.00       | 3547.00     | 731.50             | 0.97          | 0.03                 | updated       | False         |
| 79        | 1700.00    | 6100.00    | 1200.00       | 3391.00     | 768.26             | 0.97          | 0.03                 | updated       | False         |
| 80        | 1900.00    | 5600.00    | 1200.00       | 3585.00     | 761.50             | 0.97          | 0.03                 | updated       | False         |
| 81        | 1800.00    | 4900.00    | 1200.00       | 3517.00     | 699.29             | 0.97          | 0.04                 | updated       | False         |
| 82        | 2100.00    | 5400.00    | 1200.00       | 3486.00     | 750.87             | 0.97          | 0.04                 | updated       | False         |
| 83        | 1900.00    | 5700.00    | 1200.00       | 3526.00     | 740.62             | 0.97          | 0.04                 | updated       | False         |
| 84        | 1400.00    | 6000.00    | 1200.00       | 3327.00     | 700.69             | 0.96          | 0.04                 | updated       | False         |
| 85        | 1800.00    | 5700.00    | 1200.00       | 3421.00     | 704.46             | 0.96          | 0.04                 | updated       | False         |
| 86        | 1900.00    | 5100.00    | 1200.00       | 3507.00     | 647.03             | 0.96          | 0.04                 | updated       | False         |
| 87        | 1500.00    | 5600.00    | 1200.00       | 3521.00     | 724.61             | 0.96          | 0.04                 | updated       | False         |
| 88        | 1900.00    | 5900.00    | 1200.00       | 3588.00     | 787.31             | 0.96          | 0.04                 | updated       | False         |
| 89        | 2000.00    | 5500.00    | 1200.00       | 3590.00     | 722.29             | 0.96          | 0.04                 | updated       | False         |
| 90        | 1600.00    | 5900.00    | 1200.00       | 3426.00     | 745.47             | 0.96          | 0.04                 | updated       | False         |
| 91        | 1800.00    | 5100.00    | 1200.00       | 3460.00     | 688.48             | 0.96          | 0.04                 | updated       | False         |
| 92        | 1700.00    | 5600.00    | 1200.00       | 3609.00     | 735.27             | 0.96          | 0.04                 | updated       | False         |
| 93        | 2000.00    | 5600.00    | 1200.00       | 3514.00     | 764.72             | 0.96          | 0.04                 | updated       | False         |
| 94        | 1800.00    | 5100.00    | 1200.00       | 3461.00     | 742.14             | 0.96          | 0.04                 | updated       | False         |
| 95        | 1900.00    | 5700.00    | 1200.00       | 3438.00     | 736.31             | 0.96          | 0.04                 | updated       | False         |
| 96        | 2100.00    | 5300.00    | 1200.00       | 3572.00     | 686.89             | 0.96          | 0.04                 | updated       | False         |
| 97        | 1900.00    | 5200.00    | 1200.00       | 3487.00     | 614.44             | 0.96          | 0.04                 | updated       | False         |
| 98        | 2100.00    | 5200.00    | 1200.00       | 3512.00     | 727.36             | 0.96          | 0.04                 | updated       | False         |
| 99        | 2000.00    | 5700.00    | 1200.00       | 3433.00     | 797.25             | 0.96          | 0.04                 | updated       | False         |
| 100       | 1700.00    | 6200.00    | 1200.00       | 3442.00     | 762.52             | 0.96          | 0.04                 | updated       | False         |
*
*
*
| 1257      | 500.00     | 4100.00    | 200.00        | 2227.00     | 673.03             | 0.86          | 0.22                 | updated       | False         |
| 1258      | 500.00     | 4400.00    | 200.00        | 2236.00     | 681.40             | 0.86          | 0.22                 | updated       | False         |
| 1259      | 600.00     | 4700.00    | 200.00        | 2304.00     | 813.62             | 0.86          | 0.22                 | updated       | False         |
| 1260      | 200.00     | 3500.00    | 200.00        | 2184.00     | 651.26             | 0.88          | 0.22                 | updated       | False         |
| 1261      | 500.00     | 4800.00    | 200.00        | 2333.00     | 823.54             | 0.88          | 0.22                 | updated       | False         |
| 1262      | 600.00     | 4100.00    | 200.00        | 2134.00     | 786.28             | 0.88          | 0.22                 | updated       | False         |
| 1263      | 1100.00    | 4300.00    | 200.00        | 2283.00     | 694.13             | 0.88          | 0.22                 | updated       | False         |
| 1264      | 700.00     | 4300.00    | 200.00        | 2235.00     | 770.24             | 0.88          | 0.22                 | updated       | False         |
| 1265      | 1200.00    | 3800.00    | 200.00        | 2348.00     | 579.39             | 0.88          | 0.22                 | updated       | False         |
| 1266      | 600.00     | 4100.00    | 200.00        | 2325.00     | 757.81             | 0.88          | 0.22                 | updated       | False         |
| 1267      | 1100.00    | 4200.00    | 200.00        | 2170.00     | 663.40             | 0.88          | 0.22                 | updated       | False         |
| 1268      | 800.00     | 4000.00    | 200.00        | 2261.00     | 778.96             | 0.87          | 0.22                 | updated       | False         |
| 1269      | 900.00     | 4200.00    | 200.00        | 2352.00     | 731.23             | 0.87          | 0.22                 | updated       | False         |
| 1270      | 700.00     | 3700.00    | 200.00        | 2169.00     | 748.02             | 0.87          | 0.22                 | updated       | False         |
| 1271      | 900.00     | 3800.00    | 200.00        | 2245.00     | 676.07             | 0.87          | 0.22                 | updated       | False         |
| 1272      | 600.00     | 4100.00    | 200.00        | 2111.00     | 723.45             | 0.87          | 0.22                 | updated       | False         |
| 1273      | 800.00     | 4200.00    | 200.00        | 2238.00     | 663.44             | 0.87          | 0.22                 | updated       | False         |
| 1274      | 400.00     | 4500.00    | 200.00        | 2278.00     | 805.68             | 0.87          | 0.22                 | updated       | False         |
| 1275      | 500.00     | 4700.00    | 200.00        | 2367.00     | 739.47             | 0.87          | 0.22                 | updated       | False         |
| 1276      | 800.00     | 4200.00    | 200.00        | 2223.00     | 739.30             | 0.87          | 0.22                 | updated       | False         |
| 1277      | 400.00     | 5000.00    | 200.00        | 2275.00     | 808.63             | 0.87          | 0.22                 | updated       | False         |
| 1278      | 600.00     | 4000.00    | 200.00        | 2129.00     | 768.67             | 0.87          | 0.22                 | updated       | False         |
| 1279      | 500.00     | 3900.00    | 200.00        | 2238.00     | 741.59             | 0.87          | 0.22                 | updated       | False         |
| 1280      | 600.00     | 4300.00    | 200.00        | 2254.00     | 732.04             | 0.87          | 0.22                 | updated       | False         |
| 1281      | 500.00     | 4600.00    | 200.00        | 2075.00     | 646.43             | 0.87          | 0.22                 | updated       | False         |
| 1282      | 600.00     | 3800.00    | 200.00        | 2028.00     | 639.70             | 0.87          | 0.22                 | updated       | False         |
| 1283      | 500.00     | 4300.00    | 200.00        | 2200.00     | 688.19             | 0.87          | 0.22                 | updated       | False         |
| 1284      | 700.00     | 4200.00    | 200.00        | 2240.00     | 693.40             | 0.87          | 0.22                 | updated       | False         |
| 1285      | 1100.00    | 4300.00    | 200.00        | 2409.00     | 758.04             | 0.87          | 0.22                 | updated       | False         |
| 1286      | 700.00     | 4400.00    | 200.00        | 2228.00     | 680.16             | 0.87          | 0.22                 | updated       | False         |
| 1287      | 700.00     | 3800.00    | 200.00        | 2172.00     | 716.25             | 0.87          | 0.22                 | updated       | False         |
| 1288      | 700.00     | 4400.00    | 200.00        | 2194.00     | 681.15             | 0.87          | 0.22                 | updated       | False         |
| 1289      | 600.00     | 4500.00    | 200.00        | 2194.00     | 694.52             | 0.87          | 0.22                 | updated       | False         |
| 1290      | 800.00     | 4000.00    | 200.00        | 2252.00     | 684.76             | 0.87          | 0.22                 | updated       | False         |
| 1291      | 1000.00    | 4400.00    | 200.00        | 2224.00     | 751.55             | 0.87          | 0.22                 | updated       | False         |
| 1292      | 600.00     | 4300.00    | 200.00        | 2227.00     | 764.05             | 0.87          | 0.22                 | updated       | False         |
| 1293      | 800.00     | 3500.00    | 200.00        | 2161.00     | 643.10             | 0.87          | 0.22                 | updated       | False         |
| 1294      | 700.00     | 3600.00    | 200.00        | 2124.00     | 660.47             | 0.87          | 0.22                 | updated       | False         |
| 1295      | 200.00     | 4600.00    | 200.00        | 2157.00     | 772.56             | 0.88          | 0.23                 | updated       | False         |
| 1296      | 500.00     | 3400.00    | 200.00        | 2149.00     | 664.00             | 0.88          | 0.23                 | updated       | False         |
| 1297      | 400.00     | 3800.00    | 200.00        | 2087.00     | 676.71             | 0.88          | 0.23                 | updated       | False         |
| 1298      | 700.00     | 3500.00    | 200.00        | 2166.00     | 668.16             | 0.88          | 0.23                 | updated       | False         |
| 1299      | 500.00     | 4000.00    | 200.00        | 2077.00     | 649.44             | 0.88          | 0.23                 | updated       | False         |
| 1300      | 500.00     | 3900.00    | 200.00        | 2207.00     | 677.83             | 0.88          | 0.23                 | updated       | False         |
| 1301      | 600.00     | 4000.00    | 200.00        | 2093.00     | 688.80             | 0.88          | 0.23                 | updated       | False         |
| 1302      | 900.00     | 3900.00    | 200.00        | 2113.00     | 678.92             | 0.88          | 0.23                 | updated       | False         |
| 1303      | 400.00     | 3600.00    | 200.00        | 2110.00     | 658.10             | 0.87          | 0.23                 | updated       | False         |
| 1304      | 800.00     | 3500.00    | 200.00        | 2157.00     | 651.19             | 0.87          | 0.23                 | updated       | False         |
| 1305      | 800.00     | 4200.00    | 200.00        | 2185.00     | 693.02             | 0.87          | 0.23                 | updated       | False         |
| 1306      | 600.00     | 3600.00    | 200.00        | 2144.00     | 683.86             | 0.87          | 0.23                 | updated       | False         |
| 1307      | 800.00     | 4600.00    | 200.00        | 2236.00     | 738.58             | 0.87          | 0.23                 | updated       | False         |
| 1308      | 400.00     | 4300.00    | 200.00        | 2049.00     | 683.15             | 0.87          | 0.23                 | updated       | False         |
| 1309      | 600.00     | 4100.00    | 200.00        | 2064.00     | 717.57             | 0.87          | 0.23                 | updated       | False         |
| 1310      | 600.00     | 4000.00    | 200.00        | 2223.00     | 698.98             | 0.87          | 0.23                 | updated       | False         |
| 1311      | 400.00     | 4300.00    | 200.00        | 2146.00     | 727.52             | 0.87          | 0.23                 | updated       | False         |
| 1312      | 400.00     | 3900.00    | 200.00        | 2150.00     | 681.54             | 0.87          | 0.23                 | updated       | False         |
| 1313      | 600.00     | 4100.00    | 200.00        | 1953.00     | 706.61             | 0.87          | 0.23                 | updated       | False         |
| 1314      | 1100.00    | 3600.00    | 200.00        | 2173.00     | 564.95             | 0.87          | 0.23                 | updated       | False         |
| 1315      | 700.00     | 4100.00    | 200.00        | 2170.00     | 707.74             | 0.87          | 0.23                 | updated       | False         |
| 1316      | 900.00     | 3900.00    | 200.00        | 2170.00     | 678.90             | 0.87          | 0.23                 | updated       | False         |
| 1317      | 600.00     | 3900.00    | 200.00        | 2033.00     | 699.86             | 0.87          | 0.23                 | updated       | False         |
| 1318      | 400.00     | 3800.00    | 200.00        | 2057.00     | 663.06             | 0.87          | 0.23                 | updated       | False         |
| 1319      | 300.00     | 4400.00    | 200.00        | 2104.00     | 785.36             | 0.87          | 0.23                 | updated       | False         |
| 1320      | 500.00     | 4600.00    | 200.00        | 2094.00     | 743.21             | 0.87          | 0.23                 | updated       | False         |
| 1321      | 500.00     | 4900.00    | 200.00        | 2134.00     | 815.13             | 0.87          | 0.23                 | updated       | False         |
| 1322      | 700.00     | 4300.00    | 200.00        | 2225.00     | 772.45             | 0.87          | 0.23                 | updated       | False         |
| 1323      | 700.00     | 4300.00    | 200.00        | 2222.00     | 739.13             | 0.87          | 0.23                 | updated       | False         |
| 1324      | 700.00     | 3900.00    | 200.00        | 2098.00     | 641.40             | 0.87          | 0.23                 | updated       | False         |
| 1325      | 400.00     | 4000.00    | 200.00        | 2067.00     | 752.34             | 0.87          | 0.23                 | updated       | False         |
| 1326      | 800.00     | 4200.00    | 200.00        | 2131.00     | 689.74             | 0.87          | 0.23                 | updated       | False         |
| 1327      | 800.00     | 4300.00    | 200.00        | 2148.00     | 717.14             | 0.87          | 0.23                 | updated       | False         |
| 1328      | 200.00     | 3900.00    | 200.00        | 1982.00     | 673.41             | 0.87          | 0.23                 | updated       | False         |
| 1329      | 600.00     | 4100.00    | 200.00        | 2173.00     | 700.55             | 0.87          | 0.23                 | updated       | False         |
| 1330      | 500.00     | 4200.00    | 200.00        | 2264.00     | 736.82             | 0.87          | 0.23                 | updated       | False         |
| 1331      | 600.00     | 4600.00    | 200.00        | 2062.00     | 668.40             | 0.87          | 0.23                 | updated       | False         |
| 1332      | 800.00     | 4000.00    | 200.00        | 2089.00     | 721.65             | 0.87          | 0.23                 | updated       | False         |
| 1333      | 900.00     | 4000.00    | 200.00        | 2151.00     | 672.83             | 0.87          | 0.23                 | updated       | False         |
| 1334      | 900.00     | 3800.00    | 200.00        | 2116.00     | 664.49             | 0.87          | 0.23                 | updated       | False         |
| 1335      | 500.00     | 4400.00    | 200.00        | 2119.00     | 757.46             | 0.87          | 0.23                 | updated       | False         |
| 1336      | 400.00     | 4100.00    | 200.00        | 2063.00     | 702.94             | 0.87          | 0.23                 | updated       | False         |
| 1337      | 600.00     | 3600.00    | 200.00        | 2121.00     | 654.26             | 0.87          | 0.23                 | updated       | False         |
| 1338      | 900.00     | 4100.00    | 200.00        | 2085.00     | 667.59             | 0.87          | 0.23                 | updated       | False         |
| 1339      | 200.00     | 4000.00    | 200.00        | 2101.00     | 744.78             | 0.88          | 0.23                 | updated       | False         |
| 1340      | 600.00     | 4100.00    | 200.00        | 2051.00     | 741.15             | 0.88          | 0.23                 | updated       | False         |
| 1341      | 600.00     | 4400.00    | 200.00        | 2026.00     | 776.74             | 0.88          | 0.23                 | updated       | False         |
| 1342      | 800.00     | 4000.00    | 200.00        | 2202.00     | 684.69             | 0.88          | 0.23                 | updated       | False         |
| 1343      | 500.00     | 3900.00    | 200.00        | 2146.00     | 722.97             | 0.88          | 0.23                 | updated       | False         |
| 1344      | 600.00     | 3900.00    | 200.00        | 2089.00     | 700.13             | 0.87          | 0.23                 | updated       | False         |
| 1345      | 600.00     | 4300.00    | 200.00        | 2168.00     | 684.09             | 0.87          | 0.23                 | updated       | False         |
| 1346      | 600.00     | 4500.00    | 200.00        | 2212.00     | 745.56             | 0.87          | 0.23                 | updated       | False         |
| 1347      | 600.00     | 3700.00    | 200.00        | 2097.00     | 654.59             | 0.87          | 0.23                 | updated       | False         |
| 1348      | 400.00     | 3700.00    | 200.00        | 2090.00     | 682.86             | 0.87          | 0.23                 | updated       | False         |
| 1349      | 600.00     | 4100.00    | 200.00        | 2114.00     | 729.25             | 0.87          | 0.23                 | updated       | False         |
| 1350      | 400.00     | 3600.00    | 200.00        | 2007.00     | 712.64             | 0.87          | 0.23                 | updated       | False         |
| 1351      | 600.00     | 3800.00    | 200.00        | 2135.00     | 671.92             | 0.87          | 0.23                 | updated       | False         |
| 1352      | 700.00     | 3600.00    | 200.00        | 1998.00     | 620.48             | 0.87          | 0.23                 | updated       | False         |
| 1353      | 700.00     | 4500.00    | 200.00        | 2116.00     | 739.69             | 0.87          | 0.23                 | updated       | False         |
| 1354      | 600.00     | 5200.00    | 200.00        | 2166.00     | 754.75             | 0.87          | 0.23                 | updated       | False         |
| 1355      | 700.00     | 4400.00    | 200.00        | 2005.00     | 664.74             | 0.87          | 0.23                 | updated       | False         |
| 1356      | 0.00       | 3600.00    | 0.00          | 1998.00     | 676.01             | 0.90          | 0.22                 | updated       | True          |

`````

## Comment:
This problem is solved using the power of ACO Algorithms. It is just a guide that the user can follow 
to build its own problems.

In this example, we didn't conduct an extensive optimization of the ant properties, behaviors nor pheromone properties.
As a consequence, and due to the bad fit of ACO to this kind of problem, the program does not allways achieve the result on the first try, and we may need to rerun it a few times. :smirk_cat:
