public class Tester//Works for all bases
{
   private static int base = 2;
   public static void main(String[] args)
   {
      double time = 1.1;
      System.out.println(encodeTime(time));
      
       time = 2.1;
      System.out.println(encodeTime(time));
      
       time = 4.1;
      System.out.println(encodeTime(time));
      
       time = 5.1;
      System.out.println(encodeTime(time));
      
       time = 7.1;
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
      long len = Math.round(Math.ceil(Math.log(9999999) / Math.log(base)));
   
      int rounded = Integer.parseInt((""+Math.round(time*10000)),10);
      
      System.out.println("::"+rounded+"::");
   
      String zeros=""
      ,ret = Integer.toString(rounded,base);
     
      
      
      int i;
      long g=(len-ret.length());
      
      for(i=0;i<g;i++)
         zeros += "0";
         
      return zeros + ret;
   }//return at target base
}

