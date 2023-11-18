import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import entities.*;
import repositories.connections.ConnectionMySql;
import repositories.connections.ConnectionSqlServer;
import repositories.mysql.*;
import utils.Util;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Application {

    private final static ComputerRepository COMPUTER_REPOSITORY = new ComputerRepository();
    private final static CpuRepository CPU_REPOSITORY = new CpuRepository();
    private final static DiskRepository DISK_REPOSITORY = new DiskRepository();
    private final static NetworkRepository NETWORK_REPOSITORY = new NetworkRepository();
    private final static AlertRepository ALERT_REPOSITORY = new AlertRepository();
    private final static StatisticsRepository STATISTICS_REPOSITORY = new StatisticsRepository();
    private static int i = 0;
    private static int logCounter = 1;

    public static void main(String[] args) {
        if(args.length > 0){
            setup();
        }
        Statistics statistics = new Statistics();
        Util.setInterval(() -> {
            fillFields(statistics);

            STATISTICS_REPOSITORY.save(statistics, new ConnectionSqlServer());
            STATISTICS_REPOSITORY.save(statistics, new ConnectionMySql());
            System.out.println("Deveria ter salvado");
            if(i == 15){
                Util.createTextFileInRootDirectory(statistics, "logs" + logCounter + Instant.now());
                logCounter++;
                i = 0;
            }
            i++;

        }, 2, TimeUnit.SECONDS);
    }

    // ATENÇÃO: rode esse método apenas uma vez na main
    public static void setup(){
        Looca looca = new Looca();
        Computer computer = new Computer();
        Cpu cpu = new Cpu(looca.getProcessador().getId(), (looca.getProcessador().getNome()));
        Disk disk = new Disk();
        for (Disco d : looca.getGrupoDeDiscos().getDiscos()){
            disk.setModel(d.getModelo());
            disk.setId(d.getSerial());
        }
        computer.setHostname(looca.getRede().getParametros().getHostName());
        computer.setSystemInfo(looca.getSistema().getSistemaOperacional());
        computer.setMaker(looca.getSistema().getFabricante());
        computer.setDisk(disk);
        computer.setCpu(cpu);
        computer.setCompany(new Company(1));

        DISK_REPOSITORY.save(disk, new ConnectionSqlServer());
        DISK_REPOSITORY.save(disk, new ConnectionMySql());

        CPU_REPOSITORY.save(cpu, new ConnectionSqlServer());
        CPU_REPOSITORY.save(cpu, new ConnectionMySql());

        COMPUTER_REPOSITORY.save(computer, new ConnectionSqlServer());
        COMPUTER_REPOSITORY.save(computer, new ConnectionMySql());

        computer = COMPUTER_REPOSITORY.findByCpuId(computer.getCpu().getId(), new ConnectionSqlServer());
        Network net = new Network(computer, looca.getRede().getGrupoDeInterfaces().getInterfaces().get(0));

        NETWORK_REPOSITORY.save(net, new ConnectionSqlServer());
        NETWORK_REPOSITORY.save(net, new ConnectionMySql());
    }

    private static double calculateGigaBytes(double value) {
        return value / 1024 / 1024 / 1024;
    }

    private static void fillFields(Statistics statistics) {
        Looca looca = new Looca();

        double cpuUsage = looca.getProcessador().getUso();
        double ramUsage = calculateGigaBytes(looca.getMemoria().getEmUso().doubleValue());
        double ramAvaliable = calculateGigaBytes(looca.getMemoria().getDisponivel().doubleValue());
        double ramTotal = calculateGigaBytes(looca.getMemoria().getTotal().doubleValue());
        double diskTotal = calculateGigaBytes(looca.getGrupoDeDiscos().getTamanhoTotal().doubleValue());
        double diskUsage = 0.0;

        statistics.setComputer(new Computer(COMPUTER_REPOSITORY.countComputers(new ConnectionSqlServer())));
        statistics.setTemperature(looca.getTemperatura().getTemperatura());
        statistics.setCpuUsage(cpuUsage);
        statistics.setRamUsage(ramUsage);
        statistics.setRamAvailable(ramAvaliable);
        statistics.setRamTotal(ramTotal);
        statistics.setDiskTotal(diskTotal);
        for (Disco disco : looca.getGrupoDeDiscos().getDiscos()) {
            diskUsage = (double) disco.getBytesDeEscritas();
            statistics.setDiskUsage(calculateGigaBytes(diskUsage));
        }

        sendAlerts(cpuUsage, diskUsage, ramUsage);
    }

    private static void sendAlerts(double cpuUsage, double diskUsage, double ramUsage) {

        Alert alert = ALERT_REPOSITORY.findByCompany(new Company(1), new ConnectionSqlServer());

        double taxCpu = cpuUsage * (alert.getPercentualCpu() / 100);
        double taxDisk = diskUsage * (alert.getPercentualCpu() / 100);
        double taxRam = ramUsage * (alert.getPercentualCpu() / 100);

        if(cpuUsage >= taxCpu ||
           diskUsage >= taxDisk ||
           ramUsage >= taxRam){
            Util.sendAlert(taxCpu, taxDisk, taxRam, cpuUsage, diskUsage, ramUsage);
        }

    }
}
