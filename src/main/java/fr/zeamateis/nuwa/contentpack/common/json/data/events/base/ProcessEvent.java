package fr.zeamateis.nuwa.contentpack.common.json.data.events.base;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IProcess;

import java.util.List;

public class ProcessEvent {
    private List<IProcess> processes;

    public List<IProcess> getProcesses() {
        return processes;
    }
}
