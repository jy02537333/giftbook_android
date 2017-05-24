package pri.zxw.library.entity;

/**
 * 
 * @description 通用id和name
 * @author 张相伟
 * @date 2016-10-20
 */
public class ComIdNameInfo {
	private String id;
	private String name;
	private String phone;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
