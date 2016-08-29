# Concurrency Practice
## Few different implementations of parsing the text of a short story, and keeping track of the count of each word
### The motivation behind this seperate project is to find out the best, and most suitable way to parse bodies of text, and then add that functionality into the Articles project (see: https://github.com/ammartinez008/articles). 

#### Parser
- linear: single threaded parser that uses a hashmap to keep track of word count
- SynchronizedParser: multi threaded parser that uses synchronization methods for thread handling
- ThreadedParser: multi threaded parser that uses the Concurrent library for thread handling


#### How to Use
- Scheduler is the class that runs the main. runLinear will call the linear parser, and runSynchronized will call the synchronized parser. TODO: add seperate method for ThreadedParser.


#### Contributors
Alex Martinez
