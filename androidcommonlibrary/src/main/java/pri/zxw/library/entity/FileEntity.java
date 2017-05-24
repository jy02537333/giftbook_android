package pri.zxw.library.entity;

import java.io.File;

/**
 * com.android.volley 上传文件
 * @author 张相伟
 *
 */
public class FileEntity {
   public FileEntity(){}
   public FileEntity(File file, String fileName){
	   this.file=file;this.fileName=fileName;}
  public File file;
  public String fileName;
}
