package api.contentpack.common.json.datas.blocks.events.base;

import api.contentpack.common.json.datas.blocks.events.processes.base.IProcess;

import java.util.List;

public class ProcessEvent {
    private List<IProcess> processes;

    public List<IProcess> getProcesses() {
        return processes;
    }
}
