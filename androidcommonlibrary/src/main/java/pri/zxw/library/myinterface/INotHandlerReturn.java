package pri.zxw.library.myinterface;

import android.os.Message;

import okhttp3.Call;

/**
 * 功能 ServicesTool 异步返回接口
 * Createdy 张相伟
 * 2017/6/18.
 */

public interface INotHandlerReturn {
    public void onSuccess(Message ret, int id);
    public void onFailure(Call call, Exception error, int requestCode);
}
