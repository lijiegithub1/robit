package cn.jd.pojo.TextContent;

import java.util.List;

/**
 * Created by lijie32 on 2018/7/28.
 */
public class TulingResp {
    private Intent intent;
    private List<Results> results;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public static class Intent{
        private String code;
        private String intentName;
        private String actionName;
        private Parameters parameters;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIntentName() {
            return intentName;
        }

        public void setIntentName(String intentName) {
            this.intentName = intentName;
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public Parameters getParameters() {
            return parameters;
        }

        public void setParameters(Parameters parameters) {
            this.parameters = parameters;
        }
    }
    public static class Parameters{
        private String nearby_place;

        public String getNearby_place() {
            return nearby_place;
        }

        public void setNearby_place(String nearby_place) {
            this.nearby_place = nearby_place;
        }
    }
    public static class Results{
        private String groupType;
        private String resultType;
        private Values values;

        public String getGroupType() {
            return groupType;
        }

        public void setGroupType(String groupType) {
            this.groupType = groupType;
        }

        public String getResultType() {
            return resultType;
        }

        public void setResultType(String resultType) {
            this.resultType = resultType;
        }

        public Values getValues() {
            return values;
        }

        public void setValues(Values values) {
            this.values = values;
        }
    }


    public static class Values{
        private String url;
        private String text;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @Override
    public String toString() {
        return "TulingResp{" +
                "intent=" + intent +
                ", results=" + results +
                '}';
    }
}
