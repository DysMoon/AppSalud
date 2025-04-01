package DataAccess;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class DAOCalls {

    private final Uri callLogUri = CallLog.Calls.CONTENT_URI;
    private final String[] callLogColumns = {
            CallLog.Calls.NUMBER,
            CallLog.Calls.TYPE
    };

    private static final int INDEX_NUMBER = 0;
    private static final int INDEX_TYPE = 1;

    @RequiresApi(api = android.os.Build.VERSION_CODES.O)
    public List<String> listCalls(Activity context, String filter) {
        List<String> callList = new ArrayList<>();
        Cursor cursor = null;

        try {
            ContentResolver contentResolver = context.getContentResolver();
            cursor = contentResolver.query(
                    callLogUri,
                    callLogColumns,
                    null,
                    null,
                    CallLog.Calls.DATE + " DESC"
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String number = cursor.getString(INDEX_NUMBER);
                    int callTypeInt = cursor.getInt(INDEX_TYPE);

                    String callType;
                    switch (callTypeInt) {
                        case CallLog.Calls.INCOMING_TYPE:
                            callType = "Incoming";
                            break;
                        case CallLog.Calls.OUTGOING_TYPE:
                            callType = "Outgoing";
                            break;
                        case CallLog.Calls.MISSED_TYPE:
                            callType = "Missed";
                            break;
                        case CallLog.Calls.REJECTED_TYPE:
                            callType = "Rejected";
                            break;
                        default:
                            continue;
                    }

                    if (filter.equals("All") || filter.equals(callType)) {
                        callList.add(callType + " - " + number);
                    }

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Toast.makeText(context, "Error al obtener llamadas: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return callList;
    }
}
