# AIFT

Repository to store files for our assignment for AI (CS520).
Group members: Ariel & Ashni
Assignment #1: Fast Trajectory Planning


Ariel Ahdoot (aa1046)
Ashni Mehta (am1531)
CS520 Assignment 1
Path-Finding and A*


Part 0 - Environment Setup / Description of Our Environment
We created a couple objects to help us run our searches - GridWorld, SquareNode, Square, and BinaryHeap. Our BinaryHeap has all the functionality of a traditional BinaryHeap, but bubbles down/up in different ways based on the tie-breaking method selected (in favor of larger g-values or smaller g-values). We also have a couple utility methods that a traditional BinaryHeap doesn’t have, which we use in our implementations of forward/backward/adaptive a*. 
---
SquareNode is primarily used to build linked lists, and we use it when building the ‘closed’ list.
GridWorld is our 101 x 101 grid world, that is comprised of squares. Each GridWorld has a fixed location of agent and a fixed location of target, both of which are randomly determined. The blocked/unblocked locations in GridWorld are also determined randomly (assigned with a chance of 30% being blocked).
---
Square represents a single square on the grid, and has associated f-, g-, and h-values, all of which are assigned upon grid creation. We do reassign h-values in Adaptive A*, and have to change them slightly when running Repeated Backwards A*, but they stay constant in Repeated Forward A*.
---
For ease of demonstrating the program to the TA/grader, we implemented three separate main classes. The number of cells expanded (total, over 50 grid-worlds), will be outputted.
---
To see what happens when small g-values are favored vs larger g-values being favored, run the Java class SmallLarge.java.
To see what happens when repeated forward A* and repeated backward A* are run on the same grids, run the Java class ForwardBackward.java.
To see what happens when Adaptive A* runs vs when regular Repeated Forward A* runs, run AdaptiveA.java.
---
