package pri.zxw.library.tool;

import java.io.File;
import java.util.Map;

import pri.zxw.library.entity.FileByteEntity;

/**
 * @author 张相伟修改
 * @version 2014年10月7日 上午11:04:36
 */
public interface MultiPartRequest {

	/**
	 * 上传文件，name相同时，使用该属性
	 */
	public String getFileName();
    public void addFileUpload(String param, File file);
    
    public void addStringUpload(String param, String content);
    
    public Map<String,File> getFileUploads();
    /**
     * 文件byte
     * @return
     */
    public Map<String,FileByteEntity> getFileByteUploads();
    
    public Map<String,String> getStringUploads(); 
}