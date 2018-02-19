import java.io.File;
import java.lang.reflect.Array;
import java.util.*;


class Person{
    int size;
    int[] rank;
    boolean free;
    int index;
    boolean[] reject;
    int rejection;
    Person(int size,int i ){
        rank = new int[size];
        reject = new boolean[size];
        free = true;
        index = i;
        rejection =0;
    }
}
/*
class Man{
    int size;
    int[] rank;
    boolean free;
    int index;
    boolean[] reject;
    int rejection;
    Man(int size,int i ){
        rank = new int[size];
        reject = new boolean[size];
        free = true;
        index = i;
        rejection =0;
    }
}

class Woman{
    int size;
    int[] rank= new int[size];
    boolean free = true;
    int index;
    boolean[] reject;
    int rejection;
    Woman(int size, int i){
        rank = new int[size];
        free = true;
        index = i;
        reject = new boolean[size];
        rejection =0;
    }
}*/

public class DaweiZhang {
    ArrayList<Person> men = new ArrayList<>();
    ArrayList<Person> women = new ArrayList<>();
    ArrayList<Person> men1 = new ArrayList<>();
    ArrayList<Person> women1= new ArrayList<>();
    Map<Person, Person> oneStable;
    Map<Person, Person> secStable;
    ArrayList<int[]> possibleManRank;
    int size;
    static int answer;
    ArrayList<int[]> combineResult = new ArrayList<int[]>();
    ArrayList<int[]> stableResult = new ArrayList<int[]>();
    public DaweiZhang(String fileName) {
        try {
            readfile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        oneStable = stableW();
        secStable = stableM();
        possibleManRank=possibleManRanks(oneStable,secStable);
        combine(possibleManRank,new int[possibleManRank.size()] ,0);
        stableResult = stable(combineResult);
        p();
        answer =stableResult.size();
    }

    public static void main(String args[]) {
        DaweiZhang dz = new DaweiZhang("src/input1.txt");//change when you using JGRASP input1.txt
        System.out.println("There are "+answer+ " difference stable matches.  ");
        //Using src/input.txt for here.
    }

    public void readfile (String fileName) throws Exception {
        Scanner inFile = new Scanner(new File(fileName));
        size = inFile.nextInt();
        int lineNo = 0;
        men = new ArrayList<>();
        women = new ArrayList<>();
        for (int i = 0; i < size * 2; i++) {
            // initial the men and women in arrays
            if (i < size){
                men.add(new Person(size, i % size ));
                men1.add(new Person(size, i % size ));
            }
            else{
                women.add(new Person(size, i % size ));
                women1.add(new Person(size, i % size ));
            }
        }
        inFile.nextLine();
        while (inFile.hasNextLine()) {
            String line = inFile.nextLine();
            String[] components = line.split(" ");
            if(lineNo < size) // men & women is with women purposing(men's worst case) and men1&women1 with man purposing(men's best case)
            {
                for (int index = 0; index < size; index ++) {
                    men.get(lineNo).rank[index]  = Integer.parseInt(components[index])-1;
                    men1.get(lineNo).rank[index] = Integer.parseInt(components[index])-1;
                }
            }
            else
            {
                for (int index = 0; index < size; index ++) {
                    women.get(lineNo - size).rank[index] = Integer.parseInt(components[index])-1;
                    women1.get(lineNo - size).rank[index] = Integer.parseInt(components[index])-1;
                }
            }
            lineNo++;
        }


    }

    public Map stableW() {
        int Index  = 0;
        Map<Person, Person> chioce = new HashMap<>();
        while (chioce.size()  <= women.size()) {
            int RejectIndex = 0;
            for (int e : women.get(Index).rank) {
                // if this woman already has her significant other, skip for next
                if (!women.get(Index).free) {
                    break;
                }
                // if the man get reject once, do not need to check
                if (!women.get(Index).reject[RejectIndex]) {
                    if (men.get(e).free) {
                        chioce.put(men.get(e), women.get(Index));
                        men.get(e).free = false;
                        women.get(Index).free = false;
                        break;
                    } else if (findIndexFromRank(Index,men.get(e).rank) < findIndexFromRank(chioce.get(men.get(e)).index,men.get(e).rank)) {
                        women.get(chioce.get(men.get(e)).index).reject[women.get(chioce.get(men.get(e)).index).rejection] = true;
                        chioce.get(men.get(e)).rejection++;
                        women.get(chioce.get(men.get(e)).index).free = true;
                        chioce.remove(men.get(e));
                        chioce.put(men.get(e), women.get(Index));
                        women.get(Index).free = false;
                        break;
                    } else {
                        women.get(Index).reject[RejectIndex] = true;
                        women.get(Index).rejection++;
                    }
                }
                RejectIndex++;
            }
            if(chioce.size() == women.size())
                break;
            if(Index == women.size() - 1 )
                Index=0;
            else
                Index++;
        }
        return chioce;
    }

    public int findIndexFromRank(int value, int[] rank){
        int index=0;
        for (int e : rank) {
            if(value == e) {
                break;
            }
            index++;
        }
        return index;
    }

    public Map stableM() {
        int manIndex  = 0;
        Map<Person, Person> chioce = new HashMap<>();
        while (chioce.size()  <= men1.size()) {
            int manRejectIndex = 0;
            for (int e : men1.get(manIndex).rank) {
                // if the man already has his significant other, skip for next
                if (!men1.get(manIndex).free) {
                    break;
                }
                // if the man get reject once, do not need to check
                if (!men1.get(manIndex).reject[manRejectIndex]) {
                    if (women1.get(e).free) {
                        chioce.put(women1.get(e), men1.get(manIndex));
                        women1.get(e).free = false;
                        men1.get(manIndex).free = false;
                        break;
                    } else if (findIndexFromRank(manIndex,women1.get(e).rank) < findIndexFromRank(chioce.get(women1.get(e)).index,women1.get(e).rank)) {
                        men1.get(chioce.get(women1.get(e)).index).reject[men1.get(chioce.get(women1.get(e)).index).rejection] = true;
                        chioce.get(women1.get(e)).rejection++;
                        men1.get(chioce.get(women1.get(e)).index).free = true;
                        chioce.remove(women1.get(e));
                        chioce.put(women1.get(e), men1.get(manIndex));
                        men1.get(manIndex).free = false;
                        break;
                    } else {
                        men1.get(manIndex).reject[manRejectIndex] = true;
                        men1.get(manIndex).rejection++;
                    }
                }
                manRejectIndex++;
            }
            if(chioce.size() == men1.size())
                break;
            if(manIndex == men1.size() - 1 )
                manIndex=0;
            else
                manIndex++;
        }
        return chioce;
    }
    private void p(){
        /**
         *   test reading of the input.txt
          *//*

        for (int e:men.get(0).rank) {
            System.out.print(e+1);
        }
        System.out.println();
        for (int e:men.get(1).rank) {
            System.out.print(e+1);
        }
        System.out.println();
        for (int e:men.get(2).rank) {
            System.out.print(e+1);
        }
        System.out.println();
        for (int e:men.get(3).rank) {
            System.out.print(e+1);
        }
        System.out.println();
        for (int e:women.get(0).rank) {
            System.out.print(e+1);
        }
        System.out.println();
        for (int e:women.get(1).rank) {
            System.out.print(e+1);
        }
        System.out.println();
        for (int e:women.get(2).rank) {
            System.out.print(e+1);
        }
        System.out.println();
        for (int e:women.get(3).rank) {
            System.out.print(e+1);
        }
        System.out.println();
        */


            // print reject list
        /*
        for (Boolean e:women.get(0).reject) {
            System.out.print(e+",");
        }
        System.out.println();
        for (Boolean e:women.get(1).reject) {
            System.out.print(e+",");
        }
        System.out.println();
        for (Boolean e:women.get(2).reject) {
            System.out.print(e+",");
        }
        System.out.println();
        for (Boolean e:women.get(3).reject) {
            System.out.print(e+",");
        }
        System.out.println();
        */
        System.out.println("Men's worst case:");
        for (Map.Entry<Person,Person> entry : oneStable.entrySet())
            System.out.println("Man = " + entry.getKey().index +
                    ", Woman = " + entry.getValue().index);
       // System.out.println(women.get(0).rejection +","+women.get(1).rejection +","+women.get(2).rejection +","+women.get(3).rejection );
        System.out.println("Men's best case:");
        for (Map.Entry<Person,Person> entry : secStable.entrySet())
           System.out.println("Man = " + entry.getValue().index +
                    ", Woman = " + entry.getKey().index);
        for (int[] a: possibleManRank) {
            for(int e:a){
                System.out.print((e)+" ");
            }
            System.out.println();
        }
        /*
        for (int[] e: combineResult) {
            for(int e1:e){
                System.out.print((e1+1)+" ");
            }
            System.out.println();
        }*/
        for (int[] e: stableResult) {
            for(int e1:e){
                System.out.print((e1+1)+" ");
            }
            System.out.println();
        }
    }

    // using the best case and worst case get the range of men's rank
    private ArrayList<int[]> possibleManRanks(Map<Person, Person> matches1,Map<Person, Person> matches2){
        ArrayList<int[]> result= new ArrayList<int[]>();
        int[][] ListOfrange = new int[matches1.size()][2];
        for (Map.Entry<Person,Person> entry : matches1.entrySet())
            ListOfrange[entry.getKey().index][0] =entry.getValue().index;
        for (Map.Entry<Person,Person> entry : matches2.entrySet())
            ListOfrange[entry.getValue().index][1] =entry.getKey().index;
        for(int i = 0; i< men.size();i++){
            int[] currentRank = men.get(i).rank;
            int endwith= findIndexFromRank(ListOfrange[i][0],men.get(i).rank)+1;
            int startFrom  = findIndexFromRank(ListOfrange[i][1],men.get(i).rank);
            result.add(Arrays.copyOfRange(currentRank,startFrom,endwith));
        }
        return result;
    }
    // get the permutation of the ArrayList<int[]>
    private void combine(ArrayList<int[]> input, int[] current, int k) {
        ArrayList<int[]> result = new ArrayList<int[]>();
        int[] count = new int[100];
        boolean repeat = false;
        if(k == input.size()) {
            for(int i = 0; i < k; i++) {
                if (count[current[i]] == 1){
                    repeat = true;
                    break;
                }
                else{
                    count[current[i]]++;
                }
                //        System.out.print(current[i] + " ");
            }
            if(!repeat)
                result.add(current);
            //    System.out.println();
        } else {
            for(int j = 0; j < input.get(k).length; j++) {
                current[k] = input.get(k)[j];
                combine(input, current, k + 1);
            }
        }
        int[] currentRank = new int[size];
        for(int i = 0; i < result.size(); i++){
            for(int e=0;e< result.get(i).length;e++ ){
                //System.out.print((result.get(i)[e]+1)+" ");
                currentRank[e]=result.get(i)[e];
            }
            combineResult.add(currentRank);
            answer++;
            //System.out.println();
        }

    }
    // check the which combination is not stable

    public ArrayList<int[]> stable(ArrayList<int[]> combineResult) {
        for (int i = 0; i < combineResult.size(); i++) {
            boolean womenPreferOther = false;
            for (int j = 0; j < combineResult.get(i).length; j++) {
                if (possibleManRank.get(i).length == 1)// if there is only one possible choice for this man then don't have to check.
                    break;
                else {
                    int womenCurr, womenCompare;
                    womenCurr = findIndexFromRank(j, women.get(combineResult.get(i)[j]).rank);
                    for (int menRank = 0; menRank < possibleManRank.get(i).length; menRank++) {// check all current man's rank and review the better choice then the current
                        //if (possibleManRank.get(i)[menRank] == combineResult.get(i)[j])// if already went to the current choice
                         //   break;
                        womenCompare = findIndexFromRank(j, women.get(possibleManRank.get(i)[menRank]).rank);
                        if (womenCompare < womenCurr) {
                            combineResult.remove(i);
                            break;
                        }
                    }
                }
            }
        }
        return combineResult;
    }

}

