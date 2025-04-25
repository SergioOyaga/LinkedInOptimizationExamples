# MathModelZip
For this LinkedIn Zip problem, we use MIP to formulate and solve a linear problem.
In the [mathematical formulation](#mathematical-formulation) we can see how the problem sets, parameters, variables and 
constraints are defined.

## Mathematical formulation:
We have to represent the problem using lineal mathematical expressions.

<table>
   <tr>
      <th>Sets</th>
      <th>Parameters:</th>
   </tr>
   <tr>
      <td>
         <ul>
            <li>$\textcolor{blue}{C} =$ Columns in the board.</li>
            <li>$\textcolor{blue}{R} =$ Row number in the board.</li>
            <li>$\textcolor{blue}{PC} =$ Priority cells in a sorted set.</li>
         </ul>
      </td>
      <td>
        <ul>
           <li>
                $\textcolor{magenta}{CR_{r,c, i}} \in \{0,1\} \vee \{ (r, c) \mid r \in \textcolor{blue}{R}, c \in \textcolor{blue}{C}, i \in [0,3]\}, \; = \textbf{(CellRelation)}$ Cell relation with surrounding cells North, East, South and West.
           </li>
        </ul>
      </td>
   </tr>
</table>

### Variables:

<table>
   <tr>
      <th>Name:</th>
      <th>Variable:</th>
      <th>Description:</th>
   </tr>
   <tr>
      <td>
           Grid Cell Value
      </td>
      <td>
           $GCV_{r, c} \in [1,\textcolor{blue}{R} \cdot \textcolor{blue}{C}], \; \forall r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C} $
      </td>
      <td>
        Grid Cell Value. Integer that indicates the order in which the solution is built.
      </td>
   </tr>
   <tr>
      <td>
           Auxiliary
      </td>
      <td>
           $AUX_{r, c, i} \in \mathbb{Z}:\{0,1\}, \;  \forall r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C}, \; \vee i \in [0,3] $
      </td>
      <td>
        Auxiliary Variable for the OR disjunction implementation.
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
    <td><b>StartNode</b></td>
    <td>$$GCV_{r,c} ==1,\; \vee (r,c) \in \textcolor{blue}{PC_{0}}\$$</td>
    <td> $1$ </td>
    <td> The problem fixes the start node.</td>
  </tr>
  <tr>
    <td><b>EndNode</b></td>
    <td>$$GCV_{r,c} ==\textcolor{blue}{R} \cdot \textcolor{blue}{C},\; \vee (r,c) \in \textcolor{blue}{PC_{-1}}\$$</td>
    <td> $1$ </td>
    <td> The problem fixes the end node.</td>
  </tr>
  <tr>
    <td><b>AllCellsUsed</b></td>
    <td>$$\sum_{r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C}} GCV_{r,c} = \frac{(\textcolor{blue}{R} \cdot \textcolor{blue}{C} +1) \cdot \textcolor{blue}{R} \cdot \textcolor{blue}{C}}{2}$$</td>
    <td> $1$ </td>
    <td> All cells must be used.</td>
  </tr>
  <tr>
    <td><b>Priority</b></td>
    <td>$$GCV_{r_i,c_i} < GCV_{r_j,c_j},\; \vee (r_i,c_i), (r_j,c_j) \in \textcolor{blue}{PC} \mid i = j-1 $$</td>
    <td> $|\textcolor{blue}{PC}-1|$ </td>
    <td> Respect the priority assignation.</td>
  </tr>
  <tr>
    <td><b>AuxUnique</b></td>
    <td>$$\sum_{i=[0,3]} AUX_{r,c,i}==1,\; \forall r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C}$$</td>
    <td> $|\textcolor{blue}{R} \cdot \textcolor{blue}{C}|$ </td>
    <td> Only one can be selected. In this constraint, we take into account barriers in the graph and borders.</td>
  </tr>
  <tr>
    <td><b>PathContinuity</b></td>
    <td>
        <ul>
            <li>$$\ GCV_{r-1,c} - GCV_{r,c} + \textcolor{magenta}{Big} \cdot AUX_{r,c,0}<= 1+\textcolor{magenta}{Big},\; \forall r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C}$$</li>
            <li>$$\ GCV_{r-1,c} - GCV_{r,c} - \textcolor{magenta}{Big} \cdot AUX_{r,c,0}<= 1-\textcolor{magenta}{Big},\; \forall r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C}$$</li>
            <li>$$\ GCV_{r+1,c} - GCV_{r,c} + \textcolor{magenta}{Big} \cdot AUX_{r,c,1}<= 1+\textcolor{magenta}{Big},\; \forall r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C}$$</li>
            <li>$$\ GCV_{r+1,c} - GCV_{r,c} - \textcolor{magenta}{Big} \cdot AUX_{r,c,1}<= 1-\textcolor{magenta}{Big},\; \forall r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C}$$</li>
            <li>$$\ GCV_{r,c-1} - GCV_{r,c} + \textcolor{magenta}{Big} \cdot AUX_{r,c,2}<= 1+\textcolor{magenta}{Big},\; \forall r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C}$$</li>
            <li>$$\ GCV_{r,c-1} - GCV_{r,c} - \textcolor{magenta}{Big} \cdot AUX_{r,c,2}<= 1-\textcolor{magenta}{Big},\; \forall r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C}$$</li>
            <li>$$\ GCV_{r,c+1} - GCV_{r,c} + \textcolor{magenta}{Big} \cdot AUX_{r,c,3}<= 1+\textcolor{magenta}{Big},\; \forall r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C}$$</li>
            <li>$$\ GCV_{r,c+1} - GCV_{r,c} - \textcolor{magenta}{Big} \cdot AUX_{r,c,3}<= 1-\textcolor{magenta}{Big},\; \forall r \in \textcolor{blue}{R}, \; c \in \textcolor{blue}{C}$$</li>
        </ul>
    </td>
    <td> 
        <ul>
            <li> $|\textcolor{blue}{R} \cdot \textcolor{blue}{C}|-1$ </li>
            <li> $|\textcolor{blue}{R} \cdot \textcolor{blue}{C}|-1$ </li>
            <li> $|\textcolor{blue}{R} \cdot \textcolor{blue}{C}|-1$ </li>
            <li> $|\textcolor{blue}{R} \cdot \textcolor{blue}{C}|-1$ </li>
            <li> $|\textcolor{blue}{R} \cdot \textcolor{blue}{C}|-1$ </li>
            <li> $|\textcolor{blue}{R} \cdot \textcolor{blue}{C}|-1$ </li>
            <li> $|\textcolor{blue}{R} \cdot \textcolor{blue}{C}|-1$ </li>
            <li> $|\textcolor{blue}{R} \cdot \textcolor{blue}{C}|-1$ </li>
        </ul>
    </td>
    <td> The numbers must be connected. In this constraint, we take into account barriers in the graph and borders.</td>
  </tr>
