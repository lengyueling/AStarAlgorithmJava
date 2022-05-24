package AStar;
public class TestMaze
{
    public static void main(String[] args)
    {
        Maze maze = new Maze();
        //初始化地图
        maze.init();
        MazeFrame frame = new MazeFrame(maze);
    }
}
