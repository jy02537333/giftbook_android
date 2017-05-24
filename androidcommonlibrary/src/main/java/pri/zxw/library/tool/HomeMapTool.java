package pri.zxw.library.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import pri.zxw.library.entity.FragmentOrder;
import pri.zxw.library.R;


public class HomeMapTool {
	/**
	 *  Integer 显示级别
	 */
	private  static Map<Integer, List<FragmentOrder>> fragmentMap;
	private static HomeMapTool mHomeMapTool;
	private static Map<String ,FragmentOrder> dataMap ;
	/**
	 * 
	 */
	public static void clear()
	{
		fragmentMap.clear();
	    dataMap.clear();
	    mHomeMapTool=null;
	}
	/***
	 * 底部菜单
	 */
	public static int BOTTOM_MENU=1;
	/**
	 * 更多菜单
	 */
	public static int MORE_MENU=2;
	/**
	 * 菜单页面
	 */
	public static int MENU=0;
	
	
	
	private HomeMapTool(){}
	public static HomeMapTool  getInstance()
	{
		if(mHomeMapTool==null)
		{
			mHomeMapTool=new HomeMapTool();
			setMenuData(null);
			fragmentMap=new HashMap<Integer, List<FragmentOrder>>();
			getRoleMenu();
		}
		return mHomeMapTool;
	}
	public static HomeMapTool  getInstance(TreeMap<String, FragmentOrder> map)
	{
		if(mHomeMapTool==null)
		{
			mHomeMapTool=new HomeMapTool();
			setMenuData(map);
			fragmentMap=new HashMap<Integer, List<FragmentOrder>>();
			getRoleMenu();
		}
		return mHomeMapTool;
	}
	public static void setMenuData( TreeMap<String, FragmentOrder> map){
		if(map!=null&&map.size()>0)
		{
		dataMap= map;
		dataMap=sortMap(dataMap);
		}
	}
	
	/**
	 * 超级管理员菜�?
	 * @return
	 */
	private static Map<Integer, List<FragmentOrder>> getRoleMenu()
	{
		 List<FragmentOrder> orders = new ArrayList<FragmentOrder>();
			 for (FragmentOrder fragmentOrder : dataMap.values()) {
				 orders.add(fragmentOrder);
			 }
		
			 fragmentMap.put(BOTTOM_MENU, orders);
		 
		return fragmentMap;
	}
	
	/**
	 * 
	* @Title: getSupervisorRoleMenu
	* @Description: 获取当前用户的操作权�?
	* @param 设定文件
	* @return Map<Integer,List<FragmentOrder>>    返回类型
	* @throws
	 */
	private static Map<Integer, List<FragmentOrder>> getSupervisorRoleMenu()
	{
		
		return null;
	}
	/**
	 * 获取底部菜单
	 * @return
	 */
	public List<FragmentOrder> getBottomMenu()
	{
		return fragmentMap.get(BOTTOM_MENU);
	}
	/**
	 * 获取更多功能菜单
	 * @return
	 */
	public List<FragmentOrder> getMoreMenu()
	{
		return fragmentMap.get(MORE_MENU);
	}
	
	/**
	 * 
	* @Title: getBottomDraw
	* @Description: 获取底部菜单的图�?
	* @return List<Integer>    返回类型
	* @throws
	 */
	public List<Integer> getBottomDraw(){
		List<Integer> ids = new ArrayList<Integer>();
		List<FragmentOrder> list = fragmentMap.get(BOTTOM_MENU);
		for(int i = 0;i<list.size();i++)
			ids.add(list.get(i).getDrawableId());
		return ids;
	}
	
	/**
	 * 
	* @Title: getBottomName
	* @Description: 获取底部菜单的文�?
	* @return List<CharSequence>    返回类型
	* @throws
	 */
	public List<CharSequence> getBottomName(){
		List<CharSequence> names = new ArrayList<CharSequence>();
		List<FragmentOrder> list = fragmentMap.get(BOTTOM_MENU);
		for (int i = 0; i < list.size(); i++) {
			names.add(list.get(i).getName());
		}
		return names;
	}
	
	/**
	 * 
	* @Title: getMoreName
	* @Description: 获取更多菜单项的菜单文字
	* @param @return    设定文件
	* @return List<CharSequence>    返回类型
	* @throws
	 */
	public List<CharSequence> getMoreName(){
		List<CharSequence> moreMenuName = null;
		List<FragmentOrder> list = fragmentMap.get(MORE_MENU);
		if(list != null && list.size() > 0){
			moreMenuName = new ArrayList<CharSequence>();
			for (int i = 0; i < list.size(); i++)
				moreMenuName.add(list.get(i).getName());
		}
		return moreMenuName;
	}
	public static Map sortMap(Map oldMap) {  
        ArrayList<Entry<String, FragmentOrder>> list = new ArrayList<Entry<String, FragmentOrder>>(oldMap.entrySet());
        Collections.sort(list, new Comparator<Entry<String, FragmentOrder>>() {
            @Override  
            public int compare(Entry<String, FragmentOrder> arg0,
                    Entry<String, FragmentOrder> arg1) {
                return arg0.getValue().getOrderKey() - arg1.getValue().getOrderKey();  
            }  
        });  
        Map newMap = new LinkedHashMap();  
        for (int i = 0; i < list.size(); i++) {  
            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
        }  
        return newMap;  
    } 
}
