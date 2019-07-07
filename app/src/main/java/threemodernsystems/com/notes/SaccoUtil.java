package threemodernsystems.com.notes;

/**
 * Created by ekaranja on 11/26/17.
 */

public  class SaccoUtil {
   // private  static String url="http://178.62.10.52:8080/SalonApp/"; //production url
//   private  static String url="https://f3888f56.ngrok.io/SaccoManagement/";//development url
   private  static String url="https://2f73c5cc.ngrok.io/SaccoManagement/";//development url

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        SaccoUtil.url = url;
    }
}
