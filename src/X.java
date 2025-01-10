public class X {

    public static void main(String[] args) {


        System.out.println("loool");

        for (int i = 0 ; i < args.length; i++){

            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append("; ");
            sb.append(args[i]);
            System.out.println(sb.toString());


        }

    }

}
