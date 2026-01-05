//package com.example.finalyearproject;
//
//import com.cloudinary.Cloudinary;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class CloudinaryUploadHelper {
//
//    private static Cloudinary cloudinary;
//
//    public static Cloudinary getCloudinary() {
//        if (cloudinary == null) {
//
//            Map<String, Object> config = new HashMap<>();
//            config.put("cloud_name", "ddmzfmizl");
//            config.put("api_key", "212475491761777");
//            config.put("api_secret", "6qobWnjujBMoa8cs-XA8HfsO4D8");
//            config.put("secure", true);
//
//            cloudinary = new Cloudinary(config);
//        }
//        return cloudinary;
//    }
//}


package com.example.finalyearproject;

import android.content.Context;
import com.cloudinary.android.MediaManager;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryUploadHelper {

    private static boolean initialized = false;

    public static void init(Context context) {
        if (initialized) return;

        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "ddmzfmizl"); // ✅ only cloud name
        config.put("secure", true);

        MediaManager.init(context, config);
        initialized = true;
    }
}
