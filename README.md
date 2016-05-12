# SE450 OOP design final project

# Instrutions to run the program:
1. Clone the repo, run the SE450Project/main/Main.java
2. Type the parameters to adjust the traffic pattern or just `run`

# Notes on patterns:

-   State pattern used in the light class: after finishing the code of
    light class, I immediately recognized it as state pattern, which I
    didn't mean to implement at the first place. Just like the “gum ball
    machine” in the head first book, light class has 4 different states
    and each state has it's own effect to the car on the roads. By
    implementing a state pattern, I encapsulate the **time **variables
    and unify the output of the class by using getState() method call.
-   Singleton pattern used for generating random variables across the
    model package. By using singleton pattern, I can manage the outcome
    of the animation easily by adjust the seed variable inside
    RandomGenerator class. Also, singleton class save the resource by
    only have one instance of that class per program run, encapsulate
    the method for generating a random number for the car, road and
    light parameters.
-   Observer pattern used for connecting view and model. This pattern is
    the most import one in the program: by observing the change of the
    internal parameter change of the car, road and light, the view could
    change the animation accordingly. Also, the view class is not
    actively observing the model class but rather being notified by the
    model class that the model has changed. This pattern solved the
    problem of how to synchronies the model and its presentation – view.
    Without this pattern, the separation of model and view will be
    not possible. Also, by let the observed notify the observer, we
    follow the sequence of data flow.
-   Builder pattern for facilitating of generating the different
    components of the UI elements. Builder pattern encapsulated the
    parameter and objects into simple method calls. Builder pattern
    hides the detailed implementation and makes it a more abstract way
    of constructing the objects. Without the builder pattern, the
    separation of model and its way of representing could not
    be possible. If you want a different way of the look the model, we
    only need to change a few line of code inside builder class since
    the builder is using the abstract **animator** interface.

# Successes and failures:

This project is the single biggest programming homework I ever had. At
start, I was so intimidated by the huge amount of work I should do to
the project and the huge number of the classes and functions inside of
them that I did nothing in the first week beside staring at(highlight
and unhighlight) the starter code hopelessly. I mean, this project is so
different from the homework I did before: how can I do this without
proper guide comment inside code and "TODO" mark that tells you where to
put the code?

After one week's struggle, I finally made my mind trying to figure out
what the starter code did, what data should I provide to the view. I
start to make commend on the every function of SimpleModel explaining
what these method did. After going through the whole package, I felt
that my fear of this project is much lesser than before. I can starting
real thinking now! This is a huge leap of my project progress because
after this point, I'm energized by the desire to finish the project than
haunted by the thought I can never do this and give up.

Another lesson I learned from the project is think small. After the
first week's "I can't do this" panic, I start thinking about how can I
do to get the work done: chunk big goal down into some steps and step
one: making a car goes from one road to another and then die. Then, two
car in two roads. After that, many cars in many roads but a single
horizontal direction. The hardest part is the first step, after
finishing that, everything just came handy because the code and
mechanism are all in my head.

As for the design patterns, my approach is not thinking of them when I
writing the code. Functionality is my first concern because if I
consider the patterns first, I can't not make any progress of the
project. Do whatever it takes to fulfill the functions of the code, then
when I get everything working I'll look at the code and think about
design patterns. When I look at all the random numbers across the model
package, a singleton random class can fit the spot. For the light class,
when I look at all the states of the lights have, a state pattern is
there to be used.

In summary, although very hard and time consuming, I learned a lot from
this project(I even thinking about putting this into my resume). I
realized the huge difference between writing code from "TODO" from
designing programs. There's still a lot for me to learn and practice in
order to be a real software programmer.

