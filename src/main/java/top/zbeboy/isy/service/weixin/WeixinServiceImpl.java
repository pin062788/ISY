package top.zbeboy.isy.service.weixin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import top.zbeboy.isy.service.util.MD5Utils;
import top.zbeboy.isy.web.util.weixin.AesException;
import top.zbeboy.isy.web.util.weixin.WXBizMsgCrypt;
import top.zbeboy.isy.web.vo.weixin.WeixinVo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

/**
 * Created by zbeboy on 2016/11/15.
 */
@Slf4j
@Service("weixinService")
public class WeixinServiceImpl implements WeixinService {

    @Override
    public boolean checkSignature(WeixinVo weixinVo) {
        String[] arr = new String[]{weixinVo.getToken(), weixinVo.getTimestamp(), weixinVo.getNonce()};
        Arrays.sort(arr);
        String newArr = MD5Utils.sha_1(arr[0] + arr[1] + arr[2]);
        return StringUtils.equals(weixinVo.getSignature(), newArr);
    }

    @Override
    public String encryptMsg(String msg, WeixinVo weixinVo) {
        String afterMsg = "";
        try {
            String encodingAesKey = weixinVo.getEncodingAESKey();
            String token = weixinVo.getToken();
            String timestamp = weixinVo.getTimestamp();
            String nonce = weixinVo.getNonce();
            String appId = weixinVo.getAppId();
            WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            afterMsg = pc.encryptMsg(msg, timestamp, nonce);
        } catch (AesException e) {
            log.error("Encrypt weixin msg is exception {}", e);
        }
        return afterMsg;
    }

    @Override
    public String decryptMsg(String msg, WeixinVo weixinVo) {
        String afterMsg = "";
        try {
            String encodingAesKey = weixinVo.getEncodingAESKey();
            String token = weixinVo.getToken();
            String timestamp = weixinVo.getTimestamp();
            String nonce = weixinVo.getNonce();
            String appId = weixinVo.getAppId();
            WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(msg);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);
            Element root = document.getDocumentElement();
            NodeList nodelist1 = root.getElementsByTagName("Encrypt");
            NodeList nodelist2 = root.getElementsByTagName("MsgSignature");
            String encrypt = nodelist1.item(0).getTextContent();
            String msgSignature = nodelist2.item(0).getTextContent();
            String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
            String fromXML = String.format(format, encrypt);
            afterMsg = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
        } catch (SAXException | ParserConfigurationException | AesException | IOException e) {
            log.error("Decrypt weixin msg is exception {}", e);
        }
        return afterMsg;
    }
}
