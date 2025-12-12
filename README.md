# Chess Game

This project is an implementation of a chess game using **Java Swing**. It features a **graphical user interface (GUI)** that allows two players to play chess, with functionalities like moving pieces, checking rules, and customizing themes.

## Screenshots

### Login Screen

The Login Screen allows players to enter their names and choose a theme for the game.
![Login Screen](https://github.com/user-attachments/assets/ea827ba3-582d-44e7-85d6-fa89a9f4b893)

### Chessboard During Gameplay

The chessboard shows the ongoing game, highlighting possible moves and indicating captured pieces.
![Chess Gameplay](https://github.com/user-attachments/assets/0ecf3834-e9da-4303-b3cf-6fd690b9257e)

---

## Features

### Key Features

1. **Graphical User Interface (GUI):**
   - The game uses **Java Swing** for a dynamic and interactive interface.
   - Background themes and styles enhance the gaming experience.

2. **Chess Rules:**
   - Implements basic chess rules such as moving pieces, capturing, checking, and checkmate detection.
   - Highlights valid moves to assist players during gameplay.

3. **Customization:**
   - Players can enter their names and choose a theme (e.g., **Green** or **Blue**) at the login screen.

4. **Game Flow:**
   - Displays captured pieces for each player.
   - A timer for each player tracks their gameplay duration.

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 11** or higher is required.
- A Java-compatible IDE (e.g., IntelliJ IDEA, Eclipse, VS Code) is recommended.

### Project Structure

The project follows a standard Maven-like directory layout:

- `src/main/java`: Source code (organized in `com.checkmate` packages).
- `src/main/resources`: Game resources (images, fonts).
- `bin`: Compiled class files (generated after running `run.sh`).

### Steps to Run

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/your-repo/chess-game.git
   cd CheckMate
   ./run.sh
   ```
