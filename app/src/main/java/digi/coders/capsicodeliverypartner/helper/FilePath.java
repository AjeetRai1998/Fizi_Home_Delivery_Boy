package digi.coders.capsicodeliverypartner.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

/* loaded from: classes5.dex */
public class FilePath {
    public static String getPath(final Context context, final Uri uri) {
        boolean isKitKat = Build.VERSION.SDK_INT >= 19;
        Log.i("sdkversosdfg", Build.VERSION.SDK_INT + "");
        if (Build.VERSION.SDK_INT < 19) {
            Toast.makeText(context, ";slkjfh;lahs'lhsd'g", 0).show();
        } else if (!isKitKat || !DocumentsContract.isDocumentUri(context, uri)) {
            String docId = uri.getScheme();
            if ("content".equalsIgnoreCase(docId)) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(context, uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else if (isExternalStorageDocument(uri)) {
            String docId2 = DocumentsContract.getDocumentId(uri);
            String[] split = docId2.split(":");
            if ("primary".equalsIgnoreCase(split[0])) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        } else if (isDownloadsDocument(uri)) {
            String id = DocumentsContract.getDocumentId(uri);
            if (id.startsWith("raw:")) {
                String path = id.replaceFirst("raw:", "");
                Log.e("sads", path + "");
                return path;
            }
            Log.e("sdid", id + "");
        } else if (isMediaDocument(uri)) {
            String docId3 = DocumentsContract.getDocumentId(uri);
            String[] split2 = docId3.split(":");
            String type = split2[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            String[] selectionArgs = {split2[1]};
            return getDataColumn(context, contentUri, "_id=?", selectionArgs);
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String[] projection = {"_data"};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow("_data");
                return cursor.getString(index);
            } else if (cursor == null) {
                return null;
            } else {
                cursor.close();
                return null;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
