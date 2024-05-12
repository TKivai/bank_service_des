## Description

A project to simulate a Discrete Event Simulation of customer service of a bank.

### Project Input

The simulation input data is in a [CSV file](src/main/resources/values.csv).

It contains records of two columns: **Inter-Arrival Time** and **Service Time**. 

### Project Outputs

1. A table with all the inputs and generated event parameters for each customer.
2. General simulation statistics:

- Average Wait Time
- Probability that customer waits in queue
- Proportion of idle time of server
- Average Service time
- Average Waiting Time for those who wait
- Average Time spent in system
- Average time between Arrivals

## Running the Project

- If you have a fully featured Java IDE, make sure it is configured to use **maven** for this project.

- If you don't, a [Taskfile](https://taskfile.dev/) script is included to help easy build/run the project. Make sure you have the following installed.

  - Java (JRE or JDK)

  - Maven (Dependency Management)

  - [Task](https://taskfile.dev/installation/)

- Run **task run** to both build the project and run the generated JAR file.