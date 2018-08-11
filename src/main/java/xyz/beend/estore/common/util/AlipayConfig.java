package xyz.beend.estore.common.util;

import java.io.FileWriter;
import java.io.IOException;

/* *
 * 支付宝付款设置
 *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016091800542565";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMzU6Kq+J5+cQeOR9OBQEjeio5EvMc+D4gTdA8Wj9IWBTL0kRgxw0ScJ3tYpBC6esoVkpt8Fd1heO3AYqQEHm+93KD6NU3O2eZhv+PCuJO5H522pKR2H3NmtSwn8E0a/JUSi9ueuGLCMOljYJnaoLRvyby7OkLNonPPkdRU+RWZB3IccJv1dd5x+XHf9ugKO34m1ZgptlbKm5n4qAIHkG+F42I208XaYETvl0Jlq3mmQOtpT+4vMDVNl32s4HtIv0RMhzwgxYZkJoktL5aeFv/az3kPm4l0931Rkqz7X9fts+B2uXIkTPwtraVoRPVwrMPs7lMdln0A4D7PVc6DrPJAgMBAAECggEAEXFx1H4czY2ivTfIaAr4rCN7TZzKX4FleO0K6h8oDddTpDiFXhPqy6kF952pT5DZICMoj1wiCZ4vqsFXICfaXU0cc7kJiREj8YIm8NqhVLK79A957vWKCEmxJbx03ACvgb+7Bo3UmTvZuQ3B5Pvt/vwWYCv3Zvs3sWP+w9WHySfodYMaCthWceUHbxfzN6ZoqBIC+GGl4GzAuaHvM09CONwq/PdWVodCyXNa++y18Ry4IrnQe/5NZt4AuOo2v6S8/h7ngxNUuPPnh7M60c5CUsYEsANMQyvj5aDwDdBQDPW/WA1OFOdFg8m2P4j7HEdRZQRxn5ljDo0pJ8HJHKQdsQKBgQDcM9gqz6lQah1kpamUG8MmySeK3Ntth39e6PgtfBCwb1DoBTqVL+sWlCVrMp+XlUe0behDiX2C47SGFW8Ix+Y3TxDbNEs+Ir+LP/lPzFHLqO1xi9E26YT3WKfLWxp1NNTY/OFLP6czjFDVIpQMjToTuCx6xl7edVwqsOCiR1q6BQKBgQCjsQ4EyaAMRbH3hvHb8jNBepniFB+6FZx0+1kIJ2RC0sirV9pCuK7WcCSM1lAKT1eDazCCCiRNUz2PZJCBSPJW3kpSWkmyTqRlJKcsLnOfF+RCZXscvBkzkAMPSzEBnTBW4mmQ4TigaPZ7B22unCv+pkQoWy2AeoQoYkvHYVaJ9QKBgQCcXqiUBjFa7HABB+87QNe9VD0jTCD6T60wbcnHKwIC+JmAmi32wTd1KjDERge0sgpgY2f7hcg+SYrpaJEXsDSYdCopL6J69lwE+6IEe7heIpCzADHKh9undip5qzFHqAO6LyGhuQr3/R1trQdrPmLOnAviB7pYyQhxPHfYZUlVLQKBgEUp6310X+6viBnJZ+oCNUwGbHzM+fbbaMc46I58bm2ZuHZFYZBtjlpG+fqUFy+0BIsfwFhBxsLxWAJ9gF1QIC06MXcIdTkk52thCE2vtuTS6HxxeopSo+dYgyJ0J9nBvAoKEobH0vBcSUEEi4CKtkAbqpLOq7QskCisF86/vxv9AoGAP3c0MK2LqHr1TyUQ7Et7sjY5K3Q6OTb4rTtD0mT4ggbuKCAErCgYvYV2ruPdmaq1amJqOWFVu8Rp19wuXFb7xgBY3Y3yvJXmK5N/17ppFGpxdE60fnmeN2gKxjnkUlvy85OBvJThBkxJ4YVNRE5MfFVBCoGDYnmS5cYsCFcczUY=";
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxOj3w4Tws6LX0k6eozfeLFsiAgw7+2oaqwOrcv+QSvJHGxfy7c1VsT94KTCDjbs2j7ctkqxFJGDSwxMkxd+GabqQ13ztYfo2FE1h7YQW1bKFkOQyRlU0Unv707lt7eZWia2ESb+LkGbsY9eefiswJZY226PcvNl8v1DVuNyBqopoEIYCLK0ve+xdoR00Vfa2NamGmVFtqWdivrC6uhMBH7dHhCCtvxcChZfL1DfpZBf6WCJ8CSZohVLMJCf13EkRdsi7BBkqMUL/Y1B1oYsEr5fjlJoJhBvP7aKwwxXy6nqDxxm8DP3iBMln2a6SqbnEUvSqY0cxjNInLhe0RcKiHwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "https://estore.beend.xyz/superUser/order/notifyForPay";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "https://estore.beend.xyz/superUser/order/returnForPay";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

