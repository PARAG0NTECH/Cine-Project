package entities;

import entities.enums.TotemStatus;

public class Totem {

    private Integer id;
    private TotemStatus status;
    private Double cpuPercentage;
    private Double ramPercentage;
    private Double diskPercentage;

    public Totem(){}

    public Totem(Integer id, TotemStatus status, Double cpuPercentage, Double ramPercentage, Double diskPercentage) {
        this.id = id;
        this.status = status;
        this.cpuPercentage = cpuPercentage;
        this.ramPercentage = ramPercentage;
        this.diskPercentage = diskPercentage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TotemStatus getStatus() {
        return status;
    }

    public void setStatus(TotemStatus status) {
        this.status = status;
    }

    public Double getCpuPercentage() {
        return cpuPercentage;
    }

    public void setCpuPercentage(Double cpuPercentage) {
        this.cpuPercentage = cpuPercentage;
    }

    public Double getRamPercentage() {
        return ramPercentage;
    }

    public void setRamPercentage(Double ramPercentage) {
        this.ramPercentage = ramPercentage;
    }

    public Double getDiskPercentage() {
        return diskPercentage;
    }

    public void setDiskPercentage(Double diskPercentage) {
        this.diskPercentage = diskPercentage;
    }
}
