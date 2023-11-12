package entities;

public class Computer {

    private Integer id;

    private String hostname;
    private String maker;
    private String systemInfo;

    private Cpu cpu;

    private Disk disk;

    private Company company;

    public Computer() {
    }

    public Computer(Integer id){
        this.id = id;
    }

    public Computer(Integer id, String hostname, String maker, String systemInfo, Cpu cpu, Disk disk, Company company) {
        this.id = id;
        this.hostname = hostname;
        this.maker = maker;
        this.systemInfo = systemInfo;
        this.cpu = cpu;
        this.disk = disk;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(String systemInfo) {
        this.systemInfo = systemInfo;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
