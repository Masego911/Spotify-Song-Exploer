public class Edge//represents a connection between 2 vertices(songs)
{
    private Vertex from; //(V)-->
    private Vertex to;// -->(V)
    private String type;

    public Edge(Vertex from,Vertex to){
        this.from=from;
        this.to=to;
        this.type=null;
    }

    public Vertex getOther(Vertex current)
    {
        if(current==from)
        {
            return to;
        }else{
            return from;
        }
    }

    public Vertex getFrom()
    {
        return from;
    }

    public Vertex getTo(){
        return to;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String  type){
        this.type=type;
    }
}
