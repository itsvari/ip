# Songbird User Guide

Songbird is your ~~omnipotent AI overlord~~ friendly command-line task management chatbot from VariTech Heavy Industries.  It helps you keep track of your tasks, deadlines, and events, allowing you to stay organized and productive.  Powered by natural language processing, Songbird allows you to easily input date and time information using plain English.

## Table of Contents

1.  [Quick Start](#quick-start)
1. [Features & Commands](#features--commands)
    *   [List](#list)
    *   [Todo](#todo)
    *   [Deadline](#deadline)
    *   [Event](#event)
    *   [Mark](#mark)
    *   [Unmark](#unmark)
    *   [Delete](#delete)
    *   [Due](#due)
    *   [Find](#find)
    *   [Bye](#bye)
1. [Command Summary](#command-summary)

## Quick Start

1.  **Download the latest release:** Get the `songbird-v0.2.0.jar` file from the [Releases](https://github.com/itsvari/ip/releases) page.
2.  **Run Songbird:** Open your terminal or command prompt and navigate to the directory where you saved the `songbird-v0.2.0.jar` file.
3.  **Start the application:** Execute the following command:

    ```bash
    java -jar songbird-v0.2.0.jar
    ```
4.  **Start using Songbird:** Songbird will greet you, and you can start entering commands to manage your tasks.

## Features & Commands

Here's a list of all available commands and how to use them:

### List

*   **Description:**  Lists all saved tasks.

*   **Usage:**

    ```
    list
    ```


### Todo

*   **Description:** Adds a new "To Do" task to your list. A todo is a task without any specific deadline.

*   **Usage:**

    ```
    todo <task description>
    ```

*   **Example:**

    ```
    todo Buy groceries
    ```

### Deadline

*   **Description:** Adds a new task with a specific deadline.  You can specify the deadline using natural language.

*   **Usage:**

    ```
    deadline <task description> /by <deadline>
    ```

*   **Examples:**

    ```
    deadline Submit report /by next Friday 5pm
    deadline Pay bills /by 2024-12-24
    ```

### Event

*   **Description:**  Adds a new event with a start and end time. You can specify the start and end times using natural language.

*   **Usage:**

    ```
    event <event description> /from <start time> /to <end time>
    ```

*   **Examples:**

    ```
    event Project meeting /from next monday 2pm /to next monday 4pm
    event Vacation /from 2024-07-01 /to 2024-07-15
    ```

### Mark

*   **Description:** Marks a task as "done."

*   **Usage:**

    ```
    mark <task number>
    ```

*   **Example:**

    ```
    mark 1
    ```

### Unmark

*   **Description:** Marks a task as "not done."

*   **Usage:**

    ```
    unmark <task number>
    ```

*   **Example:**

    ```
    unmark 1
    ```

### Delete

*   **Description:** Deletes a task from the list.

*   **Usage:**

    ```
    delete <task number>
    ```

*   **Example:**

    ```
    delete 1
    ```

### Due

*   **Description:** Lists all tasks that are due on a specific date. Only applies to Deadline and Event tasks.  You can specify the date using natural language.

*   **Usage:**

    ```
    due <date>
    ```

*   **Examples:**

    ```
    due tomorrow
    due 2024-03-15
    ```

### Find

*   **Description:** Finds tasks that contain a specific keyword.  The search is case-insensitive.

*   **Usage:**

    ```
    find <keyword>
    ```

*   **Example:**

    ```
    find report
    ```

### Bye

*   **Description:** Exits the Songbird application.

*   **Usage:**

    ```
    bye
    ```


## Command Summary

| Command    | Description                                 | Usage                                               |
|:-----------|:--------------------------------------------|:----------------------------------------------------|
| `list`     | Lists all saved tasks.                      | `list`                                              |
| `todo`     | Adds a new ToDo task.                       | `todo <task description>`                           |
| `deadline` | Adds a new task with a deadline.            | `deadline <task description> /by <deadline>`        |
| `event`    | Adds a new event with a start and end time. | `event <event description> /from <start> /to <end>` |
| `mark`     | Marks a task as done.                       | `mark <task number>`                                |
| `unmark`   | Marks a task as not done.                   | `unmark <task number>`                              |
| `delete`   | Deletes a task.                             | `delete <task number>`                              |
| `due`      | Lists tasks due on a specific date.         | `due <date>`                                        |
| `find`     | Finds tasks containing a keyword.           | `find <keyword>`                                    |
| `bye`      | Exits Songbird.                             | `bye`                                               |