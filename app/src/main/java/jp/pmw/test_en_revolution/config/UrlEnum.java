package jp.pmw.test_en_revolution.config;

/**
 * Created by si on 2016/06/29.
 */
public enum UrlEnum {
    //  園田学園女子大学様
    SONODA("172.17.101.201","cms"),
    //  愛知工業大学
    AIT("192.168.53.76","cms");

    //  IPアドレス
    private final String mIpAddress;
    //  WEBアプリ名称
    private final String mWebAppName;

    UrlEnum(String ipAddress, String webAppName) {
        this.mIpAddress = ipAddress;
        this.mWebAppName = webAppName;
    }
    /**
     *  getIpAddressメソッド
     * */
    public String getIpAddress(){
        return this.mIpAddress;
    }
    /**
     *  getWebAppNameメソッド
     * */
    public String getWebAppName(){
        return this.mWebAppName;
    }

}
