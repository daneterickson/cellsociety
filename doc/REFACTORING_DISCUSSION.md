## Lab Discussion
### Team
8
### Names
Dane Erickson dte12, Aaric Han ah496, Nick Strauch njs35, Albert Yuan aly11

### Issues in Current Code
Not very fully flexible for new changes. We also have a couple very large classes that have too many responsibilities. We also have a few protected variables. Our view and controller testing also needs to be improved slightly. We also need to use the properties file.

#### fishRules method and sharkRules method in predatorPreyModel class
 * These methods are well over the 20 line method limit.

 * These methods have too many responsibilties. The methods should instead call helper methods when needed to divide the workload.

#### Controller class
 * This class slowly slowly began growing in size and developing oversized methods. The class currently is not readable

 * The openSIM method needs to be refactored. It currently is way too long.


### Refactoring Plan

 * What are the code's biggest issues?
  * We need to include the properties file. We also need to refactor some of the larger classes in model and view.

 * Which issues are easy to fix and which are hard?
   * Adding the properties file should be an easy fix. Resizing the large classes will be a harder fix.

 * What is your plan to implement the changes without losing control of the process?
   * We will make sure to divide the work up into very small chunks. We plan to tackle the large classes by first planning what methods could be extracted and/or moved to an entirely separate class. 


### Refactoring Work

 * Issue chosen: Fix and Alternatives
   * We are adding the properties file to the view. Our fix was to create on resource bundle instance in the mainview class and then we are passing it to all of the other view classes when they are created.
  * An alternative would be to directly create multiple resource bundle classes in all of the classes where we needed it. This would be a faster fix; however, it would lead to duplicated code.


 * Issue chosen: Fix and Alternatives
