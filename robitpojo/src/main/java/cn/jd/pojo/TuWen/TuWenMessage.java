package cn.jd.pojo.TuWen;

import cn.jd.pojo.BaseMessage;

import java.util.List;

/**
 * Created by lijie32 on 2018/7/29.
 */
public class TuWenMessage extends BaseMessage {
    private String ArticleCount;
    private List<Item> items;

    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>\n");
        sb.append("<ToUserName><![CDATA["+ToUserName+"]]></ToUserName>").append("\n");
        sb.append("<FromUserName><![CDATA["+FromUserName+"]]></FromUserName>").append("\n");
        sb.append("<CreateTime><![CDATA["+CreateTime+"]]></CreateTime>").append("\n");
        sb.append("<MsgType><![CDATA["+MsgType+"]]></MsgType>").append("\n");
        sb.append("<ArticleCount>"+ArticleCount+"</ArticleCount>").append("\n");
        sb.append("<Articles>\n");
        if (items != null) {
            for (Item item : items) {
                sb.append("<item>\n");
                sb.append(item.toXml());
                sb.append("</item>\n");
            }
        }
        sb.append("</Articles>\n");
        sb.append("</xml>\n");
        return sb.toString();
    }





    public static class Item{
        private String title;
        private String description;
        private String picUrl;
        private String url;


        public String toXml() {
            StringBuilder sb = new StringBuilder();
            sb.append("<Title><![CDATA[").append(title).append("]]></Title>").append("\n");
            sb.append("<Description><![CDATA[").append(description).append("]]></Description>").append("\n");
            sb.append("<PicUrl><![CDATA[").append(picUrl).append("]]></PicUrl>").append("\n");
            sb.append("<Url><![CDATA[").append(url).append("]]></Url>").append("\n");
            return sb.toString();
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public String getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(String articleCount) {
        ArticleCount = articleCount;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
