# MathModelTango
For this LinkedIn Tango problem, we use MIP to formulate and solve a linear problem.
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
            <li>$\textcolor{blue}{FC} =$ FixedCells in the board.
            </li>
         </ul>
      </td>
      <td>
        <ul>
           <li>
                $\textcolor{magenta}{FCV_{fc}} \in \{0,1\} \for \{ (r, c) \mid r \in \textcolor{blue}{R}, c \in \textcolor{blue}{C}\}, \; = \textbf{(Fixed Cell)}$ Cells type.
           </li>
           <li>
                $\textcolor{magenta}{CR_{r,c, i}} \in \{0,1\} \for \{ (r, c) \mid r \in \textcolor{blue}{R}, c \in \textcolor{blue}{C}, i \in N\}, \; = \textbf{(Fixed Cell)}$ Cell relation with other.
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
           Grid Cell Type
      </td>
      <td>
           $GCT_{r, c} \in \mathbb{Z}:\{0,1\}, \; r \in \textcolor{blue}{R}, \; \forall c \in \textcolor{blue}{C} $
      </td>
      <td>
        Grid Cell Type. Binary variable where 1 implies "sun" :sunny: and 0 "moon" :first_quarter_moon_with_face:
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
    <td><b>NotThreeConsecutiveByRow</b></td>
    <td>$$ $0 < GCT_{r,c} + GCT_{r,c+1} + GCT_{r,c+2}<3,\; \forall c \in \textcolor{blue}{C}-2$$</td>
    <td> $|\textcolor{blue}{C}-2|$ </td>
    <td> At most two ones every three consecutive columns, for each row.</td>
  </tr>
  <tr>
    <td><b>NotThreeConsecutiveByCol</b></td>
    <td>$$ $0 < GCT_{r,c} + GCT_{r+1,c} + GCT_{r+2,c}<3,\; \forall r \in \textcolor{blue}{R}-2$$</td>
    <td> $|\textcolor{blue}{R}-2|$ </td>
    <td> At most two ones every three consecutive rows, for each column.</td>
  </tr>
  <tr>
    <td><b>SameZeroesAndOnesByRow</b></td>
    <td>$$\sum_{c \in \textcolor{blue}{C}} GCT_{r,c}==|\textcolor{blue}{C}/2|,\; \forall r \in \textcolor{blue}{R}$$</td>
    <td> $|\textcolor{blue}{R}|$ </td>
    <td> Each row must contain as many ones as zeros.</td>
  </tr>
  <tr>
    <td><b>SameZeroesAndOnesByCol</b></td>
    <td>$$\sum_{r \in \textcolor{blue}{R}} GCT_{r,c}==|\textcolor{blue}{R}/2|,\; \forall c \in \textcolor{blue}{C}$$</td>
    <td> $|\textcolor{blue}{C}|$ </td>
    <td> Each column must contain as many ones as zeros.</td>
  </tr>
  <tr>
    <td><b>FixedCells</b></td>
    <td>$$\ GCT_{r,c} == \textcolor{magenta}{FC_{r,c}},\; \for c \in \textcolor{blue}{C}, \; \for r \in \textcolor{blue}{R}$$</td>
    <td> $\cdot |\textcolor{blue}{C} \cdot \textcolor{blue}{R}|$ </td>
    <td> Follow cell assignation.</td>
  </tr>
  <tr>
    <td><b>FixedRelations</b></td>
    <td>$$\ GCT_{r,c} == \textcolor{magenta}{CR_{r,c,1}} \and \textcolor{magenta}{CR_{r,c,2}} \and \cdot\cdot\cdot \and\textcolor{magenta}{CR_{r,c,n}},\; \forall c \in \textcolor{blue}{C}, \; \forall r \in \textcolor{blue}{R}$$</td>
    <td> $4 \cdot |\textcolor{blue}{C} \cdot \textcolor{blue}{R}|$ </td>
    <td> Follow the fixed relations between cells.</td>
  </tr>
</table>

## In this folder:
This folder contains two different classes and two packages that define the structures required for solving the problem.
These classes are used as main and controller.
1. [Controller](#controller): Controller that communicates the UI with the MathematicaModel.
2. [RunLinkedInTango](#runlinkedinTango): The main class for instantiation of the frontend.


The classes created to contain the problem information.
1. [MathModel](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/MathModelTango/MathModel/):
    Package with the mathematical model built using the OptimizationLib.mm module.
2. [UI](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/MathModelTango/UI/):
   Package with the UI for the MathModel LinkedIn Tango problem.

### [Controller](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/MathModelTango/Controller.java):
This Class controls the communication between the UI and the math model running in the back.

### [RunLinkedInTango](https://github.com/SergioOyaga/LinkedInOptimizationExamples/blob/master/src/main/java/org/soyaga/examples/Tango/MathModelTango/RunLinkedInTango.java):
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
(round 1, fast)       30 del vars, 67 del conss, 0 add conss, 12 chg bounds, 3 chg sides, 0 chg coeffs, 0 upgd conss, 0 impls, 1 clqs
(round 2, fast)       36 del vars, 94 del conss, 0 add conss, 18 chg bounds, 4 chg sides, 0 chg coeffs, 0 upgd conss, 0 impls, 0 clqs
   Deactivated symmetry handling methods, since SCIP was built without symmetry detector (SYM=none).
presolving (3 rounds: 3 fast, 1 medium, 1 exhaustive):
 36 deleted vars, 96 deleted constraints, 0 added constraints, 18 tightened bounds, 0 added holes, 4 changed sides, 0 changed coefficients
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
