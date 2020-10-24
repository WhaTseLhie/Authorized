package pardillo.john.jv.authorized.style;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pardillo.john.jv.authorized.R;

public class MyToast {

    private int drawableToast;
    private int drawableImage;

    public MyToast() {
        // Empty Constructor
    }

    public void makeToast(Context context, String message, String type) {
        toastType(type);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.style_toast, null);
        view.setBackground(context.getResources().getDrawable(this.drawableToast));
        view.getBackground().setAlpha(204);

        TextView toastText = view.findViewById(R.id.toast_text);
        ImageView toastImage = view.findViewById(R.id.toast_image);
        toastText.setText(message);
        toastImage.setImageResource(this.drawableImage);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 30);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    private void toastType(String type) {
        switch(type) {
            case "SUCCESS":
                this.drawableToast = R.drawable.style_success_toast;
                this.drawableImage = R.drawable.ic_toast_check;

                break;
            case "NORMAL":
                this.drawableToast = R.drawable.style_normal_toast;
                this.drawableImage = R.drawable.ic_toast_info;

                break;
            case "WARNING":
                this.drawableToast = R.drawable.style_warning_toast;
                this.drawableImage = R.drawable.ic_toast_warning;

                break;
            case "ERROR":
                this.drawableToast = R.drawable.style_error_toast;
                this.drawableImage = R.drawable.ic_toast_error;

                break;
        }
    }
}