import java.util.ArrayList;

public class player 
{
    private int money;
    private final String id;
    private ArrayList<card> hand = new ArrayList<>();
    private ArrayList<card> gained = new ArrayList<>();
    private boolean friend=false;
    private boolean boss=false;

    public player(String n, int m)
    {
        id=n;
        money=m;
    }
    public void sortCards()
    {
        int len = hand.size();
        Object[] temp = hand.toArray();
        for(int i=1;i<len;i++)
        {
            if(temp[i-1].toString().compareTo(temp[i].toString())>0)
            {
                Object holder = temp[i-1];
                temp[i-1]=temp[i];
                temp[i]=holder;
                i-=2;
            }
            if(i<0)
            {
                i=0;
            }
        }
        hand.clear();
        for(int i=0;i<len;i++)
        {
            hand.add((card)temp[i]);
        }
    }
    public void addCard(card c)
    {
        hand.add(c);
    }
    public void addCard(ArrayList<card> c)
    {
        for(int i=0;i<c.size();i++)
        {
            hand.add(c.get(i));
        }
    }
    public void printHand()
    {
        sortCards();
        System.out.print(hand.size()+" ");
        for(int i=0;i<hand.size();i++)
        {
            System.out.print(hand.get(i)+" ");
        }
        System.out.println();
    }
    public void printGained()
    {
        for(int i=0;i<gained.size();i++)
        {
            System.out.print(gained.get(i)+" ");
        }
    }
    //helper to put card down with right suit
    public boolean hasGiruda(char g)
    {
        for(int i=0;i<hand.size();i++)
        {
            if(hand.get(i).getSuitChar()==g)
            {
                return true;
            }
        }
        return false;
    }
    //helper to find if he has this card
    public boolean hasCard(char s, String v)
    {
        if(s<91)
        {
            s=(char)(s+32);
        }
        v=v.toUpperCase();
        for(int i=0;i<hand.size();i++)
        {
            if(hand.get(i).getNumber().equals(v) && hand.get(i).getSuitChar()==s)
            {
                return true;
            }
        }
        return false;
    }
    //need to match giruda
    public card putCard(char turnG, int idx)
    {
        if(idx>=hand.size())
        {
            return null;
        }
        if(turnG!=0 && hasGiruda(turnG))
        {
            if(turnG!=this.hand.get(idx).getSuitChar())
            {
                System.err.println("Not the Right one!");
                return null;
            }
        }
        return this.hand.remove(idx);
    }
    public void gainPoint(ArrayList<card> p)
    {
        for(int i=0;i<p.size();i++)
        {
            if(p.get(i).getPoint())
            {
                gained.add(p.get(i));
            }
        }
    }
    public ArrayList<card> getGained()
    {
        return gained;
    }
    public ArrayList<card> getHand()
    {
        return hand;
    }
    public boolean setFriend(char s, String v)
    {
        if(s<91)
        {
            s=(char)(s+32);
        }
        v=v.toUpperCase();
        for(int i=0;i<hand.size();i++)
        {
            if(hand.get(i).getNumber().equals(v) && hand.get(i).getSuitChar()==s)
            {
                friend = true;
                return true;
            }
        }
        return false;
    }
    public void unsetFriend()
    {
        friend=false;
    }
    public boolean getFriend()
    {
        return friend;
    }
    public void setBoss()
    {
        boss=true;
    }
    public void unsetBoss()
    {
        boss=false;
    }
    public boolean getBoss()
    {
        return boss;
    }
    public String getId()
    {
        return id; 
    }
    public void changeMoney(int m)
    {
        money+=m;
    }
    public int getMoney()
    {
        return money;
    }
}
