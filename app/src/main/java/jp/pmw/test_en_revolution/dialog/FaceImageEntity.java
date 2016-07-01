package jp.pmw.test_en_revolution.dialog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/07/01.
 */
public class FaceImageEntity {
    //  存在しない
    public static final int S_NOT_EXIST = 0;
    //  存在する.
    public static final int S_EXIST = 1;

    //  サーバーの特定ディレクトリに顔画像が存在するか.
    //  0 : 存在しない, 1 : 存在する.
    @SerializedName("face_image_existence")
    public int mFaceImageExistance;
}
