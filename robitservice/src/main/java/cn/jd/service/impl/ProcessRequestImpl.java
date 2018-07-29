package cn.jd.service.impl;


import cn.jd.service.ProcessRequest;
import cn.jd.service.utils.ParseUtils;;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2018/07/08.
 */
@Service(value = "processRequestImpl")
public class ProcessRequestImpl implements ProcessRequest {
    @Autowired
    private MusicProcessImpl musicProcess;

    @Autowired
    private TulingRobotProcesImpl tulingRobotProcesImpl;

    @Autowired
    private WeatherProcesImpl weatherProcesImpl;


    private static Logger logger = LogManager.getLogger(ProcessRequestImpl.class);
    //处理的核心代码块
    public String process(HttpServletRequest request) {
        try {
            String inputStream = request.getInputStream().toString();
            Map<String, String> parseMap = ParseUtils.parseXml(request);
            String msgType = parseMap.get("MsgType");//文档的类型，可以用作后面的判断
            String processxml = "";
            //文本消息处理
            String content = parseMap.get("Content");
            if(StringUtils.isNotBlank(msgType) && ParseUtils.REQ_MESSAGE_TYPE_TEXT.equals(msgType) ){
               if(content.startsWith("音乐@")){
                    content = content.substring(content.indexOf("@")+1);
                   parseMap.put("Content", content);
                    processxml = musicProcess.process(parseMap);
                }else if(content.startsWith("天气@")){
                    content = content.substring(content.indexOf("@")+1);
                    parseMap.put("Content", content);
                    processxml = weatherProcesImpl.process(parseMap);
                }/*else if(content.startsWith("翻译") && content.contains(">")){//翻译
                    processxml = translationService.process(parseMap);
                }else if(content.startsWith("天气") && content.contains("@")){//天气
                    processxml = weatherService.process(parseMap);
                }*/else{//图灵机器人
                    parseMap.put("reqType","0");
                    processxml = tulingRobotProcesImpl.process(parseMap);
                }
            }/*else if(StringUtils.isNotBlank(msgType) && ParseUtils.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)){
                processxml = imageProcess.process(parseMap);
            }else if(StringUtils.isNotBlank(msgType) && ParseUtils.REQ_MESSAGE_TYPE_VOICE.equals(msgType)){
                processxml = videoProcess.process(parseMap);
            }else if(StringUtils.isNotBlank(msgType) && ParseUtils.REQ_MESSAGE_TYPE_EVENT.equals(msgType)){
                //取出自定义菜单的类型
                String event = parseMap.get("Event");
                //
                switch (event) {
                    case ParseUtils.EVENT_TYPE_CLICK://点击
                        //根据key去处理这些问题
                        processxml = clickProcess(parseMap);
                        break;
                    case "SCAN"://扫描事件
                        //根据key去处理这些问题
                        processxml = scanProcess(parseMap);
                        break;

                    default:
                        break;
                }

            }*/

            return processxml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
