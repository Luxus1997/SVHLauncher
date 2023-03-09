package com.haoyue.svhlauncher.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.YuvImage;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;

public class BitmapUtils {

    public static byte[] Bitmap2Bytes(Bitmap bitmap, int quality) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap NV21ToBitmap(byte[] byteArray, int width, int height, byte[] snapshotData, int displayOrientation, boolean isMirror) {
        new BitmapFactory.Options().inJustDecodeBounds = true;
        YuvImage yuvImage = new YuvImage(byteArray, 17, width, height, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 100, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        Matrix matrix = new Matrix();
        matrix.postRotate(displayOrientation, decodeByteArray.getWidth() / 2, decodeByteArray.getHeight() / 2);
        if (isMirror) {
            matrix.postScale(-1.0f, 1.0f);
        }
        return Bitmap.createBitmap(decodeByteArray, 0, 0, decodeByteArray.getWidth(), decodeByteArray.getHeight(), matrix, true);
    }

    public static Bitmap base64ToBitmap(String base64Data) {
        Object o2;
        Object o = o2 = null;
        if (base64Data == null) {
            return (Bitmap) o2;
        }
        o2 = o;
        if (base64Data.equals("")) {
            return (Bitmap) o2;
        }
        byte[] decode = Base64.decode(base64Data, 0);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 0;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decode);
        base64Data = (String) (o2 = new SoftReference<>(BitmapFactory.decodeStream(byteArrayInputStream, null, options)).get());
        if (byteArrayInputStream == null) {
            return (Bitmap) o2;
        }
        try {
            byteArrayInputStream.close();
            o2 = base64Data;
            return (Bitmap) o2;
        } catch (IOException ex) {
            ex.printStackTrace();
            return (Bitmap) o2;
        }
    }

    public static byte[] base64ToByte(String base64Data) {
        byte[] decode = null;
        if (base64Data != null) {
            decode = decode;
            if (!base64Data.equals("")) {
                decode = Base64.decode(base64Data, 0);
            }
        }
        return decode;
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        if (bitmap != null) {
            try {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0x64, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ((baos != null) && (baos != null)) {
                try {
                    baos.flush();
                    baos.flush();
                    baos.close();
                    baos.close();
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static Bitmap byteToBitmap(byte[] array) {
//        byte[] bitmapArray = Base64.decode(array, Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
//        return bitmap;

        Bitmap decodeByteArray = null;
        if (array != null) {
            decodeByteArray = BitmapFactory.decodeByteArray(array, 0, array.length);
        }
        return decodeByteArray;
    }

    public static Bitmap getFaceFieldBitmap(Bitmap bitmap, RectF rectF) {
        Log.e("", bitmap.getHeight() + "");
        if (rectF != null && !rectF.isEmpty()) {
            final int n = (int) ((rectF.centerX() - rectF.left) * 2.0f);
            final int n2 = (int) ((rectF.centerY() - rectF.top) * 2.0f);
            final int n3 = (int) rectF.centerX() - n / 2;
            final int n4 = (int) rectF.centerY() - n2 / 2;
            Log.e("zhangx", "w = " + n + ",h = " + n2 + ",x = " + n3 + ",y = " + n4);
            int n5 = n;
            if (n3 + n > 640) {
                n5 = 640 - n3;
            }
            int n6 = n2;
            if (n4 + n2 > 480) {
                n6 = 480 - n4;
            }
            return Bitmap.createBitmap(bitmap, n3, n4, n5, n6);
        }
        return bitmap;
    }

    public static Bitmap getSmailBitmap(Bitmap bitmap, boolean isMatrix) {
        Bitmap bitmap2 = null;
        if (bitmap != null) {
            bitmap = (bitmap2 = Bitmap.createBitmap(bitmap, 140, 0, 360, 480));
            if (isMatrix) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                float n;
                if (width > 240 || height > 320) {
                    n = (float) (240.0 / width);
                    final float n2 = (float) (320.0 / height);
                    if (n > n2) {
                        n = n2;
                    }
                } else {
                    n = 1.0f;
                }
                Matrix matrix = new Matrix();
                matrix.postScale(n, n);
                return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            }
        }
        return bitmap2;
    }

    public static void saveBitmap(Bitmap bitmap, String bitName) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + bitName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex2) {
            ex2.printStackTrace();
        }
    }

}
