package cn.jd.service.impl;


import cn.jd.service.ProcessRequest;
import cn.jd.service.utils.ParseUtils;
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
    private static Logger logger = LogManager.getLogger(ProcessRequestImpl.class);
    //处理的核心代码块
    public String process(HttpServletRequest request) {
        //1)把请求转化为map
        try {
            String inputStream = request.getInputStream().toString();
            logger.info("未转换前的 inputStream ====>" + inputStream);
            Map<String, String> parseMap = ParseUtils.parseXml(request);
            logger.info("传来的参数是====>" + parseMap);
            //2）把其中的参数进行取出
            String msgType = parseMap.get("MsgType");//文档的类型，可以用作后面的判断
            String processxml = "";
            //3）把通用的一些东西进行返回 ，其他的进行判断 ，然后在填其中需要的东西，所以这里最重要的是要把返回的对象给完善好
            //文本消息处理
            String content = parseMap.get("Content");
            if(StringUtils.isNotBlank(msgType) && ParseUtils.REQ_MESSAGE_TYPE_TEXT.equals(msgType) ){
               /* if("音乐".equals(content)){*/
                    System.out.println("进到音乐中 ");
                    processxml = musicProcess.process(parseMap);
               /* }*//*else if(content.startsWith("图片@")){
                    content = content.substring(content.indexOf("@")+1);
                    parseMap.put("Content", content);
                    processxml = imageProcess.process(parseMap);
                }else if(content.startsWith("翻译") && content.contains(">")){//翻译
                    processxml = translationService.process(parseMap);
                }else if(content.startsWith("天气") && content.contains("@")){//翻译
                    processxml = weatherService.process(parseMap);
                }else{
                    processxml = contentProcess.process(parseMap);
                }*/
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
