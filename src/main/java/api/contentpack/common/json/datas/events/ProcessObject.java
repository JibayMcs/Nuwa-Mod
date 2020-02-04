package api.contentpack.common.json.datas.events;

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
