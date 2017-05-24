package pri.zxw.library.tool;

/**
 * 
 * @className 统计字符数量
 * @author 张相伟
 * @function 类功能
 * @createDate 2014-12-7
 * @version 1
 * @upadteMemter 2014-12-7
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class StatisticalCharsTool {

 /**
 * 计算字符的数量， 全角为2，半角为1
 * @param destStr
 * @return
 */
	public static int getSemiangleCount(String destStr)
	{
		int count=0;
	  	  char[] strs=destStr.toCharArray();
    	  for(int i=0;i<strs.length;i++){ 
    	  if(strs[i]>'~'){
    		  count+=2;
    		  }else{
    			 count++;
    		  } 
    	  } 
		return count;
	}
	/**
	 * 区分全角半角，截取长度
	 * @return
	 */
	public static int subStringChar(String destStr,int length)
	{
		int count=0;
		int subLength=0;
		 char[] strs=destStr.toCharArray();
	   	  for(int i=0;i<strs.length;i++){ 
	   		  if(strs[i]>'~'){
	   		  count+=2;
	   		  }else{
	   			 count++;
	   		  } 
	   		  if(count>length)
	   		  {
	   			subLength=i;
	   			  break;
	   		  }
	   	  }
   	  return subLength;
	}
	
	 static  boolean HasChineseChar( String destStr)
    {
  	  
		 boolean bHasChinsesChar = false;
	  	  char[] strs=destStr.toCharArray();
	        int nLen = destStr.length();
	         char c1,c2;
	        boolean bFirstSpace = true;

	        for(int i=0; i<nLen; i++)
	        {
	            c1 = strs[i];

	            
	                c2 = strs[i+1];
	                if(c1 == 163)  //判断是否为全角字符 
	                {
	                    //strConv += TCHAR(c2-128);
	                    System.out.println(c1+" 全角字符 ");
	                    System.out.println(c2+" false");
	                    bFirstSpace = false;
	                }
	                else if((int)c1 > 163) //判断是否为文字
	                {
	                    //strConv += (TCHAR)c1;
	                    //strConv += (TCHAR)c2;
	                    bFirstSpace = false;
	                    bHasChinsesChar = true;
	                    System.out.println(c1+" 文字");
	                }
	                else if((c1 == 161) && (c2 == 161)) //全为空格
	                {
	                    System.out.println(c1+" 空格 ");
	                }
	                else      //如果是半角字符
	                {
	                    if (bFirstSpace && (c1 == ' ' || c1 == '\t'))
	                    {
	                    }
	                    else
	                    {
	                        System.out.println(c1+" 半角 ");
	                        //strConv += (TCHAR)c1;
	                        bFirstSpace = false;
	                    }
	                }//endi
	        }
        return bHasChinsesChar;
    } 
}
