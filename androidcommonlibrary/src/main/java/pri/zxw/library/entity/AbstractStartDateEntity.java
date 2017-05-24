package pri.zxw.library.entity;

import java.io.Serializable;

public abstract class AbstractStartDateEntity implements Serializable {
	public abstract String AcquireStartDate();

	/**
	 * 获取类型的class
	 * @return
     */
	public abstract Class getMyClass();
}