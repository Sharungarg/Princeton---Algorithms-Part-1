# Princeton - Algorithms, Part 1
Assignment's solution of [Princeton University - Algorithms, Part 1](https://www.coursera.org/learn/algorithms-part1) course hosted on [Coursera](https://www.coursera.org/).  
**Every assignmet passed the assessment with atleast 91% score.**
 
## Important Points:
* The enhancements with DataStructures involving Hashing is not done as it was out of the scope of assignments.
* **Week 2:**  
[RandomizedQueue](https://github.com/Sharungarg/Princeton---Algorithms-Part-1/blob/master/Week%202/queues/RandomizedQueue.java) uses normalization of array whenever "empty spaces" are >= to the number of elements present in array. It works in constant amortized time but the operation of finding a valid index has 0.5 probability of success in worst case.  
_One Enhancement over it is the use of SWAP method. Whenever "dequeue" is called, the element dequeued is replaced by last element in the array. That will keep the array packed._
