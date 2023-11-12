package entities;

public class Alert {

    private Integer id;
    private Company company;
    private Double percentualCpu;
    private Double percentualDisk;
    private Double percentualRam;

    public Alert() {}

    public Alert(Integer id, Company company, Double percentualCpu, Double percentualDisk, Double percentualRam) {
        this.id = id;
        this.company = company;
        this.percentualCpu = percentualCpu;
        this.percentualDisk = percentualDisk;
        this.percentualRam = percentualRam;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Double getPercentualCpu() {
        return percentualCpu;
    }

    public void setPercentualCpu(Double percentualCpu) {
        this.percentualCpu = percentualCpu;
    }

    public Double getPercentualDisk() {
        return percentualDisk;
    }

    public void setPercentualDisk(Double percentualDisk) {
        this.percentualDisk = percentualDisk;
    }

    public Double getPercentualRam() {
        return percentualRam;
    }

    public void setPercentualRam(Double percentualRam) {
        this.percentualRam = percentualRam;
    }
}
