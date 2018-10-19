package coding.yu.autoact.widget;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;

/*
 * Author: yu on 18-10-18
 */
public class SimpleSettingView extends FrameLayout {

    private TextView mTitleText;
    private TextView mSubTitleText;

    private int mExtraWhat;

    public SimpleSettingView(Context context) {
        super(context);
        init();
    }

    public SimpleSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTitleText = new TextView(getContext());
        mTitleText.setTextSize(17);
        mTitleText.setTextColor(0xFF333333);
        mSubTitleText = new TextView(getContext());
        mSubTitleText.setTextSize(14);
        mSubTitleText.setPadding(0, 4, 0, 0);
        mTitleText.setTextColor(0xFF444444);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(mTitleText);
        linearLayout.addView(mSubTitleText);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.topMargin = ConvertUtils.dp2px(16);
        params.bottomMargin = ConvertUtils.dp2px(16);
        params.leftMargin = ConvertUtils.dp2px(20);
        params.rightMargin = ConvertUtils.dp2px(20);
        addView(linearLayout, params);
    }

    public void setTitleText(@StringRes int id) {
        mTitleText.setText(id);
    }

    public void setTitleText(String text) {
        mTitleText.setText(text);
    }

    public void setSubTitleText(String text) {
        mSubTitleText.setText(text);
    }

    public String getSubTitleText() {
        return mSubTitleText.getText().toString();
    }

    public int getExtraWhat() {
        return mExtraWhat;
    }

    public void setExtraWhat(int what) {
        this.mExtraWhat = what;
    }
}
