import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


class Man{
   int size;
   int[] rank;
   boolean free;
   Man(int size){
      rank = new int[size];
      free = true;
   }
}

class Woman{
   int size;
   int[] rank= new int[size];
   boolean free = true;
   Woman(int size){
      rank = new int[size];
      free = true;
   }
}

public class DaweiZhang {
    ArrayList<Man> men = new ArrayList<>();
    ArrayList<Woman> women = new ArrayList<>();

    public DaweiZhang(String fileName) {
        try {
            readfile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        DaweiZhang dz = new DaweiZhang("src/input1.txt");//change when you using JGRASP input1.txt
        //stableM();
        //Using src/input.txt for here.
    }

        public void readfile (String fileName) throws Exception {
            Scanner inFile = new Scanner(new File(fileName));
            int count = 0;
            int size = inFile.nextInt();
            men = new ArrayList<>();
            women = new ArrayList<>();
            for (int i = 0; i < size * 2; i++) {
                // initial the men and women in arrays
                if (i < size)
                    men.add(new Man(size));
                else
                    women.add(new Woman(size));
            }
            while (inFile.hasNext()) {
                // initial the rank of men and women in arrays
                if (count < size * size) {
                    men.get(count / size).rank[inFile.nextInt()-1] = count % size;
                } else {
                    women.get((count - size * size) / size).rank[inFile.nextInt()-1] = count % size;
                }
                count++;
            }
            // test the input.txt
       for (int e:men.get(1).rank) {
          System.out.println(e);
       }

        }

        public HashMap stableM() {
            int matched = 0;
            HashMap<Woman, Man> chioce = new HashMap<>();
            while (matched < men.size()) {
                int manIndex = matched;
                for (manIndex = 0; manIndex < men.size(); manIndex++) {
                    for (int e : men.get(manIndex).rank) {
                        if (women.get(e).free) {
                            women.get(e).free = false;
                            chioce.put(women.get(e), men.get(manIndex));
                            matched++;
                        } else {
                            if (women.get(e).rank[manIndex] < women.get(e).rank[men.indexOf(chioce.get(women.get(e)))]) {
                                //men.indexOf(chioce.get(women.get(e))) >)
                                chioce.remove(women.get(e));
                                chioce.put(women.get(e), men.get(manIndex));
                                matched++;
                            }
                        }
                    }
                }
            }
            return chioce;
        }
    }

