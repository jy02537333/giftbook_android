package pri.zxw.library.tool;

import java.io.Serializable;
import java.lang.reflect.Type;

import org.json.JSONObject;

import pri.zxw.library.entity.AbstractStartDateEntity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public class JsonCommon<T extends AbstractStartDateEntity> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 23213123L;

	/**
	 * 
	 * @param jsonStr
	 * @param collectionClazz 
	 * @totaleKey 
	 * @return
	 */
	public static  NetTotaleAndEntity jsonTotaleObject(String jsonStr,Type listType,String contentKey) {
		if(jsonStr!=null)
		{
			NetTotaleAndEntity netTotaleAndEntity=new NetTotaleAndEntity();
			try {
				  JSONObject jsonObject=new JSONObject(jsonStr);     //返回的数据形式是一个Object类型，所以可以直接转换成一个Object   
				  if(jsonStr.indexOf("totale")!=-1)
				  netTotaleAndEntity.totale=jsonObject.getInt("totale");
				  if(jsonStr.indexOf(contentKey)!=-1)
				  {
					 Gson gson=new Gson();
					 String content=jsonObject.getString(contentKey);
					 netTotaleAndEntity.entity=gson.fromJson(content, listType);
				  }
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return netTotaleAndEntity;
		}
		return null;
	}
	
	
	



}
