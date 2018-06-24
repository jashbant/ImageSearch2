package com.search.image.imagesearch.com.search.utility;

/**
 * Created by jashbantkumar.singh on 24/06/18.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os) {

        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            len = is.read(buffer);
            while (len != -1) {
                os.write(buffer, 0, len);
                len = is.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}