package pri.zxw.library.tool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtilsBean;



/**
 * 
 * @className  copy类的属性到另一个类中
 * @author 张相伟
 * @function 类功能
 * @createDate 2014-12-19
 * @version 1
 * @upadteMemter 2014-12-19
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class EntityCopyTool {
	 /**
     * 方法名称：bean2Bean
     * 摘要：<p>copy对象frm中属性的值到dest对象中同名属性中</p>
     * @author cuixd
     * @date 2009-12-28 ver 1.0
     * @param frm
     * @param dest
     */
    public static void bean2Bean(Object frm, Object dest) throws Exception{
        try{  
        	BeanUtilsBean bean=new BeanUtilsBean();
        	bean.copyProperties(dest, frm);  
        }catch(Exception e){  
            throw new Exception("属性复制失败:"+e.getMessage());  
        }  
    }
    /**
    * 复制源对象和目标对象的属性值
    *
    */
    public static void copy(Object source, Object target) throws 
    NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
    InvocationTargetException{
	    Class sourceClass = source.getClass();//得到对象的Class
	    Class targetClass = target.getClass();//得到对象的Class
	    Field[] sourceFields = sourceClass.getDeclaredFields();//得到Class对象的所有属性
	    Field[] targetFields = targetClass.getDeclaredFields();//得到Class对象的所有属性
	    for(Field sourceField : sourceFields)
	    {
	    String name = sourceField.getName();//属性名
	    Class type = sourceField.getType();//属性类型
	    String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);
	    Method getMethod = sourceClass.getMethod("get" + methodName);//得到属性对应get方法
	    Object value = getMethod.invoke(source);//执行源对象的get方法得到属性值
		    for(Field targetField : targetFields){
		    String targetName = targetField.getName();//目标对象的属性名
			    if(targetName.equals(name)){
			    Method setMethod = targetClass.getMethod("set" + methodName, type);//属性对应的set方法
			    setMethod.invoke(target, value);//执行目标对象的set方法
			    }
		    }
	    }
    }

}
