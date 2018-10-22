package coding.yu.autoact.helper;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TimePicker;

import com.blankj.utilcode.util.ConvertUtils;


/**
 * Author: yu on 18-10-19
 */
public class DialogHelper {

    public static AlertDialog createNormalDialog(Context context, String title, String content, final DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onClick(dialog, which);
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    public static AlertDialog createEditDialog(Context context, String title, final OnEditDialogPosClickListener listener) {
        FrameLayout layout = new FrameLayout(context);
        layout.setPadding(ConvertUtils.dp2px(20),
                ConvertUtils.dp2px(4),
                ConvertUtils.dp2px(20),
                ConvertUtils.dp2px(4));
        final EditText editText = new EditText(context);
        layout.addView(editText);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(layout);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onClick(dialog, editText.getText().toString());
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    public interface OnEditDialogPosClickListener {
        void onClick(DialogInterface dialog, String content);
    }

    public static TimePickerDialog createTimeDialog(Context context, final OnTimeSetListener listener) {
        TimePickerDialog dialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (listener != null) {
                            listener.onTimeSet(hourOfDay, minute);
                        }
                    }
                },
                12,
                0,
                true);

        return dialog;
    }

    public interface OnTimeSetListener {
        void onTimeSet(int hourOfDay, int minute);
    }

    public static int sChoiceWhich = 0;

    public static AlertDialog createChoiceDialog(Context context, String title, int items, final OnChoicePosClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sChoiceWhich = which;
            }
        });
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onClick(dialog, sChoiceWhich);
                }
                sChoiceWhich = 0;
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                sChoiceWhich = 0;
            }
        });

        return builder.create();
    }

    public interface OnChoicePosClickListener {
        void onClick(DialogInterface dialog, int which);
    }
}