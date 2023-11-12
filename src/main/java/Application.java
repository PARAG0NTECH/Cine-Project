import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import entities.*;
import repositories.connections.ConnectionMySql;
import repositories.mysql.*;
import utils.Util;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Application {

    private final static ComputerRepository COMPUTER_REPOSITORY = new ComputerRepository(new ConnectionMySql());
    private final static CpuRepository CPU_REPOSITORY = new CpuRepository(new ConnectionMySql());
    private final static DiskRepository DISK_REPOSITORY = new DiskRepository(new ConnectionMySql());
    private final static NetworkRepository NETWORK_REPOSITORY = new NetworkRepository(new ConnectionMySql());
    private final static AlertRepository ALERT_REPOSITORY = new AlertRepository(new ConnectionMySql());
    private final static StatisticsRepository STATISTICS_REPOSITORY = new StatisticsRepository();

    private static int i = 0;
    private static int logCounter = 1;

    public static void main(String[] args) throws Exception {
        if(args.length > 0){
            if(args.length > 2){
                String SETUP = args[0];
                if(SETUP.toUpperCase().equals("SETUP")){
                    setup();
                }
                String USER_DATABASE = args[1];
                String PASSWORD_DATABASE = args[2];
                STATISTICS_REPOSITORY.setConnectionMySql(new ConnectionMySql(USER_DATABASE, PASSWORD_DATABASE));
            } else {
                String USER_DATABASE = args[0];
                String PASSWORD_DATABASE = args[1];
                STATISTICS_REPOSITORY.setConnectionMySql(new ConnectionMySql(USER_DATABASE, PASSWORD_DATABASE));
            }
        }
        Statistics statistics = new Statistics();
        Util.setInterval(() -> {
            fillFields(statistics);

            STATISTICS_REPOSITORY.save(statistics);

            if(i == 10){
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

        DISK_REPOSITORY.save(disk);
        CPU_REPOSITORY.save(cpu);
        COMPUTER_REPOSITORY.save(computer);

        computer = COMPUTER_REPOSITORY.findByCpuId(computer.getCpu().getId());
        Network net = new Network(computer, looca.getRede().getGrupoDeInterfaces().getInterfaces().get(0));

        NETWORK_REPOSITORY.save(net);
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

        statistics.setComputer(new Computer(COMPUTER_REPOSITORY.countComputers()));
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

        Alert alert = ALERT_REPOSITORY.findByCompany(new Company(1));

        double taxCpu = cpuUsage * (alert.getPercentualCpu() / 100);
        double taxDisk = cpuUsage * (alert.getPercentualCpu() / 100);
        double taxRam = cpuUsage * (alert.getPercentualCpu() / 100);

        if(cpuUsage >= taxCpu ||
           diskUsage >= taxDisk ||
           ramUsage >= taxRam){
            Util.sendAlert(alert);
        }

    }
}
