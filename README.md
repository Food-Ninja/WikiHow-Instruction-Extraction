# WikiHow Instruction Analysis for Robot Manipulation

The goal of this tool is to analyse a WikiHow corpus using basic NLP techniques to gather information about Everyday tasks like *"Pouring"*, *"Cutting"* or *"Discarding"*.
These information should support cognitive robots in understanding and parameterizing these tasks to better handle unknown tasks, working in underspecified environments and handling common task-object combinations. 

## Dependencies
This tool relies on three external dependencies:

- [**JSON Simple 1.1**](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm) - Opening JSON files containing the WikiHow data
- [**Stanford CoreNLP 4.5.0**](https://stanfordnlp.github.io/CoreNLP/) - Handling NLP techniques like PoS-Tagging[^1]
- [**WikiHow Corpus**](https://drive.google.com/file/d/1ufBrqYoHTFoBtSxwYks6i_iR9HqmobxR/view) - WikiHow Corpus where each article is represented by 1 JSON file, crawled by[^2]
- The tool is tested using [**Java Version 18.0.2**](https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html)

## Installation
1. Download the project
2. Create a new folder in ``/WikiHowInstructionExtraction`` called ``data``
3. Extract the WikiHow corpus into the created folder
4. Add references to the following three ``.jar`` files:
	- json-simple-1.1.jar
	- stanford-corenlp-4.5.0.jar
	- stanford-corenlp-4.5.0-models.jar

## Using the Project
In general, the WikiHow articles analysed in this repository are structured in the following way. 
If not specified otherwise, this projects analyses the step descriptions since they contain the most details and have the most occurrences.

<img src="WikiHow Article Structure.png" width="600" alt="Summarising the structure of a WikiHow article"/><br>

To start the analysis, execute the main-Method in the ``ExtractionStarter`` class.
In general, the ``GlobalSettings`` class in the *utils* package contains parameters that can be changed to alter the program execution.
Each parameter is thoroughly explained through its comment.
If no startup argument is provided, a single verb will be analyzed according to the current settings.
If the argument 'hyponyms' is given, the occurrences of 20 different hyponyms for the verb *"Cut"* are analyzed and printed. 


[^1]: C. D. Manning, M. Surdeanu, J. Bauer, J. Finkel, S. J. Bethard, and D. McClosky, ‘The Stanford CoreNLP Natural Language Processing Toolkit’, in Proceedings of the 52nd Annual Meeting of the Association for Computational Linguistics: System Demonstrations, 2014, pp. 55–60. [Online]. Available: http://www.aclweb.org/anthology/P/P14/P14-5010
[^2]: L. Zhang, Q. Lyu, and C. Callison-Burch, ‘Reasoning about Goals, Steps, and Temporal Ordering with WikiHow’, in Proceedings of the 2020 Conference on Empirical Methods in Natural Language Processing (EMNLP), Online, 2020, pp. 4630–4639. doi: [10.18653/v1/2020.emnlp-main.374](http://dx.doi.org/10.18653/v1/2020.emnlp-main.374), [GitHub Project](https://github.com/zharry29/wikihow-goal-step).
