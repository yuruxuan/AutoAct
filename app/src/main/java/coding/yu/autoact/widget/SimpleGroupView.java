package coding.yu.autoact.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;

import coding.yu.autoact.R;
import coding.yu.autoact.bean.AutoTask;

/**
 * Author: yu on 18-10-19
 */
public class SimpleGroupView extends FrameLayout {

    private TextView text_name;
    private TextView text_description;
    private TextView text_end_description;
    private ImageView image_end_description;

    public SimpleGroupView(Context context) {
        super(context);
        init();
    }

    public SimpleGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_simple_task, this);
        text_name = findViewById(R.id.text_name);
        text_description = findViewById(R.id.text_description);
        text_end_description = findViewById(R.id.text_end_description);
        image_end_description = findViewById(R.id.image_end_description);
    }

    public void setName(String name) {
        text_name.setText(name);
    }

    public void setDescription(String description) {
        text_description.setText(description);
    }

    public void setEndDescriptionText(String description) {
        text_end_description.setText(description);
        text_end_description.setVisibility(VISIBLE);
        image_end_description.setVisibility(GONE);
    }

    public void setEndDescriptionImage(Drawable drawable) {
        image_end_description.setImageDrawable(drawable);
        image_end_description.setVisibility(VISIBLE);
        text_end_description.setVisibility(GONE);
    }

    public static LinearLayout.LayoutParams getSimpleLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, ConvertUtils.dp2px(12));
        return params;
    }
}
