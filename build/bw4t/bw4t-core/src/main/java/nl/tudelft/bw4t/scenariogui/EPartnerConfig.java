package nl.tudelft.bw4t.scenariogui;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

/**
 * Information about an EPartner to be created by the server.
 */
public final class EPartnerConfig implements Serializable {
    public static final String DEFAULT_GOAL_FILENAME_REFERENCE = "epartner";
    public static final String DEFAULT_GOAL_FILENAME = "epartner.goal";
    private static final long serialVersionUID = -8235429942348638859L;
    private String name = "E-Partner";
    private int amount = 1;
    private String epartnerReferenceName = "";
    private String epartnerGoalFileName = "*.goal";
    private boolean gps = false;
    private boolean forgetmenot = false;

    public String getEpartnerName() {
        return name;
    }

    @XmlElement
    public void setEpartnerName(String name) {
        this.name = name;
    }

    public int getEpartnerAmount() {
        return amount;
    }

    @XmlElement
    public void setEpartnerAmount(int amount) {
        this.amount = amount;
    }

    public boolean isGps() {
        return gps;
    }

    @XmlElement
    public void setGps(boolean gps) {
        this.gps = gps;
    }

    public boolean isForgetMeNot() {
        return forgetmenot;
    }

    @XmlElement
    public void setForgetMeNot(boolean fmn) {
        this.forgetmenot = fmn;
    }

    /**
     * Returns all the properties as a String.
     *
     * @return All the EPartnerConfig properties.
     */
    public String ecToString() {
        return name + amount + gps + forgetmenot + epartnerGoalFileName + epartnerReferenceName;
    }

    public String getReferenceName() {
        return epartnerReferenceName;
    }

    @XmlElement
    public void setReferenceName(String _referenceName) {
        this.epartnerReferenceName = _referenceName;
    }

    public String getFileName() {
        return epartnerGoalFileName;
    }

    @XmlElement
    public void setFileName(String _fileName) {
        this.epartnerGoalFileName = _fileName;
    }

}
