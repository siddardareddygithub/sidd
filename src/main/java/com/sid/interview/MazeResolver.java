package com.sid.interview;

import java.io.*;
import java.util.*;

public class MazeResolver {

// Directions: Right, Left, Down, Up
private static final int[] dRow = { 0, 0, 1, -1 };
private static final int[] dCol = { 1, -1, 0, 0 };

// Helper class to store row and column information for BFS
static class Position {
int row, col;

Position(int row, int col) {
this.row = row;
this.col = col;
}
}

public static void main(String[] args) {
if (args.length != 1) {
System.out.println("MazeReSolver input file <maze_file>");
return;
}

// Read maze from file
String filename = args[0];
char[][] maze = null;
Position start = null, goal = null;

try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
// Read first line that contains the dimensions of the maze where the first
// integer is the width and the second is the height
String[] dimensions = br.readLine().split(" ");
int width = Integer.parseInt(dimensions[0]);
int height = Integer.parseInt(dimensions[1]);

maze = new char[height][width];
// load the 2D Char array maze from the rest of the lines in file
for (int i = 0; i < height; i++) {
String line = br.readLine();
for (int j = 0; j < width; j++) {
maze[i][j] = line.charAt(j);
// Identify the start position
if (maze[i][j] == 'S') {
start = new Position(i, j);
//System.out.println("start position cell: " + i + " , " + j);
}

// Identify the end/goal position
if (maze[i][j] == 'G') {
goal = new Position(i, j);
//System.out.println("goalposition cell: " + i + " , " + j);
}

}
}

} catch (IOException e) {
e.printStackTrace();
return;
}

// Solve the maze using BFS alhorithm
if (start == null || goal == null) {
System.out.println("Maze must have start 'S' and goal 'G'");
return;
}
// pass the loaded maze char array and start, goal cell matrix
if (solveMazeBFS(maze, start, goal)) {
System.out.println("Path found:");
printMaze(maze);
} else {
System.out.println("No path found!");
}
}

// BFS algorithm to solve the maze
public static boolean solveMazeBFS(char[][] solve_maze, Position start, Position goal) {
int height = solve_maze.length;
int width = solve_maze[0].length;
boolean[][] visited = new boolean[height][width]; // To mark the cell is visited
Position[][] path_found = new Position[height][width]; // To track the path
// queue holds positions to explore
Queue<Position> queue = new LinkedList<>();
queue.offer(start);
visited[start.row][start.col] = true;

// BFS loop
while (!queue.isEmpty()) {
Position current = queue.poll();

// If we reached the goal, backtrack to find the path
if (current.row == goal.row && current.col == goal.col) {
markPath(solve_maze, path_found, goal);
return true;
}

// Explore neighbors (Right, Left, Down, Up)
for (int i = 0; i < 4; i++) {
int newRow = current.row + dRow[i];
int newCol = current.col + dCol[i];

if (isSafe(solve_maze, newRow, newCol, visited)) {
visited[newRow][newCol] = true;
queue.offer(new Position(newRow, newCol));
path_found[newRow][newCol] = current; // Store the parent cell to backtrack later
}
}
}
return false; // No path found
}

// Check if the position is within bounds and not a wall
public static boolean isSafe(char[][] safe_maze, int row, int col, boolean[][] visited) {
return row >= 0 && col >= 0 && row < safe_maze.length && col < safe_maze[0].length && safe_maze[row][col] != '0'
&& !visited[row][col];
}

// Backtrack from goal to start and mark the path
public static void markPath(char[][] mark_maze, Position[][] path_found, Position goal) {
Position current = goal;
while (current != null) {
if (mark_maze[current.row][current.col] != 'S' && mark_maze[current.row][current.col] != 'G') {
mark_maze[current.row][current.col] = '#'; // Mark the path
}
current = path_found[current.row][current.col];
}
}

// Print the maze with the path marked
public static void printMaze(char[][] maze) {
for (int i = 0; i < maze.length; i++) {
for (int j = 0; j < maze[i].length; j++) {
System.out.print(maze[i][j]);
}
System.out.println();
}
}
}

