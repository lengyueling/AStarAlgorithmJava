package AStar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//gui界面初始化、监听
class MazeFrame extends JFrame
{
    //墙的数量
    int count[] = new int[]{0,0};
    String[] wallStr = new String[400];

    MazePanel mazePanel = null;
    Maze maze = null;
    MazeFrame(Maze maze)
    {
        this.maze = maze;
        mazePanel = new MazePanel(maze);

        setTitle("AStar");
        setSize(620,670);
        //初始化GUI画面
        initView();
        setVisible(true);
    }

    void initView()
    {
        getContentPane().setLayout(new BorderLayout());
        //鼠标点击监听、画图
        mazePanel.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int x = e.getX();
                int y = e.getY();
                //鼠标点击某行列
                int row = y / MazePanel.WIDTH;
                int col = x / MazePanel.WIDTH;
                //鼠标按第一次是开始点
                if(mazePanel.clickCount == 0)
                {
                    mazePanel.maze.start = mazePanel.maze.map[row][col];
                }
                else if(mazePanel.clickCount == 1)
                {
                    mazePanel.maze.end = mazePanel.maze.map[row][col];
                }
                else
                {
                    mazePanel.maze.map[row][col].cost = 100;
                    wallStr [count[0]] =  "(" + String.valueOf(row) +"," + String.valueOf(col) + ")";
                    count[0] ++;
                }

                mazePanel.repaint(0,0,700,700);
                mazePanel.clickCount++;
            }
        });
        //在GUI窗口中增加迷宫面板和按钮
        getContentPane().add(mazePanel,BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton startButton = new JButton("Start");
        JButton saveButton = new JButton("SaveMap");
        JButton loadButton = new JButton("LoadMap");
        TextField pathText = new TextField(30);
        JButton restartButton = new JButton("ReStart");
        add(bottomPanel,BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //开始
                mazePanel.repaint(0,0,700,700);
                maze.gameStart(mazePanel);
            }
        });
        bottomPanel.add(startButton);
        bottomPanel.add(pathText);
        //储存数据
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String path = pathText.getText();
                try
                {
                    FileOutputStream fos = new FileOutputStream(path,false);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    String startStr = "(" + String.valueOf(mazePanel.maze.start.row) +"," + String.valueOf(mazePanel.maze.start.col) + ")";
                    String endStr = "(" + String.valueOf(mazePanel.maze.end.row) +"," + String.valueOf(mazePanel.maze.end.col) + ")";
                    byte[] startByte = new byte[16];
                    byte[] endByte = new byte[16];
                    byte[] wallByte = new byte[1024];
                    startByte = startStr.getBytes(StandardCharsets.UTF_8);
                    endByte = endStr.getBytes(StandardCharsets.UTF_8);
                    bos.write(startByte);
                    bos.write(endByte);
                    for (int i = 0;i < count[0];i++)
                    {
                        wallByte = wallStr[i].getBytes(StandardCharsets.UTF_8);
                        bos.write(wallByte);
                    }
                    bos.close();
                }catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });
        bottomPanel.add(saveButton);
        //加载数据
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = pathText.getText();
                try {
                    FileInputStream fis = new FileInputStream(path);
                    byte[] bytes = new byte[10240];
                    int flag = 0;
                    fis.read(bytes);
                    String str = new String(bytes);
                    String regex="[0-9]+(\\.[0-9]+)?";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(str);
                    int temp = 0;
                    while (m.find())
                    {
                        switch (flag)
                        {
                            case 0:
                            {
                                temp = Integer.parseInt(m.group());
                                flag = 1;
                                break;
                            }
                            case 1:
                            {
                                mazePanel.maze.start = mazePanel.maze.map[temp][Integer.parseInt(m.group())];
                                flag = 2;
                                break;
                            }
                            case 2:
                            {
                                temp = Integer.parseInt(m.group());
                                flag = 3;
                                break;
                            }
                            case 3:
                            {
                                mazePanel.maze.end = mazePanel.maze.map[temp][Integer.parseInt(m.group())];
                                flag = 4;
                                break;
                            }
                            case 4:
                            {
                                temp = Integer.parseInt(m.group());
                                flag = 5;
                                break;
                            }
                            case 5:
                            {
                                mazePanel.maze.map[temp][Integer.parseInt(m.group())].cost = 100;
                                count[1]++;
                                if (count[1] == count[0])
                                {
                                    System.out.println(count[1]);
                                    flag = 6;
                                    break;
                                }else
                                {
                                    flag = 4;
                                    break;
                                }
                            }
                        }
                        if (flag == 6)
                        {
                            break;
                        }
                    }
                    mazePanel.repaint(0,0,700,700);
                }catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });
        bottomPanel.add(loadButton);
        //重启按钮
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Maze maze = new Maze();
                maze.init();
                MazeFrame frame = new MazeFrame(maze);
            }
        });
        bottomPanel.add(restartButton);
    }
}