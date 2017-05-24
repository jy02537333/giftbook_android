package pri.zxw.library.tool;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 龙流平(LongLiuPing)
 * @version 创建时间：2015年4月2日 上午10:13:01 package pri.zxw.library.tool;
 */
public class SerializableMap implements Serializable {
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}