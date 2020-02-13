# NFA-Simulator

Program to simulate a non deterministic finite automaton. Written in Java.

Given an NFA as described in sample_1.txt, the program will output all accept states if one or more accepts states are reacheable via state transitions. If no accept states are reacheable, the program will output all reject states.

To be compiled and used via the command line.
Usage: ./man_p1 FILE STRING
  where FILE indicates a txt file describing the NFA
    the format (tab delimited) should be as follows:
      (state lines, where x describes the state)
        state x start
        state x accept
        state x start accept
        state x accept  start
      (transition lines, where x and y describe the states and 0 is a char to be read from STRING)
        transition  x 0 y
  where STRING is an input string to test to see if it's in the language of the NFA

sample usage:
tman1@remote07:~$ java man_p1 sample_1.txt 000101
accept 7
