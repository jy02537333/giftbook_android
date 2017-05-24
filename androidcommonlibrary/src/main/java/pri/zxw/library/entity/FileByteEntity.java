package pri.zxw.library.entity;

/**
 * com.android.volley 上传文件byte
 * @author 张相伟
 *
 */
public class   FileByteEntity{
   public FileByteEntity(){}
   public FileByteEntity(byte[] fileByte,String fileName){
	   this.fileByte=fileByte;this.fileName=fileName;}
  public byte[] fileByte;
  public String fileName;
}
