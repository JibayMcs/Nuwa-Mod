package fr.zeamateis.nuwa.contentpack.common.json.data.events;

/**
 * Reprensentation of Json {@link api.contentpack.json.process.IProcess} object
 *
 * @author ZeAmateis
 */
public class ProcessObject {

    private String processClass;
    private String registryName;

    public ProcessObject(String processClass) {
        this.processClass = processClass;
    }

    public String getProcessClass() {
        return processClass;
    }

    public String getRegistryName() {
        return registryName;
    }
}
