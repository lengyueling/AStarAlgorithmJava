package AStar;
import java.util.Vector;
//路径信息类，forecast为测试点曼哈顿距离的值，cost为当前点已经经走过的路程+1
class Path
{
    int cost = 0;
    int forecast = 0;
    Vector list = new Vector();
    Path()
    {
    }
    //将(select)节点的路径信息依次存进表中
    Path(Path path)
    {
        for(int i=0;i<path.list.size();i++)
        {
            list.add(path.list.get(i));
        }

        this.cost = path.cost;
        this.forecast = path.forecast;
    }
    void addNode(Node node,int forecast)
    {
        list.add(node);
        //若经过墙壁cost+=100否则+1
        this.cost += node.cost;
        this.forecast = forecast;
    }
}