package AStar;
import java.util.Vector;
class PathList
{
    Vector list = new Vector();
    void addPath(Path path)//增加path并按照cost+forecast排序
    {
        int i = 0;
        //将当前表中cost+forecast与传入的值做对比，若传入值小于原本位置的值就插入到表的当前位置
        for(i=0;i<list.size();i++)
        {
            int cost = ((Path)list.get(i)).cost;
            int forecast = ((Path)list.get(i)).forecast;
            if((cost+forecast) > (path.cost+path.forecast))
            {
                break;
            }
        }
        //插入
        list.insertElementAt(path,i);
    }
    Path selectPath()// 找到首个在表中未被访问的点并返回该点在表中的位置（该点一定是cost+forecast最小的点）
    {
        int result = 0;
        //遍历当前表
        for(int i=0;i<list.size();i++)
        {
            //将各个路径信息存到新建的路径中
            Path path = (Path)list.get(i);
            //将各点的信息存到新建的节点中
            Node node = (Node)path.list.get(path.list.size()-1);
            //若当前点没有被访问则获得当前点的索引并返回
            if(node.visit == 0)
            {
                result = i;
                break;
            }
        }
        return (Path)list.get(result);
    }
}