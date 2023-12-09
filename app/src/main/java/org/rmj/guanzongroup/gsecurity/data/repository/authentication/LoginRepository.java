package org.rmj.guanzongroup.gsecurity.data.repository.authentication;

import io.reactivex.rxjava3.core.Observable;

import org.rmj.guanzongroup.gsecurity.data.remote.param.LoginParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.LoginBaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;

import javax.inject.Inject;

public class LoginRepository {

    private ApiService apiService;

    @Inject
    public LoginRepository(
            ApiService apiService
    ) {
        this.apiService = apiService;
    }

    // Sign in user through mpin
    public Observable<BaseResponse> login(String mpin) {
        return apiService.signIn(mpin);
    }

    // Sign in user through mpin
    public Observable<LoginBaseResponse> loginAdmin(LoginParams loginParams) {
        return apiService.loginAdmin(loginParams);
    }

//    public class testLoginLocal {
//        public static void main(String [] args){
//            String sURL = "https://restgk.guanzongroup.com.ph/security/mlogin.php";
//
//            String clientid = "GGC_BM001";
//            String productid = "gRider";
//            String imei = "GMC_SEG09";
//
//            Calendar calendar = Calendar.getInstance();
//            Map<String, String> headers =
//                    new HashMap<String, String>();
//            headers.put("Accept", "application/json");
//            headers.put("Content-Type", "application/json");
//            headers.put("g-api-id", productid);
//            headers.put("g-api-imei", imei);
//
//            headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
//            //headers.put("g-api-hash", md5Hex((String)headers.get("g-api-imei") + (String)headers.get("g-api-key")));
//            headers.put("g-api-hash", org.apache.commons.codec.digest.DigestUtils.md5Hex((String)headers.get("g-api-imei") + (String)headers.get("g-api-key")));
//            headers.put("g-api-client", clientid);
//            headers.put("g-api-user", "");
//            headers.put("g-api-log", "");
//            headers.put("g-api-token", "12312312");
//
//            //Create the parameters needed by the API
//            JSONObject param = new JSONObject();
//            param.put("user", "mikegarcia8748@gmail.com");
//            param.put("pswd", "123456");
//            //param.put("user", "bongesperida23@gmail.com");
//            //param.put("pswd", "angel1026");
//
//            JSONParser oParser = new JSONParser();
//            JSONObject json_obj = null;
//
//            String response;
//            try {
//                response = WebClient.sendHTTP(sURL, param.toJSONString(), (HashMap<String, String>) headers);
//                if(response == null){
//                    System.out.println("No Response");
//                    System.exit(1);
//                }
//
//                System.out.println(response);
//            } catch (IOException ex) {
//                Logger.getLogger(testLoginLocal.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}
