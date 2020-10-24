package pardillo.john.jv.authorized.style;

import android.support.annotation.NonNull;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MySpan extends ClickableSpan {

    @Override
    public void onClick(@NonNull View widget) {
        TextView tv = (TextView) widget;
        Spanned s = (Spanned) tv.getText();
        int start = s.getSpanStart(this);
        int end = s.getSpanEnd(this);
        CharSequence charSequence = s.subSequence(start, end);
    }
}
