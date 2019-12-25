package com.nitish.demos.ui;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitish Sharma on 25 Dec, 2019
 */
public class AppUtils {
    private static final String TAG ="AppUtils" ;
    public static final String SEED_GRADIENTS = "languages_list.json";
    private static String mFinalPath;
    public  static   List<LanguageModel> getListOfLanguageCode(Context mContext){
        Gson mGson = new Gson();
        Type type = $Gson$Types.newParameterizedTypeWithOwner(null,
                List.class, LanguageModel.class);
        try {
            List<LanguageModel> questionList = mGson
                    .fromJson(loadJSONFromAsset(mContext,
                            SEED_GRADIENTS), type);
            return  questionList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<LanguageModel>();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }


}
