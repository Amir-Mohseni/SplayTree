import java.io.*;
import java.util.*;


class Main {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        SplayTree splayTree = new SplayTree();

        while(n > 0) {
            String s = scanner.next();
            if(Objects.equals(s, "add")) {
                long value = scanner.nextLong();
                splayTree.add(value);
            }
            else if(Objects.equals(s, "find")) {
                long value = scanner.nextLong();
                System.out.println(splayTree.find(value));
            }
            else if(Objects.equals(s, "del")) {
                long value = scanner.nextLong();
                splayTree.del(value);
            }
            else {
                long l = scanner.nextLong();
                long r = scanner.nextLong();
                System.out.println(splayTree.sum(l, r));
            }
            n -= 1;
        }
    }
}