package com.example.taopiao.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

public class QREncodingCodeUtils {
    public static Bitmap receiveQRBitmap(String content, int width, int height, String charset,
                                         String eclevel, String margin, int color_black, int color_white,
                                         Bitmap logoBitmap,
                                         float logoPercent,
                                         Bitmap bit_black){

        if (isEmpty(content)){
            return null;
        }
        if (width<0||height<0){
            return null;
        }
        Hashtable<EncodeHintType,String> hints=new Hashtable<>();
        //设置字符编码
        if (!isEmpty(charset)){
            hints.put(EncodeHintType.CHARACTER_SET,charset);
        }

        //容错率设置
        if (!isEmpty(eclevel)){
            hints.put(EncodeHintType.ERROR_CORRECTION,eclevel);
        }
        //设置空白边距
        if (!isEmpty(margin)){
            hints.put(EncodeHintType.MARGIN,margin);
        }
        //
        try {

            BitMatrix bitMatrix=new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE,width,height,hints);
            if (bit_black!=null){
                //从当前位图按一定的比例创建一个新的位图
                bit_black=Bitmap.createScaledBitmap(bit_black,width,height,false);

            }
            int[] pxs=new int[width*height];
            for (int i=0;i<height;i++){
                for (int z=0;z<width;z++){
                    //方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(z,i)){
                        if (bit_black!=null){
                            pxs[i*width+z]=bit_black.getPixel(z,i);
                        }else {
                            pxs[i*width+z]=color_black;

                        }
                    }else {
                        pxs[i*width+z] =color_white;
                    }
                }
            }
           Bitmap bitmap=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pxs,0,width,0,0,width,height);
            /** 5.为二维码添加logo图标 */
            if (logoBitmap != null) {
                return addLogo(bitmap, logoBitmap, logoPercent);
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isEmpty(String content){
        if (content==null){
            return true;
        }else {
            return false;
        }
    }

    private static Bitmap addLogo(Bitmap srcBitmap,  Bitmap logoBitmap, float logoPercent){
        if(srcBitmap == null){
            return null;
        }
        if(logoBitmap == null){
            return srcBitmap;
        }
        //传值不合法时使用0.2F
        if(logoPercent < 0F || logoPercent > 1F){
            logoPercent = 0.2F;
        }

        /** 1. 获取原图片和Logo图片各自的宽、高值 */
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();

        /** 2. 计算画布缩放的宽高比 */
        float scaleWidth = srcWidth * logoPercent / logoWidth;
        float scaleHeight = srcHeight * logoPercent / logoHeight;

        /** 3. 使用Canvas绘制,合成图片 */
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(srcBitmap, 0, 0, null);
        canvas.scale(scaleWidth, scaleHeight, srcWidth/2, srcHeight/2);
        canvas.drawBitmap(logoBitmap, srcWidth/2 - logoWidth/2, srcHeight/2 - logoHeight/2, null);

        return bitmap;
    }



}
