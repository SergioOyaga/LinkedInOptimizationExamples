# OptimizationLib GA Examples
This repository contains various LinkedIn optimization problems solved using the OptimizationLib framework. The examples serve as templates for readers, enabling them to understand and develop their own optimization problems by following the structures used in these examples.


| n | Package                                                                                                                                                             | Difficulty [1&rarr;5] | Comment                                        |
|---|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------|------------------------------------------------|
| 1 | MathModelQueens [MathModelQueens](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Queens/MathModelQueens) | ¿?                    | LinkedIn Queens using Mathematical Modeling.   |
| 2 | GeneticQueens [GeneticQueens](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Queens/GeneticQueens)       | ¿?                    | LinkedIn Queens using Genetic Algorithms.      |
| 3 | ACOQueens [ACOQueens](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Queens/ACOQueens)                   | ¿?                    | LinkedIn Queens using Ant Colony Optimization. |
| 4 | MathModelTango [MathModelTango](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Tango/MathModelTango)     | ¿?                    | LinkedIn Tango using Mathematical Modeling.    |
| 5 | GeneticTango [GeneticTango](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Tango/GeneticTango)           | ¿?                    | LinkedIn Tango using Genetic Algorithms.       |
| 6 | ACOTango [ACOTango](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Tango/ACOTango)                       | ¿?                    | LinkedIn Tango using Ant Colony Optimization.  |
| 7 | MathModelZip [MathModelZip](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Zip/MathModelZip)             | ¿?                    | LinkedIn Zip using Mathematical Modeling.      |
| 8 | GeneticZip [GeneticZip](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Zip/GeneticZip)                   | ¿?                    | LinkedIn Zip using Genetic Algorithms.         |
| 9 | ACOZip [ACOZip](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Zip/ACOZip)                               | ¿?                    | LinkedIn Zip using Ant Colony Optimization.    |

The difficulty level ranges from a minimum of (1) to a maximum of (5) in this repository.

## In This Folder

This folder contains three different problems LinkedIn solved using the OptimizationLib:

1. [Queens](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Queens):
   This problem involves placing N queens on an NxN chessboard such that each row, column and color only contains one Queen, and two queens cannot touch each other, not even diagonally.

2. [Tango](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Tango):
   This problem consists of filling an NxN grid so that each cell contains either a :sunny: or a :first_quarter_moon_with_face:. No more than 2 :sunny: or :first_quarter_moon_with_face: may be next to each other either vertically or horizontally. Each row and column must contain the same number of :sunny: and :first_quarter_moon_with_face:. There are some constraints between cells. Cells separated with = must be the same type, and cells separated by X must be the opposite type.

3. [Zip](https://github.com/SergioOyaga/LinkedInOptimizationExamples/tree/master/src/main/java/org/soyaga/examples/Zip):
   This problem consists of connecting X numbered cells of an NxM grid following the number order, and filling every cell with the path followed. Some barriers separate some cells.

## Comment:
The examples in this repository illustrate the power and versatility of the OptimizationLib framework. Remember, these examples serve as templates that you can adapt for your specific optimization problems.

