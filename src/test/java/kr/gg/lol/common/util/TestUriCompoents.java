package kr.gg.lol.common.util;

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public final class TestUriCompoents {

    private static final String DELIMITER = "://";
    private static final String HOST_DELIMITER = "/";
    private static final String PATH_DELIMITER = "\\?";
    private static final String PARAMETER_DELIMITER = "&";
    private static final String PARAMETER_KEY_VALUE_DELIMITER = "=";

    private final String host;
    private final String port;
    private final String path;
    private Map<String, String> params;

    private TestUriCompoents(String host, String port, String path, Map<String, String> params){
        this.host = host;
        this.port = port;
        this.path = path;
        this.params = params;
    }

    public static TestUriCompoents generate(String url){
        String[] sp1 = url.split(DELIMITER);
        Map<String, String> params = new HashMap<>();
        String host = null, port = null, path = null;

        if(sp1.length == 2){
            String[] sp2 = sp1[1].split(HOST_DELIMITER);
            if(sp2.length == 2){
                host = sp1[0] + DELIMITER + sp2[0];
                port = sp2[0].split(":")[1];
                String[] sp3 = sp2[1].split(PATH_DELIMITER);
                path = sp3[0];
                String[] sp4 = sp3[1].split(PARAMETER_DELIMITER);

                for(String param : sp4){
                    String[] s = param.split(PARAMETER_KEY_VALUE_DELIMITER);
                    params.put(s[0], s[1]);
                }

            }
        }
        return new TestUriCompoents(host, port, path, Collections.unmodifiableMap(params));
    }

}
