package AStar;
class Maze
{
    static final int ROWS = 20;
    static final int COLS = 20;
    Node map[][] = new Node[ROWS][COLS];
    Node start = null;
    Node end = null;
    //储存当前所有的测试点信息的集合
    PathList test = new PathList();
    //储存最短路径信息的集合
    Path select = new Path();
    //初始化地图
    void init()
    {
        for(int i=0;i<ROWS;i++)
        {
            for(int j=0;j<COLS;j++)
            {
                map[i][j] = new Node(i,j,1);
            }
        }
    }
    void gameStart(MazePanel panel)
    {
        //node为当前点
        Node node = start;
        //将当前节点储存在列表中
        select.addNode(start,0);
        //死循环，直到到达end点
        while(true)
        {
            System.out.println("当前坐标：(" + node.row + "," + node.col + ")" +
                    "\n测试点的数量："+test.list.size() +
                    "\n已经走了几步："+(select.list.size() -1) +
                    "\n当前点是否被访问：" + node.visit);
            //测试点测试：将当前点标记为被访问过，将未被访问的相邻点加入到表中并录入路径信息
            test(node);

            panel.repaint(0,0,700,700);
            panel.update(panel.getGraphics());
            //80ms刷新一次
            try
            {
                Thread.sleep(80);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //获取所有当前测试点中cost+forecast最小的点并设置为当前点
            select = test.selectPath();
            node = (Node)select.list.get(select.list.size()-1);
            //如果找到已经到了目标点则将当前点设置为已访问并跳出死循环
            if((Math.abs(node.row-end.row)<=1) && (Math.abs(node.col-end.col)<=1))
            {
                node.visit = 1;
                break;
            }
        }
        System.out.println("找到最短路径，下面为最短路径坐标");
        select.addNode(end,0);
        //遍历最短路径
        for(int i=0;i<select.list.size();i++)
        {
            //输出最短路径的过程坐标
            node = (Node)select.list.get(i);
            System.out.println("(" + node.row + "," + node.col + ")");
        }
    }
    //测试点
    void test(Node node)
    {
        int row = node.row;
        int col = node.col;
        //标记当前位置已经到达
        //node.visit = 1;
        map[node.row][node.col].visit = 1;
        for(int i=-1;i<=1;i++)
        {
            //行数-1小于0或者行+i大于20（超出地图）就跳出当前循环
            if((row+i<0) || (row+i>=ROWS))
                continue;
            for(int j=-1;j<=1;j++)
            {
                if((col+j<0) || (col+j>=COLS))
                    continue;
                //将当前点的周围标记为测试点
                map[row+i][col+j].test = 1;
                //若测试点没有被访问过（有的点可能在之前的遍历中被访问了）
                if(map[row+i][col+j].visit == 0)
                {
                    //将当前测试点坐标存入temp中
                    Node temp = map[row+i][col+j];
                    //将select中的cost和forecast、list存入新的路径path
                    Path path = new Path(select);
                    //算出预测点与终点的距离（曼哈顿距离）
                    int forecast = Math.abs(end.row - temp.row) + Math.abs(end.col - temp.col);
                    path.addNode(temp,forecast);
                    test.addPath(path);
                }
            }
        }
    }
}