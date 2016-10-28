package org.springframework.cloud.stream.app.netatmo.source;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class NetatmoSourceProperties {

    private String appId;
    private String appSecret;
    private String username;
    private String userhost;
    private String password;
    private double latSW;
    private double lonSW;
    private double latNE;
    private double lonNE;
    private List<String> requiredData;
    private Boolean filter;

    /**
     * @return the appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId
     *            the appId to set
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return the appSecret
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * @param appSecret
     *            the appSecret to set
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the userhost
     */
    public String getUserhost() {
        return userhost;
    }

    /**
     * @param userhost
     *            the userhost to set
     */
    public void setUserhost(String userhost) {
        this.userhost = userhost;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the latSW
     */
    public double getLatSW() {
        return latSW;
    }

    /**
     * @param latSW the latSW to set
     */
    public void setLatSW(double latSW) {
        this.latSW = latSW;
    }

    /**
     * @return the lonSW
     */
    public double getLonSW() {
        return lonSW;
    }

    /**
     * @param lonSW the lonSW to set
     */
    public void setLonSW(double lonSW) {
        this.lonSW = lonSW;
    }

    /**
     * @return the latNE
     */
    public double getLatNE() {
        return latNE;
    }

    /**
     * @param latNE the latNE to set
     */
    public void setLatNE(double latNE) {
        this.latNE = latNE;
    }

    /**
     * @return the lonNE
     */
    public double getLonNE() {
        return lonNE;
    }

    /**
     * @param lonNE the lonNE to set
     */
    public void setLonNE(double lonNE) {
        this.lonNE = lonNE;
    }

    /**
     * @return the requiredData
     */
    public List<String> getRequiredData() {
        return requiredData;
    }

    /**
     * @param requiredData the requiredData to set
     */
    public void setRequiredData(List<String> requiredData) {
        this.requiredData = requiredData;
    }

    /**
     * @return the filter
     */
    public Boolean getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(Boolean filter) {
        this.filter = filter;
    }
}
