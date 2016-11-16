public class Tester
{
   private static int base = 10;
   public static void main(String[] args)
   {
      double time = 122.1001;
      System.out.println(encodeTime(time));
      time = 71.111;
      System.out.println(encodeTime(time));
      time = 7.111;
      System.out.println(encodeTime(time));
      time = 0.011;
      System.out.println(encodeTime(time));
   }
   
   public static String encodeTime(double time)//base 10
   {
      long rounded = Math.round(time*10000);
   
      int newNum = Integer.parseInt((""+rounded),base);
     
      String zeros=""
      ,ret = ""+newNum;
      
      int i,
      g=(7-ret.length());
      
      for(i=0;i<g;i++)
         zeros += "0";
         
      return zeros + ret;
   }//return at target base
}
