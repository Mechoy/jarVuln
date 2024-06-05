import com.alibaba.fastjson.JSONObject;

/* loaded from: PrintModel.class */
public class PrintModel {
    private Long reportId;
    private Integer reportType;
    private Long customId;
    private JSONObject params;

    public void setReportId(final Long reportId) {
        this.reportId = reportId;
    }

    public void setReportType(final Integer reportType) {
        this.reportType = reportType;
    }

    public void setCustomId(final Long customId) {
        this.customId = customId;
    }

    public void setParams(final JSONObject params) {
        this.params = params;
    }

    public Long getReportId() {
        return this.reportId;
    }

    public Integer getReportType() {
        return this.reportType;
    }

    public Long getCustomId() {
        return this.customId;
    }

    public JSONObject getParams() {
        return this.params;
    }
}
