
public class card
{
    private char suit;
    private int value;
    private boolean point;
    private boolean shot;
    private boolean mighty;

    private String s="SPADE";
    private String h="HEART";
    private String d="DIAMOND";
    private String c="CLOVER";
    private String j="JOKER";
    
    public card()
    {
        this.suit='a';
        this.value=0;
        this.point = false;
    }
    public card(char s, int v)
    {
        this.suit=s;
        if(s<91)
        {
            this.suit=(char)(s+32);
        }
        this.value=v;
        if(v>=10)
        {
            this.point=true;
        }
        else
        {
            this.point=false;
        }
    }
    public char getSuitChar()
    {
        return this.suit;
    }
    public String getSuit()
    {
        if(this.suit=='s')
        {
            return this.s;
        }
        else if(this.suit == 'd')
        {
            return this.d;
        }
        else if(this.suit == 'h')
        {
            return this.h;
        }
        else if(this.suit =='c')
        {
            return this.c;
        }
        else if(this.suit == 'j')
        {
            return this.j;
        }
        return "ERROR";
    }
    public int getValue()
    {
        return value;
    }
    public String getNumber()
    {
        if(value<=10)
        {
            return Integer.toString(this.value);
        }
        else if(value==11)
        {
            return "J";
        }
        else if(value==12)
        {
            return "Q";
        }
        else if(value ==13)
        {
            return "K";
        }
        else if(value == 14)
        {
            return "A";
        }
        return "ERROR";
    }
    public String toString()
    {
        if(getSuitChar()=='j')
        {
            return getSuit();
        }
        else
        {
            return getSuit()+" "+getNumber();
        }
    }
    public boolean getPoint()
    {
        return point;
    }
    public boolean getMighty()
    {
        return this.mighty;
    }
    public void setMighty()
    {
        this.mighty=true;
    }
    public void setShot()
    {
        this.shot=true;
    }
    public boolean getShot()
    {
        return this.shot;
    }
}