</table>

## In this folder:
This folder contains two different classes and two packages that define the structures required for solving the problem.
These classes are used as main and controller.
1. [Controller](#controller): Controller that communicates the UI with the MathematicaModel.
2. [RunLinkedInZip](#runlinkedinZip): The main class for instantiation of the frontend.


The classes created to contain the problem information.
1. [MathModel](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/MathModelZip/MathModel/):
    Package with the mathematical model built using the OptimizationLib.mm module.
2. [UI](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/MathModelZip/UI/):
   Package with the UI for the MathModel LinkedIn Zip problem.

### [Controller](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/MathModelZip/Controller.java):
This Class controls the communication between the UI and the math model running in the back.

### [RunLinkedInZip](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Zip/MathModelZip/RunLinkedInZip.java):
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
(round 1, fast)       0 del vars, 58 del conss, 0 add conss, 0 chg bounds, 228 chg sides, 269 chg coeffs, 0 upgd conss, 159 impls, 73 clqs
(round 2, exhaustive) 0 del vars, 69 del conss, 0 add conss, 0 chg bounds, 228 chg sides, 269 chg coeffs, 0 upgd conss, 159 impls, 73 clqs
(round 3, exhaustive) 0 del vars, 84 del conss, 0 add conss, 0 chg bounds, 228 chg sides, 269 chg coeffs, 0 upgd conss, 159 impls, 73 clqs
(round 4, exhaustive) 0 del vars, 84 del conss, 0 add conss, 0 chg bounds, 228 chg sides, 269 chg coeffs, 34 upgd conss, 159 impls, 73 clqs
(round 5, fast)       0 del vars, 87 del conss, 0 add conss, 0 chg bounds, 228 chg sides, 269 chg coeffs, 34 upgd conss, 169 impls, 73 clqs
presolving (6 rounds: 6 fast, 4 medium, 4 exhaustive):
 0 deleted vars, 88 deleted constraints, 0 added constraints, 0 tightened bounds, 0 added holes, 228 changed sides, 270 changed coefficients
 169 implications, 73 cliques
presolved problem has 175 variables (128 bin, 47 int, 0 impl, 0 cont) and 1827 constraints
     47 constraints of type <varbound>
     59 constraints of type <setppc>
    298 constraints of type <linear>
     10 constraints of type <logicor>
   1413 constraints of type <bounddisjunction>
transformed objective value is always integral (scale: 1)
Presolving Time: 0.00

 time | node  | left  |LP iter|LP it/n|mem/heur|mdpt |vars |cons |rows |cuts |sepa|confs|strbr|  dualbound   | primalbound  |  gap   | compl. 
  3.0s|     1 |     0 | 54881 |     - |    17M |   0 | 175 |1837 | 370 |   0 |  0 |3521 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 54901 |     - |    17M |   0 | 175 |1837 | 384 |  14 |  1 |3521 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55003 |     - |    17M |   0 | 175 |1837 | 391 |  21 |  2 |3521 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55042 |     - |    17M |   0 | 175 |1837 | 408 |  38 |  3 |3521 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55058 |     - |    17M |   0 | 175 |1837 | 416 |  46 |  4 |3521 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55126 |     - |    17M |   0 | 175 |1837 | 428 |  58 |  5 |3521 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55221 |     - |    17M |   0 | 175 |1838 | 439 |  69 |  6 |3522 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55248 |     - |    17M |   0 | 175 |1838 | 453 |  83 |  7 |3523 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55297 |     - |    17M |   0 | 175 |1840 | 468 |  98 |  8 |3525 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55317 |     - |    17M |   0 | 175 |1840 | 476 | 106 |  9 |3525 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55345 |     - |    18M |   0 | 175 |1842 | 482 | 112 | 10 |3527 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55363 |     - |    18M |   0 | 175 |1842 | 491 | 121 | 11 |3527 |  52 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55401 |     - |    18M |   0 | 175 |1835 | 491 | 121 | 11 |3531 |  55 | 0.000000e+00 |      --      |    Inf | unknown
  3.0s|     1 |     0 | 55404 |     - |    18M |   0 | 175 |1839 | 494 | 124 | 12 |3535 |  55 | 0.000000e+00 |      --      |    Inf | unknown
  4.0s|     1 |     0 | 55412 |     - |    18M |   0 | 175 |1835 | 411 | 124 | 13 |3537 |  66 | 0.000000e+00 |      --      |    Inf | unknown
 time | node  | left  |LP iter|LP it/n|mem/heur|mdpt |vars |cons |rows |cuts |sepa|confs|strbr|  dualbound   | primalbound  |  gap   | compl. 
  4.0s|     1 |     0 | 55423 |     - |    18M |   0 | 175 |1836 | 416 | 129 | 14 |3538 |  66 | 0.000000e+00 |      --      |    Inf | unknown
  4.0s|     1 |     0 | 55432 |     - |    18M |   0 | 175 |1836 | 420 | 133 | 15 |3538 |  66 | 0.000000e+00 |      --      |    Inf | unknown
  4.0s|     1 |     2 | 55432 |     - |    18M |   0 | 175 |1836 | 420 | 133 | 15 |3538 |  70 | 0.000000e+00 |      --      |    Inf | unknown
  4.0s|   100 |    89 | 56839 |  10.6 |    18M |  44 | 175 |1862 |   0 | 234 |  0 |3587 |  70 | 0.000000e+00 |      --      |    Inf | unknown
  4.0s|   200 |   125 | 57250 |  10.5 |    18M |  82 | 175 |1842 |   0 | 243 |  0 |3612 |  70 | 0.000000e+00 |      --      |    Inf | unknown
  4.0s|   300 |   137 | 57686 |  10.4 |    18M |  82 | 175 |1827 | 428 | 295 |  1 |3660 |  70 | 0.000000e+00 |      --      |    Inf |   3.32%
  4.0s|   400 |   119 | 58142 |  10.3 |    18M |  91 | 175 |1845 |   0 | 339 |  0 |3730 |  70 | 0.000000e+00 |      --      |    Inf |   3.32%
  4.0s|   500 |   135 | 58647 |  10.2 |    18M |  91 | 175 |1823 | 427 | 344 |  1 |3799 |  70 | 0.000000e+00 |      --      |    Inf |   3.32%
  4.0s|   600 |   149 | 59216 |  10.1 |    19M |  91 | 175 |1768 |   0 | 359 |  0 |3854 |  70 | 0.000000e+00 |      --      |    Inf |   3.32%
  4.0s|   700 |   145 | 59541 |  10.0 |    19M |  91 | 175 |1780 |   0 | 377 |  0 |3889 |  70 | 0.000000e+00 |      --      |    Inf |   3.32%
  4.0s|   800 |   171 | 59722 |   9.8 |    19M | 107 | 175 |1808 |   0 | 378 |  0 |3933 |  70 | 0.000000e+00 |      --      |    Inf |   3.32%
  4.0s|   900 |   164 | 59871 |   9.7 |    19M | 107 | 175 |1810 |   0 | 382 |  0 |3954 |  70 | 0.000000e+00 |      --      |    Inf |   3.32%
  4.0s|  1000 |   151 | 59966 |   9.6 |    19M | 107 | 175 |1827 | 430 | 384 |  1 |4005 |  70 | 0.000000e+00 |      --      |    Inf |   3.32%
  4.0s|  1100 |   181 | 60178 |   9.4 |    19M | 107 | 175 |1826 |   0 | 384 |  0 |4024 |  70 | 0.000000e+00 |      --      |    Inf |   3.32%
****
****
****
 time | node  | left  |LP iter|LP it/n|mem/heur|mdpt |vars |cons |rows |cuts |sepa|confs|strbr|  dualbound   | primalbound  |  gap   | compl. 
  377s|850300 |   257 |  4256k|   5.0 |   174M | 107 | 175 |4472 | 419 |1016k|  1 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
  377s|850400 |   255 |  4256k|   5.0 |   174M | 107 | 175 |4480 |   0 |1016k|  0 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
  377s|850500 |   247 |  4257k|   5.0 |   174M | 107 | 175 |4481 | 419 |1016k|  1 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
  378s|850600 |   246 |  4257k|   5.0 |   174M | 107 | 175 |4481 | 391 |1016k|  1 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
  378s|850700 |   264 |  4258k|   5.0 |   174M | 107 | 175 |4481 | 402 |1016k|  1 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
  378s|850800 |   260 |  4258k|   5.0 |   174M | 107 | 175 |4481 | 407 |1016k|  1 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
  378s|850900 |   247 |  4258k|   5.0 |   174M | 107 | 175 |4472 | 413 |1016k|  1 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
  378s|851000 |   232 |  4259k|   5.0 |   174M | 107 | 175 |4441 |   0 |1016k|  0 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
  378s|851100 |   241 |  4259k|   5.0 |   174M | 107 | 175 |4386 | 418 |1016k|  1 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
  378s|851200 |   242 |  4260k|   5.0 |   174M | 107 | 175 |4366 | 422 |1016k|  1 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
  378s|851300 |   255 |  4260k|   5.0 |   174M | 107 | 175 |4365 | 418 |1017k|  1 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
  378s|851400 |   252 |  4261k|   5.0 |   174M | 107 | 175 |4391 | 392 |1017k|  1 | 349k|  70 | 0.000000e+00 |      --      |    Inf |  61.08%
* 378s|851425 |     0 |  4261k|   5.0 |    LP  | 107 | 175 |4400 | 392 |1017k|  1 | 349k|  70 | 0.000000e+00 | 0.000000e+00 |   0.00%| 100.00%

SCIP Status        : problem is solved [optimal solution found]
Solving Time (sec) : 378.00
Solving Nodes      : 851425 (total of 856547 nodes in 2 runs)
Primal Bound       : +0.00000000000000e+00 (1 solutions)
Dual Bound         : +0.00000000000000e+00
Gap                : 0.00 %
`````

## Comment:
This problem is solved using the power of Mixed Integer Linear Programming. It is just a guide that the user can follow 
to build its own problems.

In this example, we didn't conduct an extensive optimization of the variables and constraints created. 
Nevertheless, we were able to get the solutions for the scenario in a time that is not as good as a human is able to do it. :smirk_cat:
