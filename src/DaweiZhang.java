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
      DaweiZhang dz = new DaweiZhang("input1.txt");//change when you using JGRASP input1.txt
                                                  //Using src/input.txt for here.
   
     //dz.stableM();
   }

   public void readfile(String fileName) throws Exception {
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
            men.get(count / size).rank[count%size]= inFile.nextInt();
         } else {
            women.get((count - size * size) / size).rank[count%size]=inFile.nextInt();
         }
         count++;
      }
      // test the input.txt
//       for (int e:men.get(1).rank) {
//          System.out.println(e);
//       }
   
   }

   public int stableM() {
      int matched = 0;
      HashMap<Woman, Man> chioce = new HashMap<>();
      while (matched < men.size()) {
         int s = matched;
         for (s = 0; s < men.size(); s++) {
            for (int e : men.get(s).rank) {
               if (women.get(e).free) {
                  women.get(e).free = false;
                  chioce.put(women.get(e), men.get(s));
                  matched++;
               } else {
                  if ( > 1)
                  break;
               }
            }
         }
      }
      return 0;
   }
}
