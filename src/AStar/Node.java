package AStar;
class Node//节点
{
    int row = 0;
    int col = 0;
    int cost = 0;
    int test = 0;
    int visit = 0;
    //节点的构造函数，储存当前行列信息，cost为走一步的消耗
    Node()
    {
        //
    }
    Node(int row,int col,int cost)
    {
        this.row = row;
        this.col = col;
        this.cost = cost;
    }
    //当前点与终点是否为一个点
    public boolean equals(Object object)
    {
        if(!(object instanceof Node))
            return false;

        Node node = (Node)object;
        if((node.row == row) && (node.col == col))
        {
            return true;
        }

        else
            return false;
    }
}
