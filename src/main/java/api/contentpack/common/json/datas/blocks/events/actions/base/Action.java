package api.contentpack.common.json.datas.blocks.events.actions.base;

public class Action {

    private IProcess process;

    public Action(IProcess process) {
        this.process = process;
    }


    public IProcess getProcess() {
        return process;
    }
}
