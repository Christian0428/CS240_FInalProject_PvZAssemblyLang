# CS240_FInalProject_PvZAssemblyLang

In my assembly language, I have around 20 instructions that have their own unique actions.
(REMINDER: THIS IS NOT COMPLETE AND I AM STILL MAKING MODIFICATIONS DESPITE THIS BEING AN ASSIGNMENT AND TURNED IN)

Since module 1, I have decided to add an "addi" instruction primarily for the use of setting values intially without actually using one of the dedicated functions outside of its use. I might be adding other basic primary and reused MARSMIPPS instruction (have not decided yet)
All of these instructions otehr than the basic known instructions were all implemented by me and like stated before have their very unique functions. For example, in the game Plants vs. Zombies, you first start with a set amount of sun. In my language you manually have to add sun to your inventory but am currently working on a set function to automatically do so.
Another instruction would be placing plants and spending sun. Once again I these are suppsoed to be uatmatic functions and by the time these are checked and graded (hopefully by presentations) I will try to find a way to make these functions.
One of the more unique functions I have is the frozen peashooter where it uses an add function where it takes in the three registers, the damage of the plant, the health of the zombie, and the new speed of the zombie. In the function, the plant deals damage an amount of damage to the zombiue decreasing it's health and also decreasing it's speed to the player's desire.
(UPDATE) I've made a clock as a newer more unique function where it takes in three paramters after a "second." It takes in the amount of sun a user has and adds 25, takes in the zombie postion and moves it forward, and recrements the timer by one second. I was also going to add in a second register where it modifies the zombies health but would make it incosenstent whereas not all zombies take damage when they move forward.
