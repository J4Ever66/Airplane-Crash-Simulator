public class Tester
{
   private static int base;
   public static void main(String[] args)
   {
      double time = 999.4321;
      System.out.println("time = "+time);
      
      base = 10;
      System.out.println("base 10:\t"+encodeTime(time));
      
      base = 2;
      System.out.println("base 2:  \t"+encodeTime(time));
      
      base = 36;
      System.out.println("base 36:\t"+encodeTime(time));
      
      
      time = 0.001;
      System.out.println("time = "+time);
      
      base = 10;
      System.out.println("base 10:\t"+encodeTime(time));
      
      base = 2;
      System.out.println("base 2:  \t"+encodeTime(time));
      
      base = 36;
      System.out.println("base 36:\t"+encodeTime(time));
   }
   
   public static String encodeTime(double time)//base 10
   {
      long len = Math.round(Math.ceil(Math.log(9999999) / Math.log(base)));
      
      int newNum = Integer.parseInt(( "" + Math.round( time * 10000 )) , 10);
      //           ^convert long to int    ^convert double to long
   
      String ret = Integer.toString(newNum,base).toUpperCase();
      //           ^convert to target base  
   
      int i;
      long g=(len-ret.length());
      String zeros="";
      for(i=0;i<g;i++){
         zeros += "0";}
         
      return zeros + ret;
   }//return at target base
}


