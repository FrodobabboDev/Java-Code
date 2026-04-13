
import java.util.ArrayList;
import java.util.Scanner;

//view all tasks
//mark a task as completed
//delete a task

//due date
//priority level
//save tasks to a file
//load tasks when program starts


class Task
{
  private String title;
  private boolean completed;

  //constructor: creates a new task object with a title, and starts as not completed
  public Task(String title)
  {
    this.title = title;
    this.completed = false;
  }

  //getter: returns the title of the task
  public String getTitle()
  {
    return title;
  }

  //getter: returns true of completed, false if not
  public boolean isCompleted()
  {
    return completed;
  }

  //changes the task status to completed
  public void markCompleted()
  {
    completed = true;
  }

  //toString: returns a string version of the task [X] Homework or [ ] Homework
  public String toString()
  {
    return (completed ? "[X] " : "[ ] ") + title;
  }
  
}

class ToDoList
{
  private ArrayList<Task> tasks; //array list that sores all task objects

  //constructor: creates an empty list of tasks
  public ToDoList()
  {
    tasks = new ArrayList<>();
  }

  //adds a new task to the list, takes in a title, creates a task object, and stores it
  public void addTask(String title)
  {
    tasks.add(new Task(title)); //arraylist called tasks adds a new task with title received
  }

  public void viewTasks() //displays all tasks in the list
  { 
    if(tasks.isEmpty()) // if the arrays tasks is empty
    {
      System.out.println("No tasks found"); // dispaly this message
    }

    
    for(int i = 0; i < tasks.size(); i++) // loop through the list and print each task
    {
      System.out.println((i + 1) + ". " + tasks.get(i)); // i + 1 is used so the numbering starts at 1 not 0
    }
  }

  public void completeTask(int index) // marks a task as completed using its index
  {
    if(index >= 0 && index < tasks.size()) //check if index is valid
    {
      tasks.get(index).markCompleted(); //using the index (pointer for task) call the markCompleted method
    }
    else
    {
      System.out.println("Invalid task number.");
    }
  }

  public void removeTask(int index) //removes a task from the list using its index
  {
    if(index >= 0 && index < tasks.size()) //check if index is valid
    {
      tasks.remove(index);
    }
    else
    {
      System.out.println("Invalid task nunmber");
    }
  }
}

class toDo
{
  public static void main(String[] args)
  {
    Scanner input = new Scanner(System.in); //scanner object letrs the user type input

    ToDoList list = new ToDoList(); // create one ToDoList object to manage all tasks

    int choice = 0; //stores the users menu choice

    while (choice != 5) // keep showing menu unless user inputs 5
    {
      System.out.println("\nTo-Do List Menu");
      System.out.println("1. Add Task");
      System.out.println("2. View Task");
      System.out.println("3. Complete Task");
      System.out.println("4. Remove Task");
      System.out.println("5. Exit");
      System.out.println("------------------");
      System.out.println("Enter Choice: ");

      choice = input.nextInt(); // read users input

      input.nextLine(); //clear the buffer

      if(choice == 1) //option 1
      {
        System.out.println("Enter task title: ");
        String title = input.nextLine(); // stores users title into title 
        list.addTask(title); //stoes title in list 
      }
      else if(choice == 2) // view all tasks
      {
        list.viewTasks();
      }
      else if(choice == 3) // marks as completed
      {
        System.out.println("Enter task number to complete: ");
        int num = input.nextInt();
        list.completeTask(num - 1); //sub 1 becaus list index data starts at 0
      }
      else if(choice == 4) // remove a task
      {
        System.out.println("Enter task number to remove: ");
        int num = input.nextInt();
        list.removeTask(num - 1);
      }
      else if(choice == 5) //exit the program
      {
        System.out.println("Goodbye!");
      }
      else 
      {
        System.out.println("Invalid Choice.");
      }

    }
    input.close();
  }
}
