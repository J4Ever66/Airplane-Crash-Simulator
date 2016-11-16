//BETTER VERSION. Works with base 2 and base 36. 
//Doesn't work with larger time values, yet. The parseInt function breaks there

public class Tester
{
   private static int base = 36;
   public static void main(String[] args)
   {
      double time = 1.1;
      System.out.println(encodeTime(time));
      /*time = 71.111;
      System.out.println(encodeTime(time));
      time = 7.111;
      System.out.println(encodeTime(time));
      time = 0.011;
      System.out.println(encodeTime(time));*/
   }
   
   public static String encodeTime(double time)//base 10
   {
      long len = Math.round(Math.ceil(Math.log(9999999) / Math.log(base)));
   
      long rounded = Math.round(time*10000);
   
      int newNum = Integer.parseInt((""+rounded),base);
     
      String zeros=""
      ,ret = ""+newNum;
      
      int i;
      long g=(len-ret.length());
      
      for(i=0;i<g;i++)
         zeros += "0";
         
      return zeros + ret;
   }//return at target base
}
