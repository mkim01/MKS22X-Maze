
import java.util.*;
import java.io.*;
public class Maze{


    private char[][]maze;
    private boolean animate;//false by default
    private int width;
    private int height;
    private int startxposition;
    private int startyposition;
    private int[][] move = {{1,0}, {0,1}, {-1,0}, {0,-1}};

    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)

      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!


      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:

         throw a FileNotFoundException or IllegalStateException
    */

    public Maze(String filename) throws FileNotFoundException{
        //COMPLETE CONSTRUCTOR
        File f = new File(filename);
        Scanner inf = new Scanner(f);
        while(inf.hasNextLine()){
          String line = inf.nextLine();
          width = line.length();
          height++;
        }
        maze = new char[height][width];
        boolean checkEnd = false;
        boolean checkStart = false;

        inf = new Scanner(f);
        int theight = 0;
        while(inf.hasNextLine()){
          String line = inf.nextLine();
          for(int i = 0; i < width; i++){
            char c = line.charAt(i);
            if( c == 'E') {
              if (checkEnd){
                throw new IllegalStateException("more than 1 end");
              }
              else{
                checkEnd = true;
              }
            }
            if( c == 'S') {
              if (checkStart){
                throw new IllegalStateException("more than 1 start");
              }
              else{
                checkStart = true;
                startxposition = theight;
                startyposition = i;
              }
            }
            maze[theight][i] = c;
          }
            theight++;
        }
        animate = false;
    }

    public String toString(){
      String output = "";
      for(int i = 0; i < height; i++){
        for(int j = 0; j < width; j++){
          output += maze[i][j];
          //System.out.println(maze[i][j]);
        }
        output += "\n";
      }
      return output;
    }

    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }

    public void setAnimate(boolean b){
        animate = b;
    }

    public void clearTerminal(){
        //erase terminal, go to top left of screen.
        System.out.println("\033[2J\033[1;1H");
    }

    /*Wrapper Solve Function returns the helper function

      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.

    */
    public int solve(){
        maze[startxposition][startyposition] = '@';
        return solve(startxposition, startyposition, 1);
    }


    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.


      Postcondition:

        The S is replaced with '@' but the 'E' is not.

        All visited spots that were not part of the solution are changed to '.'

        All visited spots that are part of the solution are changed to '@'
    */
    private int solve(int row, int col, int movecount){ //you can add more parameters since this is private
      //automatic animation! You are welcome.
      if(animate){
          clearTerminal();
          System.out.println(this);
          wait(20);
      }

      /////////////======= TT

      if (maze[row][col] == 'E'){
        return movecount;
      }
      else{
        for (int i = 0; i < 4; i++){
          if(checkpath(row + move[i][0],col + move[i][1],movecount)){
            return solve(row + move[i][0], col + move[i][1], movecount + 1);
          }
        }
      }
    return -1;
  }

    private boolean checkpath(int row, int col, int movecount){
      if (maze[row][col] == ' '){
        maze[row][col] = '@';
        movecount++;
        return true;
      }
      if (maze[row][col] == 'E'){
        return true;
      }
      return false;
    }

    public static void main(String[] args) throws FileNotFoundException{
        Maze m1 = new Maze("data1.dat");
        System.out.println(m1);
        System.out.println(m1.solve());
        System.out.println(m1);

      }
}
