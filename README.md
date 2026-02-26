# 📋 Task Manager Application
# 📝 Description


A comprehensive Java-based Task Manager application with both Console and Graphical User Interface (GUI) versions to help you organize and track your tasks efficiently.

---

## ✨ Features

###  Core Features
- ✅ **Add Tasks** - Create new tasks with name, priority (High/Medium/Low), and deadline
- ✅ **Track Task Status** - Monitor pending, finished, and expired tasks
- ✅ **Finish Tasks** - Mark tasks as completed
- ✅ **Remove Tasks** - Delete individual tasks or clear all tasks at once
- ✅ **Persistent Storage** - Tasks are automatically saved to `tasks.txt` file
- ✅ **Unique ID Generation** - Each task receives a unique identifier

### Console Version Features
- Interactive menu-driven interface
- View tasks in a formatted table

### GUI Version Features
- Modern Swing-based interface
- Real-time task display table
- Refresh functionality to update task statuses
- Confirmation dialogs for destructive actions

---
# 📁 File Structure
<pre>
task-manager-java/
├── src/                    # Source code
│   ├── console/           
│   │   ├── App_Runner.java    # Console version main class
│   │   └── list_methods.java  # Console version logic
│   └── UI/                
│       ├── AppRunner.java     # GUI version entry point
│       └── ToDoApp.java       # GUI implementation
├── .gitignore              # Git ignore rules
├── README.md               # This file
└── tasks.txt               # Auto-generated task storage (created after first run)
</pre>


## 📦 Requirements

- **Java Development Kit (JDK)** 8 or higher


---

## ⚙️ Installation

### Step 1: Clone or Download
```bash
git clone https://github.com/MohamedAlsayed334/Task-manager.git
```
- Or download the ZIP file and extract it to your preferred location.


### Step 2: Navigate to Project Directory
```bash
cd Task-manager
```
### Step 3: Compile the Source Code
For Console Version:
```bash
# Compile console version
javac src/console/*.java
```
For GUI Version:


```bash
# Compile GUI version
javac src/UI/*.java
```
Compile Both Versions:
```bash
# Compile all Java files
javac src/console/*.java src/UI/*.java
```
### Step 4: Run the Application
Console Version:
```bash
java -cp src console.App_Runner
```

GUI Version:
```bash
# If you compiled to bin directory
java -cp src UI.AppRunner
```
# 🚀 How to Run
  Quick Start Options:
- Using an IDE (Eclipse/IntelliJ/VS Code)
Open the project in your IDE

- Navigate to the src folder

- Run App_Runner.java for console interface

- Run AppRunner.java for GUI interface

Using Command Line:

Console Version:


```bash
# Navigate to project folder
cd Task-manager

# Compile and run console version
javac src/console/*.java
java -cp src console.App_Runner
```
GUI Version:

```bash
# Navigate to project folder
cd Task-manager

# Compile and run GUI version
javac src/UI/*.java
java -cp src UI.AppRunner
```
---
# 👨‍💻 Author

**Mohamed AlSayed**
---
Made with ❤️ by me
[GitHub](https://github.com/MohamedAlsayed334)