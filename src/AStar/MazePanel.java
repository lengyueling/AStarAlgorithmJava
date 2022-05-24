package AStar;
import javax.swing.*;
import java.awt.*;
//迷宫的GUI实现
class MazePanel extends JPanel
{
    Maze maze = null;
    int clickCount = 0;
    //定义一系列常量
    static final int WIDTH = 30;

    static final Color START_COLOR = Color.green;
    static final Color END_COLOR = Color.red;
    static final Color DEFAULT_COLOR = Color.white;
    static final Color WALL_COLOR = Color.gray;
    static final Color TEST_COLOR = Color.yellow;
    static final Color VISIT_COLOR = Color.pink;
    static final Color PATH_COLOR = Color.blue;
    //构造函数
    MazePanel(Maze maze)
    {
        this.maze = maze;
    }

    public void paintComponent(Graphics g)
    {
        //默认边框
        g.clearRect(0,0,700,700);
        g.setColor(Color.black);
        for(int i=0;i<=Maze.ROWS;i++)
        {
            g.drawLine(0,WIDTH*i,WIDTH* Maze.COLS,WIDTH*i);

        }
        for(int j = 0; j<= Maze.COLS; j++)
        {
            g.drawLine(WIDTH*j,0,WIDTH*j,WIDTH* Maze.ROWS);
        }
        //测试（当前点的四周）
        g.setColor(TEST_COLOR);
        for(int i = 0; i< Maze.ROWS; i++)
        {
            for(int j = 0; j< Maze.COLS; j++)
            {
                if(maze.map[i][j].test == 1)
                {
                    //覆盖四周八个点
                    g.fillRect(maze.map[i][j].col*WIDTH+1,maze.map[i][j].row*WIDTH+1,WIDTH-1,WIDTH-1);

                }
            }
        }
        //已经到达的点
        g.setColor(VISIT_COLOR);
        for(int i = 0; i< Maze.ROWS; i++)
        {
            for(int j = 0; j< Maze.COLS; j++)
            {
                if(maze.map[i][j].visit == 1)
                {
                    g.fillRect(maze.map[i][j].col*WIDTH+1,maze.map[i][j].row*WIDTH+1,WIDTH-1,WIDTH-1);
                }
            }
        }
        //墙壁的cost为一个巨大的值
        g.setColor(WALL_COLOR);
        for(int i = 0; i< Maze.ROWS; i++)
        {
            for(int j = 0; j< Maze.COLS; j++)
            {
                if(maze.map[i][j].cost == 100)
                {
                    g.fillRect(maze.map[i][j].col*WIDTH+1,maze.map[i][j].row*WIDTH+1,WIDTH-1,WIDTH-1);
                }
            }
        }

        if(maze.start != null)
        {
            g.setColor(START_COLOR);
            g.fillRect(maze.start.col*WIDTH+1,maze.start.row*WIDTH+1,WIDTH-1,WIDTH-1);
        }

        if(maze.end != null)
        {
            g.setColor(END_COLOR);
            g.fillRect(maze.end.col*WIDTH+1,maze.end.row*WIDTH+1,WIDTH-1,WIDTH-1);
        }
        //画最短路径线
        int length = maze.select.list.size();
        if(length > 0)
        {
            //遍历中的当前点
            Node node = (Node)maze.select.list.get(length - 1);
            //只有找到最短路径才开始画线
            if(node.equals(maze.end) == true)
            {
                Graphics2D g2d = (Graphics2D)g;
                g2d.setColor(PATH_COLOR);
                g2d.setStroke(new BasicStroke(2.0f));

                int startX = maze.start.col * WIDTH + WIDTH/2;
                int startY = maze.start.row * WIDTH + WIDTH/2;

                for(int i=1;i<length;i++)
                {
                    node = (Node)maze.select.list.get(i);

                    int endX = node.col * WIDTH + WIDTH/2;
                    int endY = node.row * WIDTH + WIDTH/2;

                    g2d.drawLine(startX,startY,endX,endY);

                    startX = endX;
                    startY = endY;
                }
            }
        }
    }
}
