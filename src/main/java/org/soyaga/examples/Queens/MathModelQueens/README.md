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
           <li>$B_{c, r} \in \mathbb{Z}:\{0,1\}, \; \forall c \in \textcolor{blue}{C}, \; r \in \textcolor{blue}{R} =$ (Board) one implies queen and zero no queen.</li>
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
    <td>$$\ \sum_{c,r \in \textcolor{blue}{CG_{gc}}}B_{c,r} ==1,\; \forall gc \in \textcolor{blue}{GC}$$</td>
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
(round 1, fast)       31 del vars, 36 del conss, 0 add conss, 22 chg bounds, 68 chg sides, 0 chg coeffs, 0 upgd conss, 0 impls, 85 clqs
(round 2, fast)       46 del vars, 46 del conss, 0 add conss, 22 chg bounds, 68 chg sides, 0 chg coeffs, 0 upgd conss, 0 impls, 77 clqs
(round 3, fast)       52 del vars, 53 del conss, 0 add conss, 22 chg bounds, 68 chg sides, 0 chg coeffs, 0 upgd conss, 0 impls, 77 clqs
(round 4, medium)     54 del vars, 53 del conss, 0 add conss, 22 chg bounds, 68 chg sides, 0 chg coeffs, 0 upgd conss, 0 impls, 75 clqs
(round 5, fast)       54 del vars, 55 del conss, 0 add conss, 22 chg bounds, 68 chg sides, 0 chg coeffs, 0 upgd conss, 0 impls, 75 clqs
(round 6, exhaustive) 54 del vars, 74 del conss, 0 add conss, 22 chg bounds, 87 chg sides, 0 chg coeffs, 0 upgd conss, 0 impls, 75 clqs
(round 7, exhaustive) 54 del vars, 74 del conss, 0 add conss, 22 chg bounds, 87 chg sides, 0 chg coeffs, 56 upgd conss, 0 impls, 75 clqs
(round 8, exhaustive) 114 del vars, 81 del conss, 0 add conss, 22 chg bounds, 87 chg sides, 0 chg coeffs, 56 upgd conss, 0 impls, 0 clqs
   Deactivated symmetry handling methods, since SCIP was built without symmetry detector (SYM=none).
presolving (9 rounds: 9 fast, 5 medium, 4 exhaustive):
 140 deleted vars, 130 deleted constraints, 0 added constraints, 22 tightened bounds, 0 added holes, 87 changed sides, 0 changed coefficients
 0 implications, 0 cliques
transformed 1/1 original solutions to the transformed problem space
Presolving Time: 0.00

SCIP Status        : problem is solved [optimal solution found]
Solving Time (sec) : 0.00
Solving Nodes      : 0
Primal Bound       : +0.00000000000000e+00 (1 solutions)
Dual Bound         : +0.00000000000000e+00
Gap                : 0.00 %
`````

## Comment:
This problem is solved using the power of Mixed Integer Linear Programming. It is just a guide that the user can follow 
to build its own problems.

In this example, we didn't conduct an extensive optimization of the variables and constraints created. 
Nevertheless, we were able to get in a pretty sort time the solutions for the scenario. :smirk_cat:
