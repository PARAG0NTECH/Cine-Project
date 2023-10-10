package repositories;

import entities.Totem;

public class TotemRepository {

    private ConnectionMySql connectionMySql;

    public TotemRepository(ConnectionMySql connectionMySql){
        this.connectionMySql = connectionMySql;
    }

    public void save(Totem totem){
        String command = "INSERT INTO totem (ativo, cpuTotal, ramTotal, discoTotal) VALUES (%d, %f, %f, %f)"
                .formatted(totem.getStatus(), totem.getCpuPercentage(),
                        totem.getRamPercentage(), totem.getDiskPercentage());

    }
}
