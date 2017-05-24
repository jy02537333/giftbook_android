package pri.zxw.library.exception;

/**
 * 
* @ClassName: CustomException
* @Description: 异常类
* @author Lix
* @date 2015-1-13 上午11:20:55
*
 */
public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomException() {
		super();
	}

	public CustomException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public CustomException(String detailMessage) {
		super(detailMessage);
	}

	public CustomException(Throwable throwable) {
		super(throwable);
	}

}
