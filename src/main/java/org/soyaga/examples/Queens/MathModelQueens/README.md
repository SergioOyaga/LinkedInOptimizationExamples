# MathModelQueens
For this LinkedIn Queens problem, we use MIP to formulate and solve a linear problem.
In the [mathematical formulation](#mathematical-formulation) we can see how the problem sets, parameters, variables and 
constraints are defined.

## Mathematical formulation:
We have to represent the problem using lineal mathematical expressions.

<table>
   <tr>
      <th>Sets</th>
      <th>Variables:</th>
   </tr>
   <tr>
      <td>
         <ul>
            <li>$\textcolor{blue}{C} =$ Column number in the board.</li>
            <li>$\textcolor{blue}{R} =$ Row number in the board.</li>
            <li>$\textcolor{blue}{GC} =$ GroupColors in the board.
                <ul>
                   <li>$\textcolor{blue}{CG_{gc}} = \{ (c, r) \mid c \in \textcolor{blue}{C} , r \in \textcolor{blue}{R} \}, \;
                        = \textbf{(ColorGroups)}$ Cells by color.</li>
                </ul>
            </li>
         </ul>
      </td>
      <td>
        <ul>
           <li>$B_{c, r} \in \mathbb{Z}:\{0,1}, \; \forall c \in \textcolor{blue}{C}, \; r \in \textcolor{blue}{R} = (Board) one implies queen and zero no queen.</li>
        </ul>
      </td>
   </tr>
</table>


### Constraints:
<table>
  <tr>
    <th>Name</th>
    <th>Expression</th>
    <th>Nº of constraints</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><b>UniqueQueenInColumn</b></td>
    <td>$$\sum_{r \in \textcolor{blue}{R}} B_{c,r}==1,\; \forall c \in \textcolor{blue}{C}$$</td>
    <td> $|\textcolor{blue}{C}|$ </td>
    <td> Each column can only contain one queen.</td>
  </tr>
  <tr>
    <td><b>UniqueQueenInRow</b></td>
    <td>$$\sum_{c \in \textcolor{blue}{C}} B_{c,r}==1,\; \forall r \in \textcolor{blue}{R}$$</td>
    <td> $|\textcolor{blue}{R}|$ </td>
    <td> Each row can only contain one queen.</td>
  </tr>
  <tr>
    <td><b>NoMultipleQueensInForwardDiagonal</b></td>
    <td>$$\ B_{c,r} + B_{c+1,r-1} + B_{c+1,r+1} <=1,\; \forall c \in \textcolor{blue}{C}, \; \forall r \in \textcolor{blue}{R}$$</td>
    <td> $|\textcolor{blue}{C}| \cdot |\textcolor{blue}{R}|$ </td>
    <td> There cannot be two queens touching in diagonal.</td>
  </tr>
  <tr>
    <td><b>NoMultipleQueensInGroupColor</b></td>
    <td>$$\ \sum_{c,r \in \\textcolor{blue}{CG_{gc}}}B_{c,r} ==1,\; \forall gc \in \textcolor{blue}{GC}$$</td>
    <td> $|\textcolor{blue}{GC}|$ </td>
    <td> There cannot be two queens in the same color.</td>
  </tr>
</table>

## In this folder:
This folder contains two different classes and two packages that define the structures required for solving the problem.
These classes are used as main and controller.
1. [Controller](#controller): Controller that communicates the UI with the MathematicaModel.
2. [RunLinkedInQueens](#runlinkedinQueens): The main class for instantiation of the frontend.


The records created to contain the problem information.
1. [MathModel](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Queens/MathModelQueens/MathModel/):
    Package with the mathematical model built using the OptimizationLib.mm module.
2. [UI](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Queens/MathModelQueens/UI/):
   Package with the UI for the MathModel LinkedIn Queens problem.

### [Controller](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Queens/MathModelQueens/Controller.java):
This Class controls the communication between the UI and the math model running in the back.

### [RunLinkedInQueens](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Queens/MathModelQueens/RunLinkedInQueens.java):
This is the main class. Is where the run starts. As simple as instantiate the UI.


## Results
We solve the problem using:
```` java
    @Override
    public void initializeModelRequest() {
        this.getProtoModelRequest()
                .setEnableInternalSolverOutput(true)
                .setSolverTimeLimitSeconds(600)
                .setSolverType(MPModelRequest.SolverType.SCIP_MIXED_INTEGER_PROGRAMMING);
    }
````
As solver SCIP, a max time of 10 minutes and asking the solver to plot the solving info, which looks like:
`````
presolving:
(round 1, fast)       96825 del vars, 268052 del conss, 0 add conss, 106569 chg bounds, 3358 chg sides, 5103 chg coeffs, 0 upgd conss, 0 impls, 224 clqs
....
....
presolving (19 rounds: 19 fast, 8 medium, 7 exhaustive):
 104114 deleted vars, 297702 deleted constraints, 40 added constraints, 126275 tightened bounds, 0 added holes, 3909 changed sides, 7835 changed coefficients
 401 implications, 52 cliques
presolved problem has 453 variables (361 bin, 0 int, 0 impl, 92 cont) and 818 constraints
    296 constraints of type <varbound>
     32 constraints of type <setppc>
     10 constraints of type <and>
    480 constraints of type <linear>
Presolving Time: 1.00

 time | node  | left  |LP iter|LP it/n|mem/heur|mdpt |vars |cons |rows |cuts |sepa|confs|strbr|  dualbound   | primalbound  |  gap   | compl. 
  1.0s|     1 |     0 |   282 |     - |   537M |   0 | 453 | 825 | 828 |   0 |  0 |   7 |   0 | 7.200000e+01 |      --      |    Inf | unknown
  ....
  .... 
  1.0s|     1 |     2 |  3488 |     - |   545M |   0 | 453 | 819 |1141 | 457 | 23 |  12 |  65 | 7.200000e+01 |      --      |    Inf | unknown
(run 1, node 1) restarting after 12 global fixings of integer variables

(restart) converted 305 cuts from the global cut pool into linear constraints

presolving:
(round 1, fast)       13 del vars, 4 del conss, 0 add conss, 21 chg bounds, 122 chg sides, 239 chg coeffs, 0 upgd conss, 401 impls, 102 clqs
(round 2, fast)       13 del vars, 4 del conss, 0 add conss, 21 chg bounds, 141 chg sides, 278 chg coeffs, 0 upgd conss, 401 impls, 102 clqs
(round 3, exhaustive) 13 del vars, 10 del conss, 0 add conss, 21 chg bounds, 141 chg sides, 278 chg coeffs, 0 upgd conss, 401 impls, 102 clqs
(round 4, exhaustive) 13 del vars, 10 del conss, 0 add conss, 21 chg bounds, 141 chg sides, 278 chg coeffs, 98 upgd conss, 401 impls, 102 clqs
(round 5, medium)     13 del vars, 13 del conss, 0 add conss, 21 chg bounds, 142 chg sides, 279 chg coeffs, 98 upgd conss, 425 impls, 102 clqs
presolving (6 rounds: 6 fast, 4 medium, 3 exhaustive):
 13 deleted vars, 13 deleted constraints, 0 added constraints, 21 tightened bounds, 0 added holes, 142 changed sides, 279 changed coefficients
 425 implications, 102 cliques
presolved problem has 440 variables (348 bin, 0 int, 0 impl, 92 cont) and 1111 constraints
    319 constraints of type <varbound>
      1 constraints of type <knapsack>
     77 constraints of type <setppc>
     10 constraints of type <and>
    674 constraints of type <linear>
     26 constraints of type <logicor>
      4 constraints of type <bounddisjunction>
Presolving Time: 1.00

 time | node  | left  |LP iter|LP it/n|mem/heur|mdpt |vars |cons |rows |cuts |sepa|confs|strbr|  dualbound   | primalbound  |  gap   | compl. 
p 1.0s|     1 |     0 |  3649 |     - |shiftand|   0 | 440 |1113 |1116 |   0 |  0 |  14 |  65 | 7.200000e+01 | 8.703030e+01 |  20.88%| unknown
 ....
 ....
d 3.0s|    14 |    11 |  8161 | 124.5 |adaptive|   7 | 440 |1117 |1020 |   0 |  1 |  28 | 133 | 7.200000e+01 | 8.700000e+01 |  20.83%|   2.29%
d 3.0s|    16 |    13 |  8650 | 139.5 |veclendi|   7 | 440 |1122 |1020 |   0 |  1 |  33 | 133 | 7.200000e+01 | 8.400000e+01 |  16.67%|   2.29%
d 3.0s|    82 |    33 | 13313 |  84.1 |distribu|  23 | 440 |1156 |1019 |   0 |  1 |  68 | 133 | 7.200000e+01 | 7.500000e+01 |   4.17%|  20.73%
  3.0s|   100 |    35 | 13496 |  70.8 |   566M |  23 | 440 |1163 |1020 |1094 |  1 |  75 | 133 | 7.200000e+01 | 7.500000e+01 |   4.17%|  23.04%
  4.0s|   200 |    27 | 15095 |  43.4 |   567M |  23 | 440 |1081 |1019 |1371 |  1 |  91 | 133 | 7.200000e+01 | 7.500000e+01 |   4.17%|  42.12%
  4.0s|   300 |    19 | 16808 |  34.6 |   568M |  23 | 440 |1081 |   0 |1613 |  0 |  91 | 133 | 7.200000e+01 | 7.500000e+01 |   4.17%|  60.00%

SCIP Status        : problem is solved [optimal solution found]
Solving Time (sec) : 4.00
Solving Nodes      : 352 (total of 353 nodes in 2 runs)
Primal Bound       : +7.50000000000000e+01 (4 solutions)
Dual Bound         : +7.50000000000000e+01
Gap                : 0.00 %
`````

## Comment:
This problem is solved using the power of Mixed Integer Linear Programming. It is just a guide that the user can follow 
to build its own problems.

In this example, we didn't conduct an extensive optimization of the variables and constraints created. 
Nevertheless, we were able to get in a pretty sort time the solutions for the scenario. :smirk_cat:
