# Assignment 2
## Problem 1
### Strategy

This problem is similar to the prisoner and light switch problem that we had encountered in class. Here are the steps I followed to solve the problem:
-  Assign any one of the guests to be the counter. The counter will keep track of the number of guests to pass through the labyrinth. In my case, I assigned the counter to be the first guest.
-  The counter is initialized to 0 to account for himself.

When the counter enters the room...
- If there is no cupcake, then the counter will increment by one because that means someone has passed through and eaten the cupcake.
- If there is a cupcake, then the counter will not increment because that means the cupcake has not been eaten yet.
- When the count reaches the number of guests, then the counter will announce that all the guests have passed through the labyrinth.

When any other guest enters the room...
- If there is no cupcake, then the guest will do nothing.
- If there is a cupcake, then the guest will eat the cupcake only if it is their first time entering the room with the cupcake present. This is to ensure that the cupcake is not eaten more than once by a guest.

In this simulation, I used a Reentrant lock to ensure that only one guest can enter the labyrinth at a time. I also toggled on the fairness policy so that the "Guest" threads will not hog the lock and preveneting the "Counter" guest thread from entering the labyrinth or other guests from eating the cupcake.

### How to run

```bash
cd "Problem 1"
javac Minotaur.java Guest.java 
java Minotaur
```

You can change the number of guests by altering the value of the `NUM_GUESTS` variable in the `Minotaur.java` file.

You can also change the speed of the simulation by altering the value of the `TIME_MULTIPLIER` variable in the `Minotaur.java` file. This is multiplied to every `Thread.sleep()` call in the program. It is currently set to 0 so that the simulation does not sleep at all.

## Problem 2
### Strategy

For this problem, I chose to implement soluton #2 from the assignment description.

I believe this strategy is most fair because it ensures that all guests who join the line will eventually get a turn to observe the vase.

To simulate a queue, I used a Reentrant lock to ensure that only one guest can enter the showroom at a time. I also toggled on the fairness policy so that the "Guest" threads that wait the longest will be granted access to the showroom first.

### How to run

```bash
cd "Problem 2"
javac Party.java Guest.java
java Party
```

You can change the number of guests by altering the value of the `NUM_GUESTS` variable in the `Party.java` file.

You can also change the duration of the party by altering the value of the `PARTY_DURATION_SECONDS` variable in the `Party.java` file.

You can also change the speed of the simulation by altering the value of the `TIME_MULTIPLIER` variable in the `Party.java` file. This is multiplied to every `Thread.sleep()` call in the program.
