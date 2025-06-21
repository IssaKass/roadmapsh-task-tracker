# roadmapsh-task-tracker

A simple command-line application to track and manage your tasks — a lightweight to-do list tool that stores tasks in a
JSON file.

---

## Features

- Add new tasks with descriptions
- Update existing task descriptions
- Delete tasks by ID
- Mark tasks as **todo**, **in-progress**, or **done**
- List all tasks or filter tasks by their status
- Tasks are persisted in a JSON file in the current directory
- No external libraries used — built with native file system and standard libraries
- Graceful handling of errors and edge cases

## Task Properties

Each task contains:

| Property      | Description                                           |
|---------------|-------------------------------------------------------|
| `id`          | Unique numeric identifier                             |
| `description` | Short text describing the task                        |
| `status`      | Task state — one of: `todo`, `in-progress`, or `done` |
| `createdAt`   | Timestamp when the task was created                   |
| `updatedAt`   | Timestamp of the last update to the task              |

---

## Usage

1. Clone the repository:
    ```bash
    git clone https://github.com/IssaKass/roadmapsh-task-tracker.git
    cd roadmapsh-task-tracker
    ```
2. Compile and run `Application.java`


Sample solution for the [Task Tracker](https://roadmap.sh/projects/task-tracker) challenge from [roadmap.sh](https://roadmap.sh).