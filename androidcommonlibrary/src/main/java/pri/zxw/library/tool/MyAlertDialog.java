package pri.zxw.library.tool;


import pri.zxw.library.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
  
/**
 *
 * Create custom Dialog windows for your application
 * Custom dialogs rely on custom layouts wich allow you to
 * create and use your own look & feel.
 *
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 *
 * @author antoine vianey
 *
 */
public class MyAlertDialog extends Dialog {
  
    public MyAlertDialog(Context context, int theme) {
        super(context, theme);
    }
  
    public MyAlertDialog(Context context) {
        super(context);
    }
    
    /**
     * Helper class for creating a custom dialog
     */
    public static class MyBuilder  {
  
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText="确认";
        private String negativeButtonText="取消";
        private boolean mPositiveDismiss ;
        private boolean mNegativeDismiss;
        private View contentView;
        private int theme;
        private View mlayout;
        private MyAlertDialog dialog;
        private LayoutParams mContentLayoutParams;
        private OnClickListener
                        positiveButtonClickListener,
                        negativeButtonClickListener;
        
        
        public MyBuilder(Context context) {
        	this.context = context;
        }
        
        public MyBuilder setTheme(int themeId)
        {
        	this.theme=themeId;
        	return this;
        }
        /**
         * Set the Dialog message from String
         * @return
         */
        public MyBuilder setMessage(String message) {
            this.message = message;
            return this;
        }
  
        /**
         * Set the Dialog message from resource
         * @return
         */
        public MyBuilder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }
  
        /**
         * Set the Dialog title from resource
         * @param title
         * @return
         */
        public MyBuilder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }
  
        /**
         * Set the Dialog title from String
         * @param title
         * @return
         */
        public MyBuilder setTitle(String title) {
            this.title = title;
            return this;
        }
  
        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         * @param v
         * @return
         */
        public MyBuilder setContentView(View v) {
            this.contentView = v;
            return this;
        }
  
        /**
         * Set the positive button resource and it's listener
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public MyBuilder setPositiveButton(int positiveButtonText,
        		OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }
        public MyBuilder setPositiveButton(int positiveButtonText) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            return this;
        }
        /**
         * Set the positive button text and it's listener
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public MyBuilder setPositiveButton(String positiveButtonText,
        		OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }
        public MyBuilder setPositiveButton(String positiveButtonText ) {
            this.positiveButtonText = positiveButtonText;
            return this;
        }
        /**
         * Set the negative button resource and it's listener
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public MyBuilder setNegativeButton(int negativeButtonText,
        		OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }
        public MyBuilder setNegativeButton(int negativeButtonText ) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            return this;
        }
        /**
         * Set the negative button text and it's listener
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public MyBuilder setNegativeButton(String negativeButtonText,
        		OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }
        public MyBuilder setNegativeButton(String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }
       public MyBuilder setParam(LayoutParams layoutParams)
       {
    	   mContentLayoutParams=layoutParams;
    	   return this;
       }
        
        
        /**
         * Create the custom dialog
         */
        public MyAlertDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new MyAlertDialog(context,R.style.Dialog);
            mlayout = inflater.inflate(R.layout.my_alert_layout, null);
            dialog.addContentView(mlayout, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) mlayout.findViewById(R.id.my_alert_layout_title_tv)).setText(title);
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) mlayout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                    ((Button) mlayout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                	if(positiveButtonClickListener!=null)
                                	positiveButtonClickListener.onClick(
                                            dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                	if(!mPositiveDismiss)
                                	dialog.dismiss();
                                }
                            });
            } else {
                // if no confirm button just set the visibility to GONE
            	mlayout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) mlayout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                    ((Button) mlayout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                   	if(negativeButtonClickListener!=null)
                                	negativeButtonClickListener.onClick(
                                            dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                	if(!mNegativeDismiss)
                                	dialog.dismiss();
                                }
                            });
            } else {
            	mlayout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
                ((TextView) mlayout.findViewById(
                        R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) mlayout.findViewById(R.id.alert_content))
                        .removeAllViews();
                LayoutParams layoutParams=null;
                if(mContentLayoutParams!=null)
                	layoutParams=mContentLayoutParams;
                else 
                	layoutParams=new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT); 
                ((LinearLayout) mlayout.findViewById(R.id.alert_content))
                        .addView(contentView,layoutParams);
            }
            dialog.setContentView(mlayout);
            return dialog;
        }
        /**
         *  是否点击取消或者确定后，自动解散,true取消Auto，false启动 Auto
         * @param positiveDismiss 正级，左边的
         * @param negativeDismiss 负极，右边的
         */
        public void setAutoDismiss(boolean positiveDismiss,boolean negativeDismiss)
        {
        	mPositiveDismiss=positiveDismiss;
        	mNegativeDismiss=negativeDismiss;
        }
        
        public void show()
        {
        	dialog.show();
        }
     
        public void dismiss()
        {
        	dialog.dismiss();
        }
        
        public interface OnDialogClickListener {
        	void onClick(MyAlertDialog dialog, int arg);
        }
        
        //buidler end
    }
  
  
    
}

