package com.r00t.v_lib.activities.addBook.OCR;

import android.content.Context;

import android.util.SparseArray;

import com.google.android.gms.internal.vision.zzaa;
import com.google.android.gms.internal.vision.zzz;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;

import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.SparseArray;
import com.google.android.gms.internal.vision.zzaa;
import com.google.android.gms.internal.vision.zzk;
import com.google.android.gms.internal.vision.zzm;
import com.google.android.gms.internal.vision.zzt;
import com.google.android.gms.internal.vision.zzv;
import com.google.android.gms.internal.vision.zzz;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.Frame.Metadata;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import com.google.android.gms.vision.text.Element;

public class ElementRecognizer extends Detector<Element> {
    private final zzz zzda;
    private ElementRecognizer() {
        throw new IllegalStateException("Default constructor called");
    }

    private ElementRecognizer(zzz var1) {
        this.zzda = var1;
    }

    public final SparseArray<Element> detect(Frame var1) {
        zzv var4 = new zzv(new Rect());
        if (var1 == null) {
            throw new IllegalArgumentException("No frame supplied.");
        } else {
            zzk var5 = zzk.zzc(var1);
            Bitmap var6;
            int var9;
            int var10;
            int var10001;
            if (var1.getBitmap() != null) {
                var6 = var1.getBitmap();
            } else {
                Metadata var7 = var1.getMetadata();
                ByteBuffer var10000 = var1.getGrayscaleImageData();
                var10001 = var7.getFormat();
                int var11 = var5.height;
                var10 = var5.width;
                var9 = var10001;
                ByteBuffer var8 = var10000;
                byte[] var13;
                if (var10000.hasArray() && var8.arrayOffset() == 0) {
                    var13 = var8.array();
                } else {
                    var13 = new byte[var8.capacity()];
                    var8.get(var13);
                }

                ByteArrayOutputStream var14 = new ByteArrayOutputStream();
                (new YuvImage(var13, var9, var10, var11, (int[])null)).compressToJpeg(new Rect(0, 0, var10, var11), 100, var14);
                byte[] var16;
                var6 = BitmapFactory.decodeByteArray(var16 = var14.toByteArray(), 0, var16.length);
            }

            var6 = zzm.zzb(var6, var5);
            if (!var4.zzdm.isEmpty()) {
                Rect var25 = var4.zzdm;
                var10001 = var1.getMetadata().getWidth();
                var10 = var1.getMetadata().getHeight();
                var9 = var10001;
                Rect var18 = var25;
                switch(var5.rotation) {
                    case 1:
                        var25 = new Rect(var10 - var18.bottom, var18.left, var10 - var18.top, var18.right);
                        break;
                    case 2:
                        var25 = new Rect(var9 - var18.right, var10 - var18.bottom, var9 - var18.left, var10 - var18.top);
                        break;
                    case 3:
                        var25 = new Rect(var18.top, var9 - var18.right, var18.bottom, var9 - var18.left);
                        break;
                    default:
                        var25 = var18;
                }

                Rect var17 = var25;
                var4.zzdm.set(var17);
            }

            var5.rotation = 0;
            zzt[] var19 = this.zzda.zza(var6, var5, var4);
            SparseArray var20 = new SparseArray();
            zzt[] var21 = var19;
            int var12 = var19.length;

            for(int var23 = 0; var23 < var12; ++var23) {
                zzt var24 = var21[var23];
                SparseArray var15;
                if ((var15 = (SparseArray)var20.get(var24.zzdk)) == null) {
                    var15 = new SparseArray();
                    var20.append(var24.zzdk, var15);
                }

                var15.append(var24.zzdl, var24);
            }

            SparseArray<Element> var22 = new SparseArray(var20.size());
            /*
            for(var12 = 0; var12 < var20.size(); ++var12) {
                var22.append(var20.keyAt(var12), new Element());//TextBlock((SparseArray)var20.valueAt(var12)));


            }
            */
            return var22;
        }
    }

    public final boolean isOperational() {
        return this.zzda.isOperational();
    }

    public final void release() {
        super.release();
        this.zzda.zzg();
    }

    public static class Builder {
        private Context mContext;
        private zzaa zzdb;

        public Builder(Context var1) {
            this.mContext = var1;
            this.zzdb = new zzaa();
        }
/*
        public ElementRecognizer build() {
            zzz var1 = new zzz(this.mContext, this.zzdb);
            return new ElementRecognizer(var1, );
        }
    }*/
}

}
