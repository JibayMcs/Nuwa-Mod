package fr.zeamateis.nuwa.contentpack.common.json.data.events.base;

import api.contentpack.json.process.IProcess;

import java.util.List;

/**
 * Reprensentation of Json {@link IProcess} object
 *
 * @author ZeAmateis
 */
public class ProcessEvent {
    private List<IProcess> processes;

    public List<IProcess> getProcesses() {
        return processes;
    }
}
