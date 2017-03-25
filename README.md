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
2. Repeated Forward A* (Tie-Breaking)
We ran repeated forward A* over the same 50 grid-worlds, with the same placement in each of agent and target, and found that when larger g-values are favored, the algorithm runs slightly faster.




Larger g(x) favored
Smaller g(x) favored
1
174.54 milliseconds
179.1 milliseconds
2
204.36 milliseconds
227.38 milliseconds
3
218.74 milliseconds
219.66 milliseconds
4
177.32 milliseconds
184.16 milliseconds
Average
193.74 milliseconds
202.575 milliseconds


On average, it took Repeated Forward A* 193.74 milliseconds to find a path from start to target (if a path exists) when larger g-values are favored.
On average, it took Repeated Forward A* 202.757 milliseconds to find a path from start to target (if a path exists) when smaller g-values are favored.
---
(We know that these times will change based on processing power, but we tested on three types of machines, and these results (where larger g-values were [faster/slower]) still held.)
---
In terms of the number of cells expanded, the chart below shows the total number of cells expanded when running Repeated Forward A* over 50 grid worlds of size 101 x 101. 

---

Small G Values
Large G Values
1
1229741
169377
2
757096
136812
3
1688772
143207
4
1521364
137192

We ran this multiple times, and found that when smaller g-values were favored, many more cells were expanded than when larger g-values were favored. 
---
Tie-breaking in favor of larger g-values (in other words, those nodes that are further from the initial destination) is better because they give you a better shot at reaching the target. That is, the target will rarely be very close to the agent. Choosing those nodes that have higher g-values will keep you from circling the agent, and will rather expand your search to a further node (that is hopefully closer to the target).
---
3. Forward vs. Backward
We ran both algorithms on the same 50 grid worlds, with the same placement of agent and target in each gridworld. We found that in every iteration of our search, Repeated Backward A* expanded more nodes than Repeated Forward A*. We chose to tie-break using larger g-values.
---



Repeated Forward A*
Repeated Backward A*
1
140907
155792
2
142130
137495
3
129070
143851
4
104009
104379
5
158030
148577
6
189230
154492
7
177601
122683

On average, Repeated Forward A* expanded 148711 cells per 50 grid worlds, and Repeated Backward A* expanded 138181 per 50 grid worlds.
---
Unsurprisingly, Repeated Forward A* and Repeated Backward A* expand around the same number of cells per grid world. In our implementations of Repeated Forward and Repeated Backward, we flip the locations of agent and target and use the same algorithm for both. As such, it’s unsurprising that the same number of cells was expanded. 
---
			
Part 5: Adaptive A*




Repeated Forward A*
Adaptive A* (run three times)
1
151908
17194
2
167821
18729
3
109485
13249
4
137826
14756
5
108475
12746
6
147204
18736
7
159820
19246
We ran both algorithms with the same placement of the target node, but allowed agent to change.
In almost all cases, the number of nodes expanded by Adaptive A* decreased, whereas in Repeated Forward A* it remained constant. This is unsurprising, given that each iteration of the algorithm (in Adaptive A*) gives even more insight into the grid and its makeup and allows for quicker path-planning every time. (Note: we broke ties in favor of cells with larger g-values).
---
