package com.alryada.etqan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Abd El-Sattar
 * on 2/11/2018.
 */

public class Order {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("representative_id")
    @Expose
    private String representativeId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("supervisor_latitude")
    @Expose
    private String supervisorLatitude;
    @SerializedName("supervisor_longitude")
    @Expose
    private String supervisorLongitude;
    @SerializedName("client_latitude")
    @Expose
    private String clientLatitude;
    @SerializedName("status_name_space")
    @Expose
    private Integer statusNameSpace;
    @SerializedName("client_longitude")
    @Expose
    private String clientLongitude;
    @SerializedName("home_name")
    @Expose
    private String homeName;
    @SerializedName("car_name")
    @Expose
    private String carName;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("nationality_name")
    @Expose
    private String nationalityName;
    @SerializedName("supervisor_name")
    @Expose
    private String supervisorName;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("ontime_rate")
    @Expose
    private String ontimeRate;
    @SerializedName("quality_rate")
    @Expose
    private String qualityRate;
    @SerializedName("team_rate")
    @Expose
    private String teamRate;
    @SerializedName("avg_rate")
    @Expose
    private String avgRate;
    @SerializedName("comment")
    @Expose
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(String representativeId) {
        this.representativeId = representativeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSupervisorLatitude() {
        return supervisorLatitude;
    }

    public void setSupervisorLatitude(String supervisorLatitude) {
        this.supervisorLatitude = supervisorLatitude;
    }

    public String getSupervisorLongitude() {
        return supervisorLongitude;
    }

    public void setSupervisorLongitude(String supervisorLongitude) {
        this.supervisorLongitude = supervisorLongitude;
    }

    public String getClientLatitude() {
        return clientLatitude;
    }

    public void setClientLatitude(String clientLatitude) {
        this.clientLatitude = clientLatitude;
    }

    public String getClientLongitude() {
        return clientLongitude;
    }

    public void setClientLongitude(String clientLongitude) {
        this.clientLongitude = clientLongitude;
    }

    public Integer getStatusNameSpace() {
        return statusNameSpace;
    }

    public void setStatusNameSpace(Integer statusNameSpace) {
        this.statusNameSpace = statusNameSpace;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNationalityName() {
        return nationalityName;
    }

    public void setNationalityName(String nationalityName) {
        this.nationalityName = nationalityName;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOntimeRate() {
        return ontimeRate;
    }

    public void setOntimeRate(String ontimeRate) {
        this.ontimeRate = ontimeRate;
    }

    public String getQualityRate() {
        return qualityRate;
    }

    public void setQualityRate(String qualityRate) {
        this.qualityRate = qualityRate;
    }

    public String getTeamRate() {
        return teamRate;
    }

    public void setTeamRate(String teamRate) {
        this.teamRate = teamRate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(String avgRate) {
        this.avgRate = avgRate;
    }
}
