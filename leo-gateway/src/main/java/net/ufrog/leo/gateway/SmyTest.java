package net.ufrog.leo.gateway;

import com.alibaba.fastjson.JSON;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import net.ufrog.common.utils.Codecs;
import net.ufrog.common.utils.Cryptos;
import net.ufrog.common.utils.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-05-24
 * @since 4.0.1
 */
public class SmyTest {

    private static final String CELLPHONE   = "13564923887";

    public static void main(String[] args) {
        String host = "https://crbc.smyfinancial.com:8443/b2b-front";
        String uri = "/bdapi/users";
        String channelCode = "E18";
        String channelKey = "5c6fcdd51d6648f39e899e1658778148";
        String body = JSON.toJSONString(new RegisterRequest(CELLPHONE));
        Map<String, String> mHeader = createHeaders();

        String hmac = sign(uri, Collections.emptyMap(), mHeader, body, channelKey);
        System.out.println("================== hmac: " + hmac);
        mHeader.put("authorization", "SMY " + channelCode + ":" + hmac);
        mHeader.put("Content-Type", "application/json");

        HttpResponse response = HttpRequest.post(host + uri).header(mHeader).form(Collections.emptyMap()).body(body).send();
        System.out.println("============= response status: " + response.statusCode());
        System.out.println("============= response body: " + response.bodyText());
        response.headerNames().forEach(name -> System.out.println("============= response header " + name + ": " + response.header(name)));
    }

    private static Map<String, String> createTerminal() {
        Map<String, String> map = new HashMap<>();
        map.put("terminalType", "app");
        map.put("productType", "loan");
        map.put("productName", "smk");
        map.put("appType", "android");
        map.put("appChannel", "bdsmk1805");
        map.put("subAppChannel", "smk1");
        map.put("activity", "default");
        return map;
    }

    private static Map<String, String> createEnvironment() {
        Map<String, String> map = new HashMap<>();
        map.put("deviceUniqueId", "0");
        return map;
    }

    private static String mapToString(Map<String, String> map, String delimiter) {
        return Optional.ofNullable(map).map(m -> m.entrySet().stream().map(entry -> {
            try {
                return entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining(delimiter))).orElse("");
    }

    private static Map<String, String> createHeaders() {
        Map<String, String> map = new HashMap<>();

        map.put("X-SMY-User", "mobilePhoneNo=" + CELLPHONE + ";externalCustNo=1");
        map.put("X-SMY-Request-ID", Codecs.uuid());
        map.put("X-SMY-Auth-Timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("X-SMY-Terminal", mapToString(createTerminal(), ";"));
        map.put("X-SMY-Environment", mapToString(createEnvironment(), ";"));
        map.put("X-SMY-ChannelToken", Codecs.uuid());
        return map;
    }

    private static String sign(String uri, Map<String, String> mParam, Map<String, String> mHeaders, String body, String key) {
        StringBuilder signStr = new StringBuilder(256);
        String paramStr = mapToString(mParam, "&");
        Map<String, String> mSortedHeaders = new TreeMap<>();

        signStr.append(uri).append(Strings.empty(paramStr) ? "" : "?" + paramStr).append("\n");
        mHeaders.forEach((k, v) -> mSortedHeaders.put(k.toLowerCase(), v));
        mSortedHeaders.forEach((k, v) -> signStr.append(k).append(":").append(v.replace(" ", "")).append("\n"));
        signStr.append(body);

        System.out.println("=========== sign string:");
        System.out.println(signStr);
        return Cryptos.hmacAndBase64(signStr.toString(), key, Cryptos.HMACType.HMAC_SHA1);
    }

    /**
     *
     */
    public static class RegisterRequest {

        private String mobilePhoneNo;

        public RegisterRequest(String mobilePhoneNo) {
            this.mobilePhoneNo = mobilePhoneNo;
        }

        public String getMobilePhoneNo() {
            return mobilePhoneNo;
        }

        public void setMobilePhoneNo(String mobilePhoneNo) {
            this.mobilePhoneNo = mobilePhoneNo;
        }
    }
}
