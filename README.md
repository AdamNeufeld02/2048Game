# My Personal Project:

## 2048 Game

**What will the project do?**

A game where the goal is to combine numbers randomly appearing on a grid until you have reached a 
goal (Usually the number 2048) or when you have no more possible moves to make.

**Rules:**
- Maximum of four possible moves per turn. left, right, up down.
    - any of these four moves shifts all numbers in the corresponding directions skipping over all empty space
    - any identical numbers adjacent in the axis of movement or separated by empty space will be combined with their values 
    added together.
    - every move a 2 or 4 will spawn randomly in one of the empty grid spaces.
- Game ends when there are no possible moves to make or when there are no empty spaces and no adjacent values.

**Who will use it?**

Anyone looking to play a relaxing easy to play game.

**Why is this project of interest to me?**

I have played this game before and am interested in what it would take to make. It seems like a fun project to complete.

## User Stories
- As a user, I want to be able to add cells to the board and customize its size before starting.
- As a user, I want to be able to set a goal to reach within the game.
- As a user, I want to be able to execute moves within the game.
- As a user, I want to be able to view my current score in the game.
- As a user, I want to be able to view the number of moves I have made in the game.
- As a user, I want to be able to save my current game.
- As a user, I want to be able to load my previous game.

## Phase 4: Task 2
I decided to make the board class robust by making the setSize method throw the SizeException and each of the swapCells 
mergeCells setCell and getCellAt throw the IndexException.

## Phase 4: Task 3.
Some refactoring I would do with my design is to create a new class which handles moving the board.
Currently the NumberMergeGame handles board movements, but I believe that it is a big enough responsibility to require its
own class. There is also a lot of duplicate code in the code that moves the board and hopefully some of that
could be abstracted out in the new class.