package fr.zeamateis.nuwa.contentpack.common.json.data.events;

public class ProcessObject {

    private String processClass;
    private String registryName;

    public ProcessObject(String processClass, String registryName) {
        this.processClass = processClass;
        this.registryName = registryName;
    }

    public String getProcessClass() {
        return processClass;
    }

    public String getRegistryName() {
        return registryName;
    }
}
