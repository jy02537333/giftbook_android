package pri.zxw.library.entity;

import pri.zxw.library.base.BaseEntity;
import pri.zxw.library.entity.AbstractStartDateEntity;

/**
 * 通用对象
 * @author 张相伟
 *
 */
public class ComInfo extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4324234234231L;
	private String id;
	private String name;

	public ComInfo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public Class getMyClass() {
		return null;
	}
}
