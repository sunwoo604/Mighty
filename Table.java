import java.util.ArrayList;
import java.util.Random;

public class Table 
{
    private ArrayList<card> deck; // list of all cards
    private ArrayList<player> playerSet; // list of players
    private int players; //num of players
    private char giruda; // winning suit
    private char turnG=0; // turn giruda
    private long bid; // target point
    private card currW; // current winner of the round
    private player boss; // refernce of boss
    private player prevW; // winner of previous round
    private player prevF; // friend of previous game
    private player excluded; // player that got excluded
    private ArrayList<card> floor; // cards on the floor
    private card friends; // card of friends
    private boolean revealed=false; // if friend has beem revealed

    public Table(ArrayList<player> ps)
    {
        this.playerSet=ps;
        this.players=ps.size();
        this.prevW=playerSet.get(0);
        this.prevF=playerSet.get(0);
        this.bid=12;
        for(int i=0;i<playerSet.size();i++)
        {
            System.out.println(playerSet.get(i).getId()+" entered the game");
        }
        System.out.println(playerSet.size());
        this.excluded=null;
        this.turnG=0;
    }
    /* add player to table
    */
    public void addPlayer(player p)
    {
        playerSet.add(p);
        players++;
    }
    /*subtract player from table
    */
    public void subPlayer(player p)
    {
        playerSet.remove(p);
        players--;
    }
    /*convert stirng to number value
    */
    public int VNconversion(String s)
    {
        if(s.equals("J"))
        {
            return 11;
        }
        else if(s.equals("Q" ))
        {
            return 12;
        }
        else if(s.equals("K"))
        {
            return 13;
        }
        else if(s.equals("A"))
        {
            return 14;
        }
        else
        {
            return Integer.parseInt(s);
        } 
    }
    /*print the floor cards
    */
    public void showFloor()
    {
        for(int i=0;i<floor.size();i++)
        {
            System.out.print(floor.get(i)+" ");
        }
        System.out.println();
    }
    /*generate the deck follwoing the players number
    */
    public void deckGenerator(int n)
    {
        deck=new ArrayList<>();
        for(int i=2;i<15;i++)
        {
            card s=new card('s',i);
            card c=new card('c',i);
            card d=new card('d',i);
            card h=new card('h',i);
            deck.add(s);
            deck.add(c);
            deck.add(d);
            deck.add(h);
        }
        card j=new card('j',0);
        deck.add(j);
        if(players==4)
        {
            for(int i=0;i<deck.size();i++)
            {
                if(deck.get(i).getValue()==2)
                {
                    deck.remove(i);
                    i--;
                }
                else if(deck.get(i).getValue()==4)
                {
                    deck.remove(i);
                    i--;
                }
                else if(deck.get(i).getValue()==3)
                {
                    if(deck.get(i).getSuitChar()=='h'||deck.get(i).getSuitChar()=='d')
                    {
                        deck.remove(i);
                        i--;
                    }
                }
            }
        }
    }
    /*sort the order of players based on boss
    */
    public void sortPlayers(player p)
    {
        int m=playerSet.indexOf(p);
        for(int i=0;i<m;i++)
        {
            playerSet.add(playerSet.remove(0));
        }
    }
    /*hand out cards to players*/
    public void handCards()
    {
        if(players==6)
        {
            for(int i=0;i<players;i++)
            {
                ArrayList<card> p1= new ArrayList<>();
                Random rand = new Random();
                for(int j=0;j<8;j++)
                {
                    int upp=deck.size();
                    int r=rand.nextInt(upp);
                    p1.add(deck.remove(r));
                }
                playerSet.get(i).addCard(p1);
            }
            System.out.println("Select player to be removed");
            char s = (char)MyLib.getchar();
            MyLib.clrbuf(s);
            String v=MyLib.getline();
            boolean bot=true;
            for(int i=0;i<players;i++)
            {
                if(playerSet.get(i).hasCard(s,v))
                {
                    int len=playerSet.get(i).getHand().size();
                    for(int j=0;j<len;j++)
                    {
                        deck.add(playerSet.get(i).getHand().remove(0));
                    }
                    excluded=playerSet.get(i);
                    playerSet.remove(i);
                    bot=false;
                    break;
                }
            }
            if(bot)
            {
                int i=playerSet.indexOf(prevF);
                for(int j=0;j<playerSet.get(i).getHand().size();j++)
                {
                    deck.add(playerSet.get(i).getHand().remove(0));
                }
                excluded=playerSet.remove(i);
            }
            for(int i=0;i<5;i++)
            {
                ArrayList<card> p1= new ArrayList<>();
                Random rand = new Random();
                for(int j=0;j<2;j++)
                {
                    int upp=deck.size();
                    int r=rand.nextInt(upp);
                    p1.add(deck.get(r));
                    deck.remove(r);
                }
                playerSet.get(i).addCard(p1);
            }
        }
        else
        {
            for(int i=0;i<players;i++)
            {
                ArrayList<card> p1= new ArrayList<>();
                Random rand = new Random();
                for(int j=0;j<10;j++)
                {
                    int upp=deck.size();
                    int r=rand.nextInt(upp);
                    p1.add(deck.get(r));
                    deck.remove(r);
                }
                playerSet.get(i).addCard(p1);
            }
        }
        for(int i=0;i<playerSet.size();i++)
        {
            System.out.print(playerSet.get(i).getId()+"'s hand ");
            playerSet.get(i).printHand();
        }
        for(int i=0;i<deck.size();i++)    
        {
            System.out.println(deck.get(i));
        }
    }
    /*bidding process*/
    public void biding()
    {
        sortPlayers(prevF);
        int passCnt = 0;
        ArrayList<player> pass = new ArrayList<>();
        for(int i=0;i<players;i++)
        {
            pass.add(playerSet.get(i));
        }
        while(passCnt<players-1)
        {
            for(int i=0;i<pass.size();i++)
            {
                System.out.print(pass.get(i).getId()+"'s turn to bid ");
                char bidS=(char)MyLib.getchar();
                MyLib.clrbuf(bidS);
                if(bidS=='p' || bidS=='P')
                {
                    passCnt++;
                    pass.remove(i);
                    i--;
                    if(passCnt>=players-1 && bid>12)
                    {
                        break;
                    }
                    continue;
                }
                else if(bidS=='s' || bidS=='S')
                {
                    giruda='s';
                }
                else if(bidS=='d' || bidS=='D')
                {
                    giruda='d';
                }
                else if(bidS=='h' || bidS=='H')
                {
                    giruda='h';
                }
                else if(bidS=='n' || bidS=='N')
                {
                    giruda='n';
                }
                else if(bidS=='c' || bidS=='C')
                {
                    giruda='c';
                }
                else
                {
                    i--;
                    continue;
                }
                long b=MyLib.decin();
                if(b<13 || b<=bid || b>20 )
                {
                    System.out.println("Can't do that! Reenter please!");
                    for(int j=0;j<1;j++)
                    {
                        b=MyLib.decin();
                        if(b<13 || b<=bid || b>20 )
                        {
                            i--;
                        }
                    }
                }
                this.bid=b;
                boss=pass.get(i);
                if(bid==20)
                {
                    break;
                }  
            }
        }
        if(passCnt==players)
        {
            System.out.println("First player became the boss automatically ");
            boss=playerSet.get(0);
            playerSet.get(0).setBoss();
            int t=deck.size();
            for(int i=0;i<t;i++)
            {
                playerSet.get(0).addCard(deck.remove(0));
            }
            System.out.println("Choose 3 cards to put down ");
            for(int i=0;i<t;i++)
            {
                playerSet.get(0).printHand();
                String idx = MyLib.getline();
                int inp=Integer.parseInt(idx);
                card c=boss.putCard((char)0,inp);
                if(c==null)
                {
                    System.out.println("redo");
                    i--;
                }
                else
                {
                    deck.add(c);
                }
            }
            playerSet.get(0).printHand();
            System.out.println();
            System.out.print(playerSet.get(0).getId()+", declare your giruda and number ");
            char s=(char)MyLib.getchar();
            MyLib.clrbuf(s);
            long b=MyLib.decin();
            giruda=s;
            bid=b;
            System.out.println("giruda is "+giruda);
            System.out.println("target point is "+bid);
        }
        else
        {
            boss.setBoss();
            System.out.println("boss is "+boss.getId());
            System.out.println("giruda is "+giruda);
            System.out.println("target point is "+bid);
            int t=deck.size();
            for(int i=0;i<t;i++)
            {
                boss.addCard(deck.remove(0));
            }
            System.out.println("Choose 3 cards to put down ");
            for(int i=0;i<t;i++)
            {
                boss.printHand();
                String idx = MyLib.getline();
                int inp=Integer.parseInt(idx);
                card c=boss.putCard((char)0,inp);
                if(c==null)
                {
                    System.out.println("redo");
                    i--;
                }
                else
                {
                    deck.add(c);
                }
            }
            boss.printHand();
            System.out.println("You can change the giruda right now by raising target point by 2 ");
            char newG = (char)MyLib.getchar();
            MyLib.clrbuf(newG);
            if(newG<91)
            {
                newG=(char)(newG+32);
            }
            if(newG=='s'&&giruda!='s')
            {
                giruda='s';
                bid=+2;
            }                 
            else if(newG=='d'&&giruda!='d')
            {
                giruda='d';
                bid=+2;
            }
            else if(newG=='h'&&giruda!='h')
            {
                giruda='h';
                bid=+2;
            }
            else if(newG=='n'&&giruda!='n')
            {
                giruda='n';
                bid=+2;
            }
            else if(newG=='c'&&giruda!='c')
            {
                giruda='c';
                bid=+2;
            }
        }
        System.out.println("Declare your friend! ");
        char sf;
        String vf=null;
        while(true)
        {
            sf=(char)MyLib.getchar();
            MyLib.clrbuf(sf);
            vf=MyLib.getline();
            if(sf<91)
            {
                sf=(char)(sf+32);
            }
            if(setF(sf,vf))
            {
                break;
            }
        }
        sortPlayers(boss);
        System.out.println(playerSet.size());
    }
    /*set the friend following information
    */
    public boolean setF(char sf,String vf)
    {
        if(sf=='n')
        {
            friends=null;
            return true;
        }
        else
        {
            if(players==4)
            {
                if((sf=='s'&&vf.toUpperCase().equals("A") && giruda!='s')||
                (sf=='j')||
                ((sf=='d')&&vf.toUpperCase().equals("A")&&giruda=='s'))
                {
                    System.out.println("You cannot do that!");
                    return false;
                }
            }
            for(int i=0;i<playerSet.size();i++)
            {
                if(playerSet.get(i).setFriend(sf,vf))
                {
                    prevF=playerSet.get(i);
                }
            }
            friends=new card(sf,VNconversion(vf));
            return true;
        }
    }
    /*determine the winner of the round
    */
    public boolean newW(card card,int r)
    {
        if(currW.getMighty())
        {
            return false;
        }
        if(card.getMighty())
        {
            return true;
        }
        if(card.getSuitChar()=='j')
        {
            if(r!=1 && r!=10)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        if(card.getSuitChar()==giruda)
        {
            if(currW.getSuitChar()!=giruda)
            {
                return true;
            }
            else
            {
                return card.getValue()>currW.getValue();
            }
        }
        if(currW.getSuitChar()!=giruda)
        {
            return card.getValue()>currW.getValue();
        }
        return false;
    }
    /*set the mighty and joker based on the giruda*/
    public void setMightyandJoker()
    {
        
        if(giruda=='s')
        {
            for(int i=0;i<this.playerSet.size();i++)
            {
                for(int j=0;j<10;j++)
                {
                    card temp =this.playerSet.get(i).getHand().get(j);
                    if(temp.getSuitChar()=='d'&&temp.getNumber().equals("A"))
                    {
                        temp.setMighty();
                    }
                    if(temp.getSuitChar()=='c'&&temp.getNumber().equals("3"))
                    {
                        temp.setShot();
                    }
                }
            }
        }
        if(giruda=='c')
        {
            for(int i=0;i<this.playerSet.size();i++)
            {
                for(int j=0;i<10;j++)
                {
                    card temp =this.playerSet.get(i).getHand().get(j);
                    if(temp.getSuitChar()=='s'&&temp.getNumber().equals("A"))
                    {
                        temp.setMighty();
                    }
                    if(temp.getSuitChar()=='s'&&temp.getNumber().equals("3"))
                    {
                        temp.setShot();
                    }
                }
            }
        }
        else
        {
            for(int i=0;i<this.playerSet.size();i++)
            {
                for(int j=0;j<10;j++)
                {
                    card temp =this.playerSet.get(i).getHand().get(j);
                    if(temp.getSuitChar()=='s'&&temp.getNumber().equals("A"))
                    {
                        temp.setMighty();
                    }
                    if(temp.getSuitChar()=='c'&&temp.getNumber().equals("3"))
                    {
                        temp.setShot();
                    }
                }
            }
        }
    }
    /*actual play function*/
    public void play()
    {
        deckGenerator(players);
        handCards();
        biding();
        setMightyandJoker();
        for(int r=1;r<11;r++)
        {
            for(int i=0;i<playerSet.size();i++)
            {
                System.out.print(playerSet.get(i).getId()+"'s hand ");
                playerSet.get(i).printHand();
                if(!(playerSet.get(i).getBoss()||(playerSet.get(i).getFriend()&&revealed)))
                {
                    System.out.print(playerSet.get(i).getId()+"'s gained ");
                    playerSet.get(i).printGained();
                    System.out.println();
                }
                System.out.println();
            }
            floor = new ArrayList<>();
            for(int t=0;t<playerSet.size();t++)
            {
                System.out.println(playerSet.get(t).getId()+"'s turn to put the card down ");
                String idx = MyLib.getline();
                int inp=Integer.parseInt(idx);
                card c;
                while(true)
                {
                    if(turnG=='j' && !playerSet.get(t).hasGiruda(turnG))
                    {
                        if(giruda=='c')
                        {
                            c=playerSet.get(t).putCard('s',inp);
                        }
                        else
                        {
                            c=playerSet.get(t).putCard('c',inp);
                        }
                    }
                    else
                    {
                        c=playerSet.get(t).putCard(turnG,inp);
                    }
                    if(c==null)
                    {
                        continue;
                    }
                    if(c.getSuitChar()==giruda&&r==1)
                    {
                        System.out.println("GIRUDA");
                        continue;
                    }
                    break;
                }
                if(friends.getSuitChar()==c.getSuitChar()&&friends.getValue()==friends.getValue())
                {
                    revealed=true;
                    prevF=playerSet.get(t);
                }
                if(t==0)
                {
                    currW=c;
                    turnG=c.getSuitChar();
                    if(c.getShot() && (r!=1 && r!=10))
                    {
                        turnG='j';
                    }
                    floor.add(c);
                    prevW=playerSet.get(t);
                }
                else
                {
                    if((turnG!='j' && c.getSuitChar()!='j')&&newW(c,r))
                    {
                        currW=c;
                        prevW=playerSet.get(t);
                    }
                    floor.add(c);
                }
            }
            prevW.gainPoint(floor);
            sortPlayers(prevW);
        }
        this.revealed=false;
        sortPlayers(prevF);
        this.turnG=0;
        this.giruda=0;
        if(excluded!=null)
        {
            playerSet.add(excluded);
            excluded=null;
        }
        this.bid=12;
        currW=null;
    }
    
}
