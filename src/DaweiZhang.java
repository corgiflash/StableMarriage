import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class Man{
    int size;
    int[] rank;
    boolean free;
    int index;
    boolean[] reject;
    Man(int size,int i ){
        rank = new int[size];
        reject = new boolean[size];
        free = true;
        index = i;
    }
}

class Woman{
    int size;
    int[] rank= new int[size];
    boolean free = true;
    int index;
    boolean[] reject;
    Woman(int size, int i){
        rank = new int[size];
        free = true;
        index = i;
        reject = new boolean[size];
    }
}

public class DaweiZhang {
    ArrayList<Man> men = new ArrayList<>();
    ArrayList<Woman> women = new ArrayList<>();
    Map<Woman, Man> oneStable;
    public DaweiZhang(String fileName) {
        try {
            readfile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        oneStable = stableM();

        for (boolean e:women.get(0).reject) {
            System.out.print(e+",");
        }
        System.out.println();
        for (boolean e:women.get(1).reject){
            System.out.print(e+",");
        }
        System.out.println();
        for (boolean e:women.get(2).reject) {
            System.out.print(e+",");
        }
        System.out.println();
        for (boolean e:women.get(3).reject) {
            System.out.print(e+",");
        }
        System.out.println();
        for (Map.Entry<Woman,Man> entry : oneStable.entrySet())
            System.out.println("Man = " + entry.getValue().index +
                    ", Woman = " + entry.getKey().index);
    }

    public static void main(String args[]) {
        DaweiZhang dz = new DaweiZhang("src/input1.txt");//change when you using JGRASP input1.txt

        //Using src/input.txt for here.
    }

    public void readfile (String fileName) throws Exception {
        Scanner inFile = new Scanner(new File(fileName));
        int size = inFile.nextInt();
        int lineNo = 0;
        men = new ArrayList<>();
        women = new ArrayList<>();
        for (int i = 0; i < size * 2; i++) {
            // initial the men and women in arrays
            if (i < size)
                men.add(new Man(size, i%size+1));
            else
                women.add(new Woman(size,i%size+1));
        }
        /**********************************************
         /**men propose*********************************
         *
        inFile.nextLine();
        while (inFile.hasNextLine()) {
            String line = inFile.nextLine();
            String[] components = line.split(" ");
            if(lineNo < size)
            {
                for (int index = 0; index < size; index ++) {
                    men.get(lineNo).rank[index] = Integer.parseInt(components[index])-1;
                }
            }
            else
            {
                for (int index = 0; index < size; index ++) {
                    women.get(lineNo - size).rank[index] = Integer.parseInt(components[index])-1;
                }
            }
            lineNo++;
        }
//        while (inFile.hasNext()) {
//            // initial the rank of men and women in arrays
//            if (count < size * size) {
//                //System.out.println(count +") men.get("+ count / size +") rank["+(inFile.nextInt())+"] = "+ (count%size+1)  );
//                men.get(count / size).rank[(inFile.nextInt()-1)] = count % size;
//            } else {
//                System.out.println("women.get("+ ((count - size * size) / size) +") rank["+(inFile.nextInt()-1)+"] = "+ (count%size+1) );
//                //women.get((count - size * size) / size).rank[inFile.nextInt()-1] = count % size;
//            }
//            count++;
//        }
//        while (inFile.hasNext()) {
//            System.out.println(count +") men.get("+ count / size +") rank["+(inFile.nextInt())+"] = "+ (count%size+1)  );
//            count++;
//                //  men.get(count / size).rank[(Integer.getInteger(inFile.next())-1)] = count % size;
//        }
        /***********************************************
         */

        // women propose

        inFile.nextLine();
        while (inFile.hasNextLine()) {
            String line = inFile.nextLine();
            String[] components = line.split(" ");
            if(lineNo < size)
            {
                for (int index = 0; index < size; index ++) {
                    women.get(lineNo).rank[index] = Integer.parseInt(components[index])-1;
                }
            }
            else
            {
                for (int index = 0; index < size; index ++) {
                    men.get(lineNo - size).rank[index] = Integer.parseInt(components[index])-1;
                }
            }
            lineNo++;
        }
        /*
        for (int i = 0; i < size * 2; i++) {
            // initial the men and women in arrays
            if (i < size)
                women.add(new Woman(size, i%size+1));
            else
                men.add(new Man(size,i%size+1));
        }
        while (inFile.hasNext()) {
            // initial the rank of men and women in arrays
            if (count < size * size) {
                women.get(count / size).rank[inFile.nextInt()-1] = count % size;
            } else {
                men.get((count - size * size) / size).rank[inFile.nextInt()-1] = count % size;
            }
            count++;
        }
         */
        // test the input.txt
//        for (int e:men.get(0).rank) {
//            System.out.print(e+1);
//        }
//        System.out.println();
//        for (int e:men.get(1).rank) {
//            System.out.print(e+1);
//        }
//        System.out.println();
//        for (int e:men.get(2).rank) {
//            System.out.print(e+1);
//        }
//        System.out.println();
//        for (int e:men.get(3).rank) {
//            System.out.print(e+1);
//        }
//        System.out.println();
//        for (int e:women.get(0).rank) {
//            System.out.print(e+1);
//        }
//        System.out.println();
//        for (int e:women.get(1).rank) {
//            System.out.print(e+1);
//        }
//        System.out.println();
//        for (int e:women.get(2).rank) {
//            System.out.print(e+1);
//        }
//        System.out.println();
//        for (int e:women.get(3).rank) {
//            System.out.print(e+1);
//        }
//        System.out.println();

    }

    public Map stableM() {
        int manIndex  = 0;

        Map<Woman, Man> chioce = new HashMap<>();

        while (chioce.size()  <= men.size()) {
            for (int e : men.get(manIndex).rank) {
                // if the man get reject once, do not need to check
                if (!men.get(manIndex).free) {
                    break;
                }
                if(!women.get(e).reject[manIndex]) {
                    if (women.get(e).free) {
                        chioce.put(women.get(e), men.get(manIndex));
                        women.get(e).free = false;
                        men.get(manIndex).free = false;

                        break;
                    } else if (women.get(e).rank[manIndex] < women.get(e).rank[men.indexOf(chioce.get(women.get(e)))]) {
                        women.get(e).reject[men.indexOf(chioce.get(women.get(e)))] = true;
                        men.get(men.indexOf(chioce.get(women.get(e)))).free = true;
                        chioce.remove(women.get(e));
                        chioce.put(women.get(e), men.get(manIndex));
                        men.get(manIndex).free = false;
                        break;
                    } else {
                        women.get(e).reject[manIndex] = true;
                    }
                }
            }
            if(chioce.size() == men.size())
                break;
            if(manIndex == men.size() - 1 )
                manIndex=0;
            else
                manIndex++;
        }
        return chioce;
    }
}

